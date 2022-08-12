import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "SignIn", value = "/SignIn")
public class SignIn extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/SignIn.jsp").forward(request,response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uname = request.getParameter("uname");
        String email = request.getParameter("email");
        String psw = request.getParameter("psw");
        String repeat_psw = request.getParameter("psw-repeat");
       if(uname == null || email == null || psw == null || repeat_psw == null){
           request.setAttribute("error","Missing Input");
           doGet(request,response);
       }
       else {
           if (!psw.equalsIgnoreCase(repeat_psw)) {

               request.setAttribute("error", "The password does not match");
               doGet(request, response);
           }
       }

    }
}
