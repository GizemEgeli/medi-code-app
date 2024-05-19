package com.gmedica.assigment.service.implemantation;

import com.gmedica.assigment.dto.MedicalCodeCSVDTO;
import com.gmedica.assigment.exception.MedicalCodeUploadException;
import com.gmedica.assigment.mapper.MedicalCodeMapper;
import com.gmedica.assigment.service.MedicalCodeFileUploadService;
import com.gmedica.assigment.service.MedicalCodeService;
import com.gmedica.assigment.util.CSVCodeFileParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MedicalCodeFileUploadServiceImpl implements MedicalCodeFileUploadService {
    private final CSVCodeFileParser csvCodeFileParser;
    private final MedicalCodeService medicalCodeService;
    private final MedicalCodeMapper medicalCodeMapper;

    @Override
    public void uploadCodes(MultipartFile multipartFile) {
        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new MedicalCodeUploadException("Upload failed: The file is empty or null.");
        }

        if (!"text/csv".equalsIgnoreCase(multipartFile.getContentType())) {
            throw new MedicalCodeUploadException("Upload failed: Unsupported file type. Expected text/csv.");
        }

        try {
            List<MedicalCodeCSVDTO> medicalCodeDTOList = csvCodeFileParser.parse(multipartFile.getInputStream());
            medicalCodeService.saveCodes(medicalCodeDTOList
                    .stream()
                    .map(medicalCodeMapper::toMedicalCodeDTO)
                    .toList());
        } catch (IOException e) {
            log.error("IOException error occurred while uploading codes", e);
            throw new MedicalCodeUploadException("IOException error occurred while uploading codes: " + e.getMessage(), e);
        }
    }
}
