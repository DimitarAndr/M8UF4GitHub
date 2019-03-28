import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {


        String username = request.getParameter("username");
        String mail = request.getParameter("mail");
        String password = request.getParameter("password");


        boolean success = false;
        Object data = null;

        if (username.matches("[A-Za-z0-9]{1,10}")) {

            if (mail.matches("^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$")) {

                if (password.matches("^[a-zA-Z0-9]{8,}$")) {
                    success = true;
                } else {
                    data = "Error en el password";
                }

            } else {
                data = "Error en el Mail";
            }

        } else {
            data = "Error en el Nick";

        }
        if (success) {
            boolean existUser = false;
            try {
                existUser = checkExistUserInDB(username);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (!existUser) {
                try {
                    writeInDDBB(username, mail, password);
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    data = "Success! Nuevo usuario: " + username;
                    request.setAttribute("data", data);
                    request.getRequestDispatcher("success.jsp").forward(request, response);
                }
            }else{
                data="Este usuario existe, elige otro";
                request.setAttribute("data", data);
                request.getRequestDispatcher("success.jsp").forward(request, response);
            }

        } else {
            request.setAttribute("data", data);
            request.getRequestDispatcher("rejected.jsp").forward(request, response);
        }
    }

    private boolean checkExistUserInDB(String username) throws ClassNotFoundException, SQLException {

        return checkUserNameDB(username);
    }

    static boolean checkUserNameDB(String username) throws ClassNotFoundException, SQLException {
        boolean existUser = false;

        Class.forName("com.mysql.jdbc.Driver");

        String url1 = "jdbc:mysql://localhost:3306/Tienda";

        Connection conn = null;

        conn = DriverManager.getConnection(url1, "root", "");

        String sqlQuery = "select count(username) from Usuario where username ='" + username + "'";
        Statement st = conn.createStatement();

        ResultSet rs = st.executeQuery(sqlQuery);

        int numberOfRows=0;
        while (rs.next()) {
             numberOfRows = rs.getInt(1);
        }
        if (numberOfRows != 0) {
            existUser = true;
        }
        conn.close();
        return existUser;
    }


    public void writeInDDBB(String username, String mail, String password) throws SQLException {
        boolean existUser = false;
        try {
            Class.forName("com.mysql.jdbc.Driver");

            String url1 = "jdbc:mysql://localhost:3306/Tienda";

            Connection conn = null;

            conn = DriverManager.getConnection(url1, "root", "");

            String sqlQueryInsert = "insert into Usuario(username,mail,password)" + " values(?,?,?)";
            PreparedStatement preparedStmt1 = conn.prepareStatement(sqlQueryInsert);
            preparedStmt1.setString(1, username);
            preparedStmt1.setString(2, mail);
            preparedStmt1.setString(3, password);
            preparedStmt1.execute();

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
