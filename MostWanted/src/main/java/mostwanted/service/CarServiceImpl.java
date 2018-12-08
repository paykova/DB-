package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.common.Constants;
import mostwanted.domain.dtos.CarImportDto;
import mostwanted.domain.entities.Car;
import mostwanted.domain.entities.Racer;
import mostwanted.repository.CarRepository;
import mostwanted.repository.RacerRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Service
public class CarServiceImpl implements CarService {

    private final static String CARS_JSON_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/cars.json";

    private final CarRepository carRepository;
    private final FileUtil fileUtil;
    private final RacerRepository racerRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, FileUtil fileUtil, RacerRepository racerRepository, ValidationUtil validationUtil, ModelMapper modelMapper, Gson gson) {
        this.carRepository = carRepository;
        this.fileUtil = fileUtil;
        this.racerRepository = racerRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public Boolean carsAreImported() {

        return this.carRepository.count() > 0;
    }

    @Override
    public String readCarsJsonFile() throws IOException {
        return this.fileUtil.readFile(CARS_JSON_FILE_PATH);
    }

    @Override
    public String importCars(String carsFileContent) {

        StringBuilder importResult = new StringBuilder();
        CarImportDto[] carImportDtos = this.gson.fromJson(carsFileContent, CarImportDto[].class);

        Arrays.stream(carImportDtos).forEach(carImportDto -> {
//            Car carEntity = this.carRepository.findByModelAndBrand(carImportDto.getModel(), carImportDto.getBrand()).orElse(null);
//
//            if (carEntity != null) {
//                importResult.append(Constants.DUPLICATE_DATA_MESSAGE).append(System.lineSeparator());
//                return;
//            }

            Racer racer = this.racerRepository.findByName(carImportDto.getRacerName()).orElse(null);

            if (!this.validationUtil.isValid(carImportDto) || racer == null) {
                importResult.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                return;
            }
            Car carEntity = this.modelMapper.map(carImportDto, Car.class);
          //  carEntity = this.modelMapper.map(carImportDto, Car.class);
            carEntity.setRacer(racer);
            this.carRepository.saveAndFlush(carEntity);
            importResult.append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE, carEntity.getClass().getSimpleName(),
                            String.format("%s %s @ %d", carImportDto.getBrand(), carImportDto.getModel(), carImportDto.getYearOfProduction())))
                    .append(System.lineSeparator());


        });
        return importResult.toString().trim();
    }
}
