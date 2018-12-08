package productshop.web.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import productshop.domain.dtos.*;
import productshop.domain.views.CategoryByProductsCountDto;
import productshop.domain.views.ProductInRangeDto;
import productshop.service.CategoryService;
import productshop.service.ProductService;
import productshop.service.UserService;
import productshop.util.FileIOUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Controller
public class ProductShopController implements CommandLineRunner {

    private static final String RESOURCES_PATH = System.getProperty("user.dir") + "/src/main/resources/";
    private final static String USER_FILE_PATH = "C:\\Users\\A\\Desktop\\flash\\productshop\\src\\main\\resources\\files\\users.json";
    private final static String PRODUCT_FILE_PATH = "C:\\Users\\A\\Desktop\\flash\\productshop\\src\\main\\resources\\files\\products.json";
    private final static String CATEGORY_FILE_PATH = "C:\\Users\\A\\Desktop\\flash\\productshop\\src\\main\\resources\\files\\categories.json";
    private static final String OUTPUT_CATEGORIES_BY_PRODUCTS_JSON = RESOURCES_PATH + "output/categories-by-products.json";

    private final UserService userService;
    private final ProductService productService;
    private final CategoryService categoryService;
    private final FileIOUtil fileIOUtil;
    private final Gson gson;


    @Autowired
    public ProductShopController(UserService userService, ProductService productService, CategoryService categoryService, FileIOUtil fileIOUtil, Gson gson) {
        this.userService = userService;
        this.productService = productService;
        this.categoryService = categoryService;
        this.fileIOUtil = fileIOUtil;
        this.gson = gson;
    }

    @Override
    public void run(String... args) throws Exception {
        this.seedUsers();
        this.seedCategories();
        this.seedProducts();
        this.productsInRange();
     //   this.exportUsersAndProducts();

    }

    private void seedUsers() throws IOException {

        String usersFileContent = this.fileIOUtil.readFile(USER_FILE_PATH);
        UserSeedDto[] userSeedDtos = this.gson.fromJson(usersFileContent, UserSeedDto[].class);
        this.userService.seedUsers(userSeedDtos);
    }

    private void seedProducts() throws IOException {

        String productsFileContent = this.fileIOUtil.readFile(PRODUCT_FILE_PATH);
        ProductSeedDto[] productSeedDtos = this.gson.fromJson(productsFileContent, ProductSeedDto[].class);
        this.productService.seedProducts(productSeedDtos);
    }

    private void seedCategories() throws IOException {
        String categoriesFileContent = this.fileIOUtil.readFile(CATEGORY_FILE_PATH);
        CategorySeedDto[] categorySeedDtos = this.gson.fromJson(categoriesFileContent, CategorySeedDto[].class);
        this.categoryService.seedCategories(categorySeedDtos);
    }

    private void productsInRange() throws IOException {
        List<ProductInRangeDto> productInRangeDtos = this.productService.productInRange(BigDecimal.valueOf(500), BigDecimal.valueOf(1000));
        String productsInRangeJason = this.gson.toJson(productInRangeDtos);

//        // Ако искаме да печата на конзолата.
//        System.out.println(productsInRangeJason);
        // Ако искаме да печата не на конзолата, а във файл.
        File file = new File("C:\\Users\\A\\Desktop\\flash\\productshop\\src\\main\\resources\\files\\output\\product-in-range.json");
        FileWriter writer = new FileWriter(file);
        writer.write(productsInRangeJason);
        writer.close();
    }

//    private void exportUsersAndProducts() throws IOException {
//        List<UserWithSoldProductsViewDto> usersAndProductsViewDto = this.userService.findUsersWithSoldProducts();
//        String usersAndProductsViewDtoJason = this.gson.toJson(usersAndProductsViewDto);
//
//        File file = new File("C:\\Users\\A\\Desktop\\flash\\productshop\\src\\main\\resources\\files\\output\\users-sold-products.json");
//        FileWriter writer = new FileWriter(file);
//        writer.write(usersAndProductsViewDtoJason);
//        writer.close();
//
//    }


}
