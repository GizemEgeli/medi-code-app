package com.gmedica.assigment.service.implemantation;

import com.gmedica.assigment.dto.MedicalCodeDTO;
import com.gmedica.assigment.entity.MedicalCode;
import com.gmedica.assigment.exception.MedicalCodeServiceException;
import com.gmedica.assigment.mapper.MedicalCodeMapper;
import com.gmedica.assigment.repository.MedicalCodeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedicalCodeServiceImplTest {
    @Mock
    private MedicalCodeMapper medicalCodeMapper;

    @Mock
    private MedicalCodeRepository medicalCodeRepository;

    @InjectMocks
    private MedicalCodeServiceImpl medicalCodeService;

    private MedicalCodeDTO medicalCodeDTO;
    private MedicalCode medicalCode;

    @BeforeEach
    void setUp() {
        medicalCodeDTO = new MedicalCodeDTO("testSource", "codeListCodeVal", "code1", "testDisplayValue", "longDescription", LocalDate.now(), LocalDate.now().plusDays(1), 1);

        medicalCode = new MedicalCode();
    }

    @Test
    void givenMedicalCodeDTOList_whenSaveCodes_thenSaveMedicalCodes() {
        List<MedicalCodeDTO> medicalCodeDTOList = Collections.singletonList(medicalCodeDTO);
        when(medicalCodeMapper.toMedicalCode(any(MedicalCodeDTO.class))).thenReturn(medicalCode);

        medicalCodeService.saveCodes(medicalCodeDTOList);

        verify(medicalCodeRepository).saveAll(anyList());
    }

    @Test
    void givenErrorOnSave_whenSaveCodes_thenThrowServiceException() {
        List<MedicalCodeDTO> medicalCodeDTOList = Collections.singletonList(medicalCodeDTO);
        when(medicalCodeMapper.toMedicalCode(any(MedicalCodeDTO.class))).thenReturn(medicalCode);
        doThrow(RuntimeException.class).when(medicalCodeRepository).saveAll(anyList());

        assertThrows(MedicalCodeServiceException.class, () -> medicalCodeService.saveCodes(medicalCodeDTOList));
    }

    @Test
    void givenPageableRequest_whenGetAllCodes_thenReturnPagedMedicalCodes() {
        Page<MedicalCode> pagedResponse = new PageImpl<>(Collections.singletonList(medicalCode));
        when(medicalCodeRepository.findAll(any(Pageable.class))).thenReturn(pagedResponse);
        when(medicalCodeMapper.toMedicalCodeDTO(any(MedicalCode.class))).thenReturn(medicalCodeDTO);

        List<MedicalCodeDTO> results = medicalCodeService.getAllCodes(0, 10);

        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
        assertEquals("code1", results.get(0).getCode());
    }

    @Test
    void givenValidCode_whenGetCodeByCode_thenReturnMedicalCodeDTO() {
        when(medicalCodeRepository.findByCode("code")).thenReturn(Optional.of(medicalCode));
        when(medicalCodeMapper.toMedicalCodeDTO(medicalCode)).thenReturn(medicalCodeDTO);

        MedicalCodeDTO result = medicalCodeService.getCodeByCode("code");

        assertNotNull(result);
        assertEquals("code1", result.getCode());
    }

    @Test
    void givenNonexistentCode_whenGetCodeByCode_thenThrowException() {
        String nonExistentCode = "1";
        when(medicalCodeRepository.findByCode(nonExistentCode)).thenReturn(Optional.empty());
        Assertions.assertThrows(MedicalCodeServiceException.class, () -> medicalCodeService.getCodeByCode(nonExistentCode));
    }

    @Test
    void whenDeleteAllCodes_thenRepositoryDeleteAllCalled() {
        medicalCodeService.deleteAllCodes();
        verify(medicalCodeRepository).deleteAll();
    }

    @Test
    void givenErrorOnDelete_whenDeleteAllCodes_thenThrowServiceException() {
        doThrow(RuntimeException.class).when(medicalCodeRepository).deleteAll();
        assertThrows(MedicalCodeServiceException.class, () -> medicalCodeService.deleteAllCodes());
    }
}