package com.example.springboot001;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Spring Boot 集成测试示例。
 *
 * <p>{@code @SpringBootTest} 会按接近真实应用启动的方式加载 Spring 容器，
 * 可以在测试中注入并验证各类 Spring Bean。它适合验证多个组件协作是否正常；
 * 若只测试单个 MVC 控制器或数据层，应使用更轻量的切片测试注解。</p>
 */
@SpringBootTest
class Springboot001ApplicationTests {

    /**
     * JUnit 5 的测试方法标记。
     *
     * <p>带有 {@code @Test} 的无参方法会被测试框架执行。
     * 这里不抛出异常就说明 {@code @SpringBootTest} 已成功加载应用上下文。</p>
     */
    @Test
    void contextLoads() {
    }

}
