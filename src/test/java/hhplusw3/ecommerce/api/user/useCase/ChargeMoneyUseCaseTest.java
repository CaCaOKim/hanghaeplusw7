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
class ChargeMoneyUseCaseTest {

    @Autowired
    ChargeMoneyUseCase chargeMoneyUseCase;

    long id = 1;
    long amount = 20000;

    @Test
    void excute() {
        UserRes user = chargeMoneyUseCase.excute(id, amount);

        assertThat(user.id()).isEqualTo(id);
        assertThat(user.name()).isEqualTo("robert");
        assertThat(user.money()).isEqualTo(30000 + amount);
    }

    @Test
    void 충전금액이_최소금액보다_낮으면_잔액충전_실패() throws InterruptedException {
        assertThatThrownBy(() -> {
            UserRes user = chargeMoneyUseCase.excute(id, 200);
        }).isInstanceOf(RuntimeException.class);
    }

    @Test
    void 충전금액이_0원_이하이면_잔액충전_실패() throws InterruptedException {
        assertThatThrownBy(() -> {
            UserRes user = chargeMoneyUseCase.excute(id, 0);
        }).isInstanceOf(RuntimeException.class);
    }

}