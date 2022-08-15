package controllerWeb;

import model.Product;
import DAO.ProductDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SearchController", value = "/search")
public class SearchController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String textSearch = request.getParameter("text");

        ProductDAO productService = new ProductDAO();
        List<Product> products = productService.searchByNameProducts(textSearch);
        request.setAttribute("products", products);
        request.getRequestDispatcher("/customer/product-list.jsp").forward(request, response);
    }
}
