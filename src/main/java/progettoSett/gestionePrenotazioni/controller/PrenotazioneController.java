package progettoSett.gestionePrenotazioni.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import progettoSett.gestionePrenotazioni.dto.NumeroPostiDto;
import progettoSett.gestionePrenotazioni.dto.PrenotazioneDto;
import progettoSett.gestionePrenotazioni.exceptions.MyBadRequestException;
import progettoSett.gestionePrenotazioni.exceptions.NotFoundException;
import progettoSett.gestionePrenotazioni.model.Prenotazione;
import progettoSett.gestionePrenotazioni.service.PrenotazioneService;

import java.util.List;
import java.util.Optional;

@RestController
public class PrenotazioneController {

    @Autowired
    private PrenotazioneService prenotazioneService;


    @PostMapping("/utente/prenotazioni")
    public String savePrenotazione(@RequestBody @Validated PrenotazioneDto prenotazioneDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new MyBadRequestException(bindingResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).reduce("", (s1, s2) -> s1 + s2));
        }
        return prenotazioneService.savePrenotazione(prenotazioneDto);
    }

    @PreAuthorize("hasAuthority('ORGANIZZATORE_DI_EVENTI')")
    @GetMapping("/prenotazioni")
    public List<Prenotazione> getAllPrenotazioni() {
        return prenotazioneService.getAllPrenotazioni();
    }

    @PreAuthorize("hasAuthority('ORGANIZZATORE_DI_EVENTI')")
    @GetMapping("prenotazioni/{id}")
    public Prenotazione getPrenotazioneById(@PathVariable int id) {
        Optional<Prenotazione> prenotazioneOptional = prenotazioneService.getPrenotazioneById(id);
        if (prenotazioneOptional.isPresent()) {
            return prenotazioneOptional.get();
        } else {
            throw new NotFoundException("Prenotazione con id " + id + "non trovata");
        }
    }


    @GetMapping("/utente/prenotazioni")
    public List<Prenotazione> getPrenotazioniByUtente() {
        return prenotazioneService.getPrenotazioniByIdUtente();
    }

    //uso l'id della prenotazione

    @PatchMapping("/utente/prenotazioni/{id}")
    public Prenotazione updatePrenotazioneByUtente(@PathVariable int id, @RequestBody NumeroPostiDto numeroPostiDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new MyBadRequestException(bindingResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).reduce("", (s1, s2) -> s1 + s2));
        }
        return prenotazioneService.patchPostiPrenotazione(id, numeroPostiDto);
    }
    //uso l'id della prenotazione
    @DeleteMapping("utente/prenotazioni/{id}")
    public String eliminaPrenotazione(@PathVariable int id) {
        return prenotazioneService.eliminaPrenotazione(id);
    }

}
