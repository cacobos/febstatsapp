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
}
