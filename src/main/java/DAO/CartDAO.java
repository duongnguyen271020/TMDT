package DAO;

import mode_utility.DBConnect;
import model.Cart;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CartDAO {

    public Cart findProductIDByUserID(Long userId, Long productID) {
        Cart cart = new Cart();

        String sql = "select * from Cart where UserID = ? and ProductID = ?";
        try {
            PreparedStatement preparedStatement = DBConnect.connect().getConnection().prepareStatement(sql);
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, productID);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                cart.setId(rs.getLong("ID"));
                cart.setQuantity(rs.getInt("Quantity"));
                cart.setUserID(rs.getLong("UserID"));
                cart.setUserName(rs.getString("UserName"));
                cart.setProductID(rs.getLong("ProductID"));
                cart.setProductName(rs.getString("ProductName"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.getMessage();
        }
        return cart;
    }

    public boolean deleteProductFromCart(Long userId, Long productId, Integer quantity) {
        Cart cart = findProductIDByUserID(userId, productId);
        String sql = "";
        if (cart.getQuantity() > quantity) {
            sql = "update Cart set Quantity = ? where UserID = ? and ProductID = ?";
        } else {
            sql = "delete from Cart where UserID = ? and ProductID = ?";
        }
        try {
            PreparedStatement preparedStatement = DBConnect.connect().getConnection().prepareStatement(sql);

            if (cart.getQuantity() > quantity) {
                preparedStatement.setLong(1, cart.getQuantity() - quantity);
                preparedStatement.setLong(2, userId);
                preparedStatement.setLong(3, productId);
            } else {
                preparedStatement.setLong(1, userId);
                preparedStatement.setLong(2, productId);
            }
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            e.getMessage();
            return false;
        }
    }

    public boolean deleteAllProductFromCart(Long userId) {
        String sql = "delete from Cart where UserID = ?";
        try {
            PreparedStatement statement = DBConnect.connect().getConnection().prepareStatement(sql);
            statement.setLong(1, userId);
            statement.executeUpdate();
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            e.getMessage();
            return false;
        }
    }

    public boolean addProductToCart(Long userId, Long productId, String userName, String productName, Double amount) {
        Cart cart = findProductIDByUserID(userId, productId);
        //  Tìm giỏ hàng, nếu null thì thêm mới, có rồi thì update
        String sql = "";
        if (cart != null) {
            sql = "update Cart set Quantity = ?, Amount = ? where UserID = ? and ProductID = ?";
        } else {
            sql = "insert into Cart (Quantity, Amount, UserID, ProductID, UserName, ProductName) values (?, ?, ?, ?, ?, ?)";
        }
        try {
            PreparedStatement statement = DBConnect.connect().getConnection().prepareStatement(sql);
            statement.setInt(1, (cart == null ? 0 : cart.getQuantity()) + 1);
            statement.setDouble(2, cart.getAmount() + amount);
            statement.setLong(3, userId);
            statement.setLong(4, productId);
            if (cart == null) {
                statement.setString(5, userName);
                statement.setString(6, productName);
            }
            statement.executeUpdate();
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            e.getMessage();
            return false;
        }
    }
}
