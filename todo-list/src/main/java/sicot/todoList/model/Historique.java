package sicot.todoList.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Historique")
public class Historique {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "tache_id", nullable = false)
	private Long tacheId;
	
	@Column(name = "dateAction", nullable = false)
	private Date dateAction;
	
	@Column(name = "typeAction", nullable = false)
	private String typeAction;

	public Historique(Long tacheId, Date dateAction, String typeAction) {
		this.tacheId = tacheId;
		this.dateAction = dateAction;
		this.typeAction = typeAction;
	}

	public Historique() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTacheId() {
		return tacheId;
	}

	public void setTache_id(Long tacheId) {
		this.tacheId = tacheId;
	}

	public Date getDateAction() {
		return dateAction;
	}

	public void setDateAction(Date dateAction) {
		this.dateAction = dateAction;
	}

	public String getTypeAction() {
		return typeAction;
	}

	public void setTypeAction(String typeAction) {
		this.typeAction = typeAction;
	}	
	
}
