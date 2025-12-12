package products_service.products.restController;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import products_service.products.exceptions.QuantityOfProductException;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> entityNotFoundExceptionHandler(){

    return new ResponseEntity<>("Entity with this id was not found", HttpStatusCode.valueOf(400));
}

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> illegalArgumentExceptionHandler(){
    return new ResponseEntity<>("Check your request, invalid data", HttpStatusCode.valueOf(400));

}

@ExceptionHandler(QuantityOfProductException.class)
public ResponseEntity<String> quantityOfProductExceptionHandler(){
        return new ResponseEntity<>("Quantity of product is <= 0", HttpStatusCode.valueOf(400));
}

}
