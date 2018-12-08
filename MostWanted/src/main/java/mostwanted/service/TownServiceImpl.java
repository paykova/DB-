package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.common.Constants;
import mostwanted.domain.dtos.TownImportDto;
import mostwanted.domain.entities.Town;
import mostwanted.repository.TownRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class TownServiceImpl implements TownService {


    private final static String TOWNS_JSON_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/towns.json";

    private final TownRepository townRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final FileUtil fileUtil;
    private final Gson gson;

    @Autowired
    public TownServiceImpl(TownRepository townRepository, ValidationUtil validationUtil, ModelMapper modelMapper, FileUtil fileUtil, Gson gson) {
        this.townRepository = townRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.fileUtil = fileUtil;
        this.gson = gson;
    }


    @Override
    public Boolean townsAreImported() {

        return this.townRepository.count()>0;
    }

    @Override
    public String readTownsJsonFile() throws IOException {

        return this.fileUtil.readFile(TOWNS_JSON_FILE_PATH);
    }

    @Override
    public String importTowns(String townsFileContent) {

        StringBuilder importResult = new StringBuilder();
        TownImportDto[] townImportDtos = this.gson.fromJson(townsFileContent, TownImportDto[].class);
        Arrays.stream(townImportDtos).forEach(townImportDto -> {
            Town townEntity = this.townRepository.findByName(townImportDto.getName()).orElse(null);
            if(townEntity != null){
                importResult.append(Constants.DUPLICATE_DATA_MESSAGE).append(System.lineSeparator());
                return;
            }
            if(!this.validationUtil.isValid(townImportDto)){
                importResult.append(Constants.INCORRECT_DATA_MESSAGE);
                return;
            }
            townEntity = this.modelMapper.map(townImportDto, Town.class);
            this.townRepository.saveAndFlush(townEntity);                           // "Town"
            importResult.append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE, townEntity.getClass().getSimpleName(), townEntity.getName())).append(System.lineSeparator());
        });
    return importResult.toString().trim();
    }

    @Override
    public String exportRacingTowns() {
        List<Town> towns= this.townRepository.exportRacingTowns();
        StringBuilder exportResult = new StringBuilder();

        towns.stream().forEach(town ->{
            exportResult.append(String.format("Name: %s", town.getName())).append(System.lineSeparator());
            exportResult.append(String.format("Racers: %d", town.getRacers().size())).append(System.lineSeparator());
            exportResult.append(System.lineSeparator());
        });
        return exportResult.toString().trim();
    }
}
