package com.konantech.spring.domain.user;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class SecurityUser extends org.springframework.security.core.userdetails.User {

	private static final long serialVersionUID = 1L;
	
	private static boolean enabled = true;
	private static boolean accountNonExpired = true;
	private static boolean credentialsNonExpired = true;
	private static boolean accountNonLocked = true;
	
	public SecurityUser(String username, String password, boolean enabled, boolean accountNonExpired,
						boolean credentialsNonExpired, boolean accountNonLocked,
						Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}

	public SecurityUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		this(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}
}
