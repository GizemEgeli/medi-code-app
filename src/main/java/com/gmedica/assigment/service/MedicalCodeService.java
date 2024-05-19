package com.gmedica.assigment.service;

import com.gmedica.assigment.dto.MedicalCodeDTO;

import java.util.List;

public interface MedicalCodeService {

    void saveCodes(List<MedicalCodeDTO> medicalCodeDTOList);

    List<MedicalCodeDTO> getAllCodes(int page, int size);

    MedicalCodeDTO getCodeByCode(String code);

    void deleteAllCodes();
}
