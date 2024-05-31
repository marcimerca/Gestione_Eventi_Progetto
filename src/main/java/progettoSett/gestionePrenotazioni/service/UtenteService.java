package progettoSett.gestionePrenotazioni.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import progettoSett.gestionePrenotazioni.dto.UtenteDto;
import progettoSett.gestionePrenotazioni.enums.Ruolo;
import progettoSett.gestionePrenotazioni.model.Utente;
import progettoSett.gestionePrenotazioni.repository.UtenteRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UtenteService {
    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String saveUtente(UtenteDto utenteDto){
        Utente utente = new Utente();
        utente.setNome(utenteDto.getNome());
        utente.setCognome(utenteDto.getCognome());
        utente.setEmail(utenteDto.getEmail());
        utente.setRuolo(Ruolo.UTENTE_NORMALE);
        utente.setPassword(passwordEncoder.encode(utenteDto.getPassword()));
        utenteRepository.save(utente);
        return "Utente con email "+ utente.getEmail()+ " salvato con successo.";
    }

    public List<Utente> getAllUtenti(){
        return utenteRepository.findAll();
    }

    public Optional<Utente> getUtenteById(int id){
        return utenteRepository.findById(id);
    }

    public Optional<Utente> getUtenteByEmail(String email){
        return utenteRepository.findByEmail(email);
    }

}
