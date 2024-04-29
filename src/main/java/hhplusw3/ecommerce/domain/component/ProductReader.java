package hhplusw3.ecommerce.domain.component;

import hhplusw3.ecommerce.domain.model.Product;
import hhplusw3.ecommerce.domain.reository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductReader {

    private final ProductRepository productRepository;

    @Autowired
    public ProductReader(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProduct(long id) {
        Product product = this.productRepository.getProduct(id);
        if (product == null) {
            product = new Product(0, null, 0, 0, 0);
        }
        return product;
    }

    public List<Product> getTopProducts(long topNum, String soldOutYn) {
        List<Product> products = this.productRepository.getTopProducts(topNum, soldOutYn);
        return products;
    }

}
