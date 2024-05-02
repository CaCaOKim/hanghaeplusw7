package hhplusw3.ecommerce.api.user.useCase;

import hhplusw3.ecommerce.api.user.dto.UserRes;
import hhplusw3.ecommerce.domain.component.UserService;
import hhplusw3.ecommerce.domain.model.TranscationType;
import hhplusw3.ecommerce.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChargeMoneyUseCase {

    private final UserService userService;

    @Autowired
    ChargeMoneyUseCase(UserService userService) {
        this.userService = userService;
    }

    public UserRes excute(long id, long amount) {
        // 1. 잔액 조회
        User user = this.userService.getUser(id);
        // 2. 잔액 충전
        User result = this.userService.chargeMoney(user, amount);
        return new UserRes(result.id(), result.name(), result.money());
    }
}
