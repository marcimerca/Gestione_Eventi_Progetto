package progettoSett.gestionePrenotazioni.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import progettoSett.gestionePrenotazioni.model.Utente;

import java.util.Optional;

public interface UtenteRepository extends JpaRepository<Utente, Integer> {
    public Optional<Utente> findByEmail(String email);
}
