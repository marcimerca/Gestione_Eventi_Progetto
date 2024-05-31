package progettoSett.gestionePrenotazioni.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import progettoSett.gestionePrenotazioni.dto.UtenteDto;
import progettoSett.gestionePrenotazioni.dto.UtenteLoginDto;
import progettoSett.gestionePrenotazioni.exceptions.MyBadRequestException;
import progettoSett.gestionePrenotazioni.service.AuthService;
import progettoSett.gestionePrenotazioni.service.UtenteService;

@RestController
public class AuthController {
    @Autowired
    private UtenteService utenteService;

    @Autowired
    private AuthService authService;

    @PostMapping("/auth/register")
    public String register(@RequestBody @Validated UtenteDto utenteDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new MyBadRequestException(bindingResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).reduce("",(s1, s2)->s1+s2));
        }
        return utenteService.saveUtente(utenteDto);
    }

    @PostMapping("/auth/login")
    public String login(@RequestBody @Validated UtenteLoginDto utenteLoginDto,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new MyBadRequestException(bindingResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).reduce("",(s1, s2)->s1+s2));
        }
        return authService.authenticateUtenteAndCreateToken(utenteLoginDto);
    }
}
