package hhplusw3.ecommerce.domain.reository;

import hhplusw3.ecommerce.domain.model.Product;

import java.util.List;

public interface ProductRepository {
    public Product getProduct(long id);

    public Product updateProduct(Product product);

    public List<Product> getTopProducts(long topNum, String soldOutYn);

}
