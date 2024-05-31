package progettoSett.gestionePrenotazioni.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class MyBadRequestException extends RuntimeException{
    public MyBadRequestException(String message) {
        super(message);
    }
}
