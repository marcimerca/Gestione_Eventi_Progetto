package progettoSett.gestionePrenotazioni.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UtenteLoginDto {

    @Email
    @NotBlank(message = "L'email non può essere nullo, vuoto, o composto da soli spazi")
    private String email;
    @NotBlank(message = "La password non può essere nullo, vuoto, o composto da soli spazi")
    private String password;
}
