package productshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import productshop.domain.entities.Category;
import productshop.domain.views.CategoryByProductsCountDto;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

//    @Query("SELECT * FROM Category c JOIN Products p GROUP BY c.id ORDER BY c.products")
//    List<CategoryByProductsCountDto> getCategoriesByProductsCount();
//
//
//    SELECT c.name, COUNT(cp.product_id), AVG(p.price), SUM(p.price)
//    FROM products p
//    JOIN category_products cp on p.id = cp.product_id
//    JOIN categories c ON cp.category_id = c.id
//    GROUP BY category_id
//    ORDER BY COUNT(c.id) DESC;
//
//
////    @Query("SELECT new product_shop.model.dto.view.CategoryNameProductsCountAverageAndTotalPricesDto
//// (c.name, c.products.size, AVG(p.price), SUM(p.price))
//// FROM Category AS c JOIN c.products AS p GROUP BY c.id ORDER BY c.products.size DESC")


//    @Query("SELECT new product_shop.model.dto.view.CategoryNameProductsCountAverageAndTotalPricesDto(" +
//            "c.name, c.products.size, AVG(p.price), SUM(p.price)) " +
//            "FROM Category AS c " +
//            "JOIN c.products AS p " +
//            "GROUP BY c.id " +
//            "ORDER BY c.products.size DESC")
//    List<CategoryByProductsCountDto> getCategoriesByProductsCount();
}
