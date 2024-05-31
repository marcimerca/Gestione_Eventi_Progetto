package progettoSett.gestionePrenotazioni.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import progettoSett.gestionePrenotazioni.model.Evento;

public interface EventoRepository extends JpaRepository<Evento,Integer> {
}
