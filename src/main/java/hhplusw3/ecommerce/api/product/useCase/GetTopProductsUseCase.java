package hhplusw3.ecommerce.api.product.useCase;


import hhplusw3.ecommerce.api.product.dto.ProductRes;
import hhplusw3.ecommerce.domain.component.ProductReader;
import hhplusw3.ecommerce.domain.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetTopProductsUseCase {

    private final ProductReader productReader;

    @Autowired
    public GetTopProductsUseCase(ProductReader productReader) {
        this.productReader = productReader;
    }

    public List<ProductRes> excute(long topNum, String soldOutYn) {
        List<Product> products = this.productReader.getTopProducts(topNum, soldOutYn);
        return products.stream().map(p -> new ProductRes(p.id(), p.name(), p.price(), p.stock())).toList();
    }
}
