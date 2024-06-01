package progettoSett.gestionePrenotazioni.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


import java.time.LocalDate;


@Data
public class EventoDto {

    @NotBlank(message = "Il titolo non può essere nullo, vuoto, o composto da soli spazi")
    private String titolo;
    @NotBlank(message = "la descrizione non può essere nullo, vuoto, o composto da soli spazi")
    private String descrizione;
    @NotNull(message = "la data non può essere null")
    private LocalDate data;
    @NotBlank(message = "Il luogo non può essere nullo, vuoto, o composto da soli spazi")
    private String luogo;
    @NotNull(message = "Il numPostiDisponibili non può essere null")
    private int numPostiDisponibili;


}
