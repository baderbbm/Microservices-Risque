package com.microservices;

import com.microservices.dto.MedecinNoteDTO;
import com.microservices.dto.PatientDTO;
import com.microservices.services.DiabetesRiskCalculationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import java.sql.Date;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DiabetesRiskCalculationServiceTest {

	@InjectMocks
	private DiabetesRiskCalculationService diabetesRiskCalculationService;

	@Test
	void calculateDiabetesRisk_PatientWithNoneResult() {
		PatientDTO patient = new PatientDTO();
		patient.setDateNaissance(Date.valueOf(LocalDate.of(1966, 12, 31)));
		patient.setGenre("F");
		MedecinNoteDTO[] medecinNotes = { new MedecinNoteDTO("1", 1L, "TestNone",
				"Le patient déclare qu'il 'se sent très bien'. Poids égal ou inférieur au poids recommandé"), };
		String riskLevel = diabetesRiskCalculationService.calculateDiabetesRisk(patient, medecinNotes);
		assertEquals("None", riskLevel);
	}

	@Test
	void calculateDiabetesRisk_PatientWithBorderlineResult() {
		PatientDTO patient = new PatientDTO();
		patient.setDateNaissance(Date.valueOf(LocalDate.of(1945, 6, 24)));
		patient.setGenre("M");
		MedecinNoteDTO[] medecinNotes = { new MedecinNoteDTO("2", 2L, "TestBorderline",
				"Le patient déclare qu'il ressent beaucoup de stress au travail. Il se plaint également que son audition est anormale dernièrement"),
				new MedecinNoteDTO("3", 2L, "TestBorderline",
						"Le patient déclare avoir fait une réaction aux médicaments au cours des 3 derniers mois. Il remarque également que son audition continue d'être anormale"), };
		String riskLevel = diabetesRiskCalculationService.calculateDiabetesRisk(patient, medecinNotes);
		assertEquals("Borderline", riskLevel);
	}

	@Test
	void calculateDiabetesRisk_PatientWithInDangerResult() {
		PatientDTO patient = new PatientDTO();
		patient.setDateNaissance(Date.valueOf(LocalDate.of(2004, 6, 18)));
		patient.setGenre("M");
		MedecinNoteDTO[] medecinNotes = {
				new MedecinNoteDTO("4", 3L, "TestInDanger", "Le patient déclare qu'il fume depuis peu"),
				new MedecinNoteDTO("5", 3L, "TestInDanger",
						"Le patient déclare qu'il est fumeur et qu'il a cessé de fumer l'année dernière. Il se plaint également de crises d’apnée respiratoire anormales. Tests de laboratoire indiquant un taux de cholestérol LDL élevé"), };
		String riskLevel = diabetesRiskCalculationService.calculateDiabetesRisk(patient, medecinNotes);
		assertEquals("In Danger", riskLevel);
	}

	@Test
	void calculateDiabetesRisk_PatientWithEarlyOnsetResult() {
		PatientDTO patient = new PatientDTO();
		patient.setDateNaissance(Date.valueOf(LocalDate.of(2002, 6, 28))); // Date de naissance du patient
																			// TestEarlyOnset
		patient.setGenre("F"); // Genre du patient TestEarlyOnset est féminin
		MedecinNoteDTO[] medecinNotes = { new MedecinNoteDTO("6", 4L, "TestEarlyOnset",
				"Le patient déclare qu'il lui est devenu difficile de monter les escaliers. Il se plaint également d’être essoufflé. Tests de laboratoire indiquant que les anticorps sont élevés. Réaction aux médicaments"),
				new MedecinNoteDTO("7", 4L, "TestEarlyOnset",
						"Le patient déclare qu'il a mal au dos lorsqu'il reste assis pendant longtemps"),
				new MedecinNoteDTO("8", 4L, "TestEarlyOnset",
						"Le patient déclare avoir commencé à fumer depuis peu. Hémoglobine A1C supérieure au niveau recommandé"),
				new MedecinNoteDTO("9", 4L, "TestEarlyOnset", "Taille, Poids, Cholestérol, Vertige et Réaction"), };
		String riskLevel = diabetesRiskCalculationService.calculateDiabetesRisk(patient, medecinNotes);
		assertEquals("Early Onset", riskLevel);
	}

	@Test
	void testIsInDanger_MaleUnder30_With3Triggers() {
		boolean result = diabetesRiskCalculationService.isInDanger(25, "M", 3);
		assertTrue(result);
	}

	@Test
	void testIsInDanger_MaleOver30_With6Triggers() {
		boolean result = diabetesRiskCalculationService.isInDanger(35, "M", 6);
		assertTrue(result);
	}

	@Test
	void testIsInDanger_FemaleUnder30_With4Triggers() {
		boolean result = diabetesRiskCalculationService.isInDanger(28, "F", 4);
		assertTrue(result);
	}

	@Test
	void testIsInDanger_FemaleOver30_With7Triggers() {
		boolean result = diabetesRiskCalculationService.isInDanger(32, "F", 7);
		assertTrue(result);
	}

	@Test
	void testIsEarlyOnset_MaleUnder30_With5Triggers() {
		boolean result = diabetesRiskCalculationService.isEarlyOnset(25, "M", 5);
		assertTrue(result);
	}

	@Test
	void testIsEarlyOnset_MaleOver30_With8Triggers() {
		boolean result = diabetesRiskCalculationService.isEarlyOnset(35, "M", 8);
		assertTrue(result);
	}

	@Test
	void testIsEarlyOnset_FemaleUnder30_With7Triggers() {
		boolean result = diabetesRiskCalculationService.isEarlyOnset(28, "F", 7);
		assertTrue(result);
	}

	@Test
	void testIsEarlyOnset_FemaleOver30_With8Triggers() {
		boolean result = diabetesRiskCalculationService.isEarlyOnset(32, "F", 8);
		assertTrue(result);
	}
}
