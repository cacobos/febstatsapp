package es.iesvjp.proyecto.service;

import java.util.List;

import es.iesvjp.proyecto.model.Jugador;

public interface IJugadorService {
public Jugador addJugador(Jugador equipoModel);
public List<Jugador> listAllJugador();
public Jugador findJugadorById(int id);
public void removeJugador(int id);
}
