import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet(name = "QRImgStreamServlet", value = "/getQRCodeImgStream")
public class QRImgStreamServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {

        String uname = request.getParameter("uname");


        ServletContext cntx= request.getServletContext();
        // Get the absolute path of the image /foldername/iamgefilename
        String filename = "/qrcodefolder/QR_" + uname + ".png";
        // retrieve mimeType dynamically
//        String mime = cntx.getMimeType(filename);

//        resp.setContentType(mime);
        resp.setContentType("image/png");
        File file = new File(filename);
        resp.setContentLength((int)file.length());

        FileInputStream in = new FileInputStream(file);//QRiamge file variable
        OutputStream out = resp.getOutputStream();

        // Copy the contents of the file to the output stream
        byte[] buf = new byte[1024];
        int count = 0;
        while ((count = in.read(buf)) >= 0) {
            out.write(buf, 0, count);
        }
        out.close();
        in.close();

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
