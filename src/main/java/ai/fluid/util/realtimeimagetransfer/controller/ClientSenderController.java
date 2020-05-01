package ai.fluid.util.realtimeimagetransfer.controller;

import ai.fluid.util.realtimeimagetransfer.dto.SenderRequestDto;
import ai.fluid.util.realtimeimagetransfer.service.ClientSenderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Slf4j
@AllArgsConstructor
@Controller
public class ClientSenderController {

    @Autowired
    private final ClientSenderService clientSenderService;

    @GetMapping("sender/client-sender")
    public ModelAndView clientSender() {
        return new ModelAndView("client-sender");
    }

    @MessageMapping("/sender/real-time-image")
    @SendTo("/topic/sender/real-time-image")
    public SenderRequestDto requestRealTimeImageToSender(SenderRequestDto senderRequest) {
        return senderRequest;
    }
}