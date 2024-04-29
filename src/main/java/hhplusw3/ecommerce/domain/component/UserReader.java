package hhplusw3.ecommerce.domain.component;

import hhplusw3.ecommerce.domain.model.User;
import hhplusw3.ecommerce.domain.reository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserReader {

    private final UserRepository userRepository;

    @Autowired
    public UserReader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(long id) {
        if (id == 0) {
            throw new RuntimeException("로그인 정보가 유실되었습니다.");
        }
        User user = this.userRepository.getUser(id);
        if (user == null) {
            user = new User(0, null, 0);
        }
        return user;
    }
}
