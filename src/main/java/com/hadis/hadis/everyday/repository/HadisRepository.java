package com.hadis.hadis.everyday.repository;

import com.hadis.hadis.everyday.model.Hadis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HadisRepository extends JpaRepository<Hadis, Long> {
}
