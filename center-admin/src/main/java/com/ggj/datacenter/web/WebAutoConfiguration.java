/**
 * Copyright (c) 2017, Alex. All rights reserved.
 */
package com.ggj.datacenter.web;

import com.ggj.datacenter.configurer.AppHookConfiguration;
import com.ggj.platform.sentry.devops.hook.AppHookIF;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcProperties;
import org.springframework.boot.autoconfigure.web.WebMvcRegistrations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * mvc配置
 *
 * @author <a href="mailto:zhongchao@gegejia.com">zhong</a>
 * @version 1.0 2017/10/11
 * @since 1.0
 */
@Configuration
public class WebAutoConfiguration extends WebMvcAutoConfiguration.EnableWebMvcConfiguration
{

    public WebAutoConfiguration(ObjectProvider<WebMvcProperties> mvcPropertiesProvider,
                                ObjectProvider<WebMvcRegistrations> mvcRegistrationsProvider,
                                ListableBeanFactory beanFactory)
    {
        super(mvcPropertiesProvider, mvcRegistrationsProvider, beanFactory);
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");

        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }

/*    @Bean
    public AppHookIF getAppHook() {
        return new AppHookConfiguration();
    }*/
}
