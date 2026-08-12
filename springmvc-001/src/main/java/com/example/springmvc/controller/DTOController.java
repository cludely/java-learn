package com.example.springmvc.controller;

import com.example.springmvc.dto.UserDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

public class DTOController {
    /**
     * 接收表单参数并绑定为 JavaBean。
     *
     * <p>例如提交 {@code application/x-www-form-urlencoded} 或普通 URL 参数：</p>
     * <pre>{@code
     * POST /register
     * id=1&name=zhangsan&age=18&sex=male
     * }</pre>
     *
     * <p>这里的 {@code UserDTO user} 等价于：</p>
     * <pre>{@code
     * public String test1(@ModelAttribute UserDTO user) { ... }
     * }</pre>
     *
     * <p>原因是 Spring MVC 对<strong>非简单类型</strong>、且未标注其他参数注解的方法参数，
     * 默认按 {@code @ModelAttribute} 处理：创建 {@code UserDTO}，读取请求参数中与属性名相同
     * 的值，再通过 setter（或字段访问方式）完成数据绑定。例如 {@code name=zhangsan} 会调用
     * {@code setName("zhangsan")}。</p>
     *
     * <p>这并不代表 {@code @RequestParam}、{@code @RequestBody}、{@code @RequestPart} 都能省略：</p>
     * <ul>
     *     <li>{@code @RequestParam}：本例不需要逐个接收字段，因此可以省略；若形参是
     *     {@code String}、{@code Long} 等简单类型，是否可省略还取决于 Spring MVC 的配置，且
     *     显式写出可固定请求参数名和必填规则。</li>
     *     <li>{@code @RequestBody}：用于读取<strong>整个</strong>请求体并通过消息转换器解析，
     *     例如 {@code application/json}。若省略它，JSON 不会自动按本例的表单绑定方式读取。</li>
     *     <li>{@code @RequestPart}：用于读取 {@code multipart/form-data} 中的某一个 part，常见于
     *     “文件 + JSON 对象”上传；它也不能由本例的默认绑定替代。</li>
     * </ul>
     */
    @PostMapping("/register")
    public String test1(UserDTO user) {
        System.out.println(user);
        return "first";
    }

    /**
     * 使用 {@code @RequestHeader} 获取 HTTP 请求头中的数据。
     *
     * <p>请求示例：</p>
     * <pre>{@code
     * POST /request-header
     * User-Agent: Mozilla/5.0
     * X-Request-Id: 8f5c7b10
     * }</pre>
     *
     * <p>{@code value} 用于指定请求头名称，HTTP 请求头名称不区分大小写。
     * {@code @RequestHeader} 默认 {@code required = true}，因此缺少 {@code User-Agent}
     * 时，Spring MVC 会返回 {@code 400 Bad Request}。</p>
     *
     * <p>自定义请求头 {@code X-Request-Id} 不一定存在，所以通过
     * {@code required = false} 将其设为可选；未传入时，形参值为 {@code null}。
     * 也可以使用 {@code defaultValue} 指定缺失时的默认值。</p>
     */
    @PostMapping("/request-header")
    public String getRequestHeader(
            @RequestHeader("User-Agent") String userAgent,
            @RequestHeader(value = "X-Request-Id", required = false) String requestId
    ) {
        System.out.println("User-Agent: " + userAgent);
        System.out.println("X-Request-Id: " + requestId);
        return "first";
    }
}
