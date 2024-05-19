package com.gmedica.assigment.service.implemantation;

import com.gmedica.assigment.dto.MedicalCodeCSVDTO;
import com.gmedica.assigment.dto.MedicalCodeDTO;
import com.gmedica.assigment.exception.MedicalCodeParseException;
import com.gmedica.assigment.exception.MedicalCodeUploadException;
import com.gmedica.assigment.mapper.MedicalCodeMapper;
import com.gmedica.assigment.service.MedicalCodeService;
import com.gmedica.assigment.util.CSVCodeFileParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class MedicalCodeFileUploadServiceImplTest {
    @Mock
    private MedicalCodeService medicalCodeService;
    @Mock
    private CSVCodeFileParser csvCodeFileParser;
    @Mock
    private MedicalCodeMapper medicalCodeMapper;
    @InjectMocks
    private MedicalCodeFileUploadServiceImpl uploadService;
    private MultipartFile file;
    private List<MedicalCodeCSVDTO> medicalCodeCSVDTOList;
    private List<MedicalCodeDTO> medicalCodeDTOList;


    @Test
    void givenEmptyFile_whenUploadCodes_thenThrowException() {
        file = new MockMultipartFile("file", "testFile.csv", "text/csv", new byte[0]);
        assertThrows(MedicalCodeUploadException.class, () -> uploadService.uploadCodes(file));
    }


    @Test
    void givenNullFile_whenUploadCodes_thenThrowException() {
        assertThrows(MedicalCodeUploadException.class, () -> uploadService.uploadCodes(null));
    }

    @Test
    void givenUnsupportedFileType_whenUploadCodes_thenThrowException() {
        file = new MockMultipartFile("file", "testFile.txt", "text/plain", "content".getBytes());
        assertThrows(MedicalCodeUploadException.class, () -> uploadService.uploadCodes(file));
    }

    @Test
    void givenValidCSVFile_whenUploadCodes_thenSaveCodes() {
        file = new MockMultipartFile("file", "testFile.csv", "text/csv", "code,description\n123,Test Description".getBytes());

        MedicalCodeDTO medicalCodeDTO = new MedicalCodeDTO("source", "codeListCode", "123", "Test Description", "Long Description", LocalDate.now(), LocalDate.now(), 1);
        medicalCodeCSVDTOList = Collections.singletonList(new MedicalCodeCSVDTO("source", "codeListCode", "123", "Test Description", "Long Description", LocalDate.now(), LocalDate.now(), 1));

        medicalCodeDTOList = Collections.singletonList(medicalCodeDTO);

        when(csvCodeFileParser.parse(any())).thenReturn(medicalCodeCSVDTOList);
        when(medicalCodeMapper.toMedicalCodeDTO(any(MedicalCodeCSVDTO.class))).thenReturn(medicalCodeDTO);

        uploadService.uploadCodes(file);

        verify(medicalCodeService).saveCodes(any());
        verify(csvCodeFileParser).parse(any());
    }

    @Test
    void givenCSVFile_whenParsingThrowsException_thenThrowUploadServiceException() {
        file = new MockMultipartFile("file", "testFile.csv", "text/csv", "code,description".getBytes());

        when(csvCodeFileParser.parse(any())).thenThrow(new MedicalCodeParseException("Parsing error"));

        assertThrows(MedicalCodeParseException.class, () -> uploadService.uploadCodes(file));
    }
}