package sicot.todoList.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import sicot.todoList.model.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long>{
	Utilisateur findByUsername(String username);
}
