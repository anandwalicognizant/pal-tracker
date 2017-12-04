package io.pivotal.pal.tracker;

/**
 * Created by aw169 on 12/4/17.
 */

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @GetMapping("/")
    public String sayHello(){
        return "hello";
    }
}
