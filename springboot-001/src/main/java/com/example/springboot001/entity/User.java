package com.example.springboot001.entity;

import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 用于演示 {@code @Value} 的三种常见注入方式。
 *
 * <p>{@code @Value} 会在 Spring 创建 Bean 时，把配置值、普通文本或 SpEL 表达式注入字段、
 * 方法参数或构造器参数。此类带有 {@code @Component}，因此会被组件扫描创建为 Spring Bean。</p>
 *
 * <p>配置项较少时可使用 {@code @Value}；如果同一业务前缀下有许多配置，
 * 应优先选择类型安全的 {@code @ConfigurationProperties}。</p>
 */
@Component
@ToString
public class User {

    /**
     * 不含 {@code ${...}} 的值会作为普通字符串直接注入，不会读取配置文件。
     */
    @Value("name")
    private String name;

    /**
     * 属性占位符：Spring 会从 Environment 查找键 {@code email}，
     * 这里对应 application.properties 中的 {@code email=1428448282@qq.com}。
     * 若该配置不存在且未提供默认值，应用启动会失败。
     */
    @Value("${email}")
    private String email;

    /**
     * 带默认值的属性占位符。冒号后的 {@code 30} 是默认值；当没有配置 {@code age} 时才会使用它。
     * Spring 会根据字段类型自动将配置文本转换为 {@link Integer}。
     *
     * <p>还可使用 SpEL，例如 {@code @Value("#{systemProperties['user.home']}")} 读取系统属性。</p>
     */
    @Value("${age: 30}")
    private Integer age;

    @Value("${user.sex}")
    private String sex;
}
