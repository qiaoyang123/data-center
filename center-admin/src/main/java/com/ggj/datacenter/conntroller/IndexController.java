package com.ggj.datacenter.conntroller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: <a href="mailto:qiaoy@gegejia.com">qy</a>
 * @version: 1.0 2018/10/23 20:34
 * @since 1.0
 */
@Controller
public class IndexController {

    @Value("${front.serverUrl}")
    private String frontServerUrl;

    @RequestMapping(value = {"","/","login"})
    @ResponseBody
    public void index(HttpServletResponse response) throws IOException {
        response.sendRedirect(frontServerUrl);
    }
}
