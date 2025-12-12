package products_service.products.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "products")
@Data
@Getter
@Setter
public class Product {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID продукта", example = "1")
    private long id;

    @Column(name = "name")
    @NotEmpty(message = "Name of product can't be empty")
    @Schema(description = "Название продукта", example = "Laptop")
    private String name;

    @Column(name = "price")
    @Schema(description = "Цена продукта", example = "1000")
    @Min(value = 0, message = "Price can't be less than zero")
    private int price;

    @Column(name = "quantity")
    @Min(value = 0, message = "Price can't be less than zero")
    @Schema(description = "Количество продукта на складе", example = "10")
    private int quantity;


    public Product(){

    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
