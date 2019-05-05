package com.registeruser;


import utils.constants.Constants;
import utils.propertiestienda.PropertiesTienda;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;


@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

/*Author: Dimitar Andreev
* Date: 05.05.2019
* M8 - UF3 i UF4: Aplicacions web / Control de versions i documentació
* RegisterServlet.class: Servlet for registering
* new Clients */


    @Override

    /*A method with the main logic
     *that handles requests and responses*/

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {


        /*Taking values from a properties file
         to use them in the database connection*/
        Properties prop = PropertiesTienda.getPropertiesDDBB();


        String username = request.getParameter("username");
        String mail = request.getParameter("mail");
        String password = request.getParameter("password");

        Object data = null;

        boolean usernameValid = checkValidUsername(username);
        boolean mailValid = checkValidMail(mail);
        boolean passwordValid = checkValidPassword(password);

        /*Adding sql connector value*/
        addClassForName();

        if (usernameValid && mailValid && passwordValid) {
            boolean existUser = false;

                existUser = checkUserNameDB(username, prop);

            if (!existUser) {
                try {
                    writeInDDBB(username, mail, password, request, response, prop);
                } catch (Exception e) {
                    Logger.getLogger(Constants.ERRORBD + e);
                }
            } else {
                data = "Este usuario existe, elige otro";
                request.setAttribute("data", data);
                request.getRequestDispatcher("success.jsp").forward(request, response);
            }

        } else {
            if (!usernameValid) {
                data = "Error en el user";
            }
            if (!mailValid) {
                data = "Error en el mail";
            }
            if (!passwordValid) {
                data = "Error en el password";
            }
            request.setAttribute("data", data);
            request.getRequestDispatcher("rejected.jsp").forward(request, response);
        }

    }


    public static boolean checkUserNameDB(String user, Properties prop) throws IOException {
        boolean existUser = false;

        String sqlQuery = "select count(username) from Usuario where username = ?";

        try (Connection conn = DriverManager.getConnection(prop.getProperty("servidor.url"), prop.getProperty("servidor.usuario"),
                prop.getProperty("servidor.password")); PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery)) {

            preparedStatement.setString(1, user);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                int numberOfRows = 0;
                while (rs.next()) {
                    numberOfRows = rs.getInt(1);
                }
                if (numberOfRows != 0) {
                    existUser = true;
                }
            }

        } catch (Exception e) {
            Logger.getLogger("Error" + e);
        }
        return existUser;
    }

    public void writeInDDBB(String username, String mail, String password, HttpServletRequest request, HttpServletResponse response, Properties prop) throws ServletException, IOException {

        String data;
        String sqlQueryInsert = "insert into Usuario(username,mail,password)" + " values(?,?,?)";
        try (Connection conn = DriverManager.getConnection(prop.getProperty("servidor.url"), prop.getProperty("servidor.usuario"),
                prop.getProperty("servidor.password")); PreparedStatement preparedStmt1 = conn.prepareStatement(sqlQueryInsert)) {
            preparedStmt1.setString(1, username);
            preparedStmt1.setString(2, mail);
            preparedStmt1.setString(3, password);
            preparedStmt1.execute();

            data = "Success! Nuevo usuario: " + username;
            request.setAttribute("data", data);
            request.getRequestDispatcher("success.jsp").forward(request, response);

        } catch (SQLException e) {
            Logger.getLogger(Constants.ERRORBD + e);
        }

    }

    private boolean checkValidPassword(String password) {
        return (password.matches("^[a-zA-Z0-9]{8,}$"));
    }

    private boolean checkValidMail(String mail) {
        return (mail.matches("^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$"));
    }

    private boolean checkValidUsername(String username) {
        return (username.matches("[A-Za-z0-9]{1,10}"));
    }

    public static void addClassForName() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            Logger.getLogger(Constants.ERRORBD + e);
        }
    }

}
