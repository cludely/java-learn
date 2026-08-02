import com.example.mybatis.entity.User;
import com.example.mybatis.mapper.UserMapper;
import com.example.mybatis.util.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.util.List;

public class test {

    @Test
    public void test() {
        UserMapper mapper = SqlSessionUtil.openSession().getMapper(UserMapper.class);
        User userList = mapper.selectUserById(13L);
        System.out.println("查询到了" + userList);
    }
}

