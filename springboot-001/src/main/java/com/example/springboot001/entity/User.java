package com.example.springboot001.entity;

import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 用于演示 {@code @Value} 的常见注入方式，并对比 {@code @ConfigurationProperties}。
 *
 * <p>{@code @Value} 会在 Spring 创建 Bean 时，把配置值、普通文本或 SpEL 表达式注入字段、
 * 方法参数或构造器参数。此类带有 {@code @Component}，因此会被组件扫描创建为 Spring Bean。</p>
 *
 * <h2>{@code @Value} 和 {@code @ConfigurationProperties} 的区别</h2>
 * <ul>
 *     <li><b>注入粒度：</b>{@code @Value} 通常一次注入一个配置项；{@code @ConfigurationProperties}
 *     按前缀把一组相关配置批量绑定到一个对象。</li>
 *     <li><b>配置前缀：</b>{@code @Value("${user.email}")} 需要逐个写完整键；
 *     {@code @ConfigurationProperties(prefix = "user")} 只需在字段上写 {@code email}、{@code age}。</li>
 *     <li><b>命名绑定：</b>{@code @ConfigurationProperties} 支持宽松绑定，例如
 *     {@code user.first-name}、{@code user.firstName} 和 {@code USER_FIRST_NAME} 可以绑定到
 *     {@code firstName}；{@code @Value} 不会自动把这些写法当成同一个字段。</li>
 *     <li><b>类型和校验：</b>{@code @ConfigurationProperties} 更适合嵌套对象、集合、Duration 等复杂类型，
 *     还可以配合 {@code @Validated} 和 {@code @NotBlank} 等校验注解；{@code @Value} 更适合少量简单值。</li>
 *     <li><b>表达式能力：</b>{@code @Value} 支持 SpEL，例如计算表达式或读取系统属性；
 *     {@code @ConfigurationProperties} 主要做配置绑定，不建议把业务表达式写进配置。</li>
 * </ul>
 *
 * <h2>{@code @ConfigurationProperties} 的典型用法</h2>
 * <p>例如配置文件中有：</p>
 * <pre>{@code
 * user:
 *   email: 1428448282@qq.com
 *   age: 20
 * }</pre>
 * <p>可定义一个带 {@code @ConfigurationProperties(prefix = "user")} 的配置 Bean，
 * 通过 getter/setter 或构造器接收 {@code email}、{@code age}。注册方式可以是：
 * 在类上同时使用 {@code @Component}，或在配置类上使用 {@code @EnableConfigurationProperties(UserProperties.class)}；
 * 也可以使用 {@code @ConfigurationPropertiesScan} 扫描配置类。</p>
 *
 * <p>选择建议：只有一两个零散配置项时使用 {@code @Value}；同一模块有一组带共同前缀的配置时，
 * 使用 {@code @ConfigurationProperties}，可读性、类型安全和可维护性更好。</p>
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
