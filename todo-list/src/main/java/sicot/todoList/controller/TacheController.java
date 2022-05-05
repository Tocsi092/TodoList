package sicot.todoList.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import sicot.todoList.model.Historique;
import sicot.todoList.model.Tache;
import sicot.todoList.repository.HistoriqueRepository;
import sicot.todoList.repository.TacheRepository;
import sicot.todoList.service.UtilisateurDetails;


@Controller
public class TacheController {
	
	@Autowired
	private TacheRepository tacheRepo;
	
	@Autowired
	private HistoriqueRepository historiqueRepo;
	
	// Méthode renvoyant la liste de toutes les tâches de l'utilisateur
	@GetMapping("/tache/acceuil")
	public String viewAcceuil(@AuthenticationPrincipal UtilisateurDetails utilisateur, Model model) {
		long idUser = utilisateur.getId();
		
		String username = utilisateur.getUsername();
		
		model.addAttribute("listeTaches", tacheRepo.findByUtilisateurId(idUser));
		model.addAttribute("username", username);
		
		return "acceuil";
	}
	
	// Méthode permettant d'initaliser le formulaire de création
	@GetMapping("/tache/nouvelleTache")
	public String nouvelleTacheForm(Model model) {
		model.addAttribute("tache", new Tache());
		
		return "nouvelle_tache";
	}
	
	// Méthode permettant la création d'une tâche
	@PostMapping("/tache/creerTache")
	public String creerTache(@AuthenticationPrincipal UtilisateurDetails utilisateur, @Validated Tache tache, Model model) {
		long idUser = utilisateur.getId();
		
		String username = utilisateur.getUsername();
		
		Date date = new Date();
		tache.setDateCreation(date);
		tache.setEtat(false);
		tache.setUtilisateurId(idUser);
		tacheRepo.save(tache);
		
		//historisation
		String typeAction = "Création";
		Historique historique = new Historique(tache.getId(), date, typeAction);
		historiqueRepo.save(historique);
		
		model.addAttribute("listeTaches", tacheRepo.findByUtilisateurId(idUser));
		model.addAttribute("username", username);
		
		return "redirect:/tache/acceuil";
	}
	
	// Méthode permettant d'initaliser le formulaire de modification
	@GetMapping("/tache/modifier/{id}")
	public String modifierForm(@AuthenticationPrincipal UtilisateurDetails utilisateur, @PathVariable("id") long id, Model model) throws NotFoundException {
		long idUser = utilisateur.getId();
		
		Tache tache = tacheRepo.findById(id).get();
		
		if(tache == null)
			throw new NotFoundException();
		
		long idUserTache = tache.getUtilisateurId();
		
		if (idUser != idUserTache) {
			throw new AccessDeniedException("Vous n'avez aucun droit sur cette tâche petit malin");
		}
		
		model.addAttribute("tache", tache);
		
		return "modifier_tache";
	}
	
	// Méthode permettant la modification d'une tâche
	@PostMapping("/tache/modifierTache/{id}")
	public String modifierTache(@AuthenticationPrincipal UtilisateurDetails utilisateur, @PathVariable("id") long id, @Validated Tache tache, Model model) throws NotFoundException {
		long idUser = utilisateur.getId();
		
		String username = utilisateur.getUsername();
		
		Tache tacheOld = tacheRepo.findById(id).get();
		
		if(tache == null)
			throw new NotFoundException();
		
		long idUserTache = tacheOld.getUtilisateurId();
		
		if (idUser != idUserTache) {
			throw new AccessDeniedException("Vous n'avez aucun droit sur cette tâche petit malin");
		}
		
		tache.setDateCreation(tacheOld.getDateCreation());
		tache.setDateCloture(tacheOld.getDateCloture());
		tache.setUtilisateurId(tacheOld.getUtilisateurId());
		tache.setEtat(tacheOld.getEtat());
		tache.setActions(tacheOld.getActions());
		
		tacheRepo.saveAndFlush(tache);
		
		//historisation
		Date date = new Date();
		String typeAction = "Modification";
		Historique historique = new Historique(id, date, typeAction);
		historiqueRepo.save(historique);
		
		
		
		model.addAttribute("listeTaches", tacheRepo.findByUtilisateurId(idUser));
		model.addAttribute("username", username);
		
		return "redirect:/tache/acceuil";
	}
	
	// Méthode permettant la suppression d'une tâche
	@GetMapping("/tache/supprimer/{id}")
	public String supprimerTache(@AuthenticationPrincipal UtilisateurDetails utilisateur, @PathVariable("id") long id, Model model) throws NotFoundException{
		long idUser = utilisateur.getId();
		
		String username = utilisateur.getUsername();
		
		Tache tache = tacheRepo.findById(id).get();
		
		if(tache == null)
			throw new NotFoundException();
		
		long idUserTache = tache.getUtilisateurId();
		
		if (idUser != idUserTache) {
			throw new AccessDeniedException("Vous n'avez aucun droit sur cette tâche petit malin");
		}
		
		List<Historique> historique = historiqueRepo.findByTacheId(tache.getId());
		for(Historique action : historique)
			historiqueRepo.delete(action);
		
		tacheRepo.delete(tache);
		
		model.addAttribute("listeTaches", tacheRepo.findByUtilisateurId(idUser));
		model.addAttribute("username", username);
		
		return "redirect:/tache/acceuil";
	}
	
	// Méthode permettant la changement d'état d'une tâche
	@GetMapping("/tache/modifierEtat/{id}")
	public String changerEtat(@AuthenticationPrincipal UtilisateurDetails utilisateur, @PathVariable("id") long id, Model model) throws NotFoundException{
		long idUser = utilisateur.getId();
		
		String username = utilisateur.getUsername();
		
		Tache tache = tacheRepo.findById(id).get();
		
		if(tache == null)
			throw new NotFoundException();
		
		long idUserTache = tache.getUtilisateurId();
		
		if (idUser != idUserTache) {
			throw new AccessDeniedException("Vous n'avez aucun droit sur cette tâche petit malin");
		}
		
		Date date = new Date();
		
		if(tache.getEtat() == false) {
			tache.setEtat(true);
			tache.setDateCloture(date);
		} else if (tache.getEtat() == true){
			tache.setEtat(false);
			tache.setDateCloture(null);
		}
		tacheRepo.save(tache);
		
		// historisation
		String typeAction = "changementEtat";
		Historique historique = new Historique(id, date, typeAction);
		historiqueRepo.save(historique);
		
		model.addAttribute("listeTaches", tacheRepo.findByUtilisateurId(idUser));
		model.addAttribute("username", username);
		
		return "redirect:/tache/acceuil";
	}
	
	// Méthode permettant d'accéder à l'historique complet d'une tâche
	@GetMapping("/tache/visualiserHistorique/{id}")
	public String visualiserHistorique(@AuthenticationPrincipal UtilisateurDetails utilisateur, @PathVariable("id") long id, Model model) throws NotFoundException{
		long idUser = utilisateur.getId();
		
		Tache tache = tacheRepo.findById(id).get();
		
		if(tache == null)
			throw new NotFoundException();
		
		List<Historique> historique = tache.getActions();
				
		historique.sort((o1,o2) -> o1.getDateAction().compareTo(o2.getDateAction()));
		
		long idUserTache = tache.getUtilisateurId();
		
		if (idUser != idUserTache) {
			throw new AccessDeniedException("Vous n'avez aucun droit sur cette tâche petit malin");
		}
		
		model.addAttribute("tache", tache);
		model.addAttribute("historique", historique);
		
		return "historique_tache";
	}
	
}
