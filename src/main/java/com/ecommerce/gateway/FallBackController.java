package com.ecommerce.gateway;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/fallback")
public class FallBackController {

    @GetMapping("/product")
    public ResponseEntity<List<String>> productServiceFallBackMethod(){
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Collections.singletonList("Product service is unavailable at the moment. Please try again later!!"));
    }

    @GetMapping("/user")
    public ResponseEntity<List<String>> userServiceFallBackMethod(){
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Collections.singletonList("User service is unavailable at the moment. Please try again later!!"));
    }
}
