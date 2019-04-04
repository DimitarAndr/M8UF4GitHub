package com.registeruser;


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

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {


        String username = request.getParameter("username");
        String mail = request.getParameter("mail");
        String password = request.getParameter("password");

        Object data = null;

        boolean usernameValid = checkValidUsername(username);
        boolean mailValid = checkValidMail(mail);
        boolean passwordValid = checkValidPassword(password);

        if (usernameValid && mailValid && passwordValid) {
            boolean existUser = false;


            existUser = checkUserNameDB(username);

            if (!existUser) {
                writeInDDBB(username, mail, password, request, response);
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

    private boolean checkValidPassword(String password) {
        return (password.matches("^[a-zA-Z0-9]{8,}$"));
    }

    private boolean checkValidMail(String mail) {
        return (mail.matches("^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$"));
    }

    private boolean checkValidUsername(String username) {
        return (username.matches("[A-Za-z0-9]{1,10}"));
    }

    public static boolean checkUserNameDB(String username) {
        boolean existUser = false;
        Properties props = new Properties();
        String url = "jdbc:mysql://localhost:3306/Tienda";
        props.setProperty("user", "root");
        props.setProperty("pass", "");
        try (Connection conn = DriverManager.getConnection(url, props)) {
            String sqlQuery = "select count(username) from Usuario where username ='" + username + "'";
            PreparedStatement st = (PreparedStatement) conn.createStatement();

            existUser = existUsername(existUser, sqlQuery, st);
        } catch (Exception e) {
            Logger.getLogger("Error" + e);
        }
        return existUser;
    }

    private static boolean existUsername(boolean existUser, String sqlQuery, PreparedStatement st) {
        try (ResultSet rs = st.executeQuery(sqlQuery)) {

            int numberOfRows = 0;
            while (rs.next()) {
                numberOfRows = rs.getInt(1);
            }
            if (numberOfRows != 0) {
                existUser = true;
            }
        } catch (Exception ex) {
            Logger.getLogger("Exception " + ex);
        }
        return existUser;
    }


    public void writeInDDBB(String username, String mail, String password, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String data;
        Properties props = new Properties();
        String url = "jdbc:mysql://localhost:3306/Tienda";
        props.setProperty("user", "root");
        props.setProperty("pass", "");
        try (Connection conn = DriverManager.getConnection(url, props)) {

            String sqlQueryInsert = "insert into Usuario(username,mail,password)" + " values(?,?,?)";
            insertUsuario(username, mail, password, conn, sqlQueryInsert);

        } catch (SQLException e) {
            Logger.getLogger("Error " + e);

        } finally {

            data = "Success! Nuevo usuario: " + username;
            request.setAttribute("data", data);
            request.getRequestDispatcher("success.jsp").forward(request, response);
        }

    }

    private void insertUsuario(String username, String mail, String password, Connection conn, String sqlQueryInsert) {
        try (PreparedStatement preparedStmt1 = conn.prepareStatement(sqlQueryInsert)) {
            preparedStmt1.setString(1, username);
            preparedStmt1.setString(2, mail);
            preparedStmt1.setString(3, password);
            preparedStmt1.execute();
        } catch (Exception ignore) {
            Logger.getLogger("Error" + ignore);
        }
    }
}
