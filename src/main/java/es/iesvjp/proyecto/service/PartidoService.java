package es.iesvjp.proyecto.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import es.iesvjp.proyecto.model.Equipo;
import es.iesvjp.proyecto.model.Jugador;
import es.iesvjp.proyecto.model.Partido;
import es.iesvjp.proyecto.repository.IEquipoRepository;
import es.iesvjp.proyecto.repository.IPartidoRepository;

@Service("partidoService")
public class PartidoService implements IPartidoService {
	@Autowired
	@Qualifier("partidoRepository")
	private IPartidoRepository partidoRepository;

	@Override
	public List<Partido> listAllPartido() {
		return partidoRepository.listAllPartido();
	}

	@Override
	public Partido findPartidoById(long id) {
		return partidoRepository.findById(id);
	}

	@Override
	public List<Partido> getPartidosEquipo(long idEquipo) {
		return partidoRepository.listPartidosEquipo(idEquipo);
	}

	@Override
	public List<Partido> getPartidosCompeticion(String competicion) {
		return partidoRepository.listPartidosCompeticion(competicion);
	}

	@Override
	public List<Partido> listPartidosEquipo(long idEquipo) {
		return partidoRepository.listPartidosEquipo(idEquipo);
	}

	
	

}
