package com.ggj.datacenter.configurer.user;

import com.ygg.autoconfig.CasSecurityClientConfigurerAdapter;
import com.ygg.autoconfig.EnableCasSecurityClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;

@Configuration
@EnableCasSecurityClient
public class SecurityConfiguration extends CasSecurityClientConfigurerAdapter {

//    @Value("${white.urls}")
//    private String whiteUrls;

    @Bean
    public AuthenticationUserDetailsService<CasAssertionAuthenticationToken> getUserDetailService() {
        return new YGGUserDetailsService();
    }

    @Override
    public AuthenticationUserDetailsService<CasAssertionAuthenticationToken> customUserDetailsService() {
        return getUserDetailService();
    }

    @Override
    public void configure(HttpSecurity http)
            throws Exception {

//        String[] whileUrlStrs = whiteUrls.split(";");
//
//        if (StringUtils.isBlank(whiteUrls)) {
//            http.headers().frameOptions().disable()
//                    .and().authorizeRequests()
//                    // 健康检查接口, 无需验证权限
//                    .antMatchers("/api/home", "/home").permitAll()
//                    .antMatchers("/login/**").anonymous()
//                    .anyRequest().authenticated();
//        } else {
        http.headers().frameOptions().disable()
                .and().authorizeRequests()
                // 健康检查接口, 无需验证权限
                .antMatchers("/api/home", "/home").permitAll()
                .antMatchers("/login/**").anonymous()
//                    .antMatchers(whileUrlStrs).anonymous()
                .anyRequest().authenticated();
//        }
    }
}