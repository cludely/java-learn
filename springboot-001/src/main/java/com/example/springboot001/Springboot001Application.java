package com.example.springboot001;

import com.example.springboot001.entity.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Spring Boot 应用的启动类。
 *
 * <p>{@code @SpringBootApplication} 是最常用的核心组合注解，等价于同时声明：</p>
 * <ul>
 *     <li>{@code @SpringBootConfiguration}：表明这是 Spring Boot 的配置类；</li>
 *     <li>{@code @EnableAutoConfiguration}：根据依赖和配置自动装配 Bean；</li>
 *     <li>{@code @ComponentScan}：扫描当前包及其子包中的组件，例如 {@code @Component}、{@code @Service}。</li>
 * </ul>
 *
 * <p>{@code @Configuration} 是 Spring Framework 的通用配置类注解，通常配合 {@code @Bean}
 * 方法定义并注册 Bean。{@code @SpringBootConfiguration} 是 Spring Boot 对配置类的特殊标记，
 * 本质上也是配置类，专门表示整个 Boot 应用的主配置类，测试框架可以据此找到启动配置。</p>
 *
 * <p>因此，{@code @SpringBootApplication} 中的 {@code @SpringBootConfiguration} 负责标记主配置类，
 * {@code @EnableAutoConfiguration} 负责自动配置，{@code @ComponentScan} 负责扫描组件；
 * 普通的额外配置类则直接使用 {@code @Configuration}。一个应用通常只保留一个
 * {@code @SpringBootConfiguration}，启动类直接使用 {@code @SpringBootApplication} 即可。</p>
 *
 * <p>因此，启动类通常放在项目包结构的最外层，保证业务组件可以被扫描到。</p>
 */
@SpringBootApplication
public class Springboot001Application {

    /**
     * Java 程序入口。
     *
     * <p>{@link SpringApplication#run(Class, String[])} 会创建 Spring 容器、执行组件扫描和自动配置，
     * 最后启动应用。</p>
     *
     * @param args 启动参数，例如 {@code --server.port=8081}
     */
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Springboot001Application.class, args);
        User user = context.getBean(User.class);

        System.out.println(user);
    }

}
