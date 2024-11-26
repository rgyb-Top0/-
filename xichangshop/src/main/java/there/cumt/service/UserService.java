package there.cumt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import there.cumt.entity.User;
import there.cumt.utils.Result;

import java.util.Map;

public interface UserService extends IService<User> {

    Map<String,Object> login(User user);//登录的方法


    Result register(User user);
}
