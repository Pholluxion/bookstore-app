package store.backendstore.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import store.backendstore.model.Cart;
import store.backendstore.service.ICartService;
import store.backendstore.rabbitmq.MsjBroker;
import store.backendstore.rabbitmq.RabbitMQSender;

@RestController
@RequestMapping("/api")
@CrossOrigin(
        origins = "*",
        methods = {
                RequestMethod.GET,
                RequestMethod.POST,
                RequestMethod.DELETE
        })
public class StoreController {

    /// The cart service
    private final ICartService service;

    /// The RabbitMQ sender
    private final RabbitMQSender rabbitMQSender;

    private static final String STATUS_OK = "{\"status\":\"OK\"}";
    private static final String STATUS_ERROR = "{\"status\":\"ERROR\"}";

    /// Constructor injection
    StoreController(ICartService service, RabbitMQSender rabbitMQSender) {
        this.service = service;
        this.rabbitMQSender = rabbitMQSender;
    }

    /// Get cart by user
    @GetMapping("/getcart")
    public ResponseEntity<List<Cart>> getCartUser(@RequestParam(value = "usuario") String nombreuser) {
        List<Cart> booksList = service.getCart(nombreuser);
        return new ResponseEntity<>(booksList, HttpStatus.OK);
    }

    /// Add a book to the cart
    @PostMapping("/addcart")
    public ResponseEntity<String> postCart(
            @RequestParam(value = "usuario") String nombreuser,
            @RequestParam(value = "isbn") String isbn,
            @RequestParam(value = "cantidad", defaultValue = "1") int cantidad) {

        service.addCartUser(isbn, nombreuser, cantidad);
        return new ResponseEntity<>(STATUS_OK, HttpStatus.OK);
    }

    /// Delete a book from the cart
    @DeleteMapping("/deletecart")
    public ResponseEntity<String> deleteCart(@RequestParam(value = "isbn") String isbn,
                                             @RequestParam(value = "usuario") String nombreuser) {
        service.deleteCartUser(isbn, nombreuser);
        return new ResponseEntity<>(STATUS_OK, HttpStatus.OK);
    }

    /// Buy the cart
    @PostMapping("/buycart")
    public ResponseEntity<String> buyCart(@RequestParam(value = "usuario") String nombreuser) {
        List<Cart> cart = service.getCart(nombreuser);
        var sendMsj = rabbitMQSender.send(new MsjBroker(nombreuser, cart));
        if (sendMsj) {
            service.deleteAllCartUser(nombreuser);
            return new ResponseEntity<>(STATUS_OK, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(STATUS_ERROR, HttpStatus.OK);
        }
    }
}