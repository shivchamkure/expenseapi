
package in.cody.expensetrackerapi.config;

import in.cody.expensetrackerapi.security.JwtRequestFilter;
import in.cody.expensetrackerapi.security.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {

    private final UserDetailsServiceImpl userService;
    private final AuthenticationConfiguration configuration;
    private final JwtRequestFilter authenticationJwtRequestFilter;

    public SecurityConfiguration(UserDetailsServiceImpl userService, AuthenticationConfiguration configuration,
                   JwtRequestFilter authenticationJwtRequestFilter ) {
        this.userService = userService;
        this.configuration = configuration;
        this.authenticationJwtRequestFilter = authenticationJwtRequestFilter;
    }

   @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable().authorizeHttpRequests()
                .requestMatchers( "/expense", "/users/**", "/login" ).authenticated()
                .requestMatchers("/register", "/signin"  ).permitAll()
                .anyRequest().authenticated()
                .and().formLogin().defaultSuccessUrl("/expense").and().httpBasic();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterAfter(authenticationJwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    //Tell spring security to use this dao authentication provider instance instead of default one.
    @Bean
    public DaoAuthenticationProvider authenticationProvider() throws Exception{
        DaoAuthenticationProvider authProvider= new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    //Get the authentication manager instance
    @Bean
    public AuthenticationManager authenticationManager() throws Exception{
        return configuration.getAuthenticationManager();
    }

   @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
