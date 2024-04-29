package hhplusw3.ecommerce.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

@Data
@Entity
@DynamicUpdate
@Table(name = "HP_ORDER_PRODUCT")
public class OrderProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name = "order_id")
    long orderId;

//    @Column(name = "product_id")
//    long productId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    ProductEntity product;

    @Column
    long count;

    @Column
    String status;

}
