package com.hadis.hadis.everyday.api;

import com.hadis.hadis.everyday.model.Hadis;
import com.hadis.hadis.everyday.service.HadisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hadis")
public class HadisController {

    private final HadisService hadisService;

    @PostMapping
    public ResponseEntity<?> addHadis(@RequestBody Hadis hadis) {
        if (hadis.getDescription().isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body("no have description");
        }

        return hadisService.addHadis(hadis);

    }
}
