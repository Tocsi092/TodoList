package sicot.todoList.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sicot.todoList.model.Historique;

public interface HistoriqueRepository extends JpaRepository<Historique, Long>{
	List<Historique> findById(long id);
	List<Historique> findByTacheId(long tacheId);
}
