package there.cumt.entity.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import there.cumt.entity.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser implements UserDetails {

    private User user;
    private List<String> list;//这是权限列表

    public LoginUser(User user, List<String> list) {
        this.user=user;
        this.list=list;
    }


    //自定义一个权限列表集合
    @JSONField(serialize = false)
    List<SimpleGrantedAuthority> authorities;//子类

    //用来返回权限信息
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //父类
        if(authorities!=null){//如果当前对象非空直接返回对象，否则遍历集合为对象填充权限
            return authorities;
        }
        authorities=new ArrayList<>(); //对象
        for (String item : list) {
            SimpleGrantedAuthority authority=new SimpleGrantedAuthority(item);
            authorities.add(authority);
        }
        return authorities;

    }

    //获取密码
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    //获取用户名
    @Override
    public String getUsername() {
        return user.getUserName();
    }

    //判断账号是否未过期
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //判断账号是否没有锁定
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //判断账号是否没有超时
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //判断账号是否可用
    @Override
    public boolean isEnabled() {
        return true;
    }

}
