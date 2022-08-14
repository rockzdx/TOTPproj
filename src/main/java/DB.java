import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import java.util.Random;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.DriverManager;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.*;


public class DB {

    Hash h = new Hash();
    SecureRandom random = new SecureRandom();
    MessageDigest md = MessageDigest.getInstance("SHA-256");
    private static final String JDBC_CONNECTION_STRING = "jdbc:mysql://localhost:3306/project";

    private Connection connection = null;


    public DB() throws NoSuchAlgorithmException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");

        try {
            this.connection = DriverManager.getConnection(JDBC_CONNECTION_STRING, "root", "root123");
        } catch (SQLException var2) {
            this.error(var2);
        }
    }

    public void testfunc() {

        try {
            Statement s = this.connection.createStatement();
            ResultSet results = s.executeQuery("SELECT name from userAuth");

            while (results.next()) {

                String result = results.getString(results.findColumn("name"));
                System.out.println(result);

            }
        } catch (SQLException var4) {
            this.error(var4);
        }
    }

    public void registration(String username, String pass, String email){

        try {

            Statement s = this.connection.createStatement();

            byte[] bytes = new byte[20];
            random.nextBytes(bytes);
            Base32 base32 = new Base32();
            String passwordhashkey = base32.encodeToString(bytes);

            bytes = new byte[20];
            random.nextBytes(bytes);
            base32 = new Base32();
            String userotphash = base32.encodeToString(bytes);

            String sql = "insert into userAuth ( username ,pass , useridhash , userotphash , email ) values (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, pass);
            preparedStatement.setString(3, passwordhashkey);
            preparedStatement.setString(4, userotphash);
            preparedStatement.setString(5, email);
            int i = preparedStatement.executeUpdate();

        }

        catch (SQLException var4) {
            this.error(var4);
        }

    }
    public void login(String username, String pass){

        try {
            Statement s = this.connection.createStatement();
            String sql = "SELECT pass FROM userAuth WHERE username = '"+username+"';";

            String sqlpass="";
            ResultSet rs = s.executeQuery(sql);

            while (rs.next()) {
                sqlpass = rs.getString(1);
            }

            if(sqlpass.equals(pass)){
                //response 1
            }
            else{
                //response 0
            }

        }
        catch (SQLException var4) {
            this.error(var4);
        }
    }

    private void error(SQLException sqle) {
        System.err.println("Problem Opening Database! " + sqle.getClass().getName());
        sqle.printStackTrace();
        System.exit(1);
    }

}