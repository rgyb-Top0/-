package there.cumt.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import there.cumt.entity.User;
import there.cumt.service.AdminService;
import there.cumt.utils.Result;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 管理员的响应类
 * </p>
 *
 * @author LinlingJiang
 * @since 2024-09-17
 */
@RestController
@RequestMapping("/admin")
public class AdministratorController {

    @Resource
    private AdminService adminService;

    //设置为普通管理员的方法
    @PutMapping("/authorise/{userName}")
    @PreAuthorize("hasAnyAuthority('sys:admin:setAdmin')")
    public Result authoriseUser(@PathVariable String userName){

        return adminService.authoriseUser(userName);
        //return R.ok().message("更改成功");
    }


    //将管理员变成普通用户的方法
    @PutMapping("/revoke/{userName}")
    @PreAuthorize("hasAnyAuthority('sys:admin:revokeAdmin')")
    public Result revoke(@PathVariable String userName){

        return adminService.revoke(userName);
    }

    //添加用户的方法
    @PutMapping("/addUsers")
    @PreAuthorize("hasAnyAuthority('sys:admin:addUser')")
    public Result addUser(@RequestBody User user){

        return adminService.addUser(user);
    }

    //删除用户的方法
    @DeleteMapping("/deleteUsers/{userName}")
    @PreAuthorize("hasAnyAuthority('sys:admin:deleteUser')")
    public Result deleteUser(@PathVariable String userName){

        return null;
    }

    //查询所有用户的方法
    @GetMapping("/getUsers")
    @PreAuthorize("hasAnyAuthority('sys:admin:getUsers')")
    public Result getUsers(){

        List<User> users = adminService.getUsers();
        return Result.ok().message("查询成功").data("用户列表",users);
    }

    //查询用户的方法
    @GetMapping("/getOneUsers/{userName}")
    @PreAuthorize("hasAnyAuthority('sys:admin:getOneUsers')")
    public Result getOneUsers(@PathVariable String userName){

        User user = adminService.getOneUsers(userName);

        if(user==null){
            return Result.error().message("不存在此用户");
        }else {
            return Result.ok().message("查询成功").data("用户信息",user);
        }
    }


}
