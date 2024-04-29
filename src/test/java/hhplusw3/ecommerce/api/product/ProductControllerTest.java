package hhplusw3.ecommerce.api.product;

import hhplusw3.ecommerce.api.product.dto.ProductRes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class ProductControllerTest {

    @Autowired
    ProductController productController;

    long id = 1;

    @Test
    void getProduct() throws InterruptedException {
        ProductRes product = this.productController.getProduct(id);

        assertThat(product.id()).isEqualTo(id);
        assertThat(product.name()).isEqualTo("bottle");
        assertThat(product.price()).isEqualTo(15000);
    }
}