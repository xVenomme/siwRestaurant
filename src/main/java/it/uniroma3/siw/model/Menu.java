package it.uniroma3.siw.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @Enumerated(EnumType.STRING)
    private MenuTipo tipo;

    @ManyToMany
    @JoinTable(
        name = "menu_piatto",
        joinColumns = @JoinColumn(name = "menu_id"),
        inverseJoinColumns = @JoinColumn(name = "piatto_id")
    )
    private List<Piatto> piatti = new ArrayList<>();

    private BigDecimal prezzoTotale;

    /* --- costruttori --- */
    public Menu() { }

    public Menu(String nome, MenuTipo tipo) {
        this.nome = nome;
        this.tipo = tipo;
    }

    /* --- business helper --- */
    public void addPiatto(Piatto p) {
        piatti.add(p);
        p.getMenu().add(this);
        // Se vuoi, aggiorna qui prezzoTotale
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public MenuTipo getTipo() {
		return tipo;
	}

	public void setTipo(MenuTipo tipo) {
		this.tipo = tipo;
	}

	public List<Piatto> getPiatti() {
		return piatti;
	}

	public void setPiatti(List<Piatto> piatti) {
		this.piatti = piatti;
	}

	public BigDecimal getPrezzoTotale() {
		return prezzoTotale;
	}

	public void setPrezzoTotale(BigDecimal prezzoTotale) {
		this.prezzoTotale = prezzoTotale;
	}

    
}

