import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "otpCheckServlet", value = "/otpCheck")
public class otpCheckServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/otpcheck.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            Hash hs = new Hash();
            DB db = new DB();
            String uname = request.getParameter("uname");
            String hashkey = db.retrieveAllHashKey(uname);
            System.out.println(db.returnhash(uname));
            System.out.println(hs.getTOTPCode(db.returnhash(uname)));

            doGet(request, response);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
