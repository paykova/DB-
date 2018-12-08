package productshop.service;

import productshop.domain.dtos.UserSeedDto;
import productshop.domain.dtos.UserWithSoldProductsViewDto;
import productshop.domain.entities.User;

import java.util.List;

public interface UserService {

    void seedUsers(UserSeedDto[] userSeedDtos);

    // List<UserWithSoldProductsViewDto> findUsersWithSoldProducts();

}
