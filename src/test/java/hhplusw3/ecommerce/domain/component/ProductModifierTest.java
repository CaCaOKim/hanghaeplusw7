package hhplusw3.ecommerce.domain.component;

import hhplusw3.ecommerce.domain.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ProductModifierTest {

    @Autowired
    ProductModifier productModifier;

    @Test
    void updateProduct() {
        Product product = new Product(1, "bottle", 3000, 2, 500);
        Product result = this.productModifier.updateProduct(product);

        assertThat(result).isEqualTo(product);
    }
}