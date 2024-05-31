package progettoSett.gestionePrenotazioni.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "prenotazioni")
public class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int numeroPosti;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    @JsonIgnore
    private Utente utente;



    @ManyToOne
    @JoinColumn(name = "evento_id")

    private Evento evento;

}
