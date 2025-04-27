package intro_softw_engi_groupb.wheel_of_fortune_web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class WheelOfFortuneWebApplication {

	@GetMapping("/hello")
    public String check() {
        return "こんにちは、世界！🇯🇵";
    }

	public static void main(String[] args) {
		SpringApplication.run(WheelOfFortuneWebApplication.class, args);
	}

}
