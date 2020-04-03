package ai.fluid.util.realtimeimagetransfer.controller;

import ai.fluid.util.realtimeimagetransfer.dto.RealTimeImageResponseDto;
import ai.fluid.util.realtimeimagetransfer.util.FilePathUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RestController
public class ClientReceiverController {

    @RequestMapping("client-receiver")
    public ModelAndView clientReceiverPage() {
        ModelAndView model = new ModelAndView("client-receiver");

        File[] allRealTimeImagesArr = new File(FilePathUtil.imagesAbsolutePath()).listFiles();

        if (allRealTimeImagesArr == null) {
            allRealTimeImagesArr = new File[0];
        }

        List<RealTimeImageResponseDto> sortedRealTimeImageList = Stream.of(allRealTimeImagesArr)
                .sorted((f1, f2) -> Long.compare(f2.lastModified(), f1.lastModified()))
                .map(File::getName)
                .map(this::mapFileNameToImageDetailsDto)
                .collect(Collectors.toList());

        model.addObject("realTimeImages", sortedRealTimeImageList);
        return model;
    }

    RealTimeImageResponseDto mapFileNameToImageDetailsDto(String imageName) {
        return RealTimeImageResponseDto.builder()
                .imageName(imageName)
                .imageTime(Long.parseLong(imageName.substring(0, imageName.length() - 5))) // remove last .jpeg
                .build();
    }
}
