package there.cumt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import there.cumt.entity.User;
import there.cumt.entity.User_Role;
import there.cumt.mapper.UserMapper;
import there.cumt.mapper.UserRoleMapper;
import there.cumt.service.AdminService;
import there.cumt.utils.Result;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private PasswordEncoder passwordEncoder;


    /*
    * 授权用户的方法
    * */
    @Override
    public Result authoriseUser(String userName) {

        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_name",userName);
        User user = userMapper.selectOne(queryWrapper);

        if(user == null){
            return Result.error().message("不存在该用户");
        }else {

            userMapper.authoriseUserByUserName(userName);
            userRoleMapper.authoriseUserByUserName(userName);

            return Result.ok().message("修改成功");
        }

    }

    /*
     * 取消授权的方法
     * */
    @Override
    public Result revoke(String userName) {

        QueryWrapper<User> queryWrapper1=new QueryWrapper<>();
        queryWrapper1.eq("user_name",userName);
        User user = userMapper.selectOne(queryWrapper1);

        if(user == null){
            return Result.error().message("不存在该用户");
        }else {

            userMapper.revoke(userName);
            userRoleMapper.revoke(userName);

            return Result.ok().message("修改成功");
        }
    }

    /*
     * 添加用户的方法
     * */
    @Override
    public Result addUser(User user) {

        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_name",user.getUserName());
        User user1 = userMapper.selectOne(queryWrapper);

        if(user.getUserName().equals("")||user.getPassword().equals("")){
            return Result.error().message("用户名或密码为空");
        } else if (user1 != null) {
            return Result.error().message("用户名已被占用");
        }else{

            user.setPassword(passwordEncoder.encode(user.getPassword()));

            userMapper.insert(user);

            queryWrapper.eq("user_name",user.getUserName());
            User user2 = userMapper.selectOne(queryWrapper);

            User_Role userRole = new User_Role(user2.getId(),Long.valueOf(user.getUserType()));
            userRoleMapper.insert(userRole);

            return Result.ok().message("添加成功").data("user",user);
        }
    }

    /*
     * 查询所有用户的方法
     * */
    @Override
    public List<User> getUsers() {

        List<User> userList = userMapper.selectList(null);
        return userList;
    }

    /*
     * 根据用户名查询用户的方法
     * */
    @Override
    public User getOneUsers(String userName) {
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_name",userName);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }


}
