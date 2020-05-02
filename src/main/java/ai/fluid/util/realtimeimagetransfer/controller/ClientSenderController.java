package ai.fluid.util.realtimeimagetransfer.controller;

import ai.fluid.util.realtimeimagetransfer.dto.SenderRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class ClientSenderController {


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