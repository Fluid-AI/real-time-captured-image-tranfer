package ai.fluid.util.realtimeimagetransfer.controller;

import ai.fluid.util.realtimeimagetransfer.dto.SenderResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Slf4j
@RestController
public class ClientReceiverController {

    @RequestMapping("receiver/client-receiver")
    public ModelAndView clientReceiverPage(Principal principal) {
        ModelAndView model = new ModelAndView("client-receiver");
        model.addObject("userName", principal.getName());
        return model;
    }
    @MessageMapping("/receiver/real-time-image")
    @SendTo("/topic/receiver/real-time-image")
    public SenderResponseDto requestRealTimeImageToSender(SenderResponseDto senderResponseDto) {
        return senderResponseDto;
    }
}
