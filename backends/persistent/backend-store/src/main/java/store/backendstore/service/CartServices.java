package store.backendstore.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import store.backendstore.model.Cart;
import store.backendstore.repository.CartRepository;

@Service
public class CartServices implements ICartService {

    /// The cart repository
    private final CartRepository cartRepository;

    /// Constructor injection
    CartServices(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    /// Get cart by user implementation
    @Override
    public List<Cart> getCart(String user) {
        return cartRepository.getCartUser(user);
    }

    /// Add a book to the cart implementation
    @Override
    @Transactional(readOnly = false)
    public void addCartUser(String isbn, String nameuser, int cantidad) {
        cartRepository.addCart(isbn, nameuser, cantidad);
    }

    /// Delete a book from the cart implementation
    @Override
    @Transactional(readOnly = false)
    public void deleteCartUser(String isbn, String nameuser) {
        cartRepository.deleteCart(isbn, nameuser);
    }

    /// Delete all books from the cart implementation
    @Override
    @Transactional(readOnly = false)
    public void deleteAllCartUser(String nameuser) {
        cartRepository.deleteAllCart(nameuser);
    }

}
