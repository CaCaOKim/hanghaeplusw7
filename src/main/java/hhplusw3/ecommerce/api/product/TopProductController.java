package hhplusw3.ecommerce.api.product;

import hhplusw3.ecommerce.api.product.dto.ProductRes;
import hhplusw3.ecommerce.api.product.useCase.GetTopProductsUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("product")
public class TopProductController {

    private final GetTopProductsUseCase getTopProductsUseCase;

    @Autowired
    TopProductController(GetTopProductsUseCase getTopProductsUseCase) {
        this.getTopProductsUseCase = getTopProductsUseCase;
    }

    // 상위 상품 조회
    @GetMapping("/top/{topNum}/{soldOutYn}")
    public List<ProductRes> getTopProducts(@PathVariable long topNum, @PathVariable String soldOutYn) throws InterruptedException {
        return this.getTopProductsUseCase.excute(topNum, soldOutYn);
    }

}
