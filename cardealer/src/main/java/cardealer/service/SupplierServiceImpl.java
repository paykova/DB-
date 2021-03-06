package cardealer.service;

import cardealer.domain.dtos.SupplierImportDto;
import cardealer.domain.dtos.SupplierImportRootDto;
import cardealer.domain.entities.Supplier;
import cardealer.repository.SupplierRepository;
import cardealer.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.supplierRepository = supplierRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }


    @Override
    public void importSuppliers(SupplierImportRootDto supplierImportRootDto) {
        for (SupplierImportDto supplierImportDto  : supplierImportRootDto.getSupplierImportDtos()) {
            if(!this.validationUtil.isValid(supplierImportRootDto)){
                System.out.println("Something went wrong.");
                continue;
            }
            Supplier entity = this.modelMapper.map(supplierImportDto, Supplier.class);
            this.supplierRepository.saveAndFlush(entity);
        }
    }
}
