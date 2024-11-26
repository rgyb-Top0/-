package there.cumt.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import there.cumt.utils.Result;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 认证用户访问无权限资源时处理器
 */
@Component
public class CustomerAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException,
            ServletException {
        //设置客户端的响应的内容类型
        response.setContentType("application/json;charset=UTF-8");
        //获取输出流
        ServletOutputStream outputStream = response.getOutputStream();
        //消除循环引用,进行R对象的序列化
        String result = JSON.toJSONString(Result.error().code(700).message("无权限访问,请联系管理员！"),
                SerializerFeature.DisableCircularReferenceDetect);
                outputStream.write(result.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }

}
