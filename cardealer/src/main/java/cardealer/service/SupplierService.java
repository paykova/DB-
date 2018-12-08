package cardealer.service;

import cardealer.domain.dtos.SupplierImportRootDto;

import javax.xml.bind.annotation.XmlRootElement;

public interface SupplierService {



    void importSuppliers(SupplierImportRootDto supplierImportRootDto);
}
