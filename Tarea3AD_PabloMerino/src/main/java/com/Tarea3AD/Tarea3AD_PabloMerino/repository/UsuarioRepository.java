package com.Tarea3AD.Tarea3AD_PabloMerino.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Parada;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Peregrino;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	Usuario findByNombreUsuarioAndContrasena(String nombreUsuario, String contrasena);

	Usuario findByNombreUsuario(String nombreUsuario);

	Optional<Usuario> findById(Long id);

}
