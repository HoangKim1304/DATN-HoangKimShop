package com.shoppingwithme.hoangkimshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccessDeniedController {

	@GetMapping("/access-denied")
    public String getAccessDenied() {
        return "accessDenied";
    }
}
