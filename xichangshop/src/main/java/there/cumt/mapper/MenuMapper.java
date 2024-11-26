package there.cumt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import there.cumt.entity.Menu;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author LinlingJiang
 * @since 2024-09-17
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> getMenuByUserId(Long id);

}
