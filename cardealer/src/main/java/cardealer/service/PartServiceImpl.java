package cardealer.service;

import cardealer.domain.dtos.PartImportDto;
import cardealer.domain.dtos.PartImportRootDto;
import cardealer.domain.entities.Part;
import cardealer.domain.entities.Supplier;
import cardealer.repository.PartRepository;
import cardealer.repository.SupplierRepository;
import cardealer.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PartServiceImpl implements PartService {

    private final PartRepository partRepository;
    private final ValidationUtil validationUtil;
    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PartServiceImpl(PartRepository partRepository, ValidationUtil validationUtil, SupplierRepository supplierRepository, ModelMapper modelMapper) {
        this.partRepository = partRepository;
        this.validationUtil = validationUtil;
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public void importParts(PartImportRootDto partImportRootDto) {

        for (PartImportDto partImportDto : partImportRootDto.getPartImportDtos()) {
            if(!this.validationUtil.isValid(partImportDto)){
                System.out.println("Something went wrong.");
                continue;
            }

            System.out.println();
            Part partEntity = this.modelMapper.map(partImportDto, Part.class);
            partEntity.setSupplier(this.getRandomSupplier());

            this.partRepository.saveAndFlush(partEntity);

        }
    }

    private Supplier getRandomSupplier (){
        Random random = new Random();
        return this.supplierRepository.findById(random.nextInt((int)(this.supplierRepository.count()- 1)) +1).orElse(null);
    }
}
