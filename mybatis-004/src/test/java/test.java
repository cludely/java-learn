import com.example.mybatis.entity.User;
import com.example.mybatis.mapper.UserMapper;
import com.example.mybatis.util.SqlSessionUtil;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class test {

    @Test
    public void test01() {
        UserMapper mapper = SqlSessionUtil.openSession().getMapper(UserMapper.class);

        // Map<String, Object> params = new HashMap<>();
        // params.put("")

        List<User> users = mapper.selectByCondition("hjj", 18, "男");
        users.forEach(System.out::println);
    }

    @Test
    public void test02() {
        UserMapper mapper = SqlSessionUtil.openSession().getMapper(UserMapper.class);

        // Map<String, Object> params = new HashMap<>();
        // params.put("")

        List<User> users = mapper.selectByCondition2("", 18, "男");
        users.forEach(System.out::println);
    }
}

