package com.gmedica.assigment.controller;

import com.gmedica.assigment.dto.MedicalCodeDTO;
import com.gmedica.assigment.service.MedicalCodeFileUploadService;
import com.gmedica.assigment.service.MedicalCodeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MedicalCodeController.class)
class MedicalCodeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicalCodeService medicalCodeService;

    @MockBean
    private MedicalCodeFileUploadService medicalCodeCSVUploadService;

    private static MedicalCodeDTO getMedicalCodeDTO() {
        return new MedicalCodeDTO("testSource", "codeListCode", "code1", "testDisplayValue", "longDescription", LocalDate.now(), LocalDate.now().plusDays(1), 1);
    }

    @Test
    void testUploadCSVCodes() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "source,codeListCode".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/medical-codes/upload")
                        .file(file))
                .andExpect(status().isCreated());

        verify(medicalCodeCSVUploadService, times(1)).uploadCodes(any(MultipartFile.class));
    }

    @Test
    void testGetCode() throws Exception {
        MedicalCodeDTO medicalCodeDTO = getMedicalCodeDTO();
        when(medicalCodeService.getCodeByCode("code1")).thenReturn(medicalCodeDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/medical-codes/{code}", "code1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("code1"));

        verify(medicalCodeService, times(1)).getCodeByCode("code1");
    }

    @Test
    void testGetAllCodes() throws Exception {
        List<MedicalCodeDTO> codes = List.of(getMedicalCodeDTO());
        when(medicalCodeService.getAllCodes(0, 10)).thenReturn(codes);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/medical-codes")
                        .param("page", "0")
                        .param("size", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].code").value("code1"));

        verify(medicalCodeService).getAllCodes(0, 10);
    }

    @Test
    void testDeleteCodes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/medical-codes"))
                .andExpect(status().isNoContent());
        verify(medicalCodeService).deleteAllCodes();
    }
}