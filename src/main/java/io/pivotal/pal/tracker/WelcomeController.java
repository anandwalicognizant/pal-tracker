package io.pivotal.pal.tracker;

/**
 * Created by aw169 on 12/4/17.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    String welcomeMessage;

    public WelcomeController(@Value("${welcome.message}")String message){
        this.welcomeMessage = message;
    }

    @GetMapping("/")
    public String sayHello(){

        return this.welcomeMessage;
    }
}
