package progettoSett.gestionePrenotazioni.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import progettoSett.gestionePrenotazioni.enums.Ruolo;
import progettoSett.gestionePrenotazioni.model.Prenotazione;

import java.util.ArrayList;
import java.util.List;

@Data
public class UtenteDto {
    @NotBlank(message = "Il nome non può essere nullo, vuoto, o composto da soli spazi")
    private String nome;
    @NotBlank(message = "Il cognome non può essere nullo, vuoto, o composto da soli spazi")
    private String cognome;
    @Email
    @NotBlank(message = "L'email non può essere nullo, vuoto, o composto da soli spazi")
    private String email;
    @NotBlank(message = "La password non può essere nullo, vuoto, o composto da soli spazi")
    private String password;

}
