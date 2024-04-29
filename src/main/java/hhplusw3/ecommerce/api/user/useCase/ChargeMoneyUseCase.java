package hhplusw3.ecommerce.api.user.useCase;

import hhplusw3.ecommerce.api.user.dto.UserRes;
import hhplusw3.ecommerce.domain.component.UserModifier;
import hhplusw3.ecommerce.domain.component.UserReader;
import hhplusw3.ecommerce.domain.model.TranscationType;
import hhplusw3.ecommerce.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChargeMoneyUseCase {

    private final UserModifier userModifier;

    @Autowired
    ChargeMoneyUseCase(UserModifier userModifier) {
        this.userModifier = userModifier;
    }

    public UserRes excute(long id, long amount) {
        if (amount <= 0) {
            throw new RuntimeException("0원 이하는 잔액충전을 할 수 없습니다.");
        }
        if (amount < 5000) {
            throw new RuntimeException("잔액 충전은 5000원 부터 가능합니다.");
        }
        User result = this.userModifier.calculateMoney(id, amount, TranscationType.CHARGE);
        return new UserRes(result.id(), result.name(), result.money());
    }
}
