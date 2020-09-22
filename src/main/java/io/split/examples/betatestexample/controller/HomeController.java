package io.split.examples.betatestexample.controller;

import io.split.client.SplitClient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        List<String> groups =  user.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        String inBeta = splitClient.getTreatment(
            user.getUsername(),
            "BETA_UI_EXPERIENCE",
            Map.of("groups", groups)
        );

        return "on".equals(inBeta) ? "home-beta" : "home";
    }
}
