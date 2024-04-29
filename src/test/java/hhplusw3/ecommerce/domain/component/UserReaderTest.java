package hhplusw3.ecommerce.domain.component;

import hhplusw3.ecommerce.domain.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserReaderTest {

    @Autowired
    UserReader userReader;

    long id = 1;

    @Test
    void getUser() {
        User user = userReader.getUser(id);

        assertThat(user.id()).isEqualTo(id);
        assertThat(user.name()).isEqualTo("robert");
        assertThat(user.money()).isEqualTo(30000);
    }

    @Test
    void userId가_유실되면_잔액조회_실패() throws InterruptedException {
        assertThatThrownBy(() -> {
            User user = userReader.getUser(0);
        }).isInstanceOf(RuntimeException.class);
    }

    @Test
    void 존재하지_않는_유저_조회_시_실패() throws InterruptedException {
        User user = userReader.getUser(99999);
        assertThat(user.id()).isEqualTo(0);
    }
}