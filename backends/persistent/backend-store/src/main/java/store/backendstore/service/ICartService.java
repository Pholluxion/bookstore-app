package store.backendstore.service;

import java.util.List;

import store.backendstore.model.Cart;

public interface ICartService {

    /// Get cart by user method contract
    public List<Cart> getCart(String user);

    /// Add a book to the cart method contract
    public void addCartUser(String isbn, String nameuser, int cantidad);

    /// Delete a book from the cart method contract
    public void deleteCartUser(String isbn, String nameuser);

    /// Delete all books from the cart method contract
    public void deleteAllCartUser(String username);
}
