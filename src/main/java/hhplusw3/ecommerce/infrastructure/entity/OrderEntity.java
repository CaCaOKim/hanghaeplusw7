package hhplusw3.ecommerce.infrastructure.entity;

import hhplusw3.ecommerce.domain.model.OrderProduct;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Data
@Entity
@DynamicUpdate
@Table(name = "HP_ORDER")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

//    @Column(name = "user_id")
//    long userId;

    @OneToOne
    @JoinColumn(name = "user_id")
    UserEntity user;

    @Column(name = "total_price")
    long totalPrice;

    @Column
    String status;
}
