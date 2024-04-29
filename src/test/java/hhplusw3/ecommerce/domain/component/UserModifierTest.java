package hhplusw3.ecommerce.domain.component;

import hhplusw3.ecommerce.api.user.dto.UserRes;
import hhplusw3.ecommerce.domain.model.TranscationType;
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
class UserModifierTest {

    @Autowired
    UserModifier userModifier;

    long id = 1;
    long amount = 20000;

    @Test
    void updateUser() {
        User user = new User(id, "robert", 100000);
        User result = this.userModifier.updateUser(user);

        assertThat(result.id()).isEqualTo(id);
        assertThat(result.name()).isEqualTo("robert");
        assertThat(result.money()).isEqualTo(100000);
    }

    @Test
    void calculateMoney_CHARGE() {
        User user = this.userModifier.calculateMoney(id, amount, TranscationType.CHARGE);

        assertThat(user.id()).isEqualTo(id);
        assertThat(user.name()).isEqualTo("robert");
        assertThat(user.money()).isEqualTo(30000 + amount);
    }

    @Test
    void calculateMoney_USE() {
        User user = this.userModifier.calculateMoney(id, amount, TranscationType.USE);

        assertThat(user.id()).isEqualTo(id);
        assertThat(user.name()).isEqualTo("robert");
        assertThat(user.money()).isEqualTo(30000 - amount);
    }
}