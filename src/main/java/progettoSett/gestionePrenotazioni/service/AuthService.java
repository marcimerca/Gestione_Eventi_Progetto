package progettoSett.gestionePrenotazioni.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import progettoSett.gestionePrenotazioni.dto.UtenteLoginDto;
import progettoSett.gestionePrenotazioni.exceptions.NotFoundException;
import progettoSett.gestionePrenotazioni.exceptions.UnauthorizedException;
import progettoSett.gestionePrenotazioni.model.Utente;
import progettoSett.gestionePrenotazioni.security.JwtTool;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private JwtTool jwtTool;
    @Autowired
    private UtenteService utenteService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String authenticateUtenteAndCreateToken(UtenteLoginDto utenteLoginDto) {
        Optional<Utente> utenteOptional = utenteService.getUtenteByEmail(utenteLoginDto.getEmail());

        if (utenteOptional.isPresent()) {
            Utente utente = utenteOptional.get();
            if (passwordEncoder.matches(utenteLoginDto.getPassword(), utente.getPassword())) {
                return jwtTool.createToken(utente);
            } else {
                throw new UnauthorizedException("Errore nel login, riloggarsi");
            }

        } else {
            throw new NotFoundException("Utente con email " + utenteLoginDto.getEmail() + "non trovato ");
        }
    }
}
