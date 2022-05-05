package sicot.todoList.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import sicot.todoList.model.Utilisateur;
import sicot.todoList.repository.UtilisateurRepository;

@Controller
public class UtilisateurController {
	
	@Autowired
	private UtilisateurRepository utilisateurRepo;
	
	// Méthode permettant d'initialiser le formulaire de création
	@GetMapping("/nouveauCompte")
	public String nouveauCompteForm(Model model) {
		model.addAttribute("utilisateur", new Utilisateur());
		return "nouvel_utilisateur";
	}
	
	// Méthode permettant la création d'un utilisateur
	@PostMapping("/creerCompte")
	public String creerCompte(@Validated Utilisateur utilisateur) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(utilisateur.getMdp());
		utilisateur.setMdp(encodedPassword);
		utilisateurRepo.save(utilisateur);
		
		return "register_success";
	}
	
	// Méthode permettant à un utilisateur de se connecter
	@GetMapping("/login")
	public String login(Utilisateur utilisateur, Model model) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if(auth == null || auth instanceof AnonymousAuthenticationToken)
			return "login";
		
		model.addAttribute("utilisateur", utilisateur);
		
		return "redirect:/tache/accueil";
	}

}
