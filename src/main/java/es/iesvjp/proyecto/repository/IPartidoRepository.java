package es.iesvjp.proyecto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.iesvjp.proyecto.model.Partido;

@Repository("partidoRepository")
public interface IPartidoRepository extends JpaRepository<Partido, Long> {
	public Partido findById(long id);
	@Query("select p from Partido p")
	public List<Partido> listAllPartido();
	@Query("select p from Partido p where p.equipo1.id=?1 or p.equipo2.id=?1")
	public List<Partido> listPartidosEquipo(long idEquipo);
	@Query("select p from Partido p where p.competicion = ?1")
	public List<Partido> listPartidosCompeticion(String competicion);
}
