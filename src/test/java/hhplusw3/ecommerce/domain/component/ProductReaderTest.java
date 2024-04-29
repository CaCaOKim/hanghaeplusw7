package hhplusw3.ecommerce.domain.component;

import hhplusw3.ecommerce.api.product.dto.ProductRes;
import hhplusw3.ecommerce.domain.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ProductReaderTest {

    @Autowired
    ProductReader productReader;

    long id = 1;

    @Test
    void getProduct() {
        Product product = this.productReader.getProduct(id);

        assertThat(product.id()).isEqualTo(id);
        assertThat(product.name()).isEqualTo("bottle");
        assertThat(product.price()).isEqualTo(15000);
    }

    @Test
    void getTopProducts() {
        long topNum = 5;
        List<Product> products = this.productReader.getTopProducts(topNum, "N");

        assertThat(products.size()).isEqualTo(topNum);
        assertThat(products.get(0).id()).isEqualTo(1);
        assertThat(products.get(1).id()).isEqualTo(2);
        assertThat(products.get(2).id()).isEqualTo(4);
        assertThat(products.get(3).id()).isEqualTo(5);
        assertThat(products.get(4).id()).isEqualTo(6);
    }
}