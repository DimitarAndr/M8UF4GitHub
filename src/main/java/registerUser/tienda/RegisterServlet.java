package registerUser.tienda;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    @Override
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
            } else {
                data = "Este usuario existe, elige otro";
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

    public static boolean checkUserNameDB(String username) throws ClassNotFoundException, SQLException {

        boolean existUser = false;
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn;
        String url = "jdbc:mysql://localhost:3306/Tienda";
        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "");
        conn=  DriverManager.getConnection(url, props);
        try {
            existUser = false;

            String sqlQuery = "select count(username) from Usuario where username ='" + username + "'";
            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery(sqlQuery);

            int numberOfRows = 0;
            while (rs.next()) {
                numberOfRows = rs.getInt(1);
            }
            if (numberOfRows != 0) {
                existUser = true;
            }


        } catch (Exception e) {
            System.out.println(e);
        } finally {

            conn.close();
        }

        return existUser;
    }


    public void writeInDDBB(String username, String mail, String password) throws SQLException {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");

            String url1 = "jdbc:mysql://localhost:3306/Tienda";

            conn = DriverManager.getConnection(url1, "root", "");

            String sqlQueryInsert = "insert into Usuario(username,mail,password)" + " values(?,?,?)";
            PreparedStatement preparedStmt1 = conn.prepareStatement(sqlQueryInsert);
            preparedStmt1.setString(1, username);
            preparedStmt1.setString(2, mail);
            preparedStmt1.setString(3, password);
            preparedStmt1.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }

    }
}
