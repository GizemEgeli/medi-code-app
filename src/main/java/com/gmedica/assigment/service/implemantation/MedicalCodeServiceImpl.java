package com.gmedica.assigment.service.implemantation;

import com.gmedica.assigment.dto.MedicalCodeDTO;
import com.gmedica.assigment.entity.MedicalCode;
import com.gmedica.assigment.exception.MedicalCodeServiceException;
import com.gmedica.assigment.mapper.MedicalCodeMapper;
import com.gmedica.assigment.repository.MedicalCodeRepository;
import com.gmedica.assigment.service.MedicalCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MedicalCodeServiceImpl implements MedicalCodeService {
    private final MedicalCodeMapper medicalCodeMapper;
    private final MedicalCodeRepository medicalCodeRepository;

    @Override
    public void saveCodes(List<MedicalCodeDTO> medicalCodeDTOList) {
        try {
            List<MedicalCode> medicalCodes = medicalCodeDTOList.stream()
                    .map(medicalCodeMapper::toMedicalCode)
                    .toList();
            medicalCodeRepository.saveAll(medicalCodes);
        } catch (Exception e) {
            log.error("Exception occurred while saving codes: {}", e.getMessage(), e);
            throw new MedicalCodeServiceException("Failed to save medical codes due to an exception.", e);
        }
    }

    @Override
    public List<MedicalCodeDTO> getAllCodes(int page, int size) {
        Sort sortMethod = Sort.by(Sort.DEFAULT_DIRECTION, "sortingPriority");
        Pageable pageable = PageRequest.of(page, size, sortMethod);
        Page<MedicalCode> pageEntity = medicalCodeRepository.findAll(pageable);
        List<MedicalCode> deliveries = pageEntity.getContent();
        return deliveries.stream().map(medicalCodeMapper::toMedicalCodeDTO).toList();
    }

    @Override
    public MedicalCodeDTO getCodeByCode(String code) {
        return medicalCodeRepository.findByCode(code)
                .map(medicalCodeMapper::toMedicalCodeDTO)
                .orElseThrow(() -> new MedicalCodeServiceException("Medical code with the specified code not found: " + code));
    }

    @Override
    public void deleteAllCodes() {
        try {
            medicalCodeRepository.deleteAll();
        } catch (Exception e) {
            log.error("Exception occurred while deleting all codes: {}", e.getMessage(), e);
            throw new MedicalCodeServiceException("Failed to delete all medical codes due to an exception.", e);
        }
    }
}
