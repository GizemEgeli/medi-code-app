package com.gmedica.assigment.dto;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MedicalCodeCSVDTO {
    @CsvBindByName(column = "source")
    private String source;

    @CsvBindByName(column = "codeListCode")
    private String codeListCode;

    @CsvBindByName(column = "code")
    private String code;

    @CsvBindByName(column = "displayValue")
    private String displayValue;

    @CsvBindByName(column = "longDescription")
    private String longDescription;

    @CsvBindByName(column = "fromDate")
    @CsvDate(value = "dd-MM-yyyy")
    private LocalDate fromDate;

    @CsvBindByName(column = "toDate")
    @CsvDate(value = "dd-MM-yyyy")
    private LocalDate toDate;

    @CsvBindByName(column = "sortingPriority")
    private int sortingPriority;

    public void setSource(String source) {
        this.source = sanitize(source);
    }

    public void setCodeListCode(String codeListCode) {
        this.codeListCode = sanitize(codeListCode);
    }

    public void setCode(String code) {
        this.code = sanitize(code);
    }

    public void setDisplayValue(String displayValue) {
        this.displayValue = sanitize(displayValue);
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = sanitize(longDescription);
    }

    private String sanitize(String input) {
        if (input != null) {
            input = input.replaceAll("[^\\p{L}\\p{Nd}\\s,.]", "").trim();
        }
        return input;
    }
}
