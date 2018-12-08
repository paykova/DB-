package productshop.domain.dtos;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CategorySeedDto {

    @Expose
    private String name;

    public CategorySeedDto() {
    }

    @NotNull(message = "Name cannot be null.")
    @Size(min = 3, message = "Name must be at lease 3 symbols long.")
    @Size(max = 15, message = "Name must be less than 15 symbols long.")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
