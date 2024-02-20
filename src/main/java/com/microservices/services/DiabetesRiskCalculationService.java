package com.microservices.services;

import com.microservices.dto.MedecinNoteDTO;
import com.microservices.dto.PatientDTO;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.regex.*;

@Service
public class DiabetesRiskCalculationService {

    public String calculateDiabetesRisk(PatientDTO patient, MedecinNoteDTO[] medecinNotes) {
        int age = calculateAge(patient.getDateNaissance());
        String gender = patient.getGenre();

        // Compter le nombre de déclencheurs présents dans les notes médicales
        int triggerCount = countTriggers(medecinNotes);

        if (triggerCount <= 1) {
            return "None";
        } else if (triggerCount >= 2 && triggerCount <= 5 && age > 30) {
            return "Borderline";
        } else if (isInDanger(age, gender, triggerCount)) {
            return "In Danger";
        } else {
            return "Early Onset";
        }
    }

    private int calculateAge(Date date) {
        LocalDate birthDate = date.toLocalDate();
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthDate, currentDate).getYears();
    }

    private int countTriggers(MedecinNoteDTO[] medecinNotes) {
        int count = 0;
        // Modèle de regex pour correspondre aux déclencheurs
       String regex = "\\b(Hémoglobine A1C|Microalbumine|Taille|Poids|fumeur|Fumeur|Fumeuse|Anormal|anormale|anormales|cholestérol|Cholestérol|Vertiges|Rechute|R[ée]action|r[ée]action|Anticorps|anticorps)\\b";

        // Création du pattern pour la recherche
       // Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
       Pattern pattern = Pattern.compile(regex);

     // Parcours de chaque note
        for (MedecinNoteDTO note : medecinNotes) {
            String noteContent = note.getNote();
            System.out.println("note :" + noteContent);

            // Recherche des déclencheurs dans le contenu de la note
            Matcher matcher = pattern.matcher(noteContent);
            while (matcher.find()) {
                // Si un déclencheur est trouvé, incrémenter le compteur et imprimer le déclencheur trouvé
                count++;
                System.out.println("Déclencheur trouvé : " + matcher.group());
            }
        }

        System.out.println("count " + count);
        return count;
    }

    private boolean isInDanger(int age, String gender, int triggerCount) {
        if (gender.equals("M")) {
            if (age < 30 && triggerCount == 3) {
                return true;
            } else if (age >= 30 && triggerCount >= 6) {
                return true;
            }
        } else if (gender.equals("F")) {
        	
        	 System.out.println("Age ici :" + age);
             
             System.out.println("Gender ici :" + gender);
             
             System.out.println("triggerCount ici :" + triggerCount);
             
            if (age < 30 && triggerCount == 4) {
                return true;
            } else if (age >= 30 && triggerCount >= 7) {
                return true;
            }
        }
        return false;
    }
}
