package ru.group.lab3;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/", "/login").permitAll()

                        .requestMatchers(

                                "/owners/new", "/owners/delete/**", "/owners/update/**",
                                "/cars/new",   "/cars/delete/**",   "/cars/update/**"
                        ).hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
                .formLogin(login -> login
                        .loginPage("/login")
                        .loginProcessingUrl("/login")   // action формы
                        .defaultSuccessUrl("/owners")
                        .failureUrl("/login?error")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }

   // @Bean
   // UserDetailsManager inMemoryUserDetailManager() {
   //     UserDetails user1 =
   //             User
   //                     .withUsername("user")
   //                     .password(passwordEncoder().encode("pass1"))
   //                     .roles("USER")
   //                     .build();
   //     UserDetails user2 =
   //             User
   //                     .withUsername("admin")
   //                     .password(passwordEncoder().encode("pass2"))
   //                     .roles("USER", "ADMIN")
   //                     .build();
   //     return new InMemoryUserDetailsManager(user1,user2);
    //
    //    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

