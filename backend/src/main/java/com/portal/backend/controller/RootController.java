package com.portal.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class RootController {

    @GetMapping("/")
    public void home(HttpServletResponse response) throws IOException {
        response.sendRedirect("swagger-ui/index.html");
    }
}
