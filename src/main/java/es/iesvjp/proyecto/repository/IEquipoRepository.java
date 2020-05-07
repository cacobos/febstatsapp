package es.iesvjp.proyecto.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.iesvjp.proyecto.model.Equipo;

/**
 * Clase repositorio de objetos de la clase Equipo
 * @author Carlos Cobos
 *
 */
@Repository("equipoRepository")
public interface IEquipoRepository extends JpaRepository<Equipo, Long> {
	public Equipo findById(long id);
	@Query("select e from Equipo e")
	public List<Equipo> listAllEquipo();
	@Query("select distinct(e.categoria) from Equipo e")
	public List<String> getCompeticiones();
	@Query("select e from Equipo e where categoria = ?1")
	public List<Equipo> getEquiposCompeticion(String competicion);
}
