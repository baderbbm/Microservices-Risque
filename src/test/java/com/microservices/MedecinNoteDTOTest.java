package com.microservices;

import org.junit.jupiter.api.Test;

import com.microservices.dto.MedecinNoteDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MedecinNoteDTOTest {

    @Test
    void testMedecinNoteDTOConstructor() {
        String id = "1";
        Long patId = 1L;
        String patient = "John";
        String note = "Note test";

        MedecinNoteDTO medecinNoteDTO = new MedecinNoteDTO(id, patId, patient, note);

        assertEquals(id, medecinNoteDTO.getId());
        assertEquals(patId, medecinNoteDTO.getPatId());
        assertEquals(patient, medecinNoteDTO.getPatient());
        assertEquals(note, medecinNoteDTO.getNote());
    }

    @Test
    void testMedecinNoteDTOSettersAndGetters() {
        MedecinNoteDTO medecinNoteDTO = new MedecinNoteDTO();

        String id = "1";
        Long patId = 1L;
        String patient = "John";
        String note = "Note test";

        medecinNoteDTO.setId(id);
        medecinNoteDTO.setPatId(patId);
        medecinNoteDTO.setPatient(patient);
        medecinNoteDTO.setNote(note);

        assertEquals(id, medecinNoteDTO.getId());
        assertEquals(patId, medecinNoteDTO.getPatId());
        assertEquals(patient, medecinNoteDTO.getPatient());
        assertEquals(note, medecinNoteDTO.getNote());
    }
}
