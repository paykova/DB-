package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.common.Constants;
import mostwanted.domain.dtos.DistrictImportDto;
import mostwanted.domain.entities.District;
import mostwanted.domain.entities.Town;
import mostwanted.repository.DistrictRepository;
import mostwanted.repository.TownRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Service
public class DistrictServiceImpl implements DistrictService {

    private final static String DISTRICTS_JSON_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/districts.json";

    private final DistrictRepository districtRepository;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;
    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public DistrictServiceImpl(DistrictRepository districtRepository, FileUtil fileUtil, ValidationUtil validationUtil, TownRepository townRepository, ModelMapper modelMapper, Gson gson) {
        this.districtRepository = districtRepository;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }


    @Override
    public Boolean districtsAreImported() {

        return this.districtRepository.count() > 0;
    }

    @Override
    public String readDistrictsJsonFile() throws IOException {
        return this.fileUtil.readFile(DISTRICTS_JSON_FILE_PATH);
    }

    @Override
    public String importDistricts(String districtsFileContent) {

        StringBuilder importResult = new StringBuilder();
        DistrictImportDto[] districtImportDtos = this.gson.fromJson(districtsFileContent, DistrictImportDto[].class);

        Arrays.stream(districtImportDtos).forEach(districtImportDto -> {
            District districtEntity = this.districtRepository.findByName(districtImportDto.getName()).orElse(null);

            if(districtEntity != null){
                importResult.append(Constants.DUPLICATE_DATA_MESSAGE).append(System.lineSeparator());
                return;
            }
            Town townEntity = this.townRepository.findByName(districtImportDto.getTownName()).orElse(null);
            if(!this.validationUtil.isValid(districtImportDto) || townEntity == null){
                importResult.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                return;
            }
            districtEntity = this.modelMapper.map(districtImportDto, District.class);
            districtEntity.setTown(townEntity);
            this.districtRepository.saveAndFlush(districtEntity);
            importResult.append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE, districtEntity.getClass().getSimpleName(), districtImportDto.getName())).append(System.lineSeparator());

        });
        return importResult.toString().trim();
    }
}
