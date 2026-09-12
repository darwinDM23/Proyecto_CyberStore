package com.cibertec.cyberstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .authorizeHttpRequests(auth -> auth

                // =====================================================
                // PAGINAS Y RECURSOS PUBLICOS
                // =====================================================
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
                        "/error"
                ).permitAll()

                // =====================================================
                // ADMINISTRADOR Y ALMACENERO
                // Gestion de productos e inventario
                // =====================================================
                .requestMatchers("/admin/productos/**")
                .hasAnyRole("ADMIN", "ALMACENERO")

                .requestMatchers("/admin/proveedores/**")
                .hasAnyRole("ADMIN", "ALMACENERO")

                // =====================================================
                // ADMINISTRADOR Y VENDEDOR
                // Gestion de ventas y clientes
                // =====================================================
                .requestMatchers("/admin/ventas/**")
                .hasAnyRole("ADMIN", "VENDEDOR")

                .requestMatchers("/admin/clientes/**")
                .hasAnyRole("ADMIN", "VENDEDOR")

                // =====================================================
                // CUALQUIER OTRA RUTA ADMINISTRATIVA
                // Solo administrador
                // =====================================================
                .requestMatchers("/admin/**")
                .hasRole("ADMIN")

                // Cualquier otra ruta necesita iniciar sesion
                .anyRequest()
                .authenticated()
            )

            // =========================================================
            // LOGIN
            // =========================================================
            .formLogin(form -> form
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error")
                .permitAll()
            )

            // =========================================================
            // ACCESO DENEGADO - ERROR 403
            // =========================================================
            .exceptionHandling(exception -> exception
                .accessDeniedPage("/acceso-denegado")
            )

            // =========================================================
            // CERRAR SESION
            // =========================================================
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