package com.mdud.backendboard24.security

import com.mdud.backendboard24.user.BoardUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.DefaultTokenServices
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig @Autowired constructor(
        @Qualifier("userDetailsServiceImpl") private val userDetailsService: UserDetailsService
): WebSecurityConfigurerAdapter() {

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BoardUser.passwordEncoder
    }

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.userDetailsService(userDetailsService)?.passwordEncoder(passwordEncoder())
    }

    override fun configure(http: HttpSecurity?) {
        http!!.authorizeRequests().anyRequest().authenticated()
    }

    @Bean
    @Primary
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }
}

@Configuration
@EnableAuthorizationServer
class OAuth2AuthorizationServerConfig : AuthorizationServerConfigurerAdapter() {
    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    lateinit var passwordEncoder: BCryptPasswordEncoder
    
    @Qualifier("userDetailsServiceImpl")
    @Autowired
    lateinit var userDetailsService: UserDetailsService

    override fun configure(security: AuthorizationServerSecurityConfigurer?) {
        security!!.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()")
    }

    override fun configure(clients: ClientDetailsServiceConfigurer?) {
        clients!!.inMemory()
                .withClient("board")
                .secret(passwordEncoder.encode("board"))
                .authorizedGrantTypes("password", "authorization_code", "refresh_token")
                .accessTokenValiditySeconds(3600)
                .refreshTokenValiditySeconds(10*24*3600)
                .scopes("read")
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer?) {
        endpoints!!.tokenStore(tokenStore())
                .accessTokenConverter(accessTokenConverter())
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
    }

    @Bean
    fun accessTokenConverter() : JwtAccessTokenConverter {
        val converter = JwtAccessTokenConverter()
        converter.setSigningKey("123")
        return converter
    }

    @Bean
    fun tokenStore() : TokenStore {
        return JwtTokenStore(accessTokenConverter())
    }

    @Bean
    @Primary
    fun tokenServices() : DefaultTokenServices {
        val defaultTokenServices = DefaultTokenServices()
        defaultTokenServices.setTokenStore(tokenStore())
        defaultTokenServices.setSupportRefreshToken(true)
        return defaultTokenServices
    }
}


@Configuration
@EnableResourceServer
class OAuth2ResourceConfiguration : ResourceServerConfigurerAdapter() {


    override fun configure(http: HttpSecurity?) {
        http!!.authorizeRequests().anyRequest().authenticated()
    }


    //
//    override fun configure(resources: ResourceServerSecurityConfigurer?) {
//        resources!!.tokenServices(defaultTokenServices)
//    }
}