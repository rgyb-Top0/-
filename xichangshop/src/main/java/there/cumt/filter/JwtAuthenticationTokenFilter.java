package there.cumt.filter;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import there.cumt.entity.vo.LoginUser;
import there.cumt.exception.CustomtomerAuthenticationException;
import there.cumt.handler.LoginFailureHandler;
import there.cumt.utils.JwtUtil;

import javax.annotation.Resource;
import java.io.IOException;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    private LoginFailureHandler loginFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException,
            IOException {
        //1. 获取当前请求的url地址
        try {
            String uri = request.getRequestURI();
            //如果当前请求不是登录请求，则需要进行token验证
            if (!uri.equals("/user/login")&&!uri.equals("/user/register")) {
                this.vaildateToken(request);
            }
        } catch (AuthenticationException e) {
            System.out.println(e);
            loginFailureHandler.onAuthenticationFailure(request, response, e);
        }

        //5. 放行
        filterChain.doFilter(request, response);
    }

    //用于token的校验方法
    private void vaildateToken(HttpServletRequest request){
        //2. 获取token
        String token = request.getHeader("Authorization");
        if (ObjectUtils.isEmpty(token)) {
            //如果token不在header里则获取路径参数
            token = request.getParameter("Authorization");
        }
        if (ObjectUtils.isEmpty(token)) {
            //header以及路径参数中没有token指就抛出异常
            throw new CustomtomerAuthenticationException("token为空");
        }

        //3. 解析token
        String loginUserStr=null;
        LoginUser loginUser=null;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            loginUserStr = claims.getSubject();
            //将字符串转化成loginUser对象
            loginUser = JSON.parseObject(loginUserStr,LoginUser.class);
        } catch (Exception e) {
            throw new CustomtomerAuthenticationException("token非法");
        }

        //存入SecurityContextHolder
        //获取权限信息封装到Authentication中
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser,null,loginUser.getAuthorities());

        //4. 设置到Spring Security上下文
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);



    }
}
