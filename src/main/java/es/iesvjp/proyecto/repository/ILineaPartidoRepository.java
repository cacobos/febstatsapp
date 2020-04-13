package es.iesvjp.proyecto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.iesvjp.proyecto.model.Lineapartido;

@Repository("lineaPartidoRepository")
public interface ILineaPartidoRepository extends JpaRepository<Lineapartido, Long> {
	public Lineapartido findById(long id);
	@Query("select l from Lineapartido l")
	public List<Lineapartido> listAllLineapartido();
	@Query("select l from Lineapartido l where l.jugador.id=?1 and l.equipo.id=?2")
	public List<Lineapartido> listLineapartidoJugadorEquipo(long idJugador, long idEquipo);
}
