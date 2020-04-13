package es.iesvjp.proyecto.service;

import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.data.jpa.repository.Query;

import es.iesvjp.proyecto.model.Equipo;
import es.iesvjp.proyecto.model.Jugador;

public interface IEquipoService {
public Equipo addEquipo(Equipo equipoModel);
public List<Equipo> listAllEquipo();
public Equipo findEquipoById(int id);
public void removeEquipo(int id);
public List<Jugador> getJugadoresEquipo(long id);
public List<String> getCompeticiones();
public List<Equipo> getEquiposCompeticion(String competicion);
}
