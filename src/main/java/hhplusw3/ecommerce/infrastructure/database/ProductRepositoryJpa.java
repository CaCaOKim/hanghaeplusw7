package hhplusw3.ecommerce.infrastructure.database;

import hhplusw3.ecommerce.domain.model.Product;
import hhplusw3.ecommerce.domain.reository.ProductRepository;
import hhplusw3.ecommerce.infrastructure.entity.ProductEntity;
import hhplusw3.ecommerce.infrastructure.jpaRepoExt.ProductJpaRepoExt;
import hhplusw3.ecommerce.infrastructure.jpaRepoExt.UserJpaRepoExt;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ProductRepositoryJpa implements ProductRepository {
//    private final EntityManager em;
//    public ProductRepositoryJpa(EntityManager em) {
//        this.em = em;
//    }

    private final ProductJpaRepoExt productJpaRepo;

    public ProductRepositoryJpa(ProductJpaRepoExt productJpaRepo) {
        this.productJpaRepo = productJpaRepo;
    }

    @Override
    public Product getProduct(long id) {
        ProductEntity productEntity = this.productJpaRepo.findById(id).orElse(new ProductEntity());
        return new Product(productEntity.getId(), productEntity.getName(), productEntity.getPrice(), productEntity.getStock(), productEntity.getSales());
    }

    @Override
    public Product updateProduct(Product product) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(product.id());
        productEntity.setName(product.name());
        productEntity.setPrice(product.price());
        productEntity.setStock(product.stock());
        productEntity.setSales(product.sales());
        ProductEntity result = this.productJpaRepo.save(productEntity);
        return new Product(result.getId(), result.getName(), result.getPrice(), result.getStock(), result.getSales());
    }

    @Override
    public List<Product> getTopProducts(long topNum, String soldOutYn) {
//        String query = "select p from ProductEntity p "
//                + (soldOutYn != null && soldOutYn.equals("N") ? "where p.stock > 0 " : "")
//                + "order by p.sales desc limit " + Long.toString(topNum);
        List<ProductEntity> ProductEntities = this.productJpaRepo.findTopProducts(topNum, soldOutYn);
        return ProductEntities.stream().map(p -> new Product(p.getId(), p.getName(), p.getPrice(), p.getStock(), p.getSales())).toList();
    }

}
