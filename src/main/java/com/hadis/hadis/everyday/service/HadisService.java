package com.hadis.hadis.everyday.service;

import com.hadis.hadis.everyday.model.Hadis;
import com.hadis.hadis.everyday.repository.HadisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HadisService {

    private final HadisRepository hadisRepository;

    public List<Hadis> getAllHadis() {
        return hadisRepository.findAll();
    }

    public ResponseEntity<?> addHadis(Hadis hadis) {
        hadisRepository.save(hadis);
        return ResponseEntity
                .ok("successfully saved");
    }
}
