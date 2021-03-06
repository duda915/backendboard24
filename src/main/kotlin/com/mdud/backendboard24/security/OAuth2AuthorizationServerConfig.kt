package com.mdud.backendboard24.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.DefaultTokenServices
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore

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