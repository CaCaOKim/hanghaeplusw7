package hhplusw3.ecommerce.domain.reository;

import hhplusw3.ecommerce.domain.model.User;

public interface UserRepository {
    public User getUser(long id);
    public User updateUser(User user);
}
