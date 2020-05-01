package ai.fluid.util.realtimeimagetransfer.controller;

import ai.fluid.util.realtimeimagetransfer.dto.RealTimeImageResponseDto;
import ai.fluid.util.realtimeimagetransfer.dto.SenderRequestDto;
import ai.fluid.util.realtimeimagetransfer.dto.SenderResponseDto;
import ai.fluid.util.realtimeimagetransfer.service.ClientReceiverService;
import ai.fluid.util.realtimeimagetransfer.util.FilePathUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ClientReceiverController {
    @Autowired
    private final ClientReceiverService clientReceiverService;

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
