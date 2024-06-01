package progettoSett.gestionePrenotazioni.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PrenotazioneDto {

    @NotNull(message = "Il numeroPosti non può essere null")
    @Min(value = 1, message = "numeroPosti deve essere almeno 1")
    private int numeroPosti;
    @NotNull(message = "eventoId non può essere null")
    @Min(value = 1, message = "eventoId deve essere almeno 1")
    private int eventoId;
}
