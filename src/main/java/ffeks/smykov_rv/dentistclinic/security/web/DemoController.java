package ffeks.smykov_rv.dentistclinic.security.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/homepage")
public class DemoController {

    @GetMapping("/basic-auth")
    public String basicAuth() {
        return "Basic Auth";
    }

    @GetMapping("/user-auth")
    public String userAuth() {
        return "User Auth";
    }

    @GetMapping("/admin-auth")
    public String adminAuth() {
        return "Admin Auth";
    }


}
