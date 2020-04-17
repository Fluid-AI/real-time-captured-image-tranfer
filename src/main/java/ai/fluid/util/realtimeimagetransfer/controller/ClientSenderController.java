package ai.fluid.util.realtimeimagetransfer.controller;

import ai.fluid.util.realtimeimagetransfer.service.ClientSenderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Slf4j
@RestController
@AllArgsConstructor
public class ClientSenderController {

    @Autowired
    private final ClientSenderService clientSenderService;

    @GetMapping("client-sender")
    public ModelAndView clientSender() {
        ModelAndView modelAndView = new ModelAndView("client-sender");
        modelAndView.addObject("captureImageInterval",clientSenderService.getCaptureImageInterval());
        return modelAndView;
    }

    @PostMapping("/upload")
    public boolean uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            clientSenderService.saveRealTimeImageInDir(file);
        } catch (IOException e) {
            log.error("Could not upload file", e);
            return false;
        }

        return true;
    }
}