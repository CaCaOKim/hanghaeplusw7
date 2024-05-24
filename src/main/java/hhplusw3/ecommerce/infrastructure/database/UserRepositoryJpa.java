package hhplusw3.ecommerce.infrastructure.database;

import hhplusw3.ecommerce.domain.model.User;
import hhplusw3.ecommerce.domain.reository.UserRepository;
import hhplusw3.ecommerce.infrastructure.entity.UserEntity;
import hhplusw3.ecommerce.infrastructure.jpaRepoExt.UserJpaRepoExt;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryJpa implements UserRepository {
//    private final EntityManager em;
//    public UserRepositoryJpa(EntityManager em) {
//        this.em = em;
//    }

    private final UserJpaRepoExt userJpaRepo;

    public UserRepositoryJpa(UserJpaRepoExt userJpaRepo) {
        this.userJpaRepo = userJpaRepo;
    }

    @Override
    public User getUser(long id) {
        UserEntity userEntity = this.userJpaRepo.findById(id).orElse(new UserEntity());
        User result = null;
        if (userEntity != null){
            result = new User(userEntity.getId(), userEntity.getName(), userEntity.getMoney());
        }
        return result;
    }

    @Override
    public User getUserForUpdate(long id) {
        UserEntity userEntity = this.userJpaRepo.findByIdWithLock(id).orElse(new UserEntity());
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
        UserEntity result = this.userJpaRepo.save(userEntity);
        return new User(result.getId(), result.getName(), result.getMoney());
    }

}
