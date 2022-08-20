import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

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
            if (uname != null && psw != null) {
                val = db.login(uname, psw);

                if (val) {

                    String hashc = db.returnhash(uname);
                    System.out.println("QR Code created successfully.");
                    getServletContext().getRequestDispatcher("/Mainpage.html").forward(request, response);
                    man(hashc);


                }
            }
            doGet(request, response);
        } catch (Exception e) {
            System.out.println(e);
        }

        //getServletContext().getRequestDispatcher("/Login.jsp").forward(request,response);
    }

    public void generateQRcode(String data, String path, String charset, Map map, int h, int w) throws WriterException, IOException {    //the BitMatrix class represents the 2D matrix of bits
        //MultiFormatWriter is a factory class that finds the appropriate Writer subclass for the BarcodeFormat requested and encodes the barcode with the supplied contents.
        BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE, w, h);
        MatrixToImageWriter.writeToFile(matrix, path.substring(path.lastIndexOf('.') + 1), new File(path));
    }

    public void man(String hash) throws WriterException, IOException, NotFoundException {
        //data that we want to store in the QR code
        String str = hash;
        //path where we want to get QR Code
        String path = "/Quote.png";
        //Encoding charset to be used
        String charset = "UTF-8";
        Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
        //generates QR code with Low level(L) error correction capability
        hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        //invoking the user-defined method that creates the QR code
        generateQRcode(str, path, charset, hashMap, 200, 200);//increase or decrease height and width accordingly
        //prints if the QR code is generated
        System.out.println("QR Code created successfully.");
    }

}

