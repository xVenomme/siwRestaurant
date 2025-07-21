package it.uniroma3.siw.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Prenotazione {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /* ----------- Dati anagrafici ----------- */
    @NotBlank
    private String nome;

    @NotBlank
    private String cognome;

    /* ----------- Dettagli prenotazione ----- */
    @NotNull
    @FutureOrPresent(message = "La data deve essere odierna o futura")
    private LocalDate data;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)   // oppure pattern = \"HH:mm\"
    @NotNull
    private LocalTime ora;


    @Min(1) @Max(20)
    private int coperti;

    /* Note opzionali (max 250 caratteri) */
    @Size(max = 250)
    private String note;

    /* ----------- Relazioni opzionali ------- */
    // Se vuoi legare la prenotazione all'utente loggato:
     @ManyToOne
     private User user;

    /* ---------- Costruttori ----------------- */
    public Prenotazione() {}

    public Prenotazione(Long id) { this.id = id; }

    /* ---------- Getter & Setter ------------- */
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public LocalTime getOra() { return ora; }
    public void setOra(LocalTime ora) { this.ora = ora; }

    public int getCoperti() { return coperti; }
    public void setCoperti(int coperti) { this.coperti = coperti; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}

