package controllerWeb.Cart;

import DAO.CartDAO;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "CartAllDeleteController", value = "/cart-all-delete")
public class CartAllDeleteController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        User user = (User) httpSession.getAttribute("user");

        CartDAO cartDAO = new CartDAO();
        Boolean delete = cartDAO.deleteAllProductFromCart(user.getId());


        request.setAttribute("isShow", true);
        String registerSuccess = (String) request.getAttribute("messageSuccess");
        request.setAttribute("messageSuccess", registerSuccess);
        request.getRequestDispatcher("/cart").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
