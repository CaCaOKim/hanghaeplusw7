package hhplusw3.ecommerce.domain.component;

import hhplusw3.ecommerce.domain.model.*;
import hhplusw3.ecommerce.domain.reository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DataJpaTest
@Transactional
class ProductServiceTest {

    @Mock
    ProductRepository productRepository;
    @InjectMocks
    ProductService productService;

    // given
    long id = 1;
    long amount = 20000;

    @Test
    void getProduct() {
        // when
        when(productRepository.getProduct(id)).thenReturn(new Product(1, "bottle", 15000, 30, 1000));
        Product product = this.productService.getProduct(id);

        // then
        assertThat(product.id()).isEqualTo(id);
        assertThat(product.name()).isEqualTo("bottle");
        assertThat(product.price()).isEqualTo(15000);
    }

    @Test
    void updateProduct() {
        // given
        Product product = new Product(1, "bottle", 3000, 2, 500);

        // when
        when(productRepository.updateProduct(product)).thenReturn(product);
        Product result = this.productService.updateProduct(product);

        // then
        assertThat(result).isEqualTo(product);
    }

    @Test
    void getTopProducts() {
        // given
        long topNum = 5;
        String soldOutYn = "N";

        // when
        List<Product> mockProducts = new ArrayList<>();
        mockProducts.add(new Product(1, "bottle", 15000, 30, 1000));
        mockProducts.add(new Product(2, "bottle2", 13000, 5, 500));
        mockProducts.add(new Product(4, "bottle4", 1000, 10, 300));
        mockProducts.add(new Product(5, "bottle5", 2000, 100, 200));
        mockProducts.add(new Product(6, "bottle6", 32000, 50, 1000));
        when(productRepository.getTopProducts(topNum, soldOutYn)).thenReturn(mockProducts);
        List<Product> products = this.productService.getTopProducts(topNum, "N");

        assertThat(products.size()).isEqualTo(topNum);
        assertThat(products.get(0).id()).isEqualTo(1);
        assertThat(products.get(1).id()).isEqualTo(2);
        assertThat(products.get(2).id()).isEqualTo(4);
        assertThat(products.get(3).id()).isEqualTo(5);
        assertThat(products.get(4).id()).isEqualTo(6);
    }

    @Test
    void checkProductsStock() {
        // given
        List<ProductWithCount> productWithCounts = new ArrayList<>();
        productWithCounts.add(new ProductWithCount(4, null, 2));
        productWithCounts.add(new ProductWithCount(5, null, 3));

        // when
        List<Product> mockProducts = new ArrayList<>();
        mockProducts.add(new Product(4, "bottle4", 1000, 10, 300));
        mockProducts.add(new Product(5, "bottle5", 2000, 100, 200));
        when(productRepository.getProductForUpdate(4)).thenReturn(mockProducts.get(0));
        when(productRepository.getProductForUpdate(5)).thenReturn(mockProducts.get(1));
        List<ProductWithCount> result = this.productService.checkProductsStock(productWithCounts);

        // then
        assertThat(result.get(0).product()).isEqualTo(mockProducts.get(0));
        assertThat(result.get(0).count()).isEqualTo(productWithCounts.get(0).count());
        assertThat(result.get(1).product()).isEqualTo(mockProducts.get(1));
        assertThat(result.get(1).count()).isEqualTo(productWithCounts.get(1).count());
    }

    @Test
    void balanceStock() {
        // given
        List<OrderProduct> orderProducts = new ArrayList<>();
        orderProducts.add(new OrderProduct(0, 0, 4, "bottle4", 2, "ready"));
        orderProducts.add(new OrderProduct(0, 0, 5, "bottle5", 3, "ready"));

        // when
        Product product1 = new Product(4, "bottle4", 1000, 10, 300);
        Product product2 = new Product(5, "bottle5", 2000, 100, 200);
        when(productRepository.getProduct(4)).thenReturn(product1);
        when(productRepository.getProduct(5)).thenReturn(product2);
        Product product1After = new Product(4, "bottle4", 1000, 8, 302);
        Product product2After = new Product(5, "bottle5", 2000, 97, 203);
        when(productRepository.updateProduct(product1After)).thenReturn(product1After);
        when(productRepository.updateProduct(product2After)).thenReturn(product2After);
        List<Product> products = this.productService.balanceStock(orderProducts);

        // then
        List<Product> result = new ArrayList<>();
        result.add(product1After);
        result.add(product2After);
        assertThat(products).isEqualTo(result);
    }

    @Test
    void calculateStock() {
        // given
        Product product = new Product(4, "bottle4", 1000, 10, 300);
        long amount = 2;
        TranscationType transcationType = TranscationType.USE;

        // when
        Product result = this.productService.calculateStock(product, amount, transcationType);

        // then
        assertThat(result).isEqualTo(new Product(product.id(), product.name(), product.price(), product.stock() - amount, product.sales() + amount));
    }

    @Test
    void getTotalPrice() {
        // given
        List<ProductWithCount> productWithCounts = new ArrayList<>();
        productWithCounts.add(new ProductWithCount(1, new Product(1, "bottle", 15000, 30, 1000), 3));
        productWithCounts.add(new ProductWithCount(1, new Product(2, "bottle2", 13000, 5, 500), 5));

        // when
        long result = this.productService.getTotalPrice(productWithCounts);

        // then
        assertThat(result).isEqualTo(15000 * 3 + 13000 * 5);
    }
}