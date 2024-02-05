//package com.VendingMachine.VendingMachine01.AuthInterceptor;// AuthInterceptor.java
//import com.VendingMachine.VendingMachine01.security.JwtTokenUtil;
//import com.VendingMachine.VendingMachine01.service.InventoryService;
//import com.VendingMachine.VendingMachine01.service.JwtUserDetailsService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@Component
//public class AuthInterceptor implements HandlerInterceptor {
//
//    @Autowired
//    private JwtTokenUtil jwtTokenUtil;
//
//    @Autowired
//    private JwtUserDetailsService userDetailsService; // Inject your UserDetailsService
//
//    private static final Logger log = LoggerFactory.getLogger(AuthInterceptor.class);
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        // Extract the token from the request header
//        String token = request.getHeader("Authorization");
//        UserDetails userDetails = userDetailsService.loadUserByUsername("javainuseforhemanthdemo");
//log.info("inside perhandle method of interceptor token=================="+token);
//        // Validate the token (you may want to add more validation logic)
//        if (token != null && jwtTokenUtil.validateToken(token,userDetails)) {
//            // Set the token in the request attributes or wherever you need it
//            request.setAttribute("token", token);
//            return true;
//        } else {
//            // Handle unauthorized access (e.g., redirect to login page or return a 401 response)
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
//            return false;
//        }
//    }
//}
