package there.cumt.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import there.cumt.entity.User;
import there.cumt.entity.User_Role;
import there.cumt.entity.vo.LoginUser;
import there.cumt.mapper.UserMapper;
import there.cumt.mapper.UserRoleMapper;
import there.cumt.service.UserService;
import there.cumt.utils.JwtUtil;
import there.cumt.utils.Result;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public Map<String, Object> login(User user) {
        //1、封装Authentication对象 ,密码校验，自动完成
        UsernamePasswordAuthenticationToken authentication=new
                UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        //2、进行校验
        Authentication authenticate =
                authenticationManager.authenticate(authentication);
        //3、如果authenticate为空
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("登录失败");
        }
        //放入的用户信息
        LoginUser loginUser=(LoginUser) authenticate.getPrincipal();
        //生成jwt，使用fastjson的方法，把对象转成字符串
        String loginUserString = JSON.toJSONString(loginUser);
        //调用JWT工具类，生成jwt令牌
        String jwt = JwtUtil.createJWT(loginUserString, null);

        Map<String,Object> map = new HashMap<>();
        map.put("token",jwt);
        map.put("username",loginUser.getUsername());

        return map;
    }

    @Override
    public Result register(User user) {

        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_name",user.getUserName());
        User user1 = userMapper.selectOne(queryWrapper);

        if(user.getUserName().equals("")||user.getPassword().equals("")){
            return Result.error().message("用户名或密码为空");
        } else if (user1 != null) {
            return Result.error().message("用户名已被占用");
        }else{

            String encodePassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodePassword);

            userMapper.insert(user);

            queryWrapper.eq("user_name",user.getUserName());
            User user2 = userMapper.selectOne(queryWrapper);

            User_Role userRole = new User_Role(user2.getId(),3L);
            userRoleMapper.insert(userRole);

            return Result.ok().message("注册成功").data("user",user);
        }


    }


}
