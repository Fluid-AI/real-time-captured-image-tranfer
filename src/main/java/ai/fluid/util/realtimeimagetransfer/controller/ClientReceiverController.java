package ai.fluid.util.realtimeimagetransfer.controller;

import ai.fluid.util.realtimeimagetransfer.dto.RealTimeImageResponseDto;
import ai.fluid.util.realtimeimagetransfer.service.ClientReceiverService;
import ai.fluid.util.realtimeimagetransfer.util.FilePathUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ClientReceiverController {
    @Autowired
    private final ClientReceiverService clientReceiverService;

    @RequestMapping("client-receiver")
    public ModelAndView clientReceiverPage() {
        ModelAndView model = new ModelAndView("client-receiver");

        List<RealTimeImageResponseDto> sortedRealTimeImageList = clientReceiverService.getRealTimeImageResponseDtos();
        model.addObject("realTimeImages", sortedRealTimeImageList);

        return model;
    }

    @RequestMapping("/image/{imageName}")
    public byte[] getImage(@PathVariable String imageName, HttpServletResponse httpServletResponse) throws IOException {
        File file = new File(FilePathUtil.imagesAbsolutePath() + File.separator + imageName);
        if (!file.exists()) {
            return new byte[0];
        }
        return Files.readAllBytes(file.toPath());
    }
}
