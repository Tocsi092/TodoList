package sicot.todoList.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import sicot.todoList.model.Utilisateur;

@SuppressWarnings("serial")
public class UtilisateurDetails implements UserDetails {

	private Utilisateur utilisateur;
	
	public UtilisateurDetails(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return utilisateur.getMdp();
	}

	@Override
	public String getUsername() {
		return utilisateur.getUsername();
	}
	
	public long getId() {
		return utilisateur.getId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
