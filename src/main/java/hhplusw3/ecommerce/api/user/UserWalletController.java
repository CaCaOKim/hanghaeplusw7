package hhplusw3.ecommerce.api.user;

import hhplusw3.ecommerce.api.user.dto.UserRes;
import hhplusw3.ecommerce.api.user.useCase.ChargeMoneyUseCase;
import hhplusw3.ecommerce.api.user.useCase.GetUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user/wallet")
public class UserWalletController {

    private final GetUserUseCase getUserUseCase;
    private final ChargeMoneyUseCase chargeMoneyUseCase;

    @Autowired
    UserWalletController(GetUserUseCase getUserUseCase, ChargeMoneyUseCase chargeMoneyUseCase) {
        this.getUserUseCase = getUserUseCase;
        this.chargeMoneyUseCase = chargeMoneyUseCase;
    }

    // 잔액 조회
    @GetMapping("/{id}")
    public UserRes getUserWallet(@PathVariable long id) throws InterruptedException {
        return this.getUserUseCase.excute(id);
    }

    // 잔액 충전
    @PatchMapping("/{id}")
    public UserRes chargeUserWallet(@PathVariable long id, @RequestBody long amount) throws InterruptedException {
        return this.chargeMoneyUseCase.excute(id, amount);
    }

}
