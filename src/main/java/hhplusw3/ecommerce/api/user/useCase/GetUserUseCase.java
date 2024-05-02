package hhplusw3.ecommerce.api.user.useCase;

import hhplusw3.ecommerce.api.user.dto.UserRes;
import hhplusw3.ecommerce.domain.component.UserService;
import hhplusw3.ecommerce.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetUserUseCase {

    private final UserService userService;

    @Autowired
    public GetUserUseCase(UserService userService) {
        this.userService = userService;
    }

    public UserRes excute(long id) {
        // 1. 잔액 조회
        User user = this.userService.getUser(id);
        return new UserRes(user.id(), user.name(), user.money());
    }
}
