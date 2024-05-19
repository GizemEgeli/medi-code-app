package com.gmedica.assigment.util;

import com.gmedica.assigment.dto.MedicalCodeCSVDTO;
import com.gmedica.assigment.exception.MedicalCodeParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CSVCodeFileParserTest {

    private CSVCodeFileParser parser;

    @BeforeEach
    void setUp() {
        parser = new CSVCodeFileParser();
    }

    @Test
    void givenNullInputStream_whenParse_thenThrowMedicalCodeParseException() {
        assertThrows(MedicalCodeParseException.class, () -> parser.parse(null));
    }

    @Test
    void givenInputStreamThrowsIOException_whenParse_thenHandleAsMedicalCodeParseException() throws IOException {
        InputStream inputStream = mock(InputStream.class);
        when(inputStream.read(any())).thenThrow(IOException.class);

        assertThrows(MedicalCodeParseException.class, () -> parser.parse(inputStream));
    }

    @Test
    void givenEmptyCSVInputStream_whenParse_thenThrowMedicalCodeParseExceptionForEmptyRecords() {
        String csvContent = "";
        InputStream inputStream = new ByteArrayInputStream(csvContent.getBytes());

        assertThrows(MedicalCodeParseException.class, () -> parser.parse(inputStream));
    }

    @Test
    void givenValidCSVInputStream_whenParse_thenSuccessfullyParseMedicalCodes() {
        String csvContent = "\"source\",\"codeListCode\",\"code\",\"displayValue\",\"longDescription\",\"fromDate\",\"toDate\",\"sortingPriority\"\n" +
                "\"sourceVal\",\"codeListCodeVal\",\"codeVal\",\"displayValueVal\",\"The long description is necessary\",\"01-01-2024\",\"\",\"1\"";
        InputStream inputStream = new ByteArrayInputStream(csvContent.getBytes());

        List<MedicalCodeCSVDTO> result = parser.parse(inputStream);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("codeVal", result.get(0).getCode());
    }
}
