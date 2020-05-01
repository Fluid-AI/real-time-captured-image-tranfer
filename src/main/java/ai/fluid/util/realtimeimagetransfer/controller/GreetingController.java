package ai.fluid.util.realtimeimagetransfer.controller;

import ai.fluid.util.realtimeimagetransfer.dto.HelloMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

@Controller
@RequiredArgsConstructor
@Slf4j
public class GreetingController {

    @Autowired
    private final SimpMessagingTemplate msgTmp;

    @Autowired
    private final SimpUserRegistry simpUserRegistry;

    @MessageMapping("/hello")
//  @SendTo("/topic/greetings")
    public void greeting(HelloMessage message) throws Exception {
        msgTmp.convertAndSend("/topic/greetings-imran", message.getName());
    }

    @GetMapping(value = "/web-socket/senders",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Set<SimpUser> getAllActiveUsers() {
        log.info("{}",simpUserRegistry.getUsers());
        return simpUserRegistry.getUsers();
    }

}