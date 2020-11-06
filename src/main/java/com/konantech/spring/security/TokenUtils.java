package com.konantech.spring.security;

import com.konantech.spring.domain.user.SecurityUser;
import com.konantech.spring.domain.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.compression.CompressionCodecs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Component
public class TokenUtils {

	private final String AUDIENCE_UNKNOWN = "unknown";
	private final String AUDIENCE_WEB = "web";
	private final String AUDIENCE_MOBILE = "mobile";
	private final String AUDIENCE_TABLET = "tablet";
	
	private final String ORIGIN_LOCAL = "local";

	@Value("${jwt.token.secret}")
	private String secret;

	@Value("${jwt.token.expiration}")
	private Long expiration;

	@Value("${jwt.token.origin}")
	private String origin;
	
	public String getUserIdFromToken(String token) {
		String userid;
		try {
			final Claims claims = this.getClaimsFromToken(token);
			userid = (String)claims.get("userid");
		} catch (Exception e) {
			userid = null;
		}
		return userid;
	}

	public String getUserNameFromToken(String token) {
		String username;
		try {
			final Claims claims = this.getClaimsFromToken(token);
			username = (String)claims.get("username");
		} catch (Exception e) {
			username = null;
		}
		return username;
	}
	public String getRealNameFromToken(String token) {
		String realname;
		try {
			final Claims claims = this.getClaimsFromToken(token);
			realname = (String)claims.get("realname");
		} catch (Exception e) {
			realname = null;
		}
		return realname;
	}
	public String getAgentIdFromToken(String token) {
		String agentid;
		try {
			final Claims claims = this.getClaimsFromToken(token);
			agentid = (String)claims.get("agentid");
		} catch (Exception e) {
			agentid = null;
		}
		return agentid;
	}

	public String getAuthoritiesFromToken(String token) {
		String authorities;
		try {
			final Claims claims = this.getClaimsFromToken(token);
			authorities = (String)claims.get("authorities");
		} catch (Exception e) {
			authorities = null;
		}
		return authorities;
	}

	public String getOriginFromToken(String token) {
		String origin;
		try {
			final Claims claims = this.getClaimsFromToken(token);
			origin = (String)claims.get("origin");
		} catch (Exception e) {
			origin = null;
		}
		return origin;
	}

	public Date getCreatedDateFromToken(String token) {
		Date created;
		try {
			final Claims claims = this.getClaimsFromToken(token);
			created = new Date((Long) claims.get("created"));
		} catch (Exception e) {
			created = null;
		}
		return created;
	}

	public Date getExpirationDateFromToken(String token) {
		Date expiration;
		try {
			final Claims claims = this.getClaimsFromToken(token);
			expiration = claims.getExpiration();
		} catch (Exception e) {
			expiration = null;
		}
		return expiration;
	}

	public String getAudienceFromToken(String token) {
		String audience;
		try {
			final Claims claims = this.getClaimsFromToken(token);
			audience = (String) claims.get("audience");
		} catch (Exception e) {
			audience = null;
		}
		return audience;
	}

	private Claims getClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}

	private Date generateCurrentDate() {
		return new Date(System.currentTimeMillis());
	}

	private Date generateExpirationDate() {
		return new Date(System.currentTimeMillis() + this.expiration * 1000);
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = this.getExpirationDateFromToken(token);
		return expiration.before(this.generateCurrentDate());
	}

	private String generateAudience(Device device) {
		String audience = this.AUDIENCE_UNKNOWN;
		if (device != null) {
			if (device.isNormal()) {
				audience = this.AUDIENCE_WEB;
			} else if (device.isTablet()) {
				audience = this.AUDIENCE_TABLET;
			} else if (device.isMobile()) {
				audience = this.AUDIENCE_MOBILE;
			}
		}
		return audience;
	}

	private Boolean ignoreTokenExpiration(String token) {
		String audience = this.getAudienceFromToken(token);
		return (this.AUDIENCE_TABLET.equals(audience) || this.AUDIENCE_MOBILE.equals(audience));
	}

	public String generateToken(User loginUser, Device device) {
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put("userid", loginUser.getUserId());
		claims.put("username", loginUser.getUserName());
		claims.put("realname", loginUser.getRealName());
		claims.put("agentid", loginUser.getAgentId());
		claims.put("authorities", loginUser.getAuthorities());
		claims.put("audience", this.generateAudience(device));
		claims.put("origin", this.origin);
		claims.put("created", this.generateCurrentDate());
		return this.generateToken(claims);
	}

	private String generateToken(Map<String, Object> claims) {
		return Jwts.builder()
					.setClaims(claims)
					.setExpiration(this.generateExpirationDate())
					.compressWith(CompressionCodecs.DEFLATE)
					.signWith(SignatureAlgorithm.HS512, this.secret)
					.compact();
	}

	public Boolean canTokenBeRefreshed(String token) {
		return (!(this.isTokenExpired(token)) || this.ignoreTokenExpiration(token));
	}

	public String refreshToken(String token) {
		String refreshedToken;
		try {
			final Claims claims = this.getClaimsFromToken(token);
			claims.put("created", this.generateCurrentDate());
			refreshedToken = this.generateToken(claims);
		} catch (Exception e) {
			refreshedToken = null;
		}
		return refreshedToken;
	}

	public Boolean validateToken(String token, SecurityUser userDetails) {
		
		boolean isValid = false;
		
		final String orgin = this.getOriginFromToken(token);

		if (ORIGIN_LOCAL.equals(orgin)) {
			final String username = this.getUserNameFromToken(token);
			String commaSprAuthorities = this.getAuthoritiesFromToken(token);
			Collection<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(commaSprAuthorities);
			if (userDetails != null && username.equals(userDetails.getUsername()) && !this.isTokenExpired(token)
					&& authorities != null && userDetails != null && userDetails.getAuthorities() != null 
					&& authorities.containsAll(userDetails.getAuthorities())) isValid = true;
		} else {
			if (!this.isTokenExpired(token)) isValid = true;
		}
		return isValid;
	}
}
