package com.example.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
@Controller
@RequestMapping("/user")
public class TestController {

    /**
     * 使用 {@code @RequestParam} 将 HTTP 请求参数绑定到方法形参。
     *
     * <p>例如访问 {@code /testGetRequestParams?username=zhangsan&password=123456} 时，
     * Spring MVC 会把请求中的 {@code username} 和 {@code password} 分别赋值给下面的两个形参。</p>
     *
     * <p>{@code value} 用于指定请求参数名。这里即使 Java 形参改名，只要 {@code value} 不变，
     * 仍会从对应的请求参数中取值。</p>
     *
     * <p>{@code @RequestParam} 默认 {@code required = true}：缺少任一参数时，Spring MVC 会返回
     * {@code 400 Bad Request}。如需允许参数缺失，可写成
     * {@code @RequestParam(value = "username", required = false)}；也可以通过
     * {@code defaultValue} 指定默认值。</p>
     */
    @GetMapping("/hello")
    public String testGetRequestParams(
            // 将请求参数 username 的值绑定到 username 形参。
            @RequestParam(value = "username") String username,
            // 将请求参数 password 的值绑定到 password 形参。
            @RequestParam(value = "password") String password
    ) {
        return "first";
    }

    /**
     * 对比一：RESTful URL 中的路径参数使用 {@code @PathVariable} 获取。
     *
     * <p>请求示例：{@code GET /users/100}。</p>
     *
     * <p>这里的 {@code 100} 位于 URL 路径中，对应 {@code /users/{id}} 的 {@code {id}}，
     * 因此不能使用 {@code @RequestParam}，而应使用 {@code @PathVariable("id")}。</p>
     */
    @GetMapping("/{id}")
    public String getUserById(@PathVariable("id") Long id) {
        return "first";
    }

    /**
     * 对比二：POST 请求提交表单时，仍然使用 {@code @RequestParam} 获取参数。
     *
     * <p>请求示例：{@code POST /login}，请求头为
     * {@code Content-Type: application/x-www-form-urlencoded}，请求体为
     * {@code username=zhangsan&password=123456}。</p>
     *
     * <p>HTTP 方法是 POST 不会改变参数绑定方式；只要数据是表单字段，
     * {@code @RequestParam} 就能读取它。</p>
     */
    @PostMapping(value = "/login", consumes = "application/x-www-form-urlencoded")
    public String loginByForm(
            @RequestParam("username") String username,
            @RequestParam("password") String password
    ) {
        return "first";
    }

    /**
     * 对比三：POST 请求体为 JSON 时，使用 {@code @RequestBody} 将整个请求体转换为对象。
     *
     * <p>请求示例：{@code POST /users}，请求头为 {@code Content-Type: application/json}，
     * 请求体为 {@code {"username":"zhangsan","password":"123456"}}。</p>
     *
     * <p>此处不能用 {@code @RequestParam}，因为用户名和密码不是独立的表单参数，而是 JSON
     * 请求体中的字段。运行该示例需要项目配置 JSON 消息转换器，通常引入
     * {@code com.fasterxml.jackson.core:jackson-databind} 即可。</p>
     */
    @PostMapping(value = "/users", consumes = "application/json")
    public String createUserByJson(@RequestBody UserRequest userRequest) {
        return "first";
    }

    /**
     * 用于接收 JSON 请求体的数据对象。JSON 字段名需与组件名一致。
     */
    public record UserRequest(String username, String password) {
    }
}
