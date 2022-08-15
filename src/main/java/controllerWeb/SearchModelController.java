package controllerWeb;

import DAO.CategoryDAO;
import DAO.ProductDAO;
import model.Categories;
import model.Model;
import model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SearchModelController", value = "/search-model")
public class SearchModelController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String textSearch = request.getParameter("category");

        CategoryDAO categoryDAO = new CategoryDAO();
        Model model = categoryDAO.getModelByName(textSearch);
        Categories categories = categoryDAO.getItemCategory(model.getCategoryID());
        List<Model> models = categoryDAO.getAllModelByCategoryID(model.getCategoryID());

        ProductDAO productDAO = new ProductDAO();
        List<Product> products = productDAO.findAllProductByModelID(model.getId());

        request.setAttribute("category", categories);
        request.setAttribute("models", model);
        request.setAttribute("products", products);
        request.getRequestDispatcher("/web/productView.jsp").forward(request, response);
    }
}
