package there.cumt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import there.cumt.entity.User;

public interface UserMapper extends BaseMapper<User> {

    Long getUserByUserName(String userName);

    void authoriseUserByUserName(String userName);

    void revoke(String userName);
}
