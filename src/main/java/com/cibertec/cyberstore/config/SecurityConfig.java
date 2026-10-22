package com.cibertec.cyberstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // ENCRIPTADOR DE CONTRASENAS
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // CADENA DE FILTROS DE SEGURIDAD
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .authorizeHttpRequests(auth -> auth

                // PAGINAS Y RECURSOS PUBLICOS
                .requestMatchers(
                        "/",
                        "/login",
                        "/acceso-denegado",
                        "/css/**",
                        "/js/**",
                        "/img/**",
                        "/images/**",
                        "/productos/**",
                        "/marcas/**",
                        "/nosotros/**",
                        "/promociones/**",
                        "/error",
                        "/h2-console/**"
                ).permitAll()

                // ADMINISTRADOR Y ALMACENERO
                .requestMatchers("/admin/productos/**")
                .hasAnyRole("ADMIN", "ALMACENERO")

                .requestMatchers("/admin/proveedores/**")
                .hasAnyRole("ADMIN", "ALMACENERO")

                // ADMINISTRADOR Y VENDEDOR
                .requestMatchers("/admin/ventas/**")
                .hasAnyRole("ADMIN", "VENDEDOR")

                .requestMatchers("/admin/clientes/**")
                .hasAnyRole("ADMIN", "VENDEDOR")

                // SOLO ADMINISTRADOR
                .requestMatchers("/admin/**")
                .hasRole("ADMIN")

                // CUALQUIER OTRA RUTA
                .anyRequest()
                .authenticated()
            )

            // AJUSTES PARA LA CONSOLA H2
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/h2-console/**")
            )
            .headers(headers -> headers
                .frameOptions(frame -> frame.sameOrigin())
            )

            // LOGIN
            .formLogin(form -> form
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error")
                .permitAll()
            )

            // ACCESO DENEGADO
            .exceptionHandling(exception -> exception
                .accessDeniedPage("/acceso-denegado")
            )

            // CERRAR SESION
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            );

        return http.build();
    }
}