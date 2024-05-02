package hhplusw3.ecommerce.domain.component;

import hhplusw3.ecommerce.domain.model.TranscationType;
import hhplusw3.ecommerce.domain.model.User;
import hhplusw3.ecommerce.domain.reository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(long id) {
        // Exception
        if (id == 0) {
            throw new RuntimeException("로그인 정보가 유실되었습니다.");
        }

        // 유저 정보 조회(잔액 조회)
        User user = this.userRepository.getUser(id);
        if (user == null) {
            user = new User(0, null, 0);
        }

        return user;
    }

    public User updateUser(User user) {
        User result = this.userRepository.updateUser(user);
        return result;
    }

    public User calculateMoney(User user, long amount, TranscationType type) {
        // 잔액 계산
        long money = user.money();
        if (type == TranscationType.CHARGE) {

            // 충전 Exception
            if (amount <= 0) {
                throw new RuntimeException("0원 이하는 잔액충전을 할 수 없습니다.");
            }
            if (amount < 5000) {
                throw new RuntimeException("잔액 충전은 5000원 부터 가능합니다.");
            }

            money += amount;
        } else if (type == TranscationType.USE) {
            money -= amount;
        }

        User result = this.updateUser(new User(user.id(), user.name(), money));

        return result;
    }

    public User chargeMoney(User user, long amount) {
        User result = this.calculateMoney(user, amount, TranscationType.CHARGE);
        return result;
    }

    public User useMoney(User user, long amount) {
        User result = this.calculateMoney(user, amount, TranscationType.USE);
        return result;
    }

    public User checkUserMoney(long userId, long amount) {
        // 잔액 조회
        User user = this.getUser(userId);

        // Exception
        if (user.money() < amount) {
            throw new RuntimeException("잔액이 부족합니다.");
        }

        return user;
    }

}
