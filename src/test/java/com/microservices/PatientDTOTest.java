package com.microservices;

import org.junit.jupiter.api.Test;

import com.microservices.dto.PatientDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.sql.Date;

public class PatientDTOTest {

    @Test
    void testPatientDTOConstructor() {
        Long id = 1L;
        String prenom = "John";
        String nom = "Doe";
        Date dateNaissance = Date.valueOf("1990-01-01");
        String genre = "Male";
        String adressePostale = "123 Main Street";
        String numeroTelephone = "123-456-7890";

        PatientDTO patientDTO = new PatientDTO(id, prenom, nom, dateNaissance, genre, adressePostale, numeroTelephone);

        assertEquals(id, patientDTO.getId());
        assertEquals(prenom, patientDTO.getPrenom());
        assertEquals(nom, patientDTO.getNom());
        assertEquals(dateNaissance, patientDTO.getDateNaissance());
        assertEquals(genre, patientDTO.getGenre());
        assertEquals(adressePostale, patientDTO.getAdressePostale());
        assertEquals(numeroTelephone, patientDTO.getNumeroTelephone());
    }

    @Test
    void testPatientDTOSettersAndGetters() {
        PatientDTO patientDTO = new PatientDTO();

        Long id = 1L;
        String prenom = "John";
        String nom = "Doe";
        Date dateNaissance = Date.valueOf("1990-01-01");
        String genre = "Male";
        String adressePostale = "123 Main Street";
        String numeroTelephone = "123-456-7890";

        patientDTO.setId(id);
        patientDTO.setPrenom(prenom);
        patientDTO.setNom(nom);
        patientDTO.setDateNaissance(dateNaissance);
        patientDTO.setGenre(genre);
        patientDTO.setAdressePostale(adressePostale);
        patientDTO.setNumeroTelephone(numeroTelephone);

        assertEquals(id, patientDTO.getId());
        assertEquals(prenom, patientDTO.getPrenom());
        assertEquals(nom, patientDTO.getNom());
        assertEquals(dateNaissance, patientDTO.getDateNaissance());
        assertEquals(genre, patientDTO.getGenre());
        assertEquals(adressePostale, patientDTO.getAdressePostale());
        assertEquals(numeroTelephone, patientDTO.getNumeroTelephone());
    }
}
