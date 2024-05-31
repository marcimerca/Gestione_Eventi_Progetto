package progettoSett.gestionePrenotazioni.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import progettoSett.gestionePrenotazioni.model.Prenotazione;
import progettoSett.gestionePrenotazioni.model.Utente;

import java.util.List;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione,Integer> {


    public List<Prenotazione> findByUtente(Utente utente);

    @Query("select p from Prenotazione p where p.utente.id = :id")
    public List<Prenotazione>  getPrenotazioniByIdUtente(int id);
}
