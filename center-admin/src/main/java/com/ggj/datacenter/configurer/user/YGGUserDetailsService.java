package com.ggj.datacenter.configurer.user;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.ggj.platform.gsf.result.PlainResult;
import com.ygg.uc.api.UserApi;
import com.ygg.uc.domain.ResourcesDomain;
import com.ygg.uc.domain.UserDomain;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by lu on 17/1/14.
 */
@Component
public class YGGUserDetailsService implements AuthenticationUserDetailsService<CasAssertionAuthenticationToken> {

    private Logger logger = LoggerFactory.getLogger(YGGUserDetailsService.class);

    @Reference
    private UserApi userApi;

    @Value("${platform_id}")
    private  String platformId;

    @Override
    public UserDetails loadUserDetails(CasAssertionAuthenticationToken token) {
        String username = token.getName();
        if (StringUtils.isBlank(username)) {
            logger.error("用户名为空");
            throw new UsernameNotFoundException("用户名为空");
        }
        PlainResult<UserDomain> result = userApi.getUserInfo(null, username, new Integer(platformId));
        UserDomain userDomain = null;
        if (result.isOk()) {
            userDomain = result.getData();
        } else {
            logger.error("获取用户接口失败");
            throw new UsernameNotFoundException("获取用户接口失败");
        }
        if (userDomain == null) {
            logger.error("用户不存在");
            throw new UsernameNotFoundException("用户不存在");
        }


        Set<GrantedAuthority> authorities = new HashSet<>();

        if (CollectionUtils.isEmpty(userDomain.getMenuList())
                && CollectionUtils.isEmpty(userDomain.getResourcesList())) {
//            logger.error("该用户无该平台相关权限");
//            throw new UsernameNotFoundException("该用户无该平台相关权限");
            authorities.add(new SimpleGrantedAuthority("/"));
        }
        List<ResourcesDomain> resourcesList = userDomain.getResourcesList();
        if (CollectionUtils.isNotEmpty(resourcesList)) {
            for (ResourcesDomain resources : resourcesList) {
                authorities.add(new SimpleGrantedAuthority(resources.getUrlKey()));
            }
        }

        String password = userDomain.getPassword();
        userDomain.setPassword(null);
        //HttpSessionUtil.setCurrentUser(userDomain);

        return new org.springframework.security.core.userdetails.User(
                username, password,
                true,//是否可用
                true,//是否过期
                true,//证书不过期为true
                true,//账户未锁定为true
                authorities);
    }
}
