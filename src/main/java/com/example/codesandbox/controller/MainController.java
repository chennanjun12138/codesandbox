package com.example.codesandbox.controller;
import com.example.codesandbox.CDockerCodeSandbox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.codesandbox.JavaDockerCodeSandbox;
import com.example.codesandbox.JavaNativeCodeSandbox;
import com.example.codesandbox.model.ExecuteCodeRequest;
import com.example.codesandbox.model.ExecuteCodeResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class MainController {


    // 定义鉴权请求头和密钥
    private static final String AUTH_REQUEST_HEADER = "auth";

    private static final String AUTH_REQUEST_SECRET = "secretKey";

    @GetMapping("/health")
    public String healthCheck() {
        System.out.println("连接传输成功");
        return "ok";
    }
    @Resource
    private CDockerCodeSandbox cDockerCodeSandbox;

    @Resource
    private JavaDockerCodeSandbox javaDockerCodeSandbox;
    /**
     * 执行代码
     *
     * @param executeCodeRequest
     * @return
     */
    @PostMapping("/executeCode")
    ExecuteCodeResponse executeCode(@RequestBody ExecuteCodeRequest executeCodeRequest, HttpServletRequest request,
                                    HttpServletResponse response
    ) {
        System.out.println("连接传输成功");
        String authHeader = request.getHeader(AUTH_REQUEST_HEADER);
        if (!AUTH_REQUEST_SECRET.equals(authHeader)) {
            response.setStatus(403);
            return null;
        }

        if (executeCodeRequest == null) {
            throw new RuntimeException("请求参数为空");
        }
        String  language=executeCodeRequest.getLanguage();
        if("java".equals(language))
        {
            return javaDockerCodeSandbox.executeCode(executeCodeRequest);
        }
        else if("c".equals(language))
        {
            return cDockerCodeSandbox.executeCode(executeCodeRequest);

        }
        else
        {
            return null;
        }
    }

}
