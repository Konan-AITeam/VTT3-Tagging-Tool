package com.konantech.spring.mapper;

import com.konantech.spring.domain.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;


@Mapper
@Repository
public interface AuthMapper {

    Map<String, Object> readUserByName(String username);

    Collection<GrantedAuthority> readAuthority(String username);

    int insertCurrentUser(Map<String, Object> data);

    List<Map<String, Object>> getDuplUserChk(User user) throws Exception;

    int getPutUser(Map map) throws Exception;

    List<Map<String, Object>> getRoleList();
}
