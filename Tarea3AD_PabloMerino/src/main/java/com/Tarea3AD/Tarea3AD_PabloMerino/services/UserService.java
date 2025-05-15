package com.Tarea3AD.Tarea3AD_PabloMerino.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Parada;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Peregrino;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Usuario;
import com.Tarea3AD.Tarea3AD_PabloMerino.repository.UsuarioRepository;

@Service
public class UserService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	public Usuario save(Usuario entity) {
		return usuarioRepository.save(entity);
	}

	public Usuario update(Usuario entity) {
		return usuarioRepository.save(entity);
	}

	public void delete(Usuario entity) {
		usuarioRepository.delete(entity);
	}

	public void delete(Long id) {
		usuarioRepository.deleteById(id);
	}

	public Usuario find(Long id) {
		return usuarioRepository.findById(id).get();
	}

	public List<Usuario> findAll() {
		return usuarioRepository.findAll();
	}

	public boolean authenticate(String user, String contra) {
		Usuario usuario = usuarioRepository.findByNombreUsuarioAndContrasena(user, contra);
		return usuario != null;
	}

	public Usuario findByNombreUsuario(String user) {
		return usuarioRepository.findByNombreUsuario(user);
	}
	
	public Usuario findByNombreUsuarioAndContrasena(String user, String contra) {
		return usuarioRepository.findByNombreUsuarioAndContrasena(user, contra);
	}

	public void deleteInBatch(List<Usuario> users) {
		usuarioRepository.deleteAll(users);
	}
	
	

}
