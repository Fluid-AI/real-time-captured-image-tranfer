package ai.fluid.util.realtimeimagetransfer.controller;

import ai.fluid.util.realtimeimagetransfer.util.FilePathUtil;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
public class RealTimeImageController {

    @RequestMapping("/image/{imageName}")
    public byte[] realTimeImage(@PathVariable String imageName, HttpServletResponse httpServletResponse) throws IOException {
        File file = new File(FilePathUtil.imagesAbsolutePath() + File.separator + imageName);
        if (!file.exists()) {
            return new byte[0];
        }
        return Files.readAllBytes(file.toPath());
    }
}
