package example.libraries.service;

import java.util.List;

import example.libraries.model.Book;

public interface IBookService {

    /// Get all books method contract
    public List<Book> getAllBooks();

    /// Get a book by ISBN method contract
    public Book getBook(String isbn);

    /// Delete a book by ISBN method contract
    public void deleteBook(String isbn);

    /// Save a book method contract
    public Book saveBook(Book book);

    /// Update a book method contract
    public Book updateBook(Book book);

}
