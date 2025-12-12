package products_service.products.restController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import products_service.products.dto.ProductDto;
import products_service.products.model.Product;
import products_service.products.service.ProductsService;
import products_service.products.service.kafka.KafkaProducerService;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/products")
@Tag(name = "Продукты", description = "API для работы с продуктами")
public class ProductRestController {

    private final ProductsService productsService;
    private final ModelMapper modelMapper;
    private final KafkaProducerService kafkaProducerService;

    @Autowired
    public ProductRestController(ProductsService productsService, ModelMapper modelMapper, KafkaProducerService kafkaProducerService) {
        this.productsService = productsService;
        this.modelMapper = modelMapper;
        this.kafkaProducerService = kafkaProducerService;
    }

    @GetMapping("/test")
    public Map<String, Object> test (Authentication authentication){
        return Map.of("principal", authentication.getPrincipal(),
        "authorities", authentication.getAuthorities());
    }

    @GetMapping
    @Operation(summary = "Получить все продукты", description = "Возвращает список всех продуктов")
    public ResponseEntity<List<Product>> getAll(){
        List<Product> products = this.productsService.findAllProducts();

        if(products == null){
            kafkaProducerService.sendMessage("Orders", "p-service", "Not found products");
            throw new EntityNotFoundException();
        }

        else {
            kafkaProducerService.sendMessage("Orders", "p-service", "Products was presented");
            return ResponseEntity.ok(products);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить продукт по ID", description = "Возвращает продукт по идентификатору")
    public ResponseEntity<Product> getById(@PathVariable("id") long id){
    Product product = this.productsService.findById(id);
    if(product == null){
        throw new EntityNotFoundException();
    }
    else {

        return ResponseEntity.ok(product);
    }

    }


    @PostMapping
    @Operation(summary = "Создать продукт", description = "Создаёт новый продукт")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product, BindingResult bindingResult) {
if(bindingResult.hasErrors()){
    return ResponseEntity.badRequest().body(product);
}
else {
    this.productsService.save(product);
    kafkaProducerService.sendMessage("Orders", "p-service", "Product was saved");
    return ResponseEntity.status(HttpStatus.CREATED).body(product);
}
    }

    @PostMapping("/{id}")
    @Operation(summary = "Обновить продукт", description = "Обновляет продукт по ID")
    public ResponseEntity<Product> updateProduct(@Valid @RequestBody Product product, @PathVariable("id") long id, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().build();
        }
        this.productsService.update(id, product);
        return ResponseEntity.ok(product);

    }

    @DeleteMapping("/{id}/delete")
    @Operation(summary = "Удалить продукт", description = "Удаляет продукт по ID")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") long id){
        if(productsService.findById(id) == null){
            kafkaProducerService.sendMessage("Orders", "p-service", "Product with id" + id + " was not deleted");
            throw new EntityNotFoundException();
        }
        else {
            this.productsService.delete(id);
            kafkaProducerService.sendMessage("Orders", "p-service", "Product with id " + id + " was deleted");
            return ResponseEntity.noContent().build();
        }
    }




private Product convertToProduct(ProductDto productDto){
        return modelMapper.map(productDto, Product.class);
}

    private ProductDto convertToProductDto(Product product){
        return modelMapper.map(product, ProductDto.class);
    }

}
