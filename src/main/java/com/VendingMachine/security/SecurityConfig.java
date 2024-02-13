//package com.VendingMachine.security;
//
//import com.VendingMachine.security.CustomUserDetailsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private CustomUserDetailsService userDetailsService;
//
////    @Autowired
////    private JwtTokenUtil jwtTokenProvider;
//
//
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//    }
////
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/home","/product/**"," /Inventoryproduct/**","/purchasemultipleproductpage","/purchase-Inventryitem","/purchaseproductpage"," /purchaseproduct").permitAll() // Allow access to images without authentication
//                .antMatchers("/getAllInventory","/delete/**", "/addinventoryitem", "/add-Inventryitem", "/update/user/**","/getAllInventoryRest").authenticated()
//                .and()
//                .formLogin()
//             //   .loginPage("/login") // Specify the custom login page URL
//                .defaultSuccessUrl("/getAllInventory")
//                .failureUrl("/login?error=true")
//                .permitAll() // Allow anyone to access the login page
//                .and()
//                .logout()
//                .logoutSuccessUrl("/home")
//                .permitAll()
//                .and()
//                .csrf().disable();
//    }
//
//
//}
