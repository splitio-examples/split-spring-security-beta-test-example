package io.split.examples.betatestexample.controller;

import io.split.client.SplitClient;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    SplitClient splitClient;

    public HomeController(SplitClient splitClient) {
        this.splitClient = splitClient;
    }

    @GetMapping("/")
    public String home(@AuthenticationPrincipal UserDetails user, Model model) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("roles", user.getAuthorities());

        String inBeta = splitClient.getTreatment(
            user.getAuthorities().iterator().next().getAuthority(),
            "Spring_Security_App"
        );

        return "on".equals(inBeta) ? "home-beta" : "home";
    }
}
