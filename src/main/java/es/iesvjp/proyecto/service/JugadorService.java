package es.iesvjp.proyecto.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import es.iesvjp.proyecto.model.Jugador;
import es.iesvjp.proyecto.repository.IJugadorRepository;

@Service("jugadorService")
public class JugadorService implements IJugadorService {
	@Autowired
	@Qualifier("jugadorRepository")
	private IJugadorRepository jugadorRepository;

	@Override
	public Jugador addJugador(Jugador jugadorModel) {
		Jugador jugador= jugadorRepository.save(jugadorModel);
		return jugador;
	}

	@Override
	public List<Jugador> listAllJugador() {
		List<Jugador> jugadores = jugadorRepository.findAll();
		return jugadores;
	}

	@Override
	public Jugador findJugadorById(int id) {
		return jugadorRepository.findById(id);
	}

	@Override
	public void removeJugador(int id) {
		Jugador jugador= findJugadorById(id);
		if (jugador != null) {
			jugadorRepository.delete(jugador);
		}
	}

	
}
