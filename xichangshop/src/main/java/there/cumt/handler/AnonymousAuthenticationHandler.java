package there.cumt.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import there.cumt.utils.Result;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


/**
 * 客户端进行认证数据的提交时出现异常，或者是匿名用户访问受限资源的处理器
 */
@Component
public class AnonymousAuthenticationHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException, ServletException {

        //设置客户端的响应的内容类型
        response.setContentType("application/json;charset=UTF-8");
        String result = null;

        //获取输出流
        ServletOutputStream outputStream = response.getOutputStream();
        // System.out.println("异常消息:"+authException.getMessage()+",对象:"+authException);

        if (authException instanceof BadCredentialsException) {
            // 用户名未找到，可以在这里添加自定义处理逻辑
            result = JSON.toJSONString(Result.error()
                            .code(HttpServletResponse.SC_UNAUTHORIZED)//code里的参数是系统自带的，对应未找到用户名的参数
                            .message(authException.getMessage()),
                    SerializerFeature.DisableCircularReferenceDetect);

        } else if (authException instanceof InternalAuthenticationServiceException) {
            //参数代表这个是用户名为空的异常
            result = JSON.toJSONString(Result.error()
                            .code(HttpServletResponse.SC_UNAUTHORIZED)
                            .message("用户名为空!"),
                    SerializerFeature.DisableCircularReferenceDetect);

        } else {
            // 其他身份验证异常处理
            result = JSON.toJSONString(Result.error()
                            .code(600).message("匿名用户无权限访问！"),
                    SerializerFeature.DisableCircularReferenceDetect);  //消除循环引用
        }

        outputStream.write(result.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }

}
