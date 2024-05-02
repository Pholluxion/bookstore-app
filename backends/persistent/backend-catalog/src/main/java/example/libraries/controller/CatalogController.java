package example.libraries.controller;

import java.net.URI;
import java.util.List;

import example.libraries.model.Book;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import example.libraries.service.IBookService;

@RestController
@RequestMapping("/api")
@CrossOrigin(
        origins = "*",
        methods = {RequestMethod.GET,
                RequestMethod.POST,
                RequestMethod.PUT,
                RequestMethod.DELETE
        })
public class CatalogController {


    /// The book service
    private final IBookService bookService;

    /// Constructor injection
    CatalogController(IBookService service) {
        this.bookService = service;
    }

    /// Get all books
    @GetMapping("/getlibros")
    public ResponseEntity<List<Book>> getLibraries() {
        List<Book> booksList = bookService.getAllBooks();
        return ResponseEntity.ok(booksList);
    }

    /// Get a book by ISBN
    @GetMapping("/libro/{ISBN}")
    public ResponseEntity<Book> getBook(@PathVariable(value = "ISBN") String iSBN) {

        if (bookService.getBook(iSBN) == null)
            return ResponseEntity.badRequest().body(null);

        Book book = bookService.getBook(iSBN);
        return ResponseEntity.ok(book);
    }

    /// Save a book
    @PostMapping("libro")
    public ResponseEntity<Book> saveBook(@RequestBody Book book) {
        Book bookSaved = bookService.saveBook(book);
        final String uri = "/api/libro/" + bookSaved.getISBN();
        return ResponseEntity.created(URI.create(uri)).body(bookSaved);
    }

    /// Update a book
    @PutMapping("libro")
    public ResponseEntity<Book> updateBook(@RequestBody Book book) {
        if (bookService.getBook(book.getISBN()) == null)
            return ResponseEntity.badRequest().body(null);

        Book bookSaved = bookService.updateBook(book);
        return ResponseEntity.ok(bookSaved);
    }

    /// Delete a book
    @DeleteMapping("/deletelibro")
    public ResponseEntity<String> deleteBook(@RequestParam(value = "ISBN") String iSBN) {

        if (bookService.getBook(iSBN) == null)
            return ResponseEntity.badRequest().body("Book not found");

        bookService.deleteBook(iSBN);
        return ResponseEntity.ok("delete OK");
    }

}
