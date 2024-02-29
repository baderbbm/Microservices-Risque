package com.microservices.controller;

import com.microservices.dto.MedecinNoteDTO;
import com.microservices.dto.PatientDTO;
import com.microservices.services.DiabetesRiskCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class DiabetesRiskController {
	private final String urlMicroserviceGateway = "http://localhost:8081";

	@Autowired
	private DiabetesRiskCalculationService diabetesRiskCalculationService;

	@GetMapping("/diabetes-risk/patients/{patientId}")
	public ResponseEntity<String> getDiabetesRisk(@PathVariable Long patientId) {

		// Récupérer les détails du patient
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth("pra", "pra");
		HttpEntity<String> entity = new HttpEntity<>(headers);

		// Récupérer les détails du patient depuis le microservice via la gateway
		ResponseEntity<PatientDTO> patientResponse = restTemplate
				.exchange(urlMicroserviceGateway + "/patients/" + patientId, HttpMethod.GET, entity, PatientDTO.class);

		// Récupérer les notes du médecin associées au patient

		ResponseEntity<MedecinNoteDTO[]> notesResponse = restTemplate.exchange(
				urlMicroserviceGateway + "/medecin/notes/" + patientId, HttpMethod.GET, entity, MedecinNoteDTO[].class);

		MedecinNoteDTO[] medecinNotes = notesResponse.getBody();

		// Calculer le risque de diabète en utilisant le service de calcul du risque de
		// diabète
		String riskLevel = diabetesRiskCalculationService.calculateDiabetesRisk(patientResponse.getBody(),
				medecinNotes);

		return ResponseEntity.ok(riskLevel);
	}
}
