package store.backendstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import store.backendstore.model.Cart;

@Repository
public interface CartRepository extends CrudRepository<Cart, String> {
   /// Get cart by user method
    @Query(value = "CALL GetCartUser(:user)", nativeQuery = true)
    public List<Cart> getCartUser(@Param("user") String user);

    /// Add a book to the cart method
    @Modifying
    @Query(value = "CALL AddCartUser(:usuario, :isbn, :cant)", nativeQuery = true)
    public void addCart(@Param("isbn") String isbn, @Param("usuario") String user, @Param("cant") int cant);

    /// Delete a book from the cart method
    @Modifying
    @Query(value = "CALL DeleteCartUser(:usuario, :isbn)", nativeQuery = true)
    public void deleteCart(@Param("isbn") String isbn, @Param("usuario") String user);

    /// Delete all books from the cart method
    @Modifying
    @Query(value = "CALL DeleteAllCartUser(:usuario)", nativeQuery = true)
    public void deleteAllCart(@Param("usuario") String user);
}
