package products_service.products.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import products_service.products.model.Product;
import products_service.products.repositories.ProductsRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductsService {

    private final ProductsRepository productsRepository;


    @Autowired
    public ProductsService(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    @Cacheable(value = "products")
    public List<Product> findAllProducts(){
        return productsRepository.findAll();
    }

    @Cacheable(value = "products", key = "#id")
    public Product findById(long id){
        return productsRepository.findById(id)
            .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    @CachePut(value = "products", key = "#product.id")
    public Product save(Product product){
        return productsRepository.save(product);

    }

    @Transactional
    @CachePut(value = "products", key = "#id")
    public Product update(long id, Product updatedProduct){
        if(productsRepository.findById(id).isEmpty()){
            throw new EntityNotFoundException();
        }
        updatedProduct.setId(id);
        return productsRepository.save(updatedProduct);

    }


    @CacheEvict(value = "products", key = "#id")
    @Transactional
    public void delete(long id){
        if(productsRepository.findById(id).isEmpty()){
            throw new EntityNotFoundException();
        }
        productsRepository.deleteById(id);

    }



}
