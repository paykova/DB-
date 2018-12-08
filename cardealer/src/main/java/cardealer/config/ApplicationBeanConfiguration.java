package cardealer.config;

import cardealer.util.ValidationUtil;
import cardealer.util.ValidationUtilImpl;
import cardealer.util.XmlParser;
import cardealer.util.XmlParserImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.Set;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public ValidationUtil validationUtil(){
        return new ValidationUtilImpl();
    }

    @Bean
    public XmlParser xmlParser(){
        return new XmlParserImpl();
    }
}
