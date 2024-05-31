package progettoSett.gestionePrenotazioni.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import progettoSett.gestionePrenotazioni.dto.EventoDto;
import progettoSett.gestionePrenotazioni.exceptions.MyBadRequestException;
import progettoSett.gestionePrenotazioni.exceptions.NotFoundException;
import progettoSett.gestionePrenotazioni.model.Evento;
import progettoSett.gestionePrenotazioni.service.EventoService;

import java.util.List;
import java.util.Optional;

@RestController
public class EventoController {
    @Autowired
    private EventoService eventoService;

    @PreAuthorize("hasAuthority('ORGANIZZATORE_DI_EVENTI')")
    @PostMapping("/eventi")
    public String saveEvento(@RequestBody @Validated EventoDto eventoDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new MyBadRequestException(bindingResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).reduce("",(s1, s2)->s1+s2));
        }
        return eventoService.saveEvento(eventoDto);
    }

    @PreAuthorize("hasAnyAuthority('ORGANIZZATORE_DI_EVENTI','UTENTE_NORMALE')")
    @GetMapping("/eventi")
    public List<Evento> getAllEventi(){
        return eventoService.getAllEventi();
    }

    @PreAuthorize("hasAnyAuthority('ORGANIZZATORE_DI_EVENTI','UTENTE_NORMALE')")
    @GetMapping("/eventi/{id}")
    public Evento getEventoById( @PathVariable int id){
        Optional<Evento> eventoOptional = eventoService.getEventoById(id);
        if(eventoOptional.isPresent()){
            return eventoOptional.get();
        } else {
            throw new NotFoundException("Evento con id " + id+ " non è trovato");
        }
    }

    @PreAuthorize("hasAuthority('ORGANIZZATORE_DI_EVENTI')")
    @PutMapping("/eventi/{id}")
    public Evento updateEvento(@PathVariable int id, @RequestBody @Validated EventoDto eventoDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new MyBadRequestException(bindingResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).reduce("",(s1, s2)->s1+s2));
        }
        return eventoService.updateEvento(id,eventoDto);
    }

    @PreAuthorize("hasAuthority('ORGANIZZATORE_DI_EVENTI')")
    @DeleteMapping("/eventi/{id}")
    public String deleteEvento(@PathVariable int id){
        return eventoService.deleteEvento(id);
    }


}
