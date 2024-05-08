package hhplusw3.ecommerce.domain.component;

import hhplusw3.ecommerce.domain.model.TranscationType;
import hhplusw3.ecommerce.domain.model.User;
import hhplusw3.ecommerce.domain.reository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@DataJpaTest
class UserServiceTest {

    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserService userService;

    // given
    long id = 1;
    long amount = 20000;

    @Test
    void getUser() {
        // when
        when(userRepository.getUser(id)).thenReturn(new User(1, "robert", 30000));
        User user = userService.getUser(id);

        // then
        assertThat(user.id()).isEqualTo(id);
        assertThat(user.name()).isEqualTo("robert");
        assertThat(user.money()).isEqualTo(30000);
    }

    @Test
    void updateUser() {
        // given
        User user = new User(id, "robert", 100000);

        // when
        when(userRepository.updateUser(user)).thenReturn(user);
        User result = this.userService.updateUser(user);

        // then
        assertThat(result.id()).isEqualTo(id);
        assertThat(result.name()).isEqualTo("robert");
        assertThat(result.money()).isEqualTo(100000);
    }

    @Test
    void calculateMoney() {
        // given
        when(userRepository.getUser(id)).thenReturn(new User(1, "robert", 30000));
        User user = this.userService.getUser(id);

        // when
        when(userRepository.updateUser(new User(user.id(), user.name(), user.money() + amount))).thenReturn(new User(1, "robert", 30000 + amount));
        User cResult = this.userService.calculateMoney(user, amount, TranscationType.CHARGE);
        when(userRepository.updateUser(new User(user.id(), user.name(), user.money() - amount))).thenReturn(new User(1, "robert", 30000 - amount));
        User uResult = this.userService.calculateMoney(user, amount, TranscationType.USE);

        // then
        assertThat(cResult.id()).isEqualTo(id);
        assertThat(cResult.name()).isEqualTo("robert");
        assertThat(cResult.money()).isEqualTo(30000 + amount);

        assertThat(uResult.id()).isEqualTo(id);
        assertThat(uResult.name()).isEqualTo("robert");
        assertThat(uResult.money()).isEqualTo(30000 - amount);
    }

    @Test
    void chargeMoney() {
        // given
        when(userRepository.getUser(id)).thenReturn(new User(1, "robert", 30000));
        User user = this.userService.getUser(id);

        // when
        when(userRepository.updateUser(new User(user.id(), user.name(), user.money() + amount))).thenReturn(new User(1, "robert", 30000 + amount));
        User result = this.userService.chargeMoney(user, amount);

        // then
        assertThat(result.id()).isEqualTo(id);
        assertThat(result.name()).isEqualTo("robert");
        assertThat(result.money()).isEqualTo(30000 + amount);
    }

    @Test
    void useMoney() {
        // given
        when(userRepository.getUser(id)).thenReturn(new User(1, "robert", 30000));
        User user = this.userService.getUser(id);

        // when
        when(userRepository.updateUser(new User(user.id(), user.name(), user.money() - amount))).thenReturn(new User(1, "robert", 30000 - amount));
        User result = this.userService.useMoney(user, amount);

        // then
        assertThat(result.id()).isEqualTo(id);
        assertThat(result.name()).isEqualTo("robert");
        assertThat(result.money()).isEqualTo(30000 - amount);
    }

    @Test
    void checkUserMoney() {
        // when
        when(userRepository.getUser(id)).thenReturn(new User(1, "robert", 30000));
        User user = this.userService.checkUserMoney(id, amount);

        // then
        assertThat(user.id()).isEqualTo(id);
        assertThat(user.name()).isEqualTo("robert");
        assertThat(user.money()).isEqualTo(30000);
    }

    @Test
    void userId가_유실되면_잔액조회_실패() throws InterruptedException {
        // when then
        assertThatThrownBy(() -> {
            when(userRepository.getUser(99999)).thenReturn(null);
            User user = userService.getUser(0);
        }).isInstanceOf(RuntimeException.class);
    }

    @Test
    void 존재하지_않는_유저_조회_시_빈_유저객체_반환() throws InterruptedException {
        // when
        when(userRepository.getUser(99999)).thenReturn(null);
        User user = userService.getUser(99999);

        // then
        assertThat(user.id()).isEqualTo(0);
    }

    @Test
    void 충전금액_없을_시_실패() throws InterruptedException {
        // given
        when(userRepository.getUser(id)).thenReturn(new User(1, "robert", 30000));
        User user = userService.getUser(id);

        // when then
        assertThatThrownBy(() -> {
            User result = this.userService.chargeMoney(user, 0);
        }).isInstanceOf(RuntimeException.class);
    }

    @Test
    void 최소충전금액_미만_충전_시_실패() throws InterruptedException {
        // given
        when(userRepository.getUser(id)).thenReturn(new User(1, "robert", 30000));
        User user = userService.getUser(id);

        // when then
        assertThatThrownBy(() -> {
            User result = this.userService.chargeMoney(user, 300);
        }).isInstanceOf(RuntimeException.class);
    }

    @Test
    void 잔액이_부족할_경우_잔액차감_실패() throws InterruptedException {
        // when then
        assertThatThrownBy(() -> {
            when(userRepository.getUser(id)).thenReturn(new User(1, "robert", 30000));
            User user = this.userService.checkUserMoney(id, 100000);
        }).isInstanceOf(RuntimeException.class);
    }
}