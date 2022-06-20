package swlayer.project.demo.enggine.configure;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/")
public class HomeController {
    @GetMapping
    public String homePage() {
           return "Start Swlayer Aplication At " + new Date();
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String admin() {
        return "admin";
    }
    @GetMapping("/user")
    @PreAuthorize("hasAuthority('USER')")
    public String user() {
        return "user";
    }

}
