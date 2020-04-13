package es.iesvjp.proyecto.service;

import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.data.jpa.repository.Query;

import es.iesvjp.proyecto.model.Equipo;
import es.iesvjp.proyecto.model.Jugador;
import es.iesvjp.proyecto.model.Partido;

public interface IPartidoService {
public List<Partido> listAllPartido();
public Partido findPartidoById(long id);
public List<Partido> getPartidosEquipo(long idEquipo);
public List<Partido> getPartidosCompeticion(String competicion);
public List<Partido> listPartidosEquipo(long idEquipo);
}
