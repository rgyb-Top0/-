package there.cumt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import there.cumt.entity.User_Role;

public interface UserRoleMapper extends BaseMapper<User_Role> {

    void authoriseUserByUserName(String userName);

    void revoke(String userName);
}
