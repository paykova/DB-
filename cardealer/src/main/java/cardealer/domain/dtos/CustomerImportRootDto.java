package cardealer.domain.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerImportRootDto {

    @XmlElement(name = "customer")
    private CustomerImportDto[] customerImportDtos;

    public CustomerImportRootDto() {
    }

    public CustomerImportDto[] getCustomerImportDtos() {
        return customerImportDtos;
    }

    public void setCustomerImportDtos(CustomerImportDto[] customerImportDtos) {
        this.customerImportDtos = customerImportDtos;
    }
}
