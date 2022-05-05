package sicot.todoList.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import sicot.todoList.model.Utilisateur;
import sicot.todoList.repository.UtilisateurRepository;

public class UtilisateurDetailsService implements UserDetailsService{

	@Autowired
	private UtilisateurRepository utilisateurRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Utilisateur utilisateur = utilisateurRepo.findByUsername(username);
		if(utilisateur == null)
			throw new UsernameNotFoundException("Ce nom d\'utilisateur n\'a pas été trouvé");
		
		return new UtilisateurDetails(utilisateur);
	}

}
