package hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    /*@Value("${greeting.message}")
    String message;*/

    @RequestMapping("/")
    public String index() {
        return System.getenv().getOrDefault("GREETINGS_MESSAGE", "Hello");
    }

}
