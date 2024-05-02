package hhplusw3.ecommerce.domain.component;

import hhplusw3.ecommerce.domain.model.*;
import hhplusw3.ecommerce.domain.reository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProduct(long id) {
        Product product = this.productRepository.getProduct(id);
        if (product == null) {
            product = new Product(0, null, 0, 0, 0);
        }
        return product;
    }

    public Product updateProduct(Product product) {
        Product result = this.productRepository.updateProduct(product);
        if (result == null) {
            result = new Product(0, null, 0, 0, 0);
        }
        return result;
    }

    public List<Product> getTopProducts(long topNum, String soldOutYn) {
        List<Product> products = this.productRepository.getTopProducts(topNum, soldOutYn);
        return products;
    }

    public TotalProduct checkProductsStock(List<ProductWithCount> productWithCounts) {
        // 상품 재고 조회
        List<Product> products = new ArrayList<>();
        long totalPrice = 0;
        for (ProductWithCount productWithCount : productWithCounts) {
            Product product = this.getProduct(productWithCount.productId());
            if (product.stock() >= productWithCount.count()) {
                products.add(product);
                totalPrice = product.price() * productWithCount.count();
            }
        }

        // Exception
        if (CollectionUtils.isEmpty(products)) {
            throw new RuntimeException("제품들이 모두 품절되었습니다.");
        }

        return new TotalProduct(products, totalPrice);
    }

    public List<Product> balanceStock(List<OrderProduct> orderProducts) {
        List<Product> products = new ArrayList<>();
        for (OrderProduct orderProduct : orderProducts) {
            Product product = this.getProduct(orderProduct.productId());
            products.add(this.calculateStock(product, orderProduct.count(), TranscationType.USE));
        }
        return products;
    }

    public Product calculateStock(Product product, long amount, TranscationType transcationType) {
        long stock = product.stock();
        long sales = product.sales();

        if (transcationType == TranscationType.CHARGE) {
            stock += amount;
        } else if (transcationType == TranscationType.USE) {
            stock -= amount;
            sales += amount;
        }

        return new Product(product.id(), product.name(), product.price(), stock, sales);
    }


}
