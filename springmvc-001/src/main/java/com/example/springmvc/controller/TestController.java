package com.example.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

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
     * 对比四：请求体为 {@code multipart/form-data} 时，普通字段仍使用
     * {@code @RequestParam}，文件字段使用 {@link MultipartFile} 接收。
     *
     * <p>例如下面的请求包含一个文本字段 {@code username} 和一个文件字段 {@code avatar}：</p>
     * <pre>{@code
     * POST /user/profile
     * Content-Type: multipart/form-data; boundary=...
     *
     * username=zhangsan
     * avatar=<选择的文件>
     * }</pre>
     * <p>也可以使用 curl 发起请求：</p>
     * <pre>{@code
     * curl -X POST http://localhost:8080/springmvc-001/user/profile \
     *      -F "username=zhangsan" \
     *      -F "avatar=@D:/files/avatar.png"
     * }</pre>
     *
     * <p>{@code multipart/form-data} 会将请求体拆成多个 part。Spring MVC 将普通文本 part
     * 作为请求参数绑定，因此可以继续使用 {@code @RequestParam("username")}；文件 part
     * 则可直接绑定为 {@code MultipartFile}，通过 {@code getOriginalFilename()}、
     * {@code getContentType()}、{@code getInputStream()} 等方法读取文件信息和内容。</p>
     *
     * <p>这里不使用 {@code @RequestBody}，因为它表示读取并转换整个请求体，不适合分别获取
     * multipart 中的字段。如果某个 part 本身是 JSON 等需要消息转换器解析的内容，可以改用
     * {@code @RequestPart("user") UserRequest userRequest}。</p>
     *
     * <p>要实际处理文件上传，项目还需要启用 multipart 解析：注册名为
     * {@code multipartResolver} 的解析器，并为 {@code DispatcherServlet} 配置
     * {@code multipart-config}。</p>
     *
     * <p>本案例将文件流式保存到系统临时目录下的 {@code springmvc-001-uploads} 目录。
     * 实际项目还应根据业务要求限制文件大小和允许的文件类型；客户端传来的原文件名和
     * {@code Content-Type} 都不能作为可信的安全校验依据。</p>
     */
    @PostMapping(value = "/profile", consumes = "multipart/form-data")
    public String updateProfileByMultipart(
            @RequestParam("username") String username,
            @RequestParam("avatar") MultipartFile avatar,
            Model model
    ) throws IOException {
        // 参数存在不代表用户一定选择了文件，空文件需要单独判断。
        if (avatar.isEmpty()) {
            throw new IllegalArgumentException("上传文件不能为空");
        }

        String originalFilename = avatar.getOriginalFilename();
        if (originalFilename == null || originalFilename.isBlank()) {
            throw new IllegalArgumentException("上传文件名不能为空");
        }

        String contentType = avatar.getContentType();
        long fileSize = avatar.getSize();

        Path uploadDirectory = Path.of(
                System.getProperty("java.io.tmpdir"),
                "springmvc-001-uploads"
        ).toAbsolutePath().normalize();
        Files.createDirectories(uploadDirectory);

        // 客户端文件名可能包含路径，只保留最后一段，并用 UUID 避免同名覆盖。
        Path filenamePath = Path.of(originalFilename.replace('\\', '/')).getFileName();
        if (filenamePath == null || filenamePath.toString().isBlank()) {
            throw new IllegalArgumentException("非法的上传文件名");
        }
        String safeOriginalFilename = filenamePath.toString();
        String storedFilename = UUID.randomUUID() + "-" + safeOriginalFilename;
        Path targetFile = uploadDirectory.resolve(storedFilename).normalize();
        if (!targetFile.startsWith(uploadDirectory)) {
            throw new IllegalArgumentException("非法的上传文件名");
        }

        // try-with-resources 会在复制完成或发生异常时自动关闭上传文件流。
        try (InputStream inputStream = avatar.getInputStream()) {
            Files.copy(inputStream, targetFile);
        }

        model.addAttribute("username", username);
        model.addAttribute("originalFilename", originalFilename);
        model.addAttribute("contentType", contentType);
        model.addAttribute("fileSize", fileSize);
        model.addAttribute("savedFile", targetFile.toString());
        return "first";
    }

    /**
     * 用于接收 JSON 请求体的数据对象。JSON 字段名需与组件名一致。
     */
    public record UserRequest(String username, String password) {
    }
}
