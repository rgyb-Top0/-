package there.cumt.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import there.cumt.filter.JwtAuthenticationTokenFilter;
import there.cumt.handler.AnonymousAuthenticationHandler;
import there.cumt.handler.CustomerAccessDeniedHandler;
import there.cumt.handler.LoginFailureHandler;

import javax.annotation.Resource;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig{

    //自定义用于认证的过滤器，进行jwt的校验
    @Resource
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    //认证用户无权限访问资源的处理器
    @Resource
    private CustomerAccessDeniedHandler customerAccessDeniedHandler;

    //客户端进行认证数据的提交时出现异常，或者是匿名用户访问受限资源的处理器
    @Resource
    private AnonymousAuthenticationHandler anonymousAuthenticationHandler;

    //用户认证校验失败处理器
    @Resource
    private LoginFailureHandler loginFailureHandler;

    //创建BCryptPasswordEncoder注入容器
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 登录时需要调用AuthenticationManager.authenticate执行一次校验
     *
     */
    @Bean
    public AuthenticationManager
    authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    //登录请求放行
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //配置关闭csrf机制
        http.csrf(csrf -> csrf.disable());

        //用户认证校验失败处理器
        http.formLogin(configurer -> configurer.failureHandler(loginFailureHandler));

        // STATELESS（无状态）： 表示应用程序是无状态的，不会创建会话。
        http.sessionManagement(configurer ->
                configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //配置请求拦截方式
        //permitAll:随意访问
        http.authorizeHttpRequests(auth -> auth.requestMatchers("/user/login","/user/register")
                .anonymous()
                .anyRequest()
                .authenticated());

        //把token校验过滤器添加到过滤器链中
        http.addFilterBefore(jwtAuthenticationTokenFilter,
                UsernamePasswordAuthenticationFilter.class);

        //添加自定义的异常处理类
        http.exceptionHandling(configurer -> {
            configurer.accessDeniedHandler(customerAccessDeniedHandler);
            configurer.authenticationEntryPoint(anonymousAuthenticationHandler);
        });

        return http.build();
    }



}
