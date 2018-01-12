package accounts;

import accounts.api.APIAuthenticationManager;
import accounts.api.APIUserConfiguration;
import accounts.api.APIUserHandlerMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.yaml.snakeyaml.Yaml;

import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigurer {


    @Configuration
    public static class SecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Value("${security.api_users_config_path}")
        private String configApiUsersFileLocation;

        @Autowired
        private ResourceLoader resourceLoader;

        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/health", "/info");
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            APIUserConfiguration apiUserConfiguration = new Yaml()
                .loadAs(resourceLoader.getResource(configApiUsersFileLocation).getInputStream(), APIUserConfiguration
                    .class);
            http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and()
                .csrf()
                .disable()
                .addFilterBefore(new BasicAuthenticationFilter(new APIAuthenticationManager(apiUserConfiguration)),
                    BasicAuthenticationFilter.class
                )
                .authorizeRequests()
                .anyRequest()
                .hasRole("READ");
        }

    }

    @Configuration
    public class MvcConfig implements WebMvcConfigurer {

        @Override
        public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
            argumentResolvers.add(new APIUserHandlerMethodArgumentResolver());
        }

    }

}
