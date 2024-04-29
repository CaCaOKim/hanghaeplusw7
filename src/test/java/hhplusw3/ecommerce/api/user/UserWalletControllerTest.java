package hhplusw3.ecommerce.api.user;

import hhplusw3.ecommerce.api.user.dto.UserRes;
import hhplusw3.ecommerce.domain.reository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class UserWalletControllerTest {

    @Autowired
    UserWalletController userWalletController;

    long id = 1;
    long amount = 20000;

    // 잔액 조회
    @Test
    void getUserWallet() throws InterruptedException {
        UserRes user = userWalletController.getUserWallet(id);

        assertThat(user.id()).isEqualTo(id);
        assertThat(user.name()).isEqualTo("robert");
        assertThat(user.money()).isEqualTo(30000);
    }

    // 잔액 충전
    @Test
    void chargeUserWallet() throws InterruptedException {
        UserRes user = userWalletController.chargeUserWallet(id, amount);

        assertThat(user.id()).isEqualTo(id);
        assertThat(user.name()).isEqualTo("robert");
        assertThat(user.money()).isEqualTo(30000 + amount);
    }

    @Test
    void userId가_유실되면_잔액조회_실패() throws InterruptedException {
        assertThatThrownBy(() -> {
            UserRes user = userWalletController.getUserWallet(0);
        }).isInstanceOf(RuntimeException.class);
    }

    @Test
    void userId가_유실되면_잔액충전_실패() throws InterruptedException {
        assertThatThrownBy(() -> {
            UserRes user = userWalletController.chargeUserWallet(0, amount);
        }).isInstanceOf(RuntimeException.class);
    }

    @Test
    void 충전금액이_최소금액보다_낮으면_잔액충전_실패() throws InterruptedException {
        assertThatThrownBy(() -> {
            UserRes user = userWalletController.chargeUserWallet(id, 200);
        }).isInstanceOf(RuntimeException.class);
    }

    @Test
    void 충전금액이_0원_이하이면_잔액충전_실패() throws InterruptedException {
        assertThatThrownBy(() -> {
            UserRes user = userWalletController.chargeUserWallet(id, 0);
        }).isInstanceOf(RuntimeException.class);
    }
}