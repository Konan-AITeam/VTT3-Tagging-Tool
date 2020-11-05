package com.konantech.spring.service;

import com.konantech.spring.domain.user.SecurityUser;
import com.konantech.spring.domain.user.User;
import com.konantech.spring.mapper.AuthMapper;
import com.konantech.spring.security.SHAPasswordEncoder;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class AuthService implements UserDetailsService {

    @Autowired
    private AuthMapper authMapper;

    private SHAPasswordEncoder passwordEncoder = new SHAPasswordEncoder(512);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Map<String, Object> user = authMapper.readUserByName(username);
        SecurityUser securityUser = new SecurityUser(
                MapUtils.getString(user, "username"),
                MapUtils.getString(user, "password"),
                getAuthorities(username));
        return securityUser;
    }

    public Collection<GrantedAuthority> getAuthorities(String username) {
        return authMapper.readAuthority(username);
    }

    public PasswordEncoder passwordEncoder() {
        return this.passwordEncoder;
    }

    public List<Map<String, Object>> getDuplUserChk(User user) throws Exception {
        return authMapper.getDuplUserChk(user);
    }

    public int getPutUser(Map map) throws Exception {
        return authMapper.getPutUser(map);
    }

    public List<Map<String, Object>> getRoleList() {
        return authMapper.getRoleList();
    }
}
