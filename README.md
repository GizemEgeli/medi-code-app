# Medical Code Management API

## Introduction

Spring Boot application designed to manage medical codes. It provides REST API endpoints to upload medical codes from a CSV file, retrieve medical codes, list all medical codes with pagination, and delete all medical codes.

## API Endpoints

### Upload Medical Codes:
- URL: /api/v1/medical-codes/upload
- Method: POST
- Description: Uploads medical codes from a provided CSV file.
- Request Parameters:
    - file: CSV file containing medical codes
- Success Response:
    - Code: 201 (CREATED)

### Get a Medical Code:
- URL: /api/v1/medical-codes/{code}
- Method: GET
- Description: Retrieves a medical code by its unique code.
- URL Parameters:
    - code: Unique identifier of the medical code
- Success Response:
    - Code: 200 (OK)
- Content: MedicalCodeDTO

### Get all Medical Codes:
- URL: /api/v1/medical-codes
- Method: GET
- Description:  Retrieves all medical codes with pagination.
- Success Response:
    - Code: 200 (OK)
- Query Parameters:
    - page: Page number (default 0)
    - size: Page size (default 10)
- Content: List of MedicalCodeDTO

### Delete all Medical Codes:
- URL: /api/v1/medical-codes
- Method: DELETE
- Description:  Deletes all medical codes from the database.
- Success Response:
    - Code: 204 (NO CONTENT)