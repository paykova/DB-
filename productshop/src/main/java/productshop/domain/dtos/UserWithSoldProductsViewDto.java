package productshop.domain.dtos;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class UserWithSoldProductsViewDto {

    @Expose
    private String firstName;

    @Expose
    private String lastName;

    @Expose
    private List<ProductSoldView> soldProducts;

    public UserWithSoldProductsViewDto() {
        this.soldProducts = new ArrayList<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<ProductSoldView> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(List<ProductSoldView> soldProducts) {
        this.soldProducts = soldProducts;
    }
}
