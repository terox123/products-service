package products_service.products.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ProductDto {


    @NotEmpty(message = "Name of product can't be empty")
    private String name;

    @Min(value = 0, message = "Price can't be less than zero")
    private int price;

    @Min(value = 0, message = "Price can't be less than zero")
    private int quantity;

}
