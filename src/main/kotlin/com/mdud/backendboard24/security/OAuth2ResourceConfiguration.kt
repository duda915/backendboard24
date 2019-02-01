package com.mdud.backendboard24.security

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter

@Configuration
@EnableResourceServer
class OAuth2ResourceConfiguration : ResourceServerConfigurerAdapter() {


    override fun configure(http: HttpSecurity?) {
        http!!.authorizeRequests().anyRequest().authenticated()
    }


}