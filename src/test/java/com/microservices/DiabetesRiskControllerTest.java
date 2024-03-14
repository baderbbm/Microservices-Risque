package com.microservices;

import com.microservices.controller.DiabetesRiskController;
import com.microservices.dto.MedecinNoteDTO;
import com.microservices.dto.PatientDTO;
import com.microservices.services.DiabetesRiskCalculationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DiabetesRiskControllerTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private DiabetesRiskCalculationService diabetesRiskCalculationService;

    @InjectMocks
    private DiabetesRiskController diabetesRiskController;

    @Test
    void testGetDiabetesRisk_None() {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setId(1L); 
        MedecinNoteDTO[] medecinNotes = new MedecinNoteDTO[0]; 
        ResponseEntity<PatientDTO> patientResponse = ResponseEntity.ok(patientDTO);
        ResponseEntity<MedecinNoteDTO[]> notesResponse = ResponseEntity.ok(medecinNotes);
        lenient().when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(PatientDTO.class)))
                .thenReturn(patientResponse);
        lenient().when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(MedecinNoteDTO[].class)))
                .thenReturn(notesResponse);
        when(diabetesRiskCalculationService.calculateDiabetesRisk(any(), any())).thenReturn("None");
        ResponseEntity<String> responseEntity = diabetesRiskController.getDiabetesRisk(1L);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("None", responseEntity.getBody());
    }
    
    @Test
    void testGetDiabetesRisk_Borderline() {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setId(1L); 
        MedecinNoteDTO[] medecinNotes = new MedecinNoteDTO[1]; 
        ResponseEntity<PatientDTO> patientResponse = ResponseEntity.ok(patientDTO);
        ResponseEntity<MedecinNoteDTO[]> notesResponse = ResponseEntity.ok(medecinNotes);
        lenient().when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(PatientDTO.class)))
                .thenReturn(patientResponse);
        lenient().when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(MedecinNoteDTO[].class)))
                .thenReturn(notesResponse);
        when(diabetesRiskCalculationService.calculateDiabetesRisk(any(), any())).thenReturn("Borderline");
        ResponseEntity<String> responseEntity = diabetesRiskController.getDiabetesRisk(2L);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Borderline", responseEntity.getBody());
    }
    
    @Test
    void testGetDiabetesRisk_InDanger() {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setId(1L); 
        MedecinNoteDTO[] medecinNotes = new MedecinNoteDTO[2]; 
        ResponseEntity<PatientDTO> patientResponse = ResponseEntity.ok(patientDTO);
        ResponseEntity<MedecinNoteDTO[]> notesResponse = ResponseEntity.ok(medecinNotes);
        lenient().when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(PatientDTO.class)))
                .thenReturn(patientResponse);
        lenient().when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(MedecinNoteDTO[].class)))
                .thenReturn(notesResponse);
        when(diabetesRiskCalculationService.calculateDiabetesRisk(any(), any())).thenReturn("In Danger");
        ResponseEntity<String> responseEntity = diabetesRiskController.getDiabetesRisk(3L);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("In Danger", responseEntity.getBody());
    }
    
    @Test
    void testGetDiabetesRisk_EarlyOnset() {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setId(1L); 
        MedecinNoteDTO[] medecinNotes = new MedecinNoteDTO[3]; 
        ResponseEntity<PatientDTO> patientResponse = ResponseEntity.ok(patientDTO);
        ResponseEntity<MedecinNoteDTO[]> notesResponse = ResponseEntity.ok(medecinNotes);
        lenient().when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(PatientDTO.class)))
                .thenReturn(patientResponse);
        lenient().when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(MedecinNoteDTO[].class)))
                .thenReturn(notesResponse);
        when(diabetesRiskCalculationService.calculateDiabetesRisk(any(), any())).thenReturn("Early Onset");
        ResponseEntity<String> responseEntity = diabetesRiskController.getDiabetesRisk(4L);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Early Onset", responseEntity.getBody());
    }
}
