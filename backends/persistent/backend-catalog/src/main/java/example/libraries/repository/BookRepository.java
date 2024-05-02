package example.libraries.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import example.libraries.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {

    /// Delete a book by ISBN method
    public void deleteByISBN(String isbn);

    /// Find a book by ISBN method
    public Book findBookByISBN(String isbn);
}
