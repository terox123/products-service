package products_service.products.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import products_service.products.model.Product;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Long> {

    boolean existsById(long id);
    
}
