package progettoSett.gestionePrenotazioni.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "eventi")
@Data
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String titolo;
    private String descrizione;
    private LocalDate data;
    private String luogo;
    private int numPostiDisponibili;

    @OneToMany(mappedBy = "evento")
    @JsonIgnore
    private List<Prenotazione> prenotazioni;


}
