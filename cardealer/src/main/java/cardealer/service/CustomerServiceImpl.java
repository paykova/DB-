package cardealer.service;

import cardealer.domain.dtos.CustomerImportDto;
import cardealer.domain.dtos.CustomerImportRootDto;
import cardealer.domain.entities.Customer;
import cardealer.repository.CustomerRepository;
import cardealer.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class CustomerServiceImpl  implements CustomerService{


    private final CustomerRepository customerRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }


    @Override
    public void importCustomers(CustomerImportRootDto customerImportRootDto) {
        for (CustomerImportDto customerImportDto : customerImportRootDto.getCustomerImportDtos()) {
            if(!this.validationUtil.isValid(customerImportDto)){
                System.out.println("Something is wrong.");
                continue;
            }
            Customer customer = this.modelMapper.map(customerImportDto, Customer.class);

//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-d");
//            String date = customerImportDto.getBirthDate();
//            LocalDate localDate = LocalDate.parse(date.toString(), formatter);
             customer.setBirthDate(LocalDate.parse(customerImportDto.getBirthDate()));
//            customer.setBirthDate(localDate);


            this.customerRepository.saveAndFlush(customer);
        }

    }
}
