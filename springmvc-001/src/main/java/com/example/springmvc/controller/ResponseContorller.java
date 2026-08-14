package com.example.springmvc.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * {@code @RequestBody}、{@code @ResponseBody} 与 HTTP 消息转换器示例。
 *
 * <p>请求进入 Controller 时，{@code @RequestBody} 让 Spring MVC 从 HTTP 请求体读取数据；
 * Controller 返回时，{@code @ResponseBody} 让 Spring MVC 将返回值写入 HTTP 响应体。
 * 两者都由 {@code HttpMessageConverter} 完成实际转换，而不是视图解析器。</p>
 *
 * <p>转换器会根据请求的 {@code Content-Type} 选择读取方式，根据方法的 {@code produces}、
 * 客户端 {@code Accept} 请求头和返回值类型选择写出方式。{@code <mvc:annotation-driven/>}
 * 已启用这套机制；本项目额外引入 Jackson 后，会自动注册 JSON 转换器。</p>
 *
 * <p>{@code @RestController} 等价于 {@code @Controller + @ResponseBody}：类中的所有处理方法
 * 默认都将返回值写入响应体，适合只提供 JSON、文本等 HTTP API 的控制器。普通页面控制器应继续使用
 * {@code @Controller}，使 {@code String} 返回值能够被视图解析器当作逻辑视图名处理。本类刻意保留
 * {@code @Controller} 并在各方法显式标注 {@code @ResponseBody}，以便观察该注解对返回值的作用。</p>
 *
 * <p>常见映射：{@code StringHttpMessageConverter} 处理文本；
 * {@code MappingJackson2HttpMessageConverter} 处理 JSON。若 Content-Type 不匹配，通常返回
 * 415 Unsupported Media Type；若请求 JSON 无法反序列化，通常返回 400 Bad Request；若没有合适的
 * 响应转换器，通常返回 406 Not Acceptable。</p>
 */
@Controller
@RequestMapping("/response")
public class ResponseContorller {

    /**
     * {@code String} 默认会被当作逻辑视图名；加上 {@code @ResponseBody} 后，返回值直接成为响应正文。
     *
     * <p>请求示例：{@code GET /springmvc-001/response/text}</p>
     * <p>响应：{@code Content-Type: text/plain;charset=UTF-8}，正文为 {@code Spring MVC message converter}。</p>
     */
    @GetMapping(value = "/text", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String text() {
        return "Spring MVC message converter";
    }

    /**
     * 返回 Java 对象时，Jackson JSON 转换器会把它序列化为 JSON。
     *
     * <p>请求示例：{@code GET /springmvc-001/response/user}，可附带
     * {@code Accept: application/json}。方法级 {@code produces} 明确声明此接口只产生 JSON。</p>
     */
    @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UserResponse getUser() {
        return new UserResponse(1L, "zhangsan", "学生");
    }

    /**
     * {@code @RequestBody} 读取整个请求体，并把 JSON 反序列化为 {@link UserRequest}。
     *
     * <pre>{@code
     * POST /springmvc-001/response/users
     * Content-Type: application/json
     * Accept: application/json
     *
     * {"username":"lisi","age":20}
     * }</pre>
     *
     * <p>注意：JSON 字段名应与 record 组件名匹配。这里的 {@code @ResponseBody} 将返回对象再次序列化为 JSON。
     * {@code @RequestBody} 不能和读取同一个请求体的 {@code @RequestParam} 混用。</p>
     */
    @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UserResponse createUser(@RequestBody UserRequest userRequest) {
        return new UserResponse(100L, userRequest.username(), "新建用户，年龄：" + userRequest.age());
    }

    /**
     * {@link ResponseEntity} 同时携带响应状态码、响应头和响应体；其 body 仍由消息转换器写出。
     *
     * <p>请求示例：{@code GET /springmvc-001/response/created}。响应状态为 201，
     * 并包含 {@code X-Learning-Example: ResponseEntity} 响应头。</p>
     */
    @GetMapping(value = "/created", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<UserResponse> created() {
        UserResponse body = new UserResponse(101L, "wangwu", "已创建");
        return ResponseEntity.status(201)
                .header("X-Learning-Example", "ResponseEntity")
                .body(body);
    }

    /** JSON 请求体对应的数据结构。 */
    public record UserRequest(String username, Integer age) {
    }

    /** JSON 响应体对应的数据结构。 */
    public record UserResponse(Long id, String username, String message) {
    }
}
