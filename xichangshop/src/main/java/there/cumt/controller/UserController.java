package there.cumt.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import there.cumt.entity.User;
import there.cumt.service.UserService;
import there.cumt.utils.Result;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 用户基本操作的方法
 * </p>
 *
 * @author LinlingJiang
 * @since 2024-09-17
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;


    //登录的方法
    @PostMapping("/login")
    public Result login(@RequestBody User user){

        Map<String,Object> map = userService.login(user);

        if(!map.get("token").equals("")&&!map.get("username").equals("")){
            return Result.ok().message("登录成功").data("map",map);
        }
        return Result.error().message("登录失败");
    }


    //注册的方法
    @PostMapping("/register")
    public Result register(@RequestBody User user){

        return userService.register(user);

    }
}
