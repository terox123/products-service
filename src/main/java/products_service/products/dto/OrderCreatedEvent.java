package products_service.products.dto;

import java.util.List;

public class OrderCreatedEvent {
    private Long orderId;
    private Long customerId;
    private List<Long> productIds;

    public OrderCreatedEvent(Long orderId, Long customerId, List<Long> productIds) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.productIds = productIds;
    }

    public OrderCreatedEvent(){

    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public List<Long> getProductIds() {
        return productIds;
    }
}
