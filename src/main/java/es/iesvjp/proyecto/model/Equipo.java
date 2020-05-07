package es.iesvjp.proyecto.model;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import es.iesvjp.proyecto.controller.Utilidades;
import es.iesvjp.proyecto.service.PartidoService;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que gestiona la persistencia de la tabla equipo
 * 
 * @author Carlos Cobos
 *
 */
@Entity
@Table(name = "equipo")
@NamedQuery(name = "Equipo.findAll", query = "SELECT e FROM Equipo e")
public class Equipo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String categoria;

	private String nombre;

	private String temporada;

	private String url;

	// bi-directional many-to-one association to Lineapartido
	@OneToMany(mappedBy = "equipo")
	private List<Lineapartido> lineapartidos;

	// bi-directional many-to-one association to Partido
	@OneToMany(mappedBy = "equipo1")
	private List<Partido> partidos1;

	// bi-directional many-to-one association to Partido
	@OneToMany(mappedBy = "equipo2")
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
		List<Partido> devolver = new ArrayList<>();
		for (int i = 0; i < partidos1.size(); i++) {
			if (partidos1.get(i).getLineapartidos().size() > 0
					&& (partidos1.get(i).ganaLocal() || partidos1.get(i).ganaVisit())) {
				devolver.add(partidos1.get(i));
			}
		}
		return devolver;
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
		List<Partido> devolver = new ArrayList<>();
		for (int i = 0; i < partidos2.size(); i++) {
			if (partidos2.get(i).getLineapartidos().size() > 0
					&& (partidos2.get(i).ganaLocal() || partidos2.get(i).ganaVisit())) {
				devolver.add(partidos2.get(i));
			}
		}
		return devolver;
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

	public List<Partido> getPartidos() {
		List<Partido> partidos = new ArrayList<Partido>();
		partidos.addAll(getPartidos1());
		partidos.addAll(getPartidos2());
		return partidos;
	}

	/**
	 * Método que devuelve una lista de partidos con las victorias del equipo
	 * 
	 * @return
	 */
	public List<Partido> getVictorias() {
		List<Partido> victorias = new ArrayList<>();
		for (int i = 0; i < getPartidos1().size(); i++) {
			if (getPartidos1().get(i).ganaLocal()) {
				victorias.add(getPartidos1().get(i));
			}
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			if (getPartidos2().get(i).ganaVisit()) {
				victorias.add(getPartidos2().get(i));
			}
		}
		return victorias;
	}

	/**
	 * Método que devuelve una lista de partidos con las derrotas del equipo
	 * 
	 * @return
	 */
	public List<Partido> getDerrotas() {
		List<Partido> derrotas = new ArrayList<>();
		for (int i = 0; i < getPartidos1().size(); i++) {
			if (getPartidos1().get(i).ganaVisit()) {
				derrotas.add(getPartidos1().get(i));
			}
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			if (getPartidos2().get(i).ganaLocal()) {
				derrotas.add(getPartidos2().get(i));
			}
		}
		return derrotas;
	}

	/**
	 * Método que devuelve el número de posesiones del equipo
	 * 
	 * @return
	 */
	public int getPosesiones() {
		int posesiones = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			posesiones += getPartidos1().get(i).getPosesionesLocal();
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			posesiones += getPartidos2().get(i).getPosesionesVisit();
		}
		return posesiones;
	}

	/**
	 * Método que devuelve el número de posesiones del equipo en sus partidos como
	 * local
	 * 
	 * @return
	 */
	public int getPosesionesLocal() {
		int posesiones = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			posesiones += getPartidos1().get(i).getPosesionesLocal();
		}
		return posesiones;
	}

	/**
	 * Método que devuelve el número de posesiones del equipo en sus partidos como
	 * visitante
	 * 
	 * @return
	 */
	public int getPosesionesVisit() {
		int posesiones = 0;
		for (int i = 0; i < getPartidos2().size(); i++) {
			posesiones += getPartidos2().get(i).getPosesionesVisit();
		}
		return posesiones;
	}

	/**
	 * Método que devuelve el número de posesiones del equipo en sus victorias
	 * 
	 * @return
	 */
	public int getPosesionesVictorias() {
		int posesiones = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			if (getPartidos1().get(i).ganaLocal()) {
				posesiones += getPartidos1().get(i).getPosesionesLocal();
			}
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			if (getPartidos2().get(i).ganaVisit()) {
				posesiones += getPartidos2().get(i).getPosesionesVisit();
			}
		}
		return posesiones;
	}

	/**
	 * Método que devuelve el número de posesiones del equipo en sus derrotas
	 * 
	 * @return
	 */
	public int getPosesionesDerrotas() {
		int posesiones = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			if (getPartidos1().get(i).ganaVisit()) {
				posesiones += getPartidos1().get(i).getPosesionesLocal();
			}
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			if (getPartidos2().get(i).ganaLocal()) {
				posesiones += getPartidos2().get(i).getPosesionesVisit();
			}
		}
		return posesiones;
	}

	/**
	 * Método que devuelve el número de posesiones del equipo rival
	 * 
	 * @return
	 */
	public int getPosesionesRival() {
		int posesiones = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			posesiones += getPartidos1().get(i).getPosesionesVisit();
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			posesiones += getPartidos2().get(i).getPosesionesLocal();
		}
		return posesiones;
	}

	/**
	 * Método que devuelve el número de posesiones del equipo rival en los partidos
	 * jugados en casa
	 * 
	 * @return
	 */
	public int getPosesionesRivalLocal() {
		int posesiones = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			posesiones += getPartidos1().get(i).getPosesionesVisit();
		}
		return posesiones;
	}

	/**
	 * Método que devuelve el número de posesiones del equipo rival en los partidos
	 * jugados fuera de casa
	 * 
	 * @return
	 */
	public int getPosesionesRivalVisit() {
		int posesiones = 0;
		for (int i = 0; i < getPartidos2().size(); i++) {
			posesiones += getPartidos2().get(i).getPosesionesLocal();
		}
		return posesiones;
	}

	/**
	 * Método que devuelve el número de posesiones del equipo rival en las victorias
	 * 
	 * @return
	 */
	public int getPosesionesRivalVictorias() {
		int posesiones = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			if (getPartidos1().get(i).ganaLocal()) {
				posesiones += getPartidos1().get(i).getPosesionesVisit();
			}
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			if (getPartidos2().get(i).ganaVisit()) {
				posesiones += getPartidos2().get(i).getPosesionesLocal();
			}
		}
		return posesiones;
	}

	/**
	 * Método que devuelve el número de posesiones del equipo rival en las derrotas
	 * 
	 * @return
	 */
	public int getPosesionesRivalDerrotas() {
		int posesiones = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			if (getPartidos1().get(i).ganaVisit()) {
				posesiones += getPartidos1().get(i).getPosesionesVisit();
			}
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			if (getPartidos2().get(i).ganaLocal()) {
				posesiones += getPartidos2().get(i).getPosesionesLocal();
			}
		}
		return posesiones;
	}

	/**
	 * Método que devuelve el número total de minutos jugados por el equipo
	 * 
	 * @return
	 */
	public int getMinutos() {
		int minutos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			minutos += getPartidos1().get(i).getMinutos();
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			minutos += getPartidos2().get(i).getMinutos();
		}
		return minutos;
	}

	/**
	 * Método que devuelve el número total de minutos jugados por el equipo en casa
	 * 
	 * @return
	 */
	public int getMinutosLocal() {
		int minutos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			minutos += getPartidos1().get(i).getMinutos();
		}
		return minutos;
	}

	/**
	 * Método que devuelve el número total de minutos jugados por el equipo fuera de
	 * casa
	 * 
	 * @return
	 */
	public int getMinutosVisit() {
		int minutos = 0;
		for (int i = 0; i < getPartidos2().size(); i++) {
			minutos += getPartidos2().get(i).getMinutos();
		}
		return minutos;
	}

	/**
	 * Método que devuelve el número total de minutos jugados por el equipo en las
	 * victorias
	 * 
	 * @return
	 */
	public int getMinutosVictorias() {
		int minutos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			if (getPartidos1().get(i).ganaLocal()) {
				minutos += getPartidos1().get(i).getMinutos();
			}
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			if (getPartidos2().get(i).ganaVisit()) {
				minutos += getPartidos2().get(i).getMinutos();
			}
		}
		return minutos;
	}

	/**
	 * Método que devuelve el número total de minutos jugados por el equipo en las
	 * derrotas
	 * 
	 * @return
	 */
	public int getMinutosDerrotas() {
		int minutos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			if (getPartidos1().get(i).ganaVisit()) {
				minutos += getPartidos1().get(i).getMinutos();
			}
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			if (getPartidos2().get(i).ganaLocal()) {
				minutos += getPartidos2().get(i).getMinutos();
			}
		}
		return minutos;
	}

	/**
	 * Método que devuelve el ritmo del equipo
	 * 
	 * @return
	 */
	public double getRitmo() {
		return (double) getPosesiones() / (double) getMinutos();
	}

	/**
	 * Método que devuelve el ritmo del equipo en los partidos en casa
	 * 
	 * @return
	 */
	public double getRitmoLocal() {
		return (double) getPosesionesLocal() / (double) getMinutosLocal();
	}

	/**
	 * Método que devuelve el ritmo del equipo en los partidos fuera de casa
	 * 
	 * @return
	 */
	public double getRitmoVisit() {
		return (double) getPosesionesVisit() / (double) getMinutosVisit();
	}

	/**
	 * Método que devuelve el ritmo del equipo en las victorias
	 * 
	 * @return
	 */
	public double getRitmoVictorias() {
		return (double) getPosesionesVictorias() / (double) getMinutosVictorias();
	}

	/**
	 * Método que devuelve el ritmo del equipo en las derrotas
	 * 
	 * @return
	 */
	public double getRitmoDerrotas() {
		return (double) getPosesionesDerrotas() / (double) getMinutosDerrotas();
	}

	/**
	 * Método que devuelve el número total de puntos del equipo
	 * 
	 * @return
	 */
	public int getPuntos() {
		int puntos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			puntos += getPartidos1().get(i).getPtoLocal();
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			puntos += getPartidos2().get(i).getPtoVisit();
		}
		return puntos;
	}

	/**
	 * Método que devuelve el número total de puntos del equipo en los partidos en
	 * casa
	 * 
	 * @return
	 */
	public int getPuntosLocal() {
		int puntos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			puntos += getPartidos1().get(i).getPtoLocal();
		}
		return puntos;
	}

	/**
	 * Método que devuelve el número total de puntos del equipo en los partidos
	 * fuera de casa
	 * 
	 * @return
	 */
	public int getPuntosVisit() {
		int puntos = 0;
		for (int i = 0; i < getPartidos2().size(); i++) {
			puntos += getPartidos2().get(i).getPtoVisit();
		}
		return puntos;
	}

	/**
	 * Método que devuelve el número total de puntos del equipo en las victorias
	 * 
	 * @return
	 */
	public int getPuntosVictorias() {
		int puntos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			if (getPartidos1().get(i).ganaLocal()) {
				puntos += getPartidos1().get(i).getPtoLocal();
			}
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			if (getPartidos2().get(i).ganaVisit()) {
				puntos += getPartidos2().get(i).getPtoVisit();
			}
		}
		return puntos;
	}

	/**
	 * Método que devuelve el número total de puntos del equipo en las derrotas
	 * 
	 * @return
	 */
	public int getPuntosDerrotas() {
		int puntos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			if (getPartidos1().get(i).ganaVisit()) {
				puntos += getPartidos1().get(i).getPtoLocal();
			}
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			if (getPartidos2().get(i).ganaLocal()) {
				puntos += getPartidos2().get(i).getPtoVisit();
			}
		}
		return puntos;
	}

	/**
	 * Método que devuelve los puntos por partido del equipo
	 * 
	 * @return
	 */
	public double getPuntosPorPartido() {
		int puntos = 0;
		int partidos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			puntos += getPartidos1().get(i).getPtoLocal();
			partidos++;
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			puntos += getPartidos2().get(i).getPtoVisit();
			partidos++;
		}
		return (double) puntos / (double) partidos;
	}

	/**
	 * Método que devuelve los puntos por partido del equipo en los partidos en casa
	 * 
	 * @return
	 */
	public double getPuntosPorPartidoLocal() {
		int puntos = 0;
		int partidos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			puntos += getPartidos1().get(i).getPtoLocal();
			partidos++;
		}
		return (double) puntos / (double) partidos;
	}

	/**
	 * Método que devuelve los puntos por partido del equipo en los partidos fuera
	 * de casa
	 * 
	 * @return
	 */
	public double getPuntosPorPartidoVisit() {
		int puntos = 0;
		int partidos = 0;
		for (int i = 0; i < getPartidos2().size(); i++) {
			puntos += getPartidos2().get(i).getPtoVisit();
			partidos++;
		}
		return (double) puntos / (double) partidos;
	}

	/**
	 * Método que devuelve los puntos por partido del equipo en las victorias
	 * 
	 * @return
	 */
	public double getPuntosPorPartidoVictorias() {
		int puntos = 0;
		int partidos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			if (getPartidos1().get(i).ganaLocal()) {
				puntos += getPartidos1().get(i).getPtoLocal();
				partidos++;
			}
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			if (getPartidos2().get(i).ganaVisit()) {
				puntos += getPartidos2().get(i).getPtoVisit();
				partidos++;
			}
		}
		return (double) puntos / (double) partidos;
	}

	/**
	 * Método que devuelve los puntos por partido del equipo en las derrotas
	 * 
	 * @return
	 */
	public double getPuntosPorPartidoDerrotas() {
		int puntos = 0;
		int partidos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			if (getPartidos1().get(i).ganaVisit()) {
				puntos += getPartidos1().get(i).getPtoLocal();
				partidos++;
			}
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			if (getPartidos2().get(i).ganaLocal()) {
				puntos += getPartidos2().get(i).getPtoVisit();
				partidos++;
			}
		}
		return (double) puntos / (double) partidos;
	}

	/**
	 * Método que devuelve los puntos por partido del rival
	 * 
	 * @return
	 */
	public double getPuntosPorPartidoRival() {
		int puntos = 0;
		int partidos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			puntos += getPartidos1().get(i).getPtoVisit();
			partidos++;
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			puntos += getPartidos2().get(i).getPtoLocal();
			partidos++;
		}
		return (double) puntos / (double) partidos;
	}

	/**
	 * Método que devuelve los puntos por partido del rival en los partidos en casa
	 * 
	 * @return
	 */
	public double getPuntosPorPartidoRivalLocal() {
		int puntos = 0;
		int partidos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			puntos += getPartidos1().get(i).getPtoVisit();
			partidos++;
		}
		return (double) puntos / (double) partidos;
	}

	/**
	 * Método que devuelve los puntos por partido del rival en los partidos fuera de
	 * casa
	 * 
	 * @return
	 */
	public double getPuntosPorPartidoRivalVisit() {
		int puntos = 0;
		int partidos = 0;
		for (int i = 0; i < getPartidos2().size(); i++) {
			puntos += getPartidos2().get(i).getPtoLocal();
			partidos++;
		}
		return (double) puntos / (double) partidos;
	}

	/**
	 * Método que devuelve los puntos por partido del rival en las victorias
	 * 
	 * @return
	 */
	public double getPuntosPorPartidoRivalVictorias() {
		int puntos = 0;
		int partidos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			if (getPartidos1().get(i).ganaLocal()) {
				puntos += getPartidos1().get(i).getPtoVisit();
				partidos++;
			}
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			if (getPartidos2().get(i).ganaVisit()) {
				puntos += getPartidos2().get(i).getPtoLocal();
				partidos++;
			}
		}
		return (double) puntos / (double) partidos;
	}

	/**
	 * Método que devuelve los puntos por partido del rival en las derrotas
	 * 
	 * @return
	 */
	public double getPuntosPorPartidoRivalDerrotas() {
		int puntos = 0;
		int partidos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			if (getPartidos1().get(i).ganaVisit()) {
				puntos += getPartidos1().get(i).getPtoVisit();
				partidos++;
			}
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			if (getPartidos2().get(i).ganaLocal()) {
				puntos += getPartidos2().get(i).getPtoLocal();
				partidos++;
			}
		}
		return (double) puntos / (double) partidos;
	}

	/**
	 * Método que devuelve los puntos recibidos
	 * 
	 * @return
	 */
	public int getPuntosRival() {
		int puntos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			puntos += getPartidos1().get(i).getPtoVisit();
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			puntos += getPartidos2().get(i).getPtoLocal();
		}
		return puntos;
	}

	/**
	 * Método que devuelve los puntos recibidos en los partidos de casa
	 * 
	 * @return
	 */
	public int getPuntosRivalLocal() {
		int puntos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			puntos += getPartidos1().get(i).getPtoVisit();
		}
		return puntos;
	}

	/**
	 * Método que devuelve los puntos recibidos en los partidos fuera de casa
	 * 
	 * @return
	 */
	public int getPuntosRivalVisit() {
		int puntos = 0;
		for (int i = 0; i < getPartidos2().size(); i++) {
			puntos += getPartidos2().get(i).getPtoLocal();
		}
		return puntos;
	}

	/**
	 * Método que devuelve los puntos recibidos en las victorias
	 * 
	 * @return
	 */
	public int getPuntosRivalVictorias() {
		int puntos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			if (getPartidos1().get(i).ganaLocal()) {
				puntos += getPartidos1().get(i).getPtoVisit();
			}
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			if (getPartidos2().get(i).ganaVisit()) {
				puntos += getPartidos2().get(i).getPtoLocal();
			}
		}
		return puntos;
	}

	/**
	 * Método que devuelve los puntos recibidos en las derrotas
	 * 
	 * @return
	 */
	public int getPuntosRivalDerrotas() {
		int puntos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			if (getPartidos1().get(i).ganaVisit()) {
				puntos += getPartidos1().get(i).getPtoVisit();
			}
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			if (getPartidos2().get(i).ganaLocal()) {
				puntos += getPartidos2().get(i).getPtoLocal();
			}
		}
		return puntos;
	}

	/**
	 * Método que devuelve la eficiencia ofensiva del equipo
	 * 
	 * @return
	 */
	public double getEffOf() {
		return (double) getPuntos() / (double) getPosesiones() * 100;
	}

	/**
	 * Método que devuelve la eficiencia ofensiva del equipo en las victorias
	 * 
	 * @return
	 */

	public double getEffOfVictorias() {
		return (double) getPuntosVictorias() / (double) getPosesionesVictorias() * 100;
	}

	/**
	 * Método que devuelve la eficiencia ofensiva del equipo en las derrotas
	 * 
	 * @return
	 */
	public double getEffOfDerrotas() {
		return (double) getPuntosDerrotas() / (double) getPosesionesDerrotas() * 100;
	}

	/**
	 * Método que devuelve la eficiencia ofensiva del equipo en los partidos de casa
	 * 
	 * @return
	 */
	public double getEffOfLocal() {
		return (double) getPuntosLocal() / (double) getPosesionesLocal() * 100;
	}

	/**
	 * Método que devuelve la eficiencia ofensiva del equipo en los partidos fuera
	 * de casa
	 * 
	 * @return
	 */
	public double getEffOfVisit() {
		return (double) getPuntosVisit() / (double) getPosesionesVisit() * 100;
	}

	/**
	 * Método que devuelve la eficiencia defensiva del equipo
	 * 
	 * @return
	 */
	public double getEffDef() {
		return (double) getPuntosRival() / (double) getPosesionesRival() * 100;
	}

	/**
	 * Método que devuelve la eficiencia defensiva del equipo en los partidos de
	 * casa
	 * 
	 * @return
	 */
	public double getEffDefLocal() {
		return (double) getPuntosRivalLocal() / (double) getPosesionesRivalLocal() * 100;
	}

	/**
	 * Método que devuelve la eficiencia defensiva del equipo en los partidos fuera
	 * de casa
	 * 
	 * @return
	 */
	public double getEffDefVisit() {
		return (double) getPuntosRivalVisit() / (double) getPosesionesRivalVisit() * 100;
	}

	/**
	 * Método que devuelve la eficiencia defensiva del equipo en las victorias
	 * 
	 * @return
	 */
	public double getEffDefVictorias() {
		return (double) getPuntosRivalVictorias() / (double) getPosesionesRivalVictorias() * 100;
	}

	/**
	 * Método que devuelve la eficiencia defensiva del equipo en las derrotas
	 * 
	 * @return
	 */
	public double getEffDefDerrotas() {
		return (double) getPuntosRivalDerrotas() / (double) getPosesionesRivalDerrotas() * 100;
	}

	/**
	 * Método que devuelve la eficiencia neta del equipo
	 * 
	 * @return
	 */
	public double getNetEff() {
		return getEffOf() - getEffDef();
	}

	/**
	 * Método que devuelve la eficiencia neta del equipo en los partidos de casa
	 * 
	 * @return
	 */
	public double getNetEffLocal() {
		return getEffOfLocal() - getEffDefLocal();
	}

	/**
	 * Método que devuelve la eficiencia neta del equipo en los partidos fuera de
	 * casa
	 * 
	 * @return
	 */
	public double getNetEffVisit() {
		return getEffOfVisit() - getEffDefVisit();
	}

	/**
	 * Método que devuelve la eficiencia neta del equipo en las victorias
	 * 
	 * @return
	 */
	public double getNetEffVictorias() {
		return getEffOfVictorias() - getEffDefVictorias();
	}

	/**
	 * Método que devuelve la eficiencia neta del equipo en las derrotas
	 * 
	 * @return
	 */
	public double getNetEffDerrotas() {
		return getEffOfDerrotas() - getEffDefDerrotas();
	}

	/**
	 * Método que devuelve el pocentaje efectivo de tiro de campo del equipo
	 * 
	 * @return
	 */
	public double getEffTc() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			pct += getPartidos1().get(i).pctgEffTcLocal();
			total++;
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			pct += getPartidos2().get(i).pctgEffTcVisit();
			total++;
		}
		return pct / total * 100;
	}

	/**
	 * Método que devuelve el pocentaje efectivo de tiro de campo del equipo en los
	 * partidos de casa
	 * 
	 * @return
	 */
	public double getEffTcLocal() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			pct += getPartidos1().get(i).pctgEffTcLocal();
			total++;
		}
		return pct / total * 100;
	}

	/**
	 * Método que devuelve el pocentaje efectivo de tiro de campo del equipo en los
	 * partidos fuera de casa
	 * 
	 * @return
	 */
	public double getEffTcVisit() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < getPartidos2().size(); i++) {
			pct += getPartidos2().get(i).pctgEffTcVisit();
			total++;
		}
		return pct / total * 100;
	}

	/**
	 * Método que devuelve el pocentaje efectivo de tiro de campo del equipo en las
	 * victorias
	 * 
	 * @return
	 */
	public double getEffTcVictorias() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			if (getPartidos1().get(i).ganaLocal()) {
				pct += getPartidos1().get(i).pctgEffTcLocal();
				total++;
			}
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			if (getPartidos2().get(i).ganaVisit()) {
				pct += getPartidos2().get(i).pctgEffTcVisit();
				total++;
			}
		}
		return pct / total * 100;
	}

	/**
	 * Método que devuelve el pocentaje efectivo de tiro de campo del equipo en las
	 * derrotas
	 * 
	 * @return
	 */
	public double getEffTcDerrotas() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			if (getPartidos1().get(i).ganaVisit()) {
				pct += getPartidos1().get(i).pctgEffTcLocal();
				total++;
			}
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			if (getPartidos2().get(i).ganaLocal()) {
				pct += getPartidos2().get(i).pctgEffTcVisit();
				total++;
			}
		}
		return pct / total * 100;
	}

	/**
	 * Método que devuelve el pocentaje efectivo de tiro de campo del rival
	 * 
	 * @return
	 */
	public double getEffTcRival() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			pct += getPartidos1().get(i).pctgEffTcVisit();
			total++;
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			pct += getPartidos2().get(i).pctgEffTcLocal();
			total++;
		}
		return pct / total * 100;
	}

	/**
	 * Método que devuelve el pocentaje efectivo de tiro de campo del rival en los
	 * partidos de casa
	 * 
	 * @return
	 */
	public double getEffTcRivalLocal() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			pct += getPartidos1().get(i).pctgEffTcVisit();
			total++;
		}
		return pct / total * 100;
	}

	/**
	 * Método que devuelve el pocentaje efectivo de tiro de campo del rival en los
	 * partidos fuera de casa
	 * 
	 * @return
	 */
	public double getEffTcRivalVisit() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < getPartidos2().size(); i++) {
			pct += getPartidos2().get(i).pctgEffTcLocal();
			total++;
		}
		return pct / total * 100;
	}

	/**
	 * Método que devuelve el pocentaje efectivo de tiro de campo del rival en las
	 * victorias
	 * 
	 * @return
	 */
	public double getEffTcRivalVictorias() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			if (getPartidos1().get(i).ganaLocal()) {
				pct += getPartidos1().get(i).pctgEffTcVisit();
				total++;
			}
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			if (getPartidos2().get(i).ganaVisit()) {
				pct += getPartidos2().get(i).pctgEffTcLocal();
				total++;
			}
		}
		return pct / total * 100;
	}

	/**
	 * Método que devuelve el pocentaje efectivo de tiro de campo del rival en las
	 * derrotas
	 * 
	 * @return
	 */
	public double getEffTcRivalDerrotas() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			if (getPartidos1().get(i).ganaVisit()) {
				pct += getPartidos1().get(i).pctgEffTcVisit();
				total++;
			}
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			if (getPartidos2().get(i).ganaLocal()) {
				pct += getPartidos2().get(i).pctgEffTcLocal();
				total++;
			}
		}
		return pct / total * 100;
	}

	/**
	 * Método que devuelve el pocentaje de rebotes ofensivos del equipo
	 * 
	 * @return
	 */
	public double getPctgROf() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			pct += getPartidos1().get(i).pctgROfLocal();
			total++;
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			pct += getPartidos2().get(i).pctgROfVisit();
			total++;
		}
		return pct / total;
	}

	/**
	 * Método que devuelve el pocentaje de rebotes ofensivos del equipo en los
	 * partidos de casa
	 * 
	 * @return
	 */
	public double getPctgROfLocal() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			pct += getPartidos1().get(i).pctgROfLocal();
			total++;
		}
		return pct / total;
	}

	/**
	 * Método que devuelve el pocentaje de rebotes ofensivos del equipo en los
	 * partidos fuera de casa
	 * 
	 * @return
	 */
	public double getPctgROfVisit() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < getPartidos2().size(); i++) {
			pct += getPartidos2().get(i).pctgROfVisit();
			total++;
		}
		return pct / total;
	}

	/**
	 * Método que devuelve el pocentaje de rebotes ofensivos del equipo en las
	 * victorias
	 * 
	 * @return
	 */
	public double getPctgROfVictorias() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			if (getPartidos1().get(i).ganaLocal()) {
				pct += getPartidos1().get(i).pctgROfLocal();
				total++;
			}
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			if (getPartidos2().get(i).ganaVisit()) {
				pct += getPartidos2().get(i).pctgROfVisit();
				total++;
			}
		}
		return pct / total;
	}

	/**
	 * Método que devuelve el pocentaje de rebotes ofensivos del equipo en las
	 * derrotas
	 * 
	 * @return
	 */
	public double getPctgROfDerrotas() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			if (getPartidos1().get(i).ganaVisit()) {
				pct += getPartidos1().get(i).pctgROfLocal();
				total++;
			}
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			if (getPartidos2().get(i).ganaLocal()) {
				pct += getPartidos2().get(i).pctgROfVisit();
				total++;
			}
		}
		return pct / total;
	}

	/**
	 * Método que devuelve el pocentaje de rebotes ofensivos del rival
	 * 
	 * @return
	 */
	public double getPctgROfRival() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			pct += getPartidos1().get(i).pctgROfVisit();
			total++;
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			pct += getPartidos2().get(i).pctgROfLocal();
			total++;
		}
		return pct / total;
	}

	/**
	 * Método que devuelve el pocentaje de rebotes ofensivos del rival en los
	 * partidos de casa
	 * 
	 * @return
	 */
	public double getPctgROfRivalLocal() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			pct += getPartidos1().get(i).pctgROfVisit();
			total++;
		}
		return pct / total;
	}

	/**
	 * Método que devuelve el pocentaje de rebotes ofensivos del rival en los
	 * partidos fuera de casa
	 * 
	 * @return
	 */
	public double getPctgROfRivalVisit() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < getPartidos2().size(); i++) {
			pct += getPartidos2().get(i).pctgROfLocal();
			total++;
		}
		return pct / total;
	}

	/**
	 * Método que devuelve el pocentaje de rebotes ofensivos del rival en las
	 * victorias
	 * 
	 * @return
	 */
	public double getPctgROfRivalVictorias() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			if (getPartidos1().get(i).ganaLocal()) {
				pct += getPartidos1().get(i).pctgROfVisit();
				total++;
			}
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			if (getPartidos2().get(i).ganaVisit()) {
				pct += getPartidos2().get(i).pctgROfLocal();
				total++;
			}
		}
		return pct / total;
	}

	/**
	 * Método que devuelve el pocentaje de rebotes ofensivos del rival en las
	 * derrotas
	 * 
	 * @return
	 */
	public double getPctgROfRivalDerrotas() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			if (getPartidos1().get(i).ganaVisit()) {
				pct += getPartidos1().get(i).pctgROfVisit();
				total++;
			}
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			if (getPartidos2().get(i).ganaLocal()) {
				pct += getPartidos2().get(i).pctgROfLocal();
				total++;
			}
		}
		return pct / total;
	}

	/**
	 * Método que devuelve el pocentaje de rebotes defensivos del equipo
	 * 
	 * @return
	 */
	public double getPctgRDef() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			pct += getPartidos1().get(i).pctgRDefLocal();
			total++;
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			pct += getPartidos2().get(i).pctgRDefVisit();
			total++;
		}
		return pct / total;
	}

	/**
	 * Método que devuelve el pocentaje de rebotes defensivos del equipo en los
	 * partidos en casa
	 * 
	 * @return
	 */
	public double getPctgRDefLocal() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			pct += getPartidos1().get(i).pctgRDefLocal();
			total++;
		}
		return pct / total;
	}

	/**
	 * Método que devuelve el pocentaje de rebotes defensivos del equipo en los
	 * partidos fuera de casa
	 * 
	 * @return
	 */
	public double getPctgRDefVisit() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < getPartidos2().size(); i++) {
			pct += getPartidos2().get(i).pctgRDefVisit();
			total++;
		}
		return pct / total;
	}

	/**
	 * Método que devuelve el pocentaje de rebotes defensivos del equipo en las
	 * victorias
	 * 
	 * @return
	 */
	public double getPctgRDefVictorias() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			if (getPartidos1().get(i).ganaLocal()) {
				pct += getPartidos1().get(i).pctgRDefLocal();
				total++;
			}
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			if (getPartidos2().get(i).ganaVisit()) {
				pct += getPartidos2().get(i).pctgRDefVisit();
				total++;
			}
		}
		return pct / total;
	}

	/**
	 * Método que devuelve el pocentaje de rebotes defensivos del equipo en las
	 * derrotas
	 * 
	 * @return
	 */
	public double getPctgRDefDerrotas() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			if (getPartidos1().get(i).ganaVisit()) {
				pct += getPartidos1().get(i).pctgRDefLocal();
				total++;
			}
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			if (getPartidos2().get(i).ganaLocal()) {
				pct += getPartidos2().get(i).pctgRDefVisit();
				total++;
			}
		}
		return pct / total;
	}

	/**
	 * Método que devuelve el pocentaje de rebotes defensivos del rival
	 * 
	 * @return
	 */
	public double getPctgRDefRival() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			pct += getPartidos1().get(i).pctgRDefVisit();
			total++;
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			pct += getPartidos2().get(i).pctgRDefLocal();
			total++;
		}
		return pct / total;
	}

	/**
	 * Método que devuelve el pocentaje de rebotes defensivos del rival en los
	 * partidos en casa
	 * 
	 * @return
	 */
	public double getPctgRDefRivalLocal() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			pct += getPartidos1().get(i).pctgRDefVisit();
			total++;
		}
		return pct / total;
	}

	/**
	 * Método que devuelve el pocentaje de rebotes defensivos del rival en los
	 * partidos fuera de casa
	 * 
	 * @return
	 */
	public double getPctgRDefRivalVisit() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < getPartidos2().size(); i++) {
			pct += getPartidos2().get(i).pctgRDefLocal();
			total++;
		}
		return pct / total;
	}

	/**
	 * Método que devuelve el pocentaje de rebotes defensivos del rival en las
	 * victorias
	 * 
	 * @return
	 */
	public double getPctgRDefRivalVictorias() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			if (getPartidos1().get(i).ganaLocal()) {
				pct += getPartidos1().get(i).pctgRDefVisit();
				total++;
			}
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			if (getPartidos2().get(i).ganaVisit()) {
				pct += getPartidos2().get(i).pctgRDefLocal();
				total++;
			}
		}
		return pct / total;
	}

	/**
	 * Método que devuelve el pocentaje de rebotes defensivos del rival en las
	 * derrotas
	 * 
	 * @return
	 */
	public double getPctgRDefRivalDerrotas() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			if (getPartidos1().get(i).ganaVisit()) {
				pct += getPartidos1().get(i).pctgRDefVisit();
				total++;
			}
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			if (getPartidos2().get(i).ganaLocal()) {
				pct += getPartidos2().get(i).pctgRDefLocal();
				total++;
			}
		}
		return pct / total;
	}

	/**
	 * Método que devuelve el total de rebotes ofensivos del equipo
	 * 
	 * @return
	 */
	public double getROf() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			pct += getPartidos1().get(i).getRboLocal();
			total++;
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			pct += getPartidos2().get(i).getRboVisit();
			total++;
		}
		return (double) pct / (double) total;
	}

	/**
	 * Método que devuelve el total de rebotes ofensivos del equipo en los partidos
	 * de casa
	 * 
	 * @return
	 */
	public double getROfLocal() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			pct += getPartidos1().get(i).getRboLocal();
			total++;
		}
		return pct / total;
	}

	/**
	 * Método que devuelve el total de rebotes ofensivos del equipo en los partidos
	 * fuera de casa
	 * 
	 * @return
	 */
	public double getROfVisit() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < getPartidos2().size(); i++) {
			pct += getPartidos2().get(i).getRboVisit();
			total++;
		}
		return pct / total;
	}

	/**
	 * Método que devuelve el total de rebotes ofensivos del equipo en las victorias
	 * 
	 * @return
	 */
	public double getROfVictorias() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			if (getPartidos1().get(i).ganaLocal()) {
				pct += getPartidos1().get(i).getRboLocal();
				total++;
			}
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			if (getPartidos2().get(i).ganaVisit()) {
				pct += getPartidos2().get(i).getRboVisit();
				total++;
			}
		}
		return pct / total;
	}

	/**
	 * Método que devuelve el total de rebotes ofensivos del equipo en las derrotas
	 * 
	 * @return
	 */
	public double getROfDerrotas() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			if (getPartidos1().get(i).ganaVisit()) {
				pct += getPartidos1().get(i).getRboLocal();
				total++;
			}
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			if (getPartidos2().get(i).ganaLocal()) {
				pct += getPartidos2().get(i).getRboVisit();
				total++;
			}
		}
		return pct / total;
	}

	/**
	 * Método que devuelve el total de rebotes ofensivos del rival
	 * 
	 * @return
	 */
	public double getROfRival() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			pct += getPartidos1().get(i).getRboVisit();
			total++;
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			pct += getPartidos2().get(i).getRboLocal();
			total++;
		}
		return pct / total;
	}

	/**
	 * Método que devuelve el total de rebotes ofensivos del rival en los partidos
	 * de casa
	 * 
	 * @return
	 */
	public double getROfRivalLocal() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			pct += getPartidos1().get(i).getRboVisit();
			total++;
		}
		return pct / total;
	}

	public double getROfRivalVisit() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < getPartidos2().size(); i++) {
			pct += getPartidos2().get(i).getRboLocal();
			total++;
		}
		return pct / total;
	}

	/**
	 * Método que devuelve el total de rebotes ofensivos del rival en las victorias
	 * 
	 * @return
	 */
	public double getROfRivalVictorias() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			if (getPartidos1().get(i).ganaLocal()) {
				pct += getPartidos1().get(i).getRboVisit();
				total++;
			}
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			if (getPartidos2().get(i).ganaVisit()) {
				pct += getPartidos2().get(i).getRboLocal();
				total++;
			}
		}
		return pct / total;
	}

	/**
	 * Método que devuelve el total de rebotes ofensivos del rival en las derrotas
	 * 
	 * @return
	 */
	public double getROfRivalDerrotas() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			if (getPartidos1().get(i).ganaVisit()) {
				pct += getPartidos1().get(i).getRboVisit();
				total++;
			}
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			if (getPartidos2().get(i).ganaLocal()) {
				pct += getPartidos2().get(i).pctgROfLocal();
				total++;
			}
		}
		return pct / total;
	}

	/**
	 * Método que devuelve el total de rebotes defensivos del equipo
	 * 
	 * @return
	 */
	public double getRDef() {
		int total = 0;
		double pct = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			pct += getPartidos1().get(i).getRbdLocal();
			total++;
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			pct += getPartidos2().get(i).getRbdVisit();
			total++;
		}
		return pct / total;
	}

	/**
	 * Método que devuelve el total de rebotes defensivos del equipo en los partidos
	 * de casa
	 * 
	 * @return
	 */
	public double getRDefLocal() {
		int total = 0;
		double pct = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			pct += getPartidos1().get(i).getRbdLocal();
			total++;
		}
		return pct / total;
	}

	/**
	 * Método que devuelve el total de rebotes defensivos del equipo en los partidos
	 * fuera de casa
	 * 
	 * @return
	 */
	public double getRDefVisit() {
		int total = 0;
		double pct = 0;
		for (int i = 0; i < getPartidos2().size(); i++) {
			pct += getPartidos2().get(i).getRbdVisit();
			total++;
		}
		return pct / total;
	}

	/**
	 * Método que devuelve el total de rebotes defensivos del equipo en las
	 * victorias
	 * 
	 * @return
	 */
	public double getRDefVictorias() {
		int total = 0;
		double pct = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			if (getPartidos1().get(i).ganaLocal()) {
				pct += getPartidos1().get(i).getRbdLocal();
				total++;
			}
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			if (getPartidos2().get(i).ganaVisit()) {
				pct += getPartidos2().get(i).getRbdVisit();
				total++;
			}
		}
		return pct / total;
	}

	/**
	 * Método que devuelve el total de rebotes defensivos del equipo en las derrotas
	 * 
	 * @return
	 */
	public double getRDefDerrotas() {
		int total = 0;
		double pct = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			if (getPartidos1().get(i).ganaVisit()) {
				pct += getPartidos1().get(i).getRbdLocal();
				total++;
			}
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			if (getPartidos2().get(i).ganaLocal()) {
				pct += getPartidos2().get(i).getRbdVisit();
				total++;
			}
		}
		return pct / total;
	}

	/**
	 * Método que devuelve el total de rebotes defensivos del rival
	 * 
	 * @return
	 */
	public double getRDefRival() {
		int total = 0;
		double pct = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			pct += getPartidos1().get(i).getRbdVisit();
			total++;
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			pct += getPartidos2().get(i).getRbdLocal();
			total++;
		}
		return pct / total;
	}

	/**
	 * Método que devuelve el total de rebotes defensivos del rival en los partidos
	 * de casa
	 * 
	 * @return
	 */
	public double getRDefRivalLocal() {
		int total = 0;
		double pct = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			pct += getPartidos1().get(i).getRbdVisit();
			total++;
		}
		return pct / total;
	}

	/**
	 * Método que devuelve el total de rebotes defensivos del rival en los partidos
	 * fuera de casa
	 * 
	 * @return
	 */
	public double getRDefRivalVisit() {
		int total = 0;
		double pct = 0;
		for (int i = 0; i < getPartidos2().size(); i++) {
			pct += getPartidos2().get(i).getRbdLocal();
			total++;
		}
		return pct / total;
	}

	/**
	 * Método que devuelve el total de rebotes defensivos del rival en las victorias
	 * 
	 * @return
	 */
	public double getRDefRivalVictorias() {
		int total = 0;
		double pct = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			if (getPartidos1().get(i).ganaLocal()) {
				pct += getPartidos1().get(i).getRbdVisit();
				total++;
			}
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			if (getPartidos2().get(i).ganaVisit()) {
				pct += getPartidos2().get(i).getRbdLocal();
				total++;
			}
		}
		return pct / total;
	}

	/**
	 * Método que devuelve el total de rebotes defensivos del rival en las derrotas
	 * 
	 * @return
	 */
	public double getRDefRivalDerrotas() {
		int total = 0;
		double pct = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			if (getPartidos1().get(i).ganaVisit()) {
				pct += getPartidos1().get(i).getRbdVisit();
				total++;
			}
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			if (getPartidos2().get(i).ganaLocal()) {
				pct += getPartidos2().get(i).getRbdLocal();
				total++;
			}
		}
		return pct / total;
	}

	/**
	 * Método que devuelve el número de asistencias por partido del equipo
	 * 
	 * @return
	 */
	public double getAsistenciasPorPartido() {
		int puntos = 0;
		int partidos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			puntos += getPartidos1().get(i).getAsLocal();
			partidos++;
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			puntos += getPartidos2().get(i).getAsVisit();
			partidos++;
		}
		return (double) puntos / (double) partidos;
	}

	/**
	 * Método que devuelve el número de asistencias por partido del equipo en los
	 * partidos de casa
	 * 
	 * @return
	 */
	public double getAsistenciasPorPartidoLocal() {
		int puntos = 0;
		int partidos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			puntos += getPartidos1().get(i).getAsLocal();
			partidos++;
		}
		return (double) puntos / (double) partidos;
	}

	/**
	 * Método que devuelve el número de asistencias por partido del equipo en los
	 * partidos fuera de casa
	 * 
	 * @return
	 */
	public double getAsistenciasPorPartidoVisit() {
		int puntos = 0;
		int partidos = 0;
		for (int i = 0; i < getPartidos2().size(); i++) {
			puntos += getPartidos2().get(i).getAsVisit();
			partidos++;
		}
		return (double) puntos / (double) partidos;
	}

	/**
	 * Método que devuelve el número de asistencias por partido del equipo en las
	 * victorias
	 * 
	 * @return
	 */
	public double getAsistenciasPorPartidoVictorias() {
		int puntos = 0;
		int partidos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			if (getPartidos1().get(i).ganaLocal()) {
				puntos += getPartidos1().get(i).getAsLocal();
				partidos++;
			}
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			if (getPartidos2().get(i).ganaVisit()) {
				puntos += getPartidos2().get(i).getAsVisit();
				partidos++;
			}
		}
		return (double) puntos / (double) partidos;
	}

	/**
	 * Método que devuelve el número de asistencias por partido del equipo en las
	 * derrotas
	 * 
	 * @return
	 */
	public double getAsistenciasPorPartidoDerrotas() {
		int puntos = 0;
		int partidos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			if (getPartidos1().get(i).ganaVisit()) {
				puntos += getPartidos1().get(i).getAsLocal();
				partidos++;
			}
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			if (getPartidos2().get(i).ganaLocal()) {
				puntos += getPartidos2().get(i).getAsVisit();
				partidos++;
			}
		}
		return (double) puntos / (double) partidos;
	}

	/**
	 * Método que devuelve el número de pérdidas por partido del equipo
	 * 
	 * @return
	 */
	public double getPerdidasPorPartido() {
		int puntos = 0;
		int partidos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			puntos += getPartidos1().get(i).getBpLocal();
			partidos++;
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			puntos += getPartidos2().get(i).getBpVisit();
			partidos++;
		}
		return (double) puntos / (double) partidos;
	}

	/**
	 * Método que devuelve el número de pérdidas por partido del equipo en los
	 * partidos de casa
	 * 
	 * @return
	 */
	public double getPerdidasPorPartidoLocal() {
		int puntos = 0;
		int partidos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			puntos += getPartidos1().get(i).getBpLocal();
			partidos++;
		}
		return (double) puntos / (double) partidos;
	}

	/**
	 * Método que devuelve el número de pérdidas por partido del equipo en los
	 * partidos fuera de casa
	 * 
	 * @return
	 */
	public double getPerdidasPorPartidoVisit() {
		int puntos = 0;
		int partidos = 0;
		for (int i = 0; i < getPartidos2().size(); i++) {
			puntos += getPartidos2().get(i).getBpVisit();
			partidos++;
		}
		return (double) puntos / (double) partidos;
	}

	/**
	 * Método que devuelve el número de pérdidas por partido del equipo en las
	 * victorias
	 * 
	 * @return
	 */
	public double getPerdidasPorPartidoVictorias() {
		int puntos = 0;
		int partidos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			if (getPartidos1().get(i).ganaLocal()) {
				puntos += getPartidos1().get(i).getBpLocal();
				partidos++;
			}
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			if (getPartidos2().get(i).ganaVisit()) {
				puntos += getPartidos2().get(i).getBpVisit();
				partidos++;
			}
		}
		return (double) puntos / (double) partidos;
	}

	/**
	 * Método que devuelve el número de pérdidas por partido del equipo en las
	 * derrotas
	 * 
	 * @return
	 */
	public double getPerdidasPorPartidoDerrotas() {
		int puntos = 0;
		int partidos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			if (getPartidos1().get(i).ganaVisit()) {
				puntos += getPartidos1().get(i).getBpLocal();
				partidos++;
			}
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			if (getPartidos2().get(i).ganaLocal()) {
				puntos += getPartidos2().get(i).getBpVisit();
				partidos++;
			}
		}
		return (double) puntos / (double) partidos;
	}

	/**
	 * Método que devuelve el número total de tiros de dos anotados del equipo
	 * 
	 * @return
	 */
	public int getT2a() {
		int puntos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			puntos += getPartidos1().get(i).getT2aLocal();
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			puntos += getPartidos2().get(i).getT2aVisit();
		}
		return puntos;
	}

	/**
	 * Método que devuelve el número total de tiros de dos anotados del equipo en
	 * los partidos de casa
	 * 
	 * @return
	 */
	public int getT2aLocal() {
		int puntos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			puntos += getPartidos1().get(i).getT2aLocal();
		}
		return puntos;
	}

	/**
	 * Método que devuelve el número total de tiros de dos anotados del equipo en
	 * los partidos fuera de casa
	 * 
	 * @return
	 */
	public int getT2aVisit() {
		int puntos = 0;
		for (int i = 0; i < getPartidos2().size(); i++) {
			puntos += getPartidos2().get(i).getT2aVisit();
		}
		return puntos;
	}

	/**
	 * Método que devuelve el número total de tiros de dos anotados del equipo en
	 * las victorias
	 * 
	 * @return
	 */
	public int getT2aVictorias() {
		int puntos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			if (getPartidos1().get(i).ganaLocal()) {
				puntos += getPartidos1().get(i).getT2aLocal();
			}
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			if (getPartidos2().get(i).ganaVisit()) {
				puntos += getPartidos2().get(i).getT2aVisit();
			}
		}
		return puntos;
	}

	/**
	 * Método que devuelve el número total de tiros de dos anotados del equipo en
	 * las derrotas
	 * 
	 * @return
	 */
	public int getT2aDerrotas() {
		int puntos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			if (getPartidos1().get(i).ganaVisit()) {
				puntos += getPartidos1().get(i).getT2aLocal();
			}
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			if (getPartidos2().get(i).ganaLocal()) {
				puntos += getPartidos2().get(i).getT2aVisit();
			}
		}
		return puntos;
	}

	/**
	 * Método que devuelve el número total de tiros de dos intentados del equipo
	 * 
	 * @return
	 */
	public int getT2i() {
		int puntos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			puntos += getPartidos1().get(i).getT2iLocal();
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			puntos += getPartidos2().get(i).getT2iVisit();
		}
		return puntos;
	}

	/**
	 * Método que devuelve el número total de tiros de dos intentados del equipo en
	 * los partidos de casa
	 * 
	 * @return
	 */
	public int getT2iLocal() {
		int puntos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			puntos += getPartidos1().get(i).getT2iLocal();
		}
		return puntos;
	}

	/**
	 * Método que devuelve el número total de tiros de dos intentados del equipo en
	 * los partidos fuera de casa
	 * 
	 * @return
	 */
	public int getT2iVisit() {
		int puntos = 0;
		for (int i = 0; i < getPartidos2().size(); i++) {
			puntos += getPartidos2().get(i).getT2iVisit();
		}
		return puntos;
	}

	/**
	 * Método que devuelve el número total de tiros de dos intentados del equipo en
	 * las victorias
	 * 
	 * @return
	 */
	public int getT2iVictorias() {
		int puntos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			if (getPartidos1().get(i).ganaLocal()) {
				puntos += getPartidos1().get(i).getT2iLocal();
			}
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			if (getPartidos2().get(i).ganaVisit()) {
				puntos += getPartidos2().get(i).getT2iVisit();
			}
		}
		return puntos;
	}

	/**
	 * Método que devuelve el número total de tiros de dos intentados del equipo en
	 * las derrotas
	 * 
	 * @return
	 */
	public int getT2iDerrotas() {
		int puntos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			if (getPartidos1().get(i).ganaVisit()) {
				puntos += getPartidos1().get(i).getT2iLocal();
			}
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			if (getPartidos2().get(i).ganaLocal()) {
				puntos += getPartidos2().get(i).getT2iVisit();
			}
		}
		return puntos;
	}

	/**
	 * Método que devuelve el número total de tiros de tres anotados del equipo
	 * 
	 * @return
	 */
	public int getT3a() {
		int puntos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			puntos += getPartidos1().get(i).getT3aLocal();
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			puntos += getPartidos2().get(i).getT3aVisit();
		}
		return puntos;
	}

	/**
	 * Método que devuelve el número total de tiros de tres anotados del equipo en
	 * los partidos de casa
	 * 
	 * @return
	 */
	public int getT3aLocal() {
		int puntos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			puntos += getPartidos1().get(i).getT3aLocal();
		}
		return puntos;
	}

	/**
	 * Método que devuelve el número total de tiros de tres anotados del equipo en
	 * llos partidos fuera de casa
	 * 
	 * @return
	 */
	public int getT3aVisit() {
		int puntos = 0;
		for (int i = 0; i < getPartidos2().size(); i++) {
			puntos += getPartidos2().get(i).getT3aVisit();
		}
		return puntos;
	}

	/**
	 * Método que devuelve el número total de tiros de tres anotados del equipo en
	 * las victorias
	 * 
	 * @return
	 */
	public int getT3aVictorias() {
		int puntos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			if (getPartidos1().get(i).ganaLocal()) {
				puntos += getPartidos1().get(i).getT3aLocal();
			}
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			if (getPartidos2().get(i).ganaVisit()) {
				puntos += getPartidos2().get(i).getT3aVisit();
			}
		}
		return puntos;
	}

	/**
	 * Método que devuelve el número total de tiros de tres anotados del equipo en
	 * las derrotas
	 * 
	 * @return
	 */
	public int getT3aDerrotas() {
		int puntos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			if (getPartidos1().get(i).ganaVisit()) {
				puntos += getPartidos1().get(i).getT3aLocal();
			}
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			if (getPartidos2().get(i).ganaLocal()) {
				puntos += getPartidos2().get(i).getT3aVisit();
			}
		}
		return puntos;
	}

	/**
	 * Método que devuelve el número total de tiros de tres intentados del equipo
	 * 
	 * @return
	 */
	public int getT3i() {
		int puntos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			puntos += getPartidos1().get(i).getT3iLocal();
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			puntos += getPartidos2().get(i).getT3iVisit();
		}
		return puntos;
	}

	/**
	 * Método que devuelve el número total de tiros de tres intentados del equipo en
	 * los partidos de casa
	 * 
	 * @return
	 */
	public int getT3iLocal() {
		int puntos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			puntos += getPartidos1().get(i).getT3iLocal();
		}
		return puntos;
	}

	/**
	 * Método que devuelve el número total de tiros de tres intentados del equipo en
	 * los partidos fuera de casa
	 * 
	 * @return
	 */
	public int getT3iVisit() {
		int puntos = 0;
		for (int i = 0; i < getPartidos2().size(); i++) {
			puntos += getPartidos2().get(i).getT3iVisit();
		}
		return puntos;
	}

	/**
	 * Método que devuelve el número total de tiros de tres intentados del equipo en
	 * las victorias
	 * 
	 * @return
	 */
	public int getT3iVictorias() {
		int puntos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			if (getPartidos1().get(i).ganaLocal()) {
				puntos += getPartidos1().get(i).getT3iLocal();
			}
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			if (getPartidos2().get(i).ganaVisit()) {
				puntos += getPartidos2().get(i).getT3iVisit();
			}
		}
		return puntos;
	}

	/**
	 * Método que devuelve el número total de tiros de tres intentados del equipo en
	 * las derrotas
	 * 
	 * @return
	 */
	public int getT3iDerrotas() {
		int puntos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			if (getPartidos1().get(i).ganaVisit()) {
				puntos += getPartidos1().get(i).getT3iLocal();
			}
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			if (getPartidos2().get(i).ganaLocal()) {
				puntos += getPartidos2().get(i).getT3iVisit();
			}
		}
		return puntos;
	}

	/**
	 * Método que devuelve el número total de tiros libres anotados del equipo
	 * 
	 * @return
	 */
	public int getTla() {
		int puntos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			puntos += getPartidos1().get(i).getTlaLocal();
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			puntos += getPartidos2().get(i).getTlaVisit();
		}
		return puntos;
	}

	/**
	 * Método que devuelve el número total de tiros libres anotados del equipo en
	 * los partidos de casa
	 * 
	 * @return
	 */
	public int getTlaLocal() {
		int puntos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			puntos += getPartidos1().get(i).getTlaLocal();
		}
		return puntos;
	}

	/**
	 * Método que devuelve el número total de tiros libres anotados del equipo en
	 * los partidos de fuera de casa
	 * 
	 * @return
	 */
	public int getTlaVisit() {
		int puntos = 0;
		for (int i = 0; i < getPartidos2().size(); i++) {
			puntos += getPartidos2().get(i).getTlaVisit();
		}
		return puntos;
	}

	/**
	 * Método que devuelve el número total de tiros libres anotados del equipo en
	 * las victorias
	 * 
	 * @return
	 */
	public int getTlaVictorias() {
		int puntos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			if (getPartidos1().get(i).ganaLocal()) {
				puntos += getPartidos1().get(i).getTlaLocal();
			}
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			if (getPartidos2().get(i).ganaVisit()) {
				puntos += getPartidos2().get(i).getTlaVisit();
			}
		}
		return puntos;
	}

	/**
	 * Método que devuelve el número total de tiros libres anotados del equipo en
	 * las derrotas
	 * 
	 * @return
	 */
	public int getTlaDerrotas() {
		int puntos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			if (getPartidos1().get(i).ganaVisit()) {
				puntos += getPartidos1().get(i).getTlaLocal();
			}
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			if (getPartidos2().get(i).ganaLocal()) {
				puntos += getPartidos2().get(i).getTlaVisit();
			}
		}
		return puntos;
	}

	/**
	 * Método que devuelve el número total de tiros libres intentados del equipo
	 * 
	 * @return
	 */
	public int getTli() {
		int puntos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			puntos += getPartidos1().get(i).getTliLocal();
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			puntos += getPartidos2().get(i).getTliVisit();
		}
		return puntos;
	}

	/**
	 * Método que devuelve el número total de tiros libres intentados del equipo en
	 * los partidos de casa
	 * 
	 * @return
	 */
	public int getTliLocal() {
		int puntos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			puntos += getPartidos1().get(i).getTliLocal();
		}
		return puntos;
	}

	/**
	 * Método que devuelve el número total de tiros libres intentados del equipo en
	 * los partidos fuera de casa
	 * 
	 * @return
	 */
	public int getTliVisit() {
		int puntos = 0;
		for (int i = 0; i < getPartidos2().size(); i++) {
			puntos += getPartidos2().get(i).getTliVisit();
		}
		return puntos;
	}

	/**
	 * Método que devuelve el número total de tiros libres intentados del equipo en
	 * las victorias
	 * 
	 * @return
	 */
	public int getTliVictorias() {
		int puntos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			if (getPartidos1().get(i).ganaLocal()) {
				puntos += getPartidos1().get(i).getTliLocal();
			}
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			if (getPartidos2().get(i).ganaVisit()) {
				puntos += getPartidos2().get(i).getTliVisit();
			}
		}
		return puntos;
	}

	/**
	 * Método que devuelve el número total de tiros libres intentados del equipo en
	 * las derrotas
	 * 
	 * @return
	 */
	public int getTliDerrotas() {
		int puntos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			if (getPartidos1().get(i).ganaVisit()) {
				puntos += getPartidos1().get(i).getTliLocal();
			}
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			if (getPartidos2().get(i).ganaLocal()) {
				puntos += getPartidos2().get(i).getTliVisit();
			}
		}
		return puntos;
	}

	/**
	 * Método que devuelve el número total de pérdidas del equipo en los partidos de
	 * casa
	 * 
	 * @return
	 */
	public int getPerdidasLocal() {
		int puntos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			puntos += getPartidos1().get(i).getBpLocal();
		}
		return puntos;
	}

	/**
	 * Método que devuelve el número total de pérdidas del equipo en los partidos
	 * fuera de casa
	 * 
	 * @return
	 */
	public int getPerdidasVisit() {
		int puntos = 0;
		for (int i = 0; i < getPartidos2().size(); i++) {
			puntos += getPartidos2().get(i).getBpVisit();
		}
		return puntos;
	}

	/**
	 * Método que devuelve el número total de pérdidas del equipo en las victorias
	 * 
	 * @return
	 */
	public int getPerdidasVictorias() {
		int puntos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			if (getPartidos1().get(i).ganaLocal()) {
				puntos += getPartidos1().get(i).getBpLocal();
			}
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			if (getPartidos2().get(i).ganaVisit()) {
				puntos += getPartidos2().get(i).getBpVisit();
			}
		}
		return puntos;
	}

	/**
	 * Método que devuelve el número total de pérdidas del equipo en las derrotas
	 * 
	 * @return
	 */
	public int getPerdidasDerrotas() {
		int puntos = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			if (getPartidos1().get(i).ganaVisit()) {
				puntos += getPartidos1().get(i).getBpLocal();
			}
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			if (getPartidos2().get(i).ganaLocal()) {
				puntos += getPartidos2().get(i).getBpVisit();
			}
		}
		return puntos;
	}

	/**
	 * Método que devuelve el porcentaje de tiros de 2 del equipo
	 * 
	 * @return
	 */
	public double getPctgT2() {
		return (double) getT2a() / (double) getT2i() * 100;
	}

	/**
	 * Método que devuelve el porcentaje de tiros de 2 del equipo em los partidos de
	 * casa
	 * 
	 * @return
	 */
	public double getPctgT2Local() {
		return (double) getT2aLocal() / (double) getT2iLocal() * 100;
	}

	/**
	 * Método que devuelve el porcentaje de tiros de 2 del equipo en los partidos
	 * fuera de casa
	 * 
	 * @return
	 */
	public double getPctgT2Visit() {
		return (double) getT2aVisit() / (double) getT2iVisit() * 100;
	}

	/**
	 * Método que devuelve el porcentaje de tiros de 2 del equipo en las victorias
	 * 
	 * @return
	 */
	public double getPctgT2Victorias() {
		return (double) getT2aVictorias() / (double) getT2iVictorias() * 100;
	}

	/**
	 * Método que devuelve el porcentaje de tiros de 2 del equipo en las derrotas
	 * 
	 * @return
	 */
	public double getPctgT2Derrotas() {
		return (double) getT2aDerrotas() / (double) getT2iDerrotas() * 100;
	}

	/**
	 * Método que devuelve el porcentaje de tiros de 3 del equipo
	 * 
	 * @return
	 */
	public double getPctgT3() {
		return (double) getT3a() / (double) getT3i() * 100;
	}

	/**
	 * Método que devuelve el porcentaje de tiros de 3 del equipo en los partidos de
	 * casa
	 * 
	 * @return
	 */
	public double getPctgT3Local() {
		return (double) getT3aLocal() / (double) getT3iLocal() * 100;
	}

	/**
	 * Método que devuelve el porcentaje de tiros de 3 del equipo en los partidos
	 * fuera de casa
	 * 
	 * @return
	 */
	public double getPctgT3Visit() {
		return (double) getT3aVisit() / (double) getT3iVisit() * 100;
	}

	/**
	 * Método que devuelve el porcentaje de tiros de 3 del equipo en las victorias
	 * 
	 * @return
	 */

	public double getPctgT3Victorias() {
		return (double) getT3aVictorias() / (double) getT3iVictorias() * 100;
	}

	/**
	 * Método que devuelve el porcentaje de tiros de 3 del equipo en las derrotas
	 * 
	 * @return
	 */

	public double getPctgT3Derrotas() {
		return (double) getT3aDerrotas() / (double) getT3iDerrotas() * 100;
	}

	/**
	 * Método que devuelve el porcentaje de tiros libres del equipo
	 * 
	 * @return
	 */

	public double getPctgTl() {
		return (double) getTla() / (double) getTli() * 100;
	}

	/**
	 * Método que devuelve el porcentaje de tiros libres del equipo en los partidos
	 * de casa
	 * 
	 * @return
	 */
	public double getPctgTlLocal() {
		return (double) getTlaLocal() / (double) getTliLocal() * 100;
	}

	/**
	 * Método que devuelve el porcentaje de tiros libres del equipo en los partidos
	 * fuera de casa
	 * 
	 * @return
	 */

	public double getPctgTlVisit() {
		return (double) getTlaVisit() / (double) getTliVisit() * 100;
	}

	/**
	 * Método que devuelve el porcentaje de tiros libres del equipo en las victorias
	 * 
	 * @return
	 */
	public double getPctgTlVictorias() {
		return (double) getTlaVictorias() / (double) getTliVictorias() * 100;
	}

	/**
	 * Método que devuelve el porcentaje de tiros libres del equipo en las derrotas
	 * 
	 * @return
	 */

	public double getPctgTlDerrotas() {
		return (double) getTlaDerrotas() / (double) getTliDerrotas() * 100;
	}

	/**
	 * Método que devuelve el total de pérdidas del equipo
	 * 
	 * @return
	 */
	public double getPerdidas() {
		double per = 0;
		for (int i = 0; i < getPartidos1().size(); i++) {
			per += getPartidos1().get(i).getBpLocal();
		}
		for (int i = 0; i < getPartidos2().size(); i++) {
			per += getPartidos2().get(i).getBpVisit();
		}
		return per;
	}

	/**
	 * Método que devuelve el porcentaje de pérdidas del equipo
	 * 
	 * @return
	 */
	public double getPctgPer() {
		return getPerdidas() / (double) getPosesiones() * 100;
	}

	/**
	 * Método que devuelve el ratio de pérdidas del equipo
	 * 
	 * @return
	 */
	public double getRatioPerdidas() {
		return getPerdidas() * 100 / (getT2i() + getT3i() + 0.44 * getTli() + getPerdidas());
	}

	/**
	 * Método que devuelve el ratio de pérdidas del equipo en los partidos de casa
	 * 
	 * @return
	 */
	public double getRatioPerdidasLocal() {
		return getPerdidasLocal() * 100 / (getT2iLocal() + getT3iLocal() + 0.44 * getTliLocal() + getPerdidasLocal());
	}

	/**
	 * Método que devuelve el ratio de pérdidas del equipo en los partidos fuera de
	 * casa
	 * 
	 * @return
	 */
	public double getRatioPerdidasVisit() {
		return getPerdidasVisit() * 100 / (getT2iVisit() + getT3iVisit() + 0.44 * getTliVisit() + getPerdidasVisit());
	}

	/**
	 * Método que devuelve el ratio de pérdidas del equipo en las victorias
	 * 
	 * @return
	 */
	public double getRatioPerdidasVictorias() {
		return getPerdidasVictorias() * 100
				/ (getT2iVictorias() + getT3iVictorias() + 0.44 * getTliVictorias() + getPerdidasVictorias());
	}

	/**
	 * Método que devuelve el ratio de pérdidas del equipo en las derrotas
	 * 
	 * @return
	 */
	public double getRatioPerdidasDerrotas() {
		return getPerdidasDerrotas() * 100
				/ (getT2iDerrotas() + getT3iDerrotas() + 0.44 * getTliDerrotas() + getPerdidasDerrotas());
	}

	/**
	 * Método que devuelve una lista con los jugadores del equipo
	 * 
	 * @return
	 */
	public List<Jugador> getJugadores() {
		List<Jugador> jugadores = new ArrayList<>();
		for (int i = 0; i < lineapartidos.size(); i++) {
			if (!jugadores.contains(lineapartidos.get(i).getJugador())) {
				jugadores.add(lineapartidos.get(i).getJugador());
			}
		}
		return jugadores;
	}

	/**
	 * Método que devuelve el jugador con más minutos del equipo
	 * 
	 * @return
	 */
	public Jugador getJugadorMasMinutos() {
		double max = 0;
		for (int i = 0; i < getJugadores().size(); i++) {
			if (getJugadores().get(i).getMinutosPorPartido() > max
					&& getJugadores().get(i).getMinutosPorPartido() > 15) {
				max = getJugadores().get(i).getMinutosPorPartido();
			}
		}
		for (int i = 0; i < getJugadores().size(); i++) {
			if (getJugadores().get(i).getMinutosPorPartido() == max) {
				return getJugadores().get(i);
			}
		}
		return null;
	}

	/**
	 * Método que devuelve el jugador con más puntos por partido del equipo
	 * 
	 * @return
	 */
	public Jugador getJugadorMaxPtosPartido() {
		double max = 0;
		for (int i = 0; i < getJugadores().size(); i++) {
			if (getJugadores().get(i).getPuntosPorPartido() > max
					&& getJugadores().get(i).getMinutosPorPartido() > 10) {
				max = getJugadores().get(i).getPuntosPorPartido();
			}
		}
		for (int i = 0; i < getJugadores().size(); i++) {
			if (getJugadores().get(i).getPuntosPorPartido() == max) {
				return getJugadores().get(i);
			}
		}
		return null;
	}

	/**
	 * Método que devuelve el jugador con más puntos por minuto del equipo
	 * 
	 * @return
	 */
	public Jugador getMaximoAnotador() {
		double max = 0;
		for (int i = 0; i < getJugadores().size(); i++) {
			if (getJugadores().get(i).getPuntosPorMinuto() > max && getJugadores().get(i).getMinutosPorPartido() > 10) {
				max = getJugadores().get(i).getPuntosPorMinuto();
			}
		}
		for (int i = 0; i < getJugadores().size(); i++) {
			if (getJugadores().get(i).getPuntosPorMinuto() == max) {
				return getJugadores().get(i);
			}
		}
		return null;
	}

	/**
	 * Método que devuelve el jugador con más puntos por tiro del equipo
	 * 
	 * @return
	 */
	public Jugador getJugadorMasEficiente() {
		double max = 0;
		for (int i = 0; i < getJugadores().size(); i++) {
			if (getJugadores().get(i).getPuntosPorTiro() > max && getJugadores().get(i).getMinutosPorPartido() > 10) {
				max = getJugadores().get(i).getPuntosPorTiro();
			}
		}
		for (int i = 0; i < getJugadores().size(); i++) {
			if (getJugadores().get(i).getPuntosPorTiro() == max) {
				return getJugadores().get(i);
			}
		}
		return null;
	}

	/**
	 * Método que devuelve el jugador con más porcentaje efectivo en tiros de campo
	 * del equipo
	 * 
	 * @return
	 */
	public Jugador getJugadorMejorPctgEf() {
		double max = 0;
		for (int i = 0; i < getJugadores().size(); i++) {
			if (getJugadores().get(i).getPctgEfTC() > max && getJugadores().get(i).getMinutosPorPartido() > 10) {
				max = getJugadores().get(i).getPctgEfTC();
			}
		}
		for (int i = 0; i < getJugadores().size(); i++) {
			if (getJugadores().get(i).getPctgEfTC() == max) {
				return getJugadores().get(i);
			}
		}
		return null;
	}

	/**
	 * Método que devuelve el jugador con más puntos por tiro de 2 del equipo
	 * 
	 * @return
	 */
	public Jugador getJugadorMasEficienteT2() {
		double max = 0;
		for (int i = 0; i < getJugadores().size(); i++) {
			if (getJugadores().get(i).getPuntosPorTiro2() > max && getJugadores().get(i).getMinutosPorPartido() > 10&& getJugadores().get(i).getTiros2PorMinuto() > 2) {
				max = getJugadores().get(i).getPuntosPorTiro2();
			}
		}
		for (int i = 0; i < getJugadores().size(); i++) {
			if (getJugadores().get(i).getPuntosPorTiro2() == max) {
				return getJugadores().get(i);
			}
		}
		return null;
	}

	/**
	 * Método que devuelve el jugador con más puntos por tiro de 3 del equipo
	 * 
	 * @return
	 */
	public Jugador getJugadorMasEficienteT3() {
		double max = 0;
		for (int i = 0; i < getJugadores().size(); i++) {
			if (getJugadores().get(i).getPuntosPorTiro3() > max && getJugadores().get(i).getMinutosPorPartido() > 10
					&& getJugadores().get(i).getTiros3PorMinuto() > 2) {
				max = getJugadores().get(i).getPuntosPorTiro3();
			}
		}
		for (int i = 0; i < getJugadores().size(); i++) {
			if (getJugadores().get(i).getPuntosPorTiro3() == max) {
				return getJugadores().get(i);
			}
		}
		return null;
	}

	/**
	 * Método que devuelve el jugador con más tiros por minuto del equipo
	 * 
	 * @return
	 */
	public Jugador getJugadorMasConsumo() {
		double max = 0;
		for (int i = 0; i < getJugadores().size(); i++) {
			if (getJugadores().get(i).getTirosPorMinuto() > max && getJugadores().get(i).getMinutosPorPartido() > 10) {
				max = getJugadores().get(i).getTirosPorMinuto();
			}
		}
		for (int i = 0; i < getJugadores().size(); i++) {
			if (getJugadores().get(i).getTirosPorMinuto() == max) {
				return getJugadores().get(i);
			}
		}
		return null;
	}

	/**
	 * Método que devuelve el jugador con más rebotes por partido del equipo
	 * 
	 * @return
	 */
	public Jugador getMaximoReboteador() {
		double max = 0;
		for (int i = 0; i < getJugadores().size(); i++) {
			if (getJugadores().get(i).getRebotesPorPartido() > max
					&& getJugadores().get(i).getMinutosPorPartido() > 10) {
				max = getJugadores().get(i).getRebotesPorPartido();
			}
		}
		for (int i = 0; i < getJugadores().size(); i++) {
			if (getJugadores().get(i).getRebotesPorPartido() == max) {
				return getJugadores().get(i);
			}
		}
		return null;
	}

	/**
	 * Método que devuelve el jugador con más asistencias por partido del equipo
	 * 
	 * @return
	 */
	public Jugador getMaximoAsistente() {
		double max = 0;
		for (int i = 0; i < getJugadores().size(); i++) {
			if (getJugadores().get(i).getAsistenciasPorPartido() > max
					&& getJugadores().get(i).getMinutosPorPartido() > 10) {
				max = getJugadores().get(i).getAsistenciasPorPartido();
			}
		}
		for (int i = 0; i < getJugadores().size(); i++) {
			if (getJugadores().get(i).getAsistenciasPorPartido() == max) {
				return getJugadores().get(i);
			}
		}
		return null;
	}
	
	public Jugador getMaximoAsistentePorMinuto() {
		double max = 0;
		for (int i = 0; i < getJugadores().size(); i++) {
			if (getJugadores().get(i).getAsistenciasPorMinuto() > max
					&& getJugadores().get(i).getMinutosPorPartido() > 10) {
				max = getJugadores().get(i).getAsistenciasPorMinuto();
			}
		}
		for (int i = 0; i < getJugadores().size(); i++) {
			if (getJugadores().get(i).getAsistenciasPorMinuto() == max) {
				return getJugadores().get(i);
			}
		}
		return null;
	}
	
	public Jugador getMaximoReboteadorPorMinuto() {
		double max = 0;
		for (int i = 0; i < getJugadores().size(); i++) {
			if (getJugadores().get(i).getRebotesPorMinuto() > max
					&& getJugadores().get(i).getMinutosPorPartido() > 10) {
				max = getJugadores().get(i).getRebotesPorMinuto();
			}
		}
		for (int i = 0; i < getJugadores().size(); i++) {
			if (getJugadores().get(i).getRebotesPorMinuto() == max) {
				return getJugadores().get(i);
			}
		}
		return null;
	}
	
	public Jugador getMasRentable() {
		double max = -200;
		for (int i = 0; i < getJugadores().size(); i++) {
			if (getJugadores().get(i).getMasMenosPorMinuto() > max
					&& getJugadores().get(i).getMinutosPorPartido() > 5) {
				max = getJugadores().get(i).getMasMenosPorMinuto();
			}
		}
		for (int i = 0; i < getJugadores().size(); i++) {
			if (getJugadores().get(i).getMasMenosPorMinuto() == max) {
				return getJugadores().get(i);
			}
		}
		return null;
	}
}