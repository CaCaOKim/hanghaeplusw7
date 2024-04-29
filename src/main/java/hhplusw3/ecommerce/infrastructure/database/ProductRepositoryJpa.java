package hhplusw3.ecommerce.infrastructure.database;

import hhplusw3.ecommerce.domain.model.Product;
import hhplusw3.ecommerce.domain.reository.ProductRepository;
import hhplusw3.ecommerce.infrastructure.entity.ProductEntity;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ProductRepositoryJpa implements ProductRepository {
    private final EntityManager em;

    public ProductRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public Product getProduct(long id) {
        ProductEntity productEntity = em.find(ProductEntity.class, id);
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
        ProductEntity result = em.merge(productEntity);
        em.flush();
        return new Product(result.getId(), result.getName(), result.getPrice(), result.getStock(), result.getSales());
    }

    @Override
    public List<Product> getTopProducts(long topNum, String soldOutYn) {
        String query = "select p from ProductEntity p "
                + (soldOutYn != null && soldOutYn.equals("N") ? "where p.stock > 0 " : "")
                + "order by p.sales desc limit " + Long.toString(topNum);
        List<ProductEntity> ProductEntities = em.createQuery(query, ProductEntity.class).getResultList();
        return ProductEntities.stream().map(p -> new Product(p.getId(), p.getName(), p.getPrice(), p.getStock(), p.getSales())).toList();
    }

}
