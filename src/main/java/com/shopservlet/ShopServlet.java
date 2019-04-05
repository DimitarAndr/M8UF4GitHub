package com.shopservlet;

import com.registeruser.RegisterServlet;
import utils.constants.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

@WebServlet("/shopServlet")
public class ShopServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {


        String[] productos = request.getParameterValues("checkBox");
        String paymentMethod = request.getParameter("paymentMethod");
        String nameClient = request.getParameter("nameClient");
        String comment = request.getParameter("comment");
        String quantity = request.getParameter("quantity");

        int amount = productos.length * (Integer.parseInt(quantity) * 2);
        StringBuilder bld = new StringBuilder();

        for (String product : productos) {
            bld.append(product+",");
        }
        String listProductos = bld.toString();
        listProductos = listProductos.substring(0, listProductos.length() - 1);

        Object data = null;

        if (nameClient.matches("[A-Za-z0-9]{1,10}")) {
            boolean existUser = false;
            try {
                existUser = checkExistUserInDB(nameClient);
            } catch (ClassNotFoundException | SQLException e) {
                Logger.getLogger(Constants.ERRORBD + e);
            }
            if (!existUser) {
                data = "No existe usuario con este Nick";
                request.setAttribute("data", data);
                request.getRequestDispatcher("rejected.jsp").forward(request, response);
            } else {
                try {
                    writeInDDBB(listProductos, nameClient, paymentMethod, comment, quantity, amount);
                } catch (ClassNotFoundException e) {
                    Logger.getLogger(Constants.ERRORBD + e);
                }
                data = "You will receive your product soon";
                request.setAttribute("data", data);
                request.getRequestDispatcher("ShopSuccess.jsp").forward(request, response);
            }

        } else {
            data = "Erroe en el Nick";
            request.setAttribute("data", data);
            request.getRequestDispatcher("rejected.jsp").forward(request, response);
        }
    }

    private boolean checkExistUserInDB(String nameClient) throws SQLException, ClassNotFoundException, IOException {
        return RegisterServlet.checkUserNameDB(nameClient);
    }

    private boolean writeInDDBB(String listProductos, String nameClient, String paymentMethod, String comment, String quantity, int amount) throws ClassNotFoundException {
        String url = "jdbc:mysql://localhost:3306/Tienda";
        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "");
        String sqlQueryInsert = "insert into compras(Nick,Products,Payment,Quantity,Amount,Comments)" + " values(?,?,?,?,?,?)";
        try (Connection conn = DriverManager.getConnection(url, props); PreparedStatement preparedStmt1 = conn.prepareStatement(sqlQueryInsert)) {
            preparedStmt1.setString(1, nameClient);
            preparedStmt1.setString(2, listProductos);
            preparedStmt1.setString(3, paymentMethod);
            preparedStmt1.setString(4, quantity);
            preparedStmt1.setInt(5, amount);
            preparedStmt1.setString(6, comment);
            preparedStmt1.execute();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(Constants.ERRORBD + e);
        }

        return false;
    }
}


