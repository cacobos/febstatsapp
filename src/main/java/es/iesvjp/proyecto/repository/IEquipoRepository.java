package es.iesvjp.proyecto.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.iesvjp.proyecto.model.Equipo;

@Repository("equipoRepository")
public interface IEquipoRepository extends JpaRepository<Equipo, Long> {
	public Equipo findById(long id);
	@Query("select e from Equipo e")
	public List<Equipo> listAllEquipo();
}