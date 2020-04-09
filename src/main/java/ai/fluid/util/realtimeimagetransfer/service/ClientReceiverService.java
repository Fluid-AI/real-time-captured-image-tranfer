package ai.fluid.util.realtimeimagetransfer.service;

import ai.fluid.util.realtimeimagetransfer.dto.RealTimeImageResponseDto;
import ai.fluid.util.realtimeimagetransfer.util.FilePathUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class ClientReceiverService {

    public List<RealTimeImageResponseDto> getRealTimeImageResponseDtos() {
        File[] allRealTimeImagesArr = getAllRealTimeImages();

        return Stream.of(allRealTimeImagesArr)
                .sorted((f1, f2) -> Long.compare(f2.lastModified(), f1.lastModified()))
                .map(File::getName)
                .map(this::mapFileNameToImageDetailsDto)
                .collect(Collectors.toList());
    }

    File[] getAllRealTimeImages() {
        File[] allRealTimeImagesArr = new File(FilePathUtil.imagesAbsolutePath()).listFiles();

        if (allRealTimeImagesArr == null) {
            allRealTimeImagesArr = new File[0];
        }
        return allRealTimeImagesArr;
    }

    RealTimeImageResponseDto mapFileNameToImageDetailsDto(String imageName) {
        return RealTimeImageResponseDto.builder()
                .imageName(imageName)
                .imageTime(Long.parseLong(imageName.substring(0, imageName.length() - 5))) // remove last .jpeg
                .build();
    }

}
