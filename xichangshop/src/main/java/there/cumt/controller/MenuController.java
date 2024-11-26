package there.cumt.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 普通用户的权限方法
 * </p>
 *
 * @author LinlingJiang
 * @since 2024-09-17
 */
@RestController
@RequestMapping("/manager")
public class MenuController {

    @GetMapping("/getInfo")
    @PreAuthorize("hasAnyAuthority('sys:model:select')")
    public String getInfo(){
        return "信息列表";
    }

    @PostMapping("/insetInfo")
    @PreAuthorize("hasAnyAuthority('sys:model:add')")
    public String insertInfo(){
        return "修改信息";
    }

    @PutMapping("/updateInfo")
    @PreAuthorize("hasAnyAuthority('sys:model:update')")
    public String updateInfo(){
        return "更新信息";
    }

    @DeleteMapping("/deleteInfo")
    @PreAuthorize("hasAnyAuthority('sys:model:delete')")
    public String deleteInfo(){
        return "删除信息";
    }

}
