package sicot.todoList.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sicot.todoList.model.Tache;

@Repository
public interface TacheRepository extends JpaRepository<Tache, Long>{
	List<Tache> findByNom(String nom);
	List<Tache> findByUtilisateurId(Long utilisateurId);
}
