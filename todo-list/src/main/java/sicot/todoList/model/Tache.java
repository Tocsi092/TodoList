package sicot.todoList.model;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Tache")
public class Tache {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "utilisateur_id")
	private Long utilisateurId;
	
	@Column(name = "nom", nullable = false)
	private String nom;
	
	@Column(name = "description", nullable = true)
	private String description;
	
	@Column(name = "etat", nullable = false)
	private Boolean etat;
	
	@Column(name = "dateCreation", nullable = false)
	private Date dateCreation;
	
	@Column(name = "dateCloture", nullable = true)
	private Date dateCloture;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "tache_id", referencedColumnName = "id")
	List<Historique> actions = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUtilisateurId() {
		return utilisateurId;
	}

	public void setUtilisateurId(Long utilisateurId) {
		this.utilisateurId = utilisateurId;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getEtat() {
		return etat;
	}

	public void setEtat(Boolean etat) {
		this.etat = etat;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public Date getDateCloture() {
		return dateCloture;
	}

	public void setDateCloture(Date dateCloture) {
		this.dateCloture = dateCloture;
	}

	public List<Historique> getActions() {
		return actions;
	}

	public void setActions(List<Historique> actions) {
		this.actions = actions;
	}
	
	// Méthode permettant de return une date sans l'heure avec pour rendre l'affichage plus joli
	public String getDateClotureClean() {
		Format formatter = new SimpleDateFormat("dd/MM/yyyy");
		String strDate = formatter.format(dateCloture);  
		return strDate;
	}

}
