package productshop.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import productshop.domain.dtos.ProductSoldView;
import productshop.domain.dtos.UserSeedDto;
import productshop.domain.dtos.UserWithSoldProductsViewDto;
import productshop.domain.entities.User;
import productshop.repository.UserRepository;
import productshop.util.ValidatorUtil;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ValidatorUtil validatorUtil, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedUsers(UserSeedDto[] userSeedDtos) {
        for (UserSeedDto userSeedDto : userSeedDtos) {
            if (!this.validatorUtil.isValid(userSeedDto)) {
                this.validatorUtil.violations(userSeedDto)
                        .forEach(violation -> System.out.println(violation.getMessage()));
                continue;
            }
            User entity = this.modelMapper.map(userSeedDto, User.class);
            this.userRepository.saveAndFlush(entity);
        }
    }

//    @Override
//    public List<UserWithSoldProductsViewDto> findUsersWithSoldProducts() {
//        return this.userRepository
//                .findUsersWithSoldProducts()
//                .stream()
//                .map(u -> {
//                    final UserWithSoldProductsViewDto userDto =
//                            this.modelMapper.map(u, UserWithSoldProductsViewDto.class);
//                    u.setSoldProducts(u.
//                            .getSoldProducts()
//                            .stream().filter(sale -> sale.getBuyer() != null)
//                            .map(sale -> this.modelMapper.map(sale, ProductSoldView.class))
//                            .collect()
//                    return userDto;
//                })
//                .collect(Collectors.toList());
//
//        public List<UserFirstAndLastNamesAndSoldProductsDto> getSuccessfulSellers () {
//            return this.userRepository
//                    .getAllBySellContainsProduct_Buyer()
//                    .stream()
//                    .map(user -> {
//                        final UserFirstAndLastNamesAndSoldProductsDto userDto =
//                                this.modelMapper.map(user, UserFirstAndLastNamesAndSoldProductsDto.class);
//                        userDto.setSoldProducts(user
//                                .getSell()
//                                .stream()
//                                .filter(sale -> sale.getBuyer() != null)
//                                .map(sale -> this.modelMapper.map(sale, ProductNamePriceBuyerFirstAndLastNamesDto.class))
//                                .collect(Collectors.toSet()));
//                        return userDto;
//                    })
//                    .collect(Collectors.toList());
//        }
//
//    @Override
//    public List<UserWithSoldProductsViewDto> findUsersWithSoldProducts() {
//        return userRepository.findUsersWithSoldProducts()
//                .stream()
//                .map(u -> new UserWithSoldProductsViewDto(u.getFirstName(), u.getLastName(),
//                        u.getSoldProducts()
//                                .stream()
//                                .filter(p -> p.getBuyer() != null)
//                                .map(p -> new ProductSoldView(p.getName(), p.getPrice(),
//                                        p.getBuyer().getFirstName(), p.getBuyer().getLastName()))
//                                .collect(Collectors.toList())))
//                .collect(Collectors.toList());
    }


