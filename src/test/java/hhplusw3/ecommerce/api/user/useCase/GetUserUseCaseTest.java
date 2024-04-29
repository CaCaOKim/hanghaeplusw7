package hhplusw3.ecommerce.api.user.useCase;

import hhplusw3.ecommerce.api.user.dto.UserRes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class GetUserUseCaseTest {

    @Autowired
    GetUserUseCase getUserUseCase;

    long id = 1;

    @Test
    void excute() {
        UserRes user = getUserUseCase.excute(id);

        assertThat(user.id()).isEqualTo(id);
        assertThat(user.name()).isEqualTo("robert");
        assertThat(user.money()).isEqualTo(30000);
    }

    @Test
    void userId가_유실되면_잔액조회_실패() throws InterruptedException {
        assertThatThrownBy(() -> {
            UserRes user = getUserUseCase.excute(0);
        }).isInstanceOf(RuntimeException.class);
    }
}