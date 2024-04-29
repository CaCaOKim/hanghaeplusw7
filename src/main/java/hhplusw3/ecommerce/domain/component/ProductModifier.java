package hhplusw3.ecommerce.domain.component;

import hhplusw3.ecommerce.domain.model.Product;
import hhplusw3.ecommerce.domain.reository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductModifier {

    private final ProductRepository productRepository;

    @Autowired
    public ProductModifier(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product updateProduct(Product product) {
        Product result = this.productRepository.updateProduct(product);
        if (result == null) {
            result = new Product(0, null, 0, 0, 0);
        }
        return result;
    }
}
