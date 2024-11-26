package there.cumt.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 自定义验证异常类
 */
public class CustomtomerAuthenticationException extends AuthenticationException {
    public CustomtomerAuthenticationException(String msg) {
        super(msg);
    }
}
