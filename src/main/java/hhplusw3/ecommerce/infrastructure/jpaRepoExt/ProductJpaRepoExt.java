package hhplusw3.ecommerce.infrastructure.jpaRepoExt;

import hhplusw3.ecommerce.infrastructure.entity.ProductEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductJpaRepoExt extends JpaRepository<ProductEntity, Long> {


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM ProductEntity p WHERE p.id = :id")
    public Optional<ProductEntity> findByIdWithLock(Long id);

    @Query("SELECT p FROM ProductEntity p "
        + "WHERE (COALESCE(:soldOutYn, 'Y') = 'N' OR p.stock > 0) "
        + "ORDER BY p.sales DESC LIMIT :topNum")
    public List<ProductEntity> findTopProducts(@Param("topNum") long topNum, @Param("soldOutYn") String soldOutYn);
}
