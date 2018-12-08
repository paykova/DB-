package mostwanted.service;

import mostwanted.common.Constants;
import mostwanted.domain.dtos.raceentries.RaceEntryImportRootDto;
import mostwanted.domain.entities.Car;
import mostwanted.domain.entities.RaceEntry;
import mostwanted.domain.entities.Racer;
import mostwanted.repository.CarRepository;
import mostwanted.repository.RaceEntryRepository;
import mostwanted.repository.RacerRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import mostwanted.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Arrays;

@Service
public class RaceEntryServiceImpl implements RaceEntryService {

    private final static String RACE_ENTRIES_XML_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/race-entries.xml";

    private final RaceEntryRepository raceEntryRepository;
    private final RacerRepository racerRepository;
    private final CarRepository carRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final FileUtil fileUtil;
    private final XmlParser xmlParser;


    @Autowired
    public RaceEntryServiceImpl(RaceEntryRepository raceEntryRepository, RacerRepository racerRepository, CarRepository carRepository, ModelMapper modelMapper, ValidationUtil validationUtil, FileUtil fileUtil, XmlParser xmlParser) {
        this.raceEntryRepository = raceEntryRepository;
        this.racerRepository = racerRepository;
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
    }


    @Override
    public Boolean raceEntriesAreImported() {

        return this.raceEntryRepository.count() > 0;
    }

    @Override
    public String readRaceEntriesXmlFile() throws IOException {

        return this.fileUtil.readFile(RACE_ENTRIES_XML_FILE_PATH);
    }

    @Override
    public String importRaceEntries() throws JAXBException {
        StringBuilder importResult = new StringBuilder();

        RaceEntryImportRootDto raceEntryImportRootDto = this.xmlParser.parseXml(RaceEntryImportRootDto.class, RACE_ENTRIES_XML_FILE_PATH);

        Arrays.stream(raceEntryImportRootDto.getRaceEntryImportDtos()).forEach(raceEntryImportDto -> {

            Car carEntity = this.carRepository.findById(raceEntryImportDto.getCarId()).orElse(null);
            Racer racerEntity = this.racerRepository.findByName(raceEntryImportDto.getRacer()).orElse(null);

            if(carEntity == null || racerEntity == null){
                importResult.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                return;
            }
            RaceEntry raceEntryEntity = this.modelMapper.map(raceEntryImportDto, RaceEntry.class);
            raceEntryEntity.setCar(carEntity);
            raceEntryEntity.setRacer(racerEntity);

            raceEntryEntity.setRace(null);
            raceEntryEntity = this.raceEntryRepository.saveAndFlush(raceEntryEntity);
            importResult.append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE, raceEntryEntity.getClass().getSimpleName(), raceEntryEntity.getId()))
                    .append(System.lineSeparator());


        });
        return importResult.toString().trim();
    }
}
