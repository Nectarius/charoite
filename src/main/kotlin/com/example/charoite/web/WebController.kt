package com.example.charoite.web

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class WebController {

    @GetMapping("/")
    fun index(): String {
        return "redirect:/discussion"
    }

    @GetMapping("/login")
    fun login(): String {
        return "login"
    }
}
