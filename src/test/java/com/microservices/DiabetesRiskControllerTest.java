package com.microservices;

import com.microservices.controller.DiabetesRiskController;
import com.microservices.dto.MedecinNoteDTO;
import com.microservices.dto.PatientDTO;
import com.microservices.services.DiabetesRiskCalculationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class DiabetesRiskControllerTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private DiabetesRiskCalculationService diabetesRiskCalculationService;

    @Spy
    @InjectMocks
    private DiabetesRiskController diabetesRiskController;

    @BeforeEach
    void testBeforeEach() {
        MockitoAnnotations.openMocks(this);
        doReturn(restTemplate).when(diabetesRiskController).createRestTemplate();
    }
    
    @Test
    void testGetDiabetesRisk_None() {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setId(1L);
        MedecinNoteDTO[] medecinNotes = new MedecinNoteDTO[0];
        when(restTemplate.exchange(eq("http://192.168.1.3:8081/patients/1"), eq(HttpMethod.GET), any(),
                eq(PatientDTO.class))).thenReturn(ResponseEntity.ok(patientDTO));
        when(restTemplate.exchange(eq("http://192.168.1.3:8081/medecin/notes/1"), eq(HttpMethod.GET), any(),
                eq(MedecinNoteDTO[].class))).thenReturn(ResponseEntity.ok(medecinNotes));
        when(diabetesRiskCalculationService.calculateDiabetesRisk(any(), any())).thenReturn("None");
        ResponseEntity<String> responseEntity = diabetesRiskController.getDiabetesRisk(1L);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("None", responseEntity.getBody());
    }
    /*
	@Test
	void testGetDiabetesRisk_Borderline() {
		PatientDTO patientDTO = new PatientDTO();
		patientDTO.setId(2L);
		MedecinNoteDTO[] medecinNotes = new MedecinNoteDTO[1];
		when(restTemplate.exchange(eq("http://192.168.1.3:8081/patients/2"), eq(HttpMethod.GET), any(),
				eq(PatientDTO.class))).thenReturn(ResponseEntity.ok(patientDTO));
		when(restTemplate.exchange(eq("http://192.168.1.3:8081/medecin/notes/2"), eq(HttpMethod.GET), any(),
				eq(MedecinNoteDTO[].class))).thenReturn(ResponseEntity.ok(medecinNotes));
		when(diabetesRiskCalculationService.calculateDiabetesRisk(any(), any())).thenReturn("Borderline");
		ResponseEntity<String> responseEntity = diabetesRiskController.getDiabetesRisk(2L);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Borderline", responseEntity.getBody());
	}

	@Test
	void testGetDiabetesRisk_InDanger() {
		PatientDTO patientDTO = new PatientDTO();
		patientDTO.setId(3L);
		MedecinNoteDTO[] medecinNotes = new MedecinNoteDTO[2];
		when(restTemplate.exchange(eq("http://192.168.1.3:8081/patients/3"), eq(HttpMethod.GET), any(),
				eq(PatientDTO.class))).thenReturn(ResponseEntity.ok(patientDTO));
		when(restTemplate.exchange(eq("http://192.168.1.3:8081/medecin/notes/3"), eq(HttpMethod.GET), any(),
				eq(MedecinNoteDTO[].class))).thenReturn(ResponseEntity.ok(medecinNotes));
		when(diabetesRiskCalculationService.calculateDiabetesRisk(any(), any())).thenReturn("In Danger");
		ResponseEntity<String> responseEntity = diabetesRiskController.getDiabetesRisk(3L);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("In Danger", responseEntity.getBody());
	}

	@Test
	void testGetDiabetesRisk_EarlyOnset() {
		PatientDTO patientDTO = new PatientDTO();
		patientDTO.setId(4L);
		MedecinNoteDTO[] medecinNotes = new MedecinNoteDTO[3];
		when(restTemplate.exchange(eq("http://192.168.1.3:8081/patients/4"), eq(HttpMethod.GET), any(),
				eq(PatientDTO.class))).thenReturn(ResponseEntity.ok(patientDTO));
		when(restTemplate.exchange(eq("http://192.168.1.3:8081/medecin/notes/4"), eq(HttpMethod.GET), any(),
				eq(MedecinNoteDTO[].class))).thenReturn(ResponseEntity.ok(medecinNotes));
		when(diabetesRiskCalculationService.calculateDiabetesRisk(any(), any())).thenReturn("Early Onset");
		ResponseEntity<String> responseEntity = diabetesRiskController.getDiabetesRisk(4L);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Early Onset", responseEntity.getBody());
	}
	*/
}