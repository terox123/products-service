package products_service.products.service.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import products_service.products.dto.OrderCreatedEvent;
import products_service.products.exceptions.QuantityOfProductException;
import products_service.products.service.ProductsService;

@Service
public class KafkaListenerService {

    private final ProductsService productsService;
    private final ObjectMapper objectMapper;

    @Autowired
    public KafkaListenerService(ProductsService productsService, ObjectMapper objectMapper) {
        this.productsService = productsService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "Order", groupId = "products-service")
    public void listen(String message){
        try{
            OrderCreatedEvent event = objectMapper.readValue(message, OrderCreatedEvent.class);

            for(Long productId: event.getProductIds()) {
                var product = productsService.findById(productId);
                if (product.getQuantity() <= 0) {
                    throw new QuantityOfProductException();
                } else {
                    product.setQuantity(product.getQuantity() - 1);
                    productsService.update(productId, product);
                    System.out.println("Product quantity with id " + productId + " was decreased");
                }
            }
        }catch (Exception ex){
            throw new RuntimeException("Failed to listen message" + ex.getMessage());
        }

    }

}
