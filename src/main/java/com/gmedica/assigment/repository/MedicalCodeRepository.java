package com.gmedica.assigment.repository;

import com.gmedica.assigment.entity.MedicalCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalCodeRepository extends JpaRepository<MedicalCode, Long> {
}
