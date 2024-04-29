package hhplusw3.ecommerce.api.product.useCase;

import hhplusw3.ecommerce.api.product.dto.ProductRes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class GetProductUseCaseTest {

    @Autowired
    GetProductUseCase getProductUseCase;

    long id = 1;

    @Test
    void excute() {
        ProductRes product = this.getProductUseCase.excute(id);

        assertThat(product.id()).isEqualTo(id);
        assertThat(product.name()).isEqualTo("bottle");
        assertThat(product.price()).isEqualTo(15000);
    }
}