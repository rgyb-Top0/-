package there.cumt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import there.cumt.entity.User;
import there.cumt.entity.vo.LoginUser;
import there.cumt.mapper.MenuMapper;
import there.cumt.mapper.UserMapper;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws
            UsernameNotFoundException {

        if (username.equals("")){
            throw new InternalAuthenticationServiceException("");
        }
        //1. 根据用户名查询用户信息,连接数据库
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_name",username);
        User user = userMapper.selectOne(queryWrapper);

        if(user==null){
            throw new UsernameNotFoundException("");
        }

        //2. TODO 查询用户对应的权限信息
        List<String> list = menuMapper.getMenuByUserId(user.getId());

        //3. 如果有，把数据封装成UserDetails对象返回
        return new LoginUser(user,list);

    }


}
