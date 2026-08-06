package com.example.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * {@code @RequestMapping} 用于建立“请求条件”与 Controller 或处理方法之间的映射关系。
 *
 * <p>它可标注在类或方法上：类级别定义全部方法共享的路径前缀或请求条件，方法级别定义
 * 当前方法的条件。两处同时存在时，请求必须同时满足两处条件，路径会自动拼接。</p>
 *
 * <p>本类的 {@code @RequestMapping} 未设置任何属性，仅用于演示，不会改变下面两个方法的
 * 地址。因此 {@code hello()} 仍访问 {@code /hello}，{@code other()} 仍访问 {@code /other}。
 * 若改为 {@code @RequestMapping("/first")}，则它们会分别变为
 * {@code /first/hello} 与 {@code /first/other}。</p>
 *
 * <p>全部常用属性：</p>
 * <ul>
 *     <li>{@code value}：请求路径，最常用属性，可写一个或多个路径。
 *         例如 {@code value = {"/hello", "/hi"}}。</li>
 *     <li>{@code path}：{@code value} 的别名，两者不能设置为不同值。
 *         例如 {@code @RequestMapping(path = "/hello")}。</li>
 *     <li>{@code method}：限定 HTTP 方法，类型为 {@code RequestMethod[]}。
 *         例如 {@code method = RequestMethod.GET}；不设置则 GET、POST 等方法都可能匹配。
 *         多个方法可写为 {@code {RequestMethod.GET, RequestMethod.POST}}。</li>
 *     <li>{@code params}：限定请求参数。{@code "id"} 表示必须有 id；{@code "!id"} 表示
 *         不能有 id；{@code "id=1"} 表示 id 必须为 1；{@code "id!=1"} 表示 id 不能为 1。</li>
 *     <li>{@code headers}：限定请求头，表达式规则与 {@code params} 相同。例如
 *         {@code headers = "X-Requested-With=XMLHttpRequest"}。</li>
 *     <li>{@code consumes}：限定请求 {@code Content-Type}，即接口可接收的请求体媒体类型。
 *         例如 {@code consumes = "application/json"}，常用于接收 JSON 的 POST 或 PUT 请求。</li>
 *     <li>{@code produces}：限定响应 {@code Content-Type}，即接口可产生的媒体类型。
 *         例如 {@code produces = "application/json;charset=UTF-8"}，常和
 *         {@code @ResponseBody} 或 {@code @RestController} 一起使用。</li>
 *     <li>{@code name}：设置映射的逻辑名称，便于日志、监控或生成链接；它不参与请求匹配。</li>
 * </ul>
 *
 * <p>对于只处理一种 HTTP 方法的接口，通常使用组合注解更直观：{@code @GetMapping}、
 * {@code @PostMapping}、{@code @PutMapping}、{@code @DeleteMapping}、{@code @PatchMapping}。
 * 例如 {@code @GetMapping("/hello")} 等价于
 * {@code @RequestMapping(value = "/hello", method = RequestMethod.GET)}。</p>
 */
@Controller
@RequestMapping
public class FirstController {

    /**
     * 此处 {@code "/hello"} 是 {@code value} 属性的简写。因为没有指定 {@code method}，
     * 任意 HTTP 方法都能匹配；若这是展示页面，更推荐使用 {@code @GetMapping("/hello")}。
     *
     * @return 逻辑视图名 {@code first}，将由 Thymeleaf 解析为
     *         {@code /WEB-INF/templates/first.html}
     */
    @RequestMapping("/hello")
    public String hello() {
        return "first";
    }

    // 一个controller可以写多个方法
    @RequestMapping("/other")
    public String other() {
        return "other";
    }
}
