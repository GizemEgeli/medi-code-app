package com.gmedica.assigment.service;

import org.springframework.web.multipart.MultipartFile;

public interface MedicalCodeFileUploadService {
    void uploadCodes(MultipartFile multipartFile);
}
