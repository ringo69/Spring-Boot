package hello;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {

    @Value("${greeting.message}")
    String message;

    @RequestMapping("/")
    public String index() {
        return message;
    }

}
