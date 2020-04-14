package es.iesvjp.proyecto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.iesvjp.proyecto.model.Jugador;

@Repository("jugadorRepository")
public interface IJugadorRepository extends JpaRepository<Jugador, Long> {
	public Jugador findById(long id);
	@Query("select j from Jugador j")
	public List<Jugador> listAllJugador();
	@Query("select j from Jugador j where j.nombre like %"+"?1"+"%")
	public List<Jugador> buscarJugador(String like);
}
