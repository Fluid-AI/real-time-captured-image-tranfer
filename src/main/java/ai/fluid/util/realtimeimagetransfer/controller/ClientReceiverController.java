package ai.fluid.util.realtimeimagetransfer.controller;

import ai.fluid.util.realtimeimagetransfer.util.FilePathUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class ClientReceiverController {

    @RequestMapping({"client-receiver", "/"})
    public ModelAndView clientReceiverPage() {
        ModelAndView model = new ModelAndView("client-receiver");
        File[] a = new File(FilePathUtil.imagesAbsolutePath()).listFiles();
        List<String> list = Arrays.asList(a).stream()
                .sorted((f1, f2) -> Long.compare(f2.lastModified(), f1.lastModified()))
                .map(File::getName)
                .collect(Collectors.toList());
        model.addObject("images", list);
        return model;
    }
}
