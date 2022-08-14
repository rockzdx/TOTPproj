import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Login", value = "/Login")
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/Login.jsp").forward(request,response);
        }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     String uname ="";
     String psw ="";
        boolean val =false;
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            uname = request.getParameter("uname");
             psw = request.getParameter("psw");

            DB db = new DB();
            //db.login(uname, psw);
            if( uname != null && psw != null ){
                 val = db.login(uname,psw);
            }
            doGet(request,response);
        }catch (Exception e){}
        if(val){
            getServletContext().getRequestDispatcher("/Mainpage.html").forward(request,response);
        }
        //getServletContext().getRequestDispatcher("/Login.jsp").forward(request,response);
    }

}

