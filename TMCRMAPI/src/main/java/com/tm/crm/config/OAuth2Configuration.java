package com.tm.crm.config;	 
		 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import com.tm.crm.config.user.CustomUserDetailsService;

@Configuration
public class OAuth2Configuration extends SpringBootServletInitializer {
	
	@Configuration
	@EnableGlobalMethodSecurity(securedEnabled = true)	
	protected static class AuthenticationManagerConfiguration extends GlobalMethodSecurityConfiguration {
		
		@Autowired
		private CustomUserDetailsService userDetailsService;
		
		@Autowired
		public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userDetailsService).passwordEncoder(new  Md5PasswordEncoder());
		}
	}
	
	@Configuration
	@EnableAuthorizationServer
	protected static class OAuth2Config extends AuthorizationServerConfigurerAdapter {

		@Autowired
		private AuthenticationManager authenticationManager;

	    @Bean
	    public TokenStore tokenStore() {
	        return new RedisTokenStore(redisConnectionFactory());
	    }
	    
		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints)
				throws Exception {
			endpoints.tokenStore(tokenStore()).authenticationManager(authenticationManager);
		}

		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			clients.
					inMemory()
            		// WEB
					.withClient("WEB")
					.authorizedGrantTypes("password")
					.scopes("read", "write");
		}
		
		@Bean
		public RedisTokenStore redisTokenStoreBean() {
			return new RedisTokenStore(redisConnectionFactory());
		}
		
		@Bean
		public JedisConnectionFactory redisConnectionFactory() {
			return new JedisConnectionFactory();			
		}

		@Primary
		@Bean
        public AuthorizationServerTokenServices tokenServices() {
            DefaultTokenServices tokenServices = new DefaultTokenServices();
            tokenServices.setSupportRefreshToken(true);
            tokenServices.setTokenStore(this.tokenStore());
            return tokenServices;
        }
		
		@Override
	    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
	        oauthServer.allowFormAuthenticationForClients();
	    }
	}
}
