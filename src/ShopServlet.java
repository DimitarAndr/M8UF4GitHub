import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet("/shopServlet")
public class ShopServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {


        String productos[] = request.getParameterValues("checkBox");


        String paymentMethod = request.getParameter("paymentMethod");
        String nameClient = request.getParameter("nameClient");
        String comment = request.getParameter("comment");
        String quantity = request.getParameter("quantity");

        int amount = productos.length*(Integer.parseInt(quantity)*2);
        String listProductos="";
        for(String product : productos){
            listProductos+=product+",";
        }
       listProductos=listProductos.substring(0, listProductos.length() - 1);

        boolean success = false;
        Object data = null;

        if (nameClient.matches("[A-Za-z0-9]{1,10}")) {
            success = true;
        } else {
            data = "Erroe en el Nick";
            request.setAttribute("data", data);
            request.getRequestDispatcher("rejected.jsp").forward(request, response);
        }
        if (success) {
            boolean existUser = false;
            try {
                existUser = checkExistUserInDB(nameClient);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (!existUser) {
                data = "No existe usuario con este Nick";
                request.setAttribute("data", data);
                request.getRequestDispatcher("rejected.jsp").forward(request, response);
            }else{
                writeInDDBB(listProductos,nameClient,paymentMethod,comment,quantity,amount);
                data = "You will receive your product soon";
                request.setAttribute("data", data);
                request.getRequestDispatcher("ShopSuccess.jsp").forward(request, response);
            }

        }
    }

    private void writeInDDBB(String listProductos,String nameClient, String paymentMethod, String comment, String quantity,int amount) {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            String url1 = "jdbc:mysql://localhost:3306/Tienda";

            Connection conn = null;

            conn = DriverManager.getConnection(url1, "root", "");

            String sqlQueryInsert = "insert into compras(Nick,Products,Payment,Quantity,Amount,Comments)" + " values(?,?,?,?,?,?)";
            PreparedStatement preparedStmt1 = conn.prepareStatement(sqlQueryInsert);
            preparedStmt1.setString(1, nameClient);
            preparedStmt1.setString(2, listProductos);
            preparedStmt1.setString(3, paymentMethod);
            preparedStmt1.setString(4, quantity);
            preparedStmt1.setInt(5, amount);
            preparedStmt1.setString(6, comment);
            preparedStmt1.execute();

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private boolean checkExistUserInDB(String nameClient) throws ClassNotFoundException, SQLException {
        return RegisterServlet.checkUserNameDB(nameClient);
    }
}

