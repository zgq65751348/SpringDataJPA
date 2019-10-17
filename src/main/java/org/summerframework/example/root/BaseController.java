package org.summerframework.example.root;

import com.opensymphony.xwork2.ActionSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * <p>
 *
 * </p>
 * windows 7  旗舰版
 *
 * @author 墨茗琦妙
 * @since 2019/10/14 0014 上午 9:24
 */

@RestController
@Scope("prototype")
public class BaseController extends ActionSupport {

    private static final long serialVersionUID = 1L;

    @Autowired
    private HttpServletResponse response;

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public void sendReponeMessage(String msg) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.write(msg);
        out.close();
    }
}
