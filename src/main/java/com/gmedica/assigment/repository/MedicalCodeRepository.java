package com.gmedica.assigment.repository;

import com.gmedica.assigment.entity.MedicalCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicalCodeRepository extends JpaRepository<MedicalCode, Long> {
    Optional<MedicalCode> findByCode(String code);

}
