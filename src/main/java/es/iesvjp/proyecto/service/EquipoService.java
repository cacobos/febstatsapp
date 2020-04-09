package es.iesvjp.proyecto.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import es.iesvjp.proyecto.model.Equipo;
import es.iesvjp.proyecto.model.Jugador;
import es.iesvjp.proyecto.repository.IEquipoRepository;

@Service("equipoService")
public class EquipoService implements IEquipoService {
	@Autowired
	@Qualifier("equipoRepository")
	private IEquipoRepository equipoRepository;

	@Override
	public Equipo addEquipo(Equipo equipoModel) {
		Equipo equipo= equipoRepository.save(equipoModel);
		return equipo;
	}

	@Override
	public List<Equipo> listAllEquipo() {
		List<Equipo> equipos = equipoRepository.findAll();
		return equipos;
	}

	@Override
	public Equipo findEquipoById(int id) {
		return equipoRepository.findById(id);
	}

	@Override
	public void removeEquipo(int id) {
		Equipo equipo = findEquipoById(id);
		if (equipo != null) {
			equipoRepository.delete(equipo);
		}
	}

	@Override
	public List<Jugador> getJugadoresEquipo(long id) {
		Equipo equipo=equipoRepository.findById(id);
		List<Jugador> jugadores=new ArrayList<>();
		for (int i = 0; i < equipo.getLineapartidos().size(); i++) {
			jugadores.add(equipo.getLineapartidos().get(i).getJugador());
		}
		return jugadores;
	}

	
}
