package progettoSett.gestionePrenotazioni.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NumeroPostiDto {
    @NotNull
    int numPosti;
}
