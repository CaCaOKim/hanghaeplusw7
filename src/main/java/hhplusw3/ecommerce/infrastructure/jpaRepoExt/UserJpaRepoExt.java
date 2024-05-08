package hhplusw3.ecommerce.infrastructure.jpaRepoExt;

import hhplusw3.ecommerce.infrastructure.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepoExt extends JpaRepository<UserEntity, Long> {
}
