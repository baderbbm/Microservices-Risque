package com.microservices.controller;

import com.microservices.dto.MedecinNoteDTO;
import com.microservices.dto.PatientDTO;
import com.microservices.services.DiabetesRiskCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
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
		RestTemplate restTemplate = new RestTemplate();
		// Récupérer les détails du patient
		PatientDTO patient = restTemplate.getForObject(urlMicroserviceGateway + "/patients/" + patientId,
				PatientDTO.class);

		// Récupérer les notes du médecin associées au patient
		ResponseEntity<MedecinNoteDTO[]> responseEntity = restTemplate
				.getForEntity(urlMicroserviceGateway + "/medecin/notes/" + patientId, MedecinNoteDTO[].class);
		MedecinNoteDTO[] medecinNotes = responseEntity.getBody();

		// Calculer le risque de diabète en utilisant le service de calcul du risque de
		// diabète
		String riskLevel = diabetesRiskCalculationService.calculateDiabetesRisk(patient, medecinNotes);

		return ResponseEntity.ok(riskLevel);
	}
}
