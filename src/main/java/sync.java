import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "sync", value = "/sync")
public class sync extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/sync.jsp").forward(request,response);

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
            System.out.println("testies");
            DB db = new DB();

            if (uname != null && psw != null) {
                val = db.login(uname, psw);

                if (val) {

                    String hashJson =db.retrieveAllHashKey(uname);
                    System.out.println(hashJson);
                    System.out.println(hashJson);System.out.println(hashJson);
                    System.out.println(hashJson);


                    response.setContentType("text/html");

                    out.append(hashJson);
                    out.close();

                }
            }
            doGet(request, response);
        } catch (Exception e) {
            System.out.println(e);
        }




    }
}
