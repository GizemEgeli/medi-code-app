package com.gmedica.assigment.util;

import com.gmedica.assigment.dto.MedicalCodeCSVDTO;
import com.gmedica.assigment.exception.MedicalCodeParseException;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Component
@Slf4j
public class CSVCodeFileParser {
    public List<MedicalCodeCSVDTO> parse(InputStream inputStream) {
        if (inputStream == null) {
            throw new MedicalCodeParseException("Input stream cannot be null");
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            CsvToBean<MedicalCodeCSVDTO> csvToBean = new CsvToBeanBuilder<MedicalCodeCSVDTO>(reader)
                    .withType(MedicalCodeCSVDTO.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            List<MedicalCodeCSVDTO> records = csvToBean.parse();
            if (records.isEmpty()) {
                throw new MedicalCodeParseException("The provided CSV file is empty or contains no valid records.");
            }
            return records;
        } catch (IOException e) {
            log.error("Error parsing medical code CSV file: {}", e.getMessage(), e);
            throw new MedicalCodeParseException("Error occurred during CSV parsing", e);
        }
    }

}
