package es.iesvjp.proyecto.model;

import java.io.Serializable;
import javax.persistence.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import es.iesvjp.proyecto.service.PartidoService;

import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the equipo database table.
 * 
 */
@Entity
@Table(name = "equipo")
@NamedQuery(name="Equipo.findAll", query="SELECT e FROM Equipo e")
public class Equipo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String categoria;

	private String nombre;

	private String temporada;

	private String url;

	//bi-directional many-to-one association to Lineapartido
	@OneToMany(mappedBy="equipo")
	private List<Lineapartido> lineapartidos;

	//bi-directional many-to-one association to Partido
	@OneToMany(mappedBy="equipo1")
	private List<Partido> partidos1;

	//bi-directional many-to-one association to Partido
	@OneToMany(mappedBy="equipo2")
	private List<Partido> partidos2;

	public Equipo() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCategoria() {
		return this.categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTemporada() {
		return this.temporada;
	}

	public void setTemporada(String temporada) {
		this.temporada = temporada;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Lineapartido> getLineapartidos() {
		return this.lineapartidos;
	}

	public void setLineapartidos(List<Lineapartido> lineapartidos) {
		this.lineapartidos = lineapartidos;
	}

	public Lineapartido addLineapartido(Lineapartido lineapartido) {
		getLineapartidos().add(lineapartido);
		lineapartido.setEquipo(this);

		return lineapartido;
	}

	public Lineapartido removeLineapartido(Lineapartido lineapartido) {
		getLineapartidos().remove(lineapartido);
		lineapartido.setEquipo(null);

		return lineapartido;
	}

	public List<Partido> getPartidos1() {
		return this.partidos1;
	}

	public void setPartidos1(List<Partido> partidos1) {
		this.partidos1 = partidos1;
	}

	public Partido addPartidos1(Partido partidos1) {
		getPartidos1().add(partidos1);
		partidos1.setEquipo1(this);

		return partidos1;
	}

	public Partido removePartidos1(Partido partidos1) {
		getPartidos1().remove(partidos1);
		partidos1.setEquipo1(null);

		return partidos1;
	}

	public List<Partido> getPartidos2() {
		return this.partidos2;
	}

	public void setPartidos2(List<Partido> partidos2) {
		this.partidos2 = partidos2;
	}

	public Partido addPartidos2(Partido partidos2) {
		getPartidos2().add(partidos2);
		partidos2.setEquipo2(this);

		return partidos2;
	}

	public Partido removePartidos2(Partido partidos2) {
		getPartidos2().remove(partidos2);
		partidos2.setEquipo2(null);

		return partidos2;
	}

	public List<Partido> getPartidos(){
		List<Partido> partidos=new ArrayList<Partido>();
		partidos.addAll(partidos1);
		partidos.addAll(partidos2);
		return partidos;
	}
	
	public int getPosesiones() {
		int posesiones=0;
		for (int i = 0; i < partidos1.size(); i++) {
			posesiones+=partidos1.get(i).getPosesionesLocal();
		}
		for (int i = 0; i < partidos2.size(); i++) {
			posesiones+=partidos2.get(i).getPosesionesVisit();
		}
		return posesiones;
	}
	
	public int getMinutos() {
		int minutos=0;
		for (int i = 0; i < partidos1.size(); i++) {
			minutos+=partidos1.get(i).getMinutos();
		}
		for (int i = 0; i < partidos2.size(); i++) {
			minutos+=partidos2.get(i).getMinutos();
		}
		return minutos;
	}
	
	public double getRitmo() {
		return (double)getPosesiones()/(double)getMinutos();
	}
	
	public List<Jugador> getJugadores(){
		List<Jugador> jugadores=new ArrayList<>();
		for (int i = 0; i < lineapartidos.size(); i++) {
			if(!jugadores.contains(lineapartidos.get(i).getJugador())){
				jugadores.add(lineapartidos.get(i).getJugador());
			}
		}
		return jugadores;
	}
}