package hhplusw3.ecommerce.api.user.useCase;

import hhplusw3.ecommerce.api.user.dto.UserRes;
import hhplusw3.ecommerce.domain.component.UserReader;
import hhplusw3.ecommerce.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetUserUseCase {

    private final UserReader userReader;

    @Autowired
    public GetUserUseCase(UserReader userReader) {
        this.userReader = userReader;
    }

    public UserRes excute(long id) {
        User user = this.userReader.getUser(id);
        if (user == null) {
            user = new User(0, null, 0);
        }
        return new UserRes(user.id(), user.name(), user.money());
    }
}
