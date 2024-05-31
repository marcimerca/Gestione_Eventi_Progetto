package progettoSett.gestionePrenotazioni.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import progettoSett.gestionePrenotazioni.dto.NumeroPostiDto;
import progettoSett.gestionePrenotazioni.dto.PrenotazioneDto;
import progettoSett.gestionePrenotazioni.exceptions.MyBadRequestException;
import progettoSett.gestionePrenotazioni.exceptions.NotFoundException;
import progettoSett.gestionePrenotazioni.model.Evento;
import progettoSett.gestionePrenotazioni.model.Prenotazione;
import progettoSett.gestionePrenotazioni.model.Utente;
import progettoSett.gestionePrenotazioni.repository.EventoRepository;
import progettoSett.gestionePrenotazioni.repository.PrenotazioneRepository;


import java.util.List;
import java.util.Optional;

@Service
public class PrenotazioneService {

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private EventoService eventoService;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    public String savePrenotazione(PrenotazioneDto prenotazioneDto) {
        Utente utenteLoggato = (Utente) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<Evento> eventoOptional = eventoService.getEventoById(prenotazioneDto.getEventoId());
        if (eventoOptional.isPresent()) {
            Evento evento = eventoOptional.get();


            List<Prenotazione> prenotazioniUtente = prenotazioneRepository.getPrenotazioniByIdUtente(utenteLoggato.getId());
            boolean prenotazioneEsistente = prenotazioniUtente.stream()
                    .anyMatch(prenotazione -> prenotazione.getEvento().getId() == evento.getId());

            if (prenotazioneEsistente) {
                throw new MyBadRequestException("Hai già una prenotazione per questo evento.");
            }

            if (evento.getNumPostiDisponibili() >= prenotazioneDto.getNumeroPosti()) {
                Prenotazione prenotazione = new Prenotazione();
                prenotazione.setUtente(utenteLoggato);
                prenotazione.setEvento(evento);
                prenotazione.setNumeroPosti(prenotazioneDto.getNumeroPosti());
                prenotazioneRepository.save(prenotazione);
                evento.setNumPostiDisponibili(evento.getNumPostiDisponibili() - prenotazioneDto.getNumeroPosti());
                eventoRepository.save(evento);

            } else {
                throw new MyBadRequestException("L'evento ha già raggiunto il numero massimo di prenotazioni consentite");
            }
        } else {
            throw new NotFoundException("Evento non trovato");
        }

        return "Prenotazione per l'evento " + eventoOptional.get().getTitolo() + " avvenuta con successo, prenotati " + prenotazioneDto.getNumeroPosti() + " posti";
    }


    public List<Prenotazione> getAllPrenotazioni() {
        return prenotazioneRepository.findAll();
    }

    public Optional<Prenotazione> getPrenotazioneById(int id) {
        return prenotazioneRepository.findById(id);
    }

    public List<Prenotazione> getPrenotazioniByIdUtente() {
        Utente utenteLoggato = (Utente) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return prenotazioneRepository.getPrenotazioniByIdUtente(utenteLoggato.getId());
    }

    public Prenotazione patchPostiPrenotazione(int id, NumeroPostiDto numeroPostiDto) {
        List<Prenotazione> prenotazioni = getPrenotazioniByIdUtente();
        if (prenotazioni.isEmpty()) {
            throw new NotFoundException("Non sono state trovate prenotazioni");
        }
        Optional<Prenotazione> prenotazioneOptional = prenotazioni.stream().filter(p -> p.getId() == id).findFirst();
        if (prenotazioneOptional.isPresent()) {
            Prenotazione prenotazione = prenotazioneOptional.get();
            Evento evento = prenotazione.getEvento();
            int postiTotaliIniziali = evento.getNumPostiDisponibili() + prenotazione.getNumeroPosti();

            if (numeroPostiDto.getNumPosti() != prenotazione.getNumeroPosti()) {
                int differenzaPosti = numeroPostiDto.getNumPosti() - prenotazione.getNumeroPosti();
                if (numeroPostiDto.getNumPosti() <= postiTotaliIniziali) {
                    prenotazione.setNumeroPosti(numeroPostiDto.getNumPosti());
                    prenotazioneRepository.save(prenotazione);
                    evento.setNumPostiDisponibili(evento.getNumPostiDisponibili() - differenzaPosti);
                    eventoRepository.save(evento);
                } else {
                    throw new MyBadRequestException("Il numero totale massimo di posti prenotabili è " + postiTotaliIniziali);
                }
            }
            return prenotazione;
        } else {
            throw new NotFoundException("Prenotazione con id " + id + " non presente");
        }
    }


    public String eliminaPrenotazione(int id) {
        List<Prenotazione> prenotazioni = getPrenotazioniByIdUtente();
        if (prenotazioni.isEmpty()) {
            throw new NotFoundException("Non sono state trovate prenotazioni");
        }
        Optional<Prenotazione> prenotazioneOptional = prenotazioni.stream().filter(p -> p.getId() == id).findFirst();

        if (prenotazioneOptional.isPresent()) {
            Prenotazione prenotazione = prenotazioneOptional.get();
            Evento evento = prenotazione.getEvento();
            evento.setNumPostiDisponibili(evento.getNumPostiDisponibili() + prenotazione.getNumeroPosti());
            eventoRepository.save(evento);
            prenotazioneRepository.delete(prenotazioneOptional.get());
            return "Prenotazione con id " + id + " annullata con successo";

        } else {
            throw new NotFoundException("Prenotazione con id " + id + " non presente");
        }

    }

}
