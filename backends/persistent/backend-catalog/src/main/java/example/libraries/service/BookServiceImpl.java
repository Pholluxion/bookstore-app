package example.libraries.service;

import java.util.List;

import example.libraries.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import example.libraries.model.Book;

@Service
public class BookServiceImpl implements IBookService {

    /// The book repository
    private final BookRepository bookRepository;

    /// Constructor injection
    BookServiceImpl(BookRepository repo) {
        this.bookRepository = repo;
    }

    /// Get all books implementation
    @Override
    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    /// Get a book by ISBN implementation
    @Override
    public Book getBook(String isbn) {
        return bookRepository.findBookByISBN(isbn);
    }

    /// Delete a book by ISBN implementation
    @Override
    @Transactional(readOnly = false)
    public void deleteBook(String isbn) {
        bookRepository.deleteByISBN(isbn);
    }

    /// Save a book implementation
    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    /// Update a book implementation
    @Override
    public Book updateBook(Book book) {
        var bookToUpdate = bookRepository.findBookByISBN(book.getISBN());
        bookToUpdate.setAutor(book.getAutor());
        bookToUpdate.setDescripcion(book.getDescripcion());
        bookToUpdate.setTitulo(book.getTitulo());
        bookToUpdate.setUnidades(book.getUnidades());
        bookToUpdate.setValor(book.getValor());
        return bookRepository.save(bookToUpdate);
    }

}
