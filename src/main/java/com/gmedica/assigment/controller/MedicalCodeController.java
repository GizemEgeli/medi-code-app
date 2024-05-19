package com.gmedica.assigment.controller;

import com.gmedica.assigment.dto.MedicalCodeDTO;
import com.gmedica.assigment.service.MedicalCodeFileUploadService;
import com.gmedica.assigment.service.MedicalCodeService;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/medical-codes")
public class MedicalCodeController {
    private final MedicalCodeFileUploadService medicalCodeCSVUploadService;
    private final MedicalCodeService medicalCodeService;

    @PostMapping("/upload")
    public ResponseEntity<Void> uploadCodes(@NotNull @RequestParam("file") MultipartFile codeFile) {
        medicalCodeCSVUploadService.uploadCodes(codeFile);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{code}")
    public ResponseEntity<MedicalCodeDTO> getCode(@NotNull @PathVariable String code) {
        MedicalCodeDTO medicalCodeDTO = medicalCodeService.getCodeByCode(code);
        return ResponseEntity.status(HttpStatus.OK).body(medicalCodeDTO);
    }

    @GetMapping
    public ResponseEntity<List<MedicalCodeDTO>> getAllCodes(@NotNull @RequestParam(value = "page") int page, @RequestParam(value = "size", defaultValue = "10") int size) {
        List<MedicalCodeDTO> medicalCodeDTOList = medicalCodeService.getAllCodes(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(medicalCodeDTOList);
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteCodes() {
        medicalCodeService.deleteAllCodes();
        return ResponseEntity.noContent().build();
    }

}
