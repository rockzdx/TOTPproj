import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.json.simple.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
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
        try {
            uname = request.getParameter("uname");
            psw = request.getParameter("psw");

            DB db = new DB();
            //db.login(uname, psw);
            if (uname != null && psw != null) {
                val = db.login(uname, psw);

                if (val) {
                    String hashc=db.returnhash(uname);
                    man(hashc, uname);
//                     System.out.println(path);
                    request.setAttribute("uname",uname);
                    System.out.println("testing indb");

                    String hashJson =db.retrieveAllHashKey(uname);
                    System.out.println(db.retrieveUserID(uname));

//                    JsonObject jsonObject = new JsonParser().parse(hashJson).getAsJsonObject();

                    //jsonnnnnnnnn
//                    String valueOfAmazon = jsonObject.get("amazon").getAsString(); //John


//                    getServletContext().getRequestDispatcher("/Mainpage.jsp").forward(request, response);
                    /////////



                    String filename = "/qrcodefolder/QR_" + uname + ".png";
                    response.setContentType("image/png");
                    File file = new File(filename);
                    response.setContentLength((int)file.length());

                    FileInputStream in = new FileInputStream(file);//QRiamge file variable
                    OutputStream outstr = response.getOutputStream();

                    // Copy the contents of the file to the output stream
                    byte[] buf = new byte[1024];
                    int count = 0;
                    while ((count = in.read(buf)) >= 0) {
                        outstr.write(buf, 0, count);
                    }
                    outstr.close();
                    in.close();



                }
            }
            doGet(request, response);
        } catch (Exception e) {
            System.out.println(e);
        }

        //getServletContext().getRequestDispatcher("/Login.jsp").forward(request,response);
    }

    public void generateQRcode(String data, String charset, Map map, int h, int w, File file) throws WriterException, IOException {    //the BitMatrix class represents the 2D matrix of bits
        //MultiFormatWriter is a factory class that finds the appropriate Writer subclass for the BarcodeFormat requested and encodes the barcode with the supplied contents.
        BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE, w, h);
        MatrixToImageWriter.writeToFile(matrix, "png", file);
    }

    public void man(String hash, String uname) throws WriterException, IOException, NotFoundException {
        //data that we want to store in the QR code
        String str = hash;
        //path where we want to get QR Code
        //String path = getServletContext().getRealPath("WEB-INF" )+ "\\Quote_" + uname + ".png";
        //String path = "C:\\Users\\Vishwa\\IdeaProjects\\TOTPgf\\src\\main\\webapp\\Quote.png";
        File file = new File("/qrcodefolder");
        //Creating the directory
        if(!file.exists())
        {
            file.mkdirs();
        }

//        if(bool){
//            System.out.println("Directory created successfully");
//        }else{
//            System.out.println("Sorry couldn’t create specified directory");
//        }

        String filePath = "/qrcodefolder/QR_" + uname + ".png";

        file = new File(filePath);

        //Encoding charset to be used
        String charset = "UTF-8";
        Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
        //generates QR code with Low level(L) error correction capability
        hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        //invoking the user-defined method that creates the QR code
        generateQRcode(str, charset, hashMap, 200, 200, file);//increase or decrease height and width accordingly
        //prints if the QR code is generated
        System.out.println("QR Code created successfully.");
    }

}

