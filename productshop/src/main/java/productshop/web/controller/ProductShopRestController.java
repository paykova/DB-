//package productshop.web.controller;
//
//import com.google.gson.Gson;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//import productshop.domain.views.ProductInRangeDto;
//import productshop.service.ProductService;
//
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.util.List;
//
//@RestController
//public class ProductShopRestController {
//
//    private final ProductService productService;
//    private final Gson gson;
//
//    @Autowired
//    public ProductShopRestController(ProductService productService, Gson gson) {
//        this.productService = productService;
//        this.gson = gson;
//    }
//
//
//    @GetMapping("/")
//    private String productsInRange() throws IOException {
//        List<ProductInRangeDto> productInRangeDtos = this.productService.productInRange(BigDecimal.valueOf(500), BigDecimal.valueOf(1000));
//
//        String productsInRangeJason = this.gson.toJson(productInRangeDtos).replace("\n", "<br>");
//
//       return productsInRangeJason;
//    }
//}
