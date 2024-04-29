package hhplusw3.ecommerce.infrastructure.database;

import hhplusw3.ecommerce.domain.model.User;
import hhplusw3.ecommerce.domain.reository.UserRepository;
import hhplusw3.ecommerce.infrastructure.entity.UserEntity;
import jakarta.persistence.EntityManager;

public class UserRepositoryJpa implements UserRepository {
    private final EntityManager em;

    public UserRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public User getUser(long id) {
        UserEntity userEntity = em.find(UserEntity.class, id);
        User result = null;
        if (userEntity != null){
            result = new User(userEntity.getId(), userEntity.getName(), userEntity.getMoney());
        }
        return result;
    }

    @Override
    public User updateUser(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.id());
        userEntity.setName(user.name());
        userEntity.setMoney(user.money());
        UserEntity result = em.merge(userEntity);
        em.flush();
        return new User(result.getId(), result.getName(), result.getMoney());
    }

}
