package es.iesvjp.proyecto.service;

import java.util.List;

import es.iesvjp.proyecto.model.Equipo;
import es.iesvjp.proyecto.model.Jugador;

public interface IEquipoService {
public Equipo addEquipo(Equipo equipoModel);
public List<Equipo> listAllEquipo();
public Equipo findEquipoById(int id);
public void removeEquipo(int id);
public List<Jugador> getJugadoresEquipo(long id);
}
