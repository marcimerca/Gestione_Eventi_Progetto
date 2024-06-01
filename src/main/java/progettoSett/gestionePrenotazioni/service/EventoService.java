package progettoSett.gestionePrenotazioni.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import progettoSett.gestionePrenotazioni.dto.EventoDto;
import progettoSett.gestionePrenotazioni.exceptions.NotFoundException;
import progettoSett.gestionePrenotazioni.model.Evento;
import progettoSett.gestionePrenotazioni.repository.EventoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EventoService {
    @Autowired
    private EventoRepository eventoRepository;

    public String saveEvento(EventoDto eventoDto) {
        Evento evento = new Evento();
        evento.setTitolo(eventoDto.getTitolo());
        evento.setDescrizione(eventoDto.getDescrizione());
        evento.setData(eventoDto.getData());
        evento.setLuogo(eventoDto.getLuogo());
        evento.setNumPostiDisponibili(eventoDto.getNumPostiDisponibili());
        eventoRepository.save(evento);
        return "Evento con titolo " + eventoDto.getTitolo() + " inserito correttamente";
    }

    public List<Evento> getAllEventi() {
        return eventoRepository.findAll();
    }

    public Optional<Evento> getEventoById(int id) {
        return eventoRepository.findById(id);
    }

    public Evento updateEvento(int id, EventoDto eventoDto) {
        Optional<Evento> eventoOptional = getEventoById(id);
        if (eventoOptional.isPresent()) {
            Evento evento = eventoOptional.get();
            evento.setTitolo(eventoDto.getTitolo());
            evento.setDescrizione(eventoDto.getDescrizione());
            evento.setData(eventoDto.getData());
            evento.setNumPostiDisponibili(eventoDto.getNumPostiDisponibili());
            eventoRepository.save(evento);
            return evento;
        } else {
            throw new NotFoundException("Evento con id " + id + " non trovato.");
        }
    }

    public String deleteEvento(int id) {
        Optional<Evento> eventoOptional = getEventoById(id);
        if (eventoOptional.isPresent()) {
            eventoRepository.delete(eventoOptional.get());
            return "Evento con id " + id + " eliminato correttamente.";
        } else {
            throw new NotFoundException("Evento con id " + id + " non trovato.");
        }
    }
}
