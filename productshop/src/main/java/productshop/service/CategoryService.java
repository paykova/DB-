package productshop.service;

import productshop.domain.dtos.CategorySeedDto;
import productshop.domain.views.CategoryByProductsCountDto;

import java.util.List;

public interface CategoryService {

    void seedCategories(CategorySeedDto[] categorySeedDtos);

}
