package hhplusw3.ecommerce.api.product.useCase;


import hhplusw3.ecommerce.api.product.dto.ProductRes;
import hhplusw3.ecommerce.domain.component.ProductReader;
import hhplusw3.ecommerce.domain.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetProductUseCase {

    private final ProductReader productReader;

    @Autowired
    public GetProductUseCase(ProductReader productReader) {
        this.productReader = productReader;
    }

    public ProductRes excute(long id) {
        Product product = this.productReader.getProduct(id);
        if (product == null) {
            product = new Product(0, null, 0, 0, 0);
        }
        return new ProductRes(product.id(), product.name(), product.price(), product.stock());
    }
}
