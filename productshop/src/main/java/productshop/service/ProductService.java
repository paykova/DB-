package productshop.service;

import productshop.domain.views.ProductInRangeDto;
import productshop.domain.dtos.ProductSeedDto;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    void seedProducts(ProductSeedDto[] productSeedDtos);


    List<ProductInRangeDto> productInRange(BigDecimal more, BigDecimal less);
}
