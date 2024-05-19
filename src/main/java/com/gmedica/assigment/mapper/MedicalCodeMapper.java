package com.gmedica.assigment.mapper;

import com.gmedica.assigment.dto.MedicalCodeCSVDTO;
import com.gmedica.assigment.dto.MedicalCodeDTO;
import com.gmedica.assigment.entity.MedicalCode;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MedicalCodeMapper {
    MedicalCodeDTO toMedicalCodeDTO(MedicalCodeCSVDTO medicalCodeCSVDTO);

    MedicalCode toMedicalCode(MedicalCodeDTO medicalCodeDTO);

    MedicalCodeDTO toMedicalCodeDTO(MedicalCode medicalCode);

}
