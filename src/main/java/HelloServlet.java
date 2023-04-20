import java.io.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            getServletContext().getRequestDispatcher("/WEB-INF/links.html").forward(request,response);
        }
        catch (Exception E){
            System.out.println(E);
        }
    }

    public void destroy() {
    }
}