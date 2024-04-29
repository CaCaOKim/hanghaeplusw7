package hhplusw3.ecommerce.api.product.useCase;

import hhplusw3.ecommerce.api.product.dto.ProductRes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class GetTopProductsUseCaseTest {

    @Autowired
    GetTopProductsUseCase getTopProductsUseCase;

    @Test
    void excute() {
        long topNum = 5;
        List<ProductRes> products = this.getTopProductsUseCase.excute(topNum, "N");

        assertThat(products.size()).isEqualTo(topNum);
        assertThat(products.get(0).id()).isEqualTo(1);
        assertThat(products.get(1).id()).isEqualTo(2);
        assertThat(products.get(2).id()).isEqualTo(4);
        assertThat(products.get(3).id()).isEqualTo(5);
        assertThat(products.get(4).id()).isEqualTo(6);
    }
}