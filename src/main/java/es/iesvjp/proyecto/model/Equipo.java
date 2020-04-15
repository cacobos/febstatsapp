package es.iesvjp.proyecto.model;

import java.io.Serializable;
import javax.persistence.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import es.iesvjp.proyecto.controller.Utilidades;
import es.iesvjp.proyecto.service.PartidoService;

import java.util.ArrayList;
import java.util.List;

/**
 * The persistent class for the equipo database table.
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

	public List<Partido> getPartidos() {
		List<Partido> partidos = new ArrayList<Partido>();
		partidos.addAll(partidos1);
		partidos.addAll(partidos2);
		return partidos;
	}

	public List<Partido> getVictorias() {
		List<Partido> victorias = new ArrayList<>();
		for (int i = 0; i < partidos1.size(); i++) {
			if (partidos1.get(i).ganaLocal()) {
				victorias.add(partidos1.get(i));
			}
		}
		for (int i = 0; i < partidos2.size(); i++) {
			if (partidos2.get(i).ganaVisit()) {
				victorias.add(partidos2.get(i));
			}
		}
		return victorias;
	}

	public List<Partido> getDerrotas() {
		List<Partido> derrotas = new ArrayList<>();
		for (int i = 0; i < partidos1.size(); i++) {
			if (partidos1.get(i).ganaVisit()) {
				derrotas.add(partidos1.get(i));
			}
		}
		for (int i = 0; i < partidos2.size(); i++) {
			if (partidos2.get(i).ganaLocal()) {
				derrotas.add(partidos2.get(i));
			}
		}
		return derrotas;
	}

	public int getPosesiones() {
		int posesiones = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			posesiones += partidos1.get(i).getPosesionesLocal();
		}
		for (int i = 0; i < partidos2.size(); i++) {
			posesiones += partidos2.get(i).getPosesionesVisit();
		}
		return posesiones;
	}

	public int getPosesionesLocal() {
		int posesiones = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			posesiones += partidos1.get(i).getPosesionesLocal();
		}
		return posesiones;
	}

	public int getPosesionesVisit() {
		int posesiones = 0;
		for (int i = 0; i < partidos2.size(); i++) {
			posesiones += partidos2.get(i).getPosesionesVisit();
		}
		return posesiones;
	}

	public int getPosesionesVictorias() {
		int posesiones = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			if (partidos1.get(i).ganaLocal()) {
				posesiones += partidos1.get(i).getPosesionesLocal();
			}
		}
		for (int i = 0; i < partidos2.size(); i++) {
			if (partidos2.get(i).ganaVisit()) {
				posesiones += partidos2.get(i).getPosesionesVisit();
			}
		}
		return posesiones;
	}

	public int getPosesionesDerrotas() {
		int posesiones = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			if (partidos1.get(i).ganaVisit()) {
				posesiones += partidos1.get(i).getPosesionesLocal();
			}
		}
		for (int i = 0; i < partidos2.size(); i++) {
			if (partidos2.get(i).ganaLocal()) {
				posesiones += partidos2.get(i).getPosesionesVisit();
			}
		}
		return posesiones;
	}

	public int getPosesionesRival() {
		int posesiones = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			posesiones += partidos1.get(i).getPosesionesVisit();
		}
		for (int i = 0; i < partidos2.size(); i++) {
			posesiones += partidos2.get(i).getPosesionesLocal();
		}
		return posesiones;
	}

	public int getPosesionesRivalLocal() {
		int posesiones = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			posesiones += partidos1.get(i).getPosesionesVisit();
		}
		return posesiones;
	}

	public int getPosesionesRivalVisit() {
		int posesiones = 0;
		for (int i = 0; i < partidos2.size(); i++) {
			posesiones += partidos2.get(i).getPosesionesLocal();
		}
		return posesiones;
	}

	public int getPosesionesRivalVictorias() {
		int posesiones = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			if (partidos1.get(i).ganaLocal()) {
				posesiones += partidos1.get(i).getPosesionesVisit();
			}
		}
		for (int i = 0; i < partidos2.size(); i++) {
			if (partidos2.get(i).ganaVisit()) {
				posesiones += partidos2.get(i).getPosesionesLocal();
			}
		}
		return posesiones;
	}

	public int getPosesionesRivalDerrotas() {
		int posesiones = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			if (partidos1.get(i).ganaVisit()) {
				posesiones += partidos1.get(i).getPosesionesVisit();
			}
		}
		for (int i = 0; i < partidos2.size(); i++) {
			if (partidos2.get(i).ganaLocal()) {
				posesiones += partidos2.get(i).getPosesionesLocal();
			}
		}
		return posesiones;
	}

	public int getMinutos() {
		int minutos = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			minutos += partidos1.get(i).getMinutos();
		}
		for (int i = 0; i < partidos2.size(); i++) {
			minutos += partidos2.get(i).getMinutos();
		}
		return minutos;
	}

	public int getMinutosLocal() {
		int minutos = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			minutos += partidos1.get(i).getMinutos();
		}
		return minutos;
	}

	public int getMinutosVisit() {
		int minutos = 0;
		for (int i = 0; i < partidos2.size(); i++) {
			minutos += partidos2.get(i).getMinutos();
		}
		return minutos;
	}

	public int getMinutosVictorias() {
		int minutos = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			if (partidos1.get(i).ganaLocal()) {
				minutos += partidos1.get(i).getMinutos();
			}
		}
		for (int i = 0; i < partidos2.size(); i++) {
			if (partidos2.get(i).ganaVisit()) {
				minutos += partidos2.get(i).getMinutos();
			}
		}
		return minutos;
	}

	public int getMinutosDerrotas() {
		int minutos = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			if (partidos1.get(i).ganaVisit()) {
				minutos += partidos1.get(i).getMinutos();
			}
		}
		for (int i = 0; i < partidos2.size(); i++) {
			if (partidos2.get(i).ganaLocal()) {
				minutos += partidos2.get(i).getMinutos();
			}
		}
		return minutos;
	}

	public double getRitmo() {
		return (double) getPosesiones() / (double) getMinutos();
	}

	public double getRitmoLocal() {
		return (double) getPosesionesLocal() / (double) getMinutosLocal();
	}

	public double getRitmoVisit() {
		return (double) getPosesionesVisit() / (double) getMinutosVisit();
	}

	public double getRitmoVictorias() {
		return (double) getPosesionesVictorias() / (double) getMinutosVictorias();
	}

	public double getRitmoDerrotas() {
		return (double) getPosesionesDerrotas() / (double) getMinutosDerrotas();
	}

	public int getPuntos() {
		int puntos = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			puntos += partidos1.get(i).getPtoLocal();
		}
		for (int i = 0; i < partidos2.size(); i++) {
			puntos += partidos2.get(i).getPtoVisit();
		}
		return puntos;
	}

	public int getPuntosLocal() {
		int puntos = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			puntos += partidos1.get(i).getPtoLocal();
		}
		return puntos;
	}

	public int getPuntosVisit() {
		int puntos = 0;
		for (int i = 0; i < partidos2.size(); i++) {
			puntos += partidos2.get(i).getPtoVisit();
		}
		return puntos;
	}

	public int getPuntosVictorias() {
		int puntos = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			if (partidos1.get(i).ganaLocal()) {
				puntos += partidos1.get(i).getPtoLocal();
			}
		}
		for (int i = 0; i < partidos2.size(); i++) {
			if (partidos2.get(i).ganaVisit()) {
				puntos += partidos2.get(i).getPtoVisit();
			}
		}
		return puntos;
	}

	public int getPuntosDerrotas() {
		int puntos = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			if (partidos1.get(i).ganaVisit()) {
				puntos += partidos1.get(i).getPtoLocal();
			}
		}
		for (int i = 0; i < partidos2.size(); i++) {
			if (partidos2.get(i).ganaLocal()) {
				puntos += partidos2.get(i).getPtoVisit();
			}
		}
		return puntos;
	}
	
	public double getPuntosPorPartido() {
		int puntos = 0;
		int partidos=0;
		for (int i = 0; i < partidos1.size(); i++) {
			puntos += partidos1.get(i).getPtoLocal();
			partidos++;
		}
		for (int i = 0; i < partidos2.size(); i++) {
			puntos += partidos2.get(i).getPtoVisit();
			partidos++;
		}
		return (double)puntos/(double)partidos;
	}

	public double getPuntosPorPartidoLocal() {
		int puntos = 0;
		int partidos=0;
		for (int i = 0; i < partidos1.size(); i++) {
			puntos += partidos1.get(i).getPtoLocal();
			partidos++;
		}
		return (double)puntos/(double)partidos;
	}

	public double getPuntosPorPartidoVisit() {
		int puntos = 0;
		int partidos=0;
		for (int i = 0; i < partidos2.size(); i++) {
			puntos += partidos2.get(i).getPtoVisit();
			partidos++;
		}
		return (double)puntos/(double)partidos;
	}

	public double getPuntosPorPartidoVictorias() {
		int puntos = 0;
		int partidos=0;
		for (int i = 0; i < partidos1.size(); i++) {
			if (partidos1.get(i).ganaLocal()) {
				puntos += partidos1.get(i).getPtoLocal();
				partidos++;
			}
		}
		for (int i = 0; i < partidos2.size(); i++) {
			if (partidos2.get(i).ganaVisit()) {
				puntos += partidos2.get(i).getPtoVisit();
				partidos++;
			}
		}
		return (double)puntos/(double)partidos;
	}

	public double getPuntosPorPartidoDerrotas() {
		int puntos = 0;
		int partidos=0;
		for (int i = 0; i < partidos1.size(); i++) {
			if (partidos1.get(i).ganaVisit()) {
				puntos += partidos1.get(i).getPtoLocal();
				partidos++;
			}
		}
		for (int i = 0; i < partidos2.size(); i++) {
			if (partidos2.get(i).ganaLocal()) {
				puntos += partidos2.get(i).getPtoVisit();
				partidos++;
			}
		}
		return (double)puntos/(double)partidos;
	}
	
	public double getPuntosPorPartidoRival() {
		int puntos = 0;
		int partidos=0;
		for (int i = 0; i < partidos1.size(); i++) {
			puntos += partidos1.get(i).getPtoVisit();
			partidos++;
		}
		for (int i = 0; i < partidos2.size(); i++) {
			puntos += partidos2.get(i).getPtoLocal();
			partidos++;
		}
		return (double)puntos/(double)partidos;
	}

	public double getPuntosPorPartidoRivalLocal() {
		int puntos = 0;
		int partidos=0;
		for (int i = 0; i < partidos1.size(); i++) {
			puntos += partidos1.get(i).getPtoVisit();
			partidos++;
		}
		return (double)puntos/(double)partidos;
	}

	public double getPuntosPorPartidoRivalVisit() {
		int puntos = 0;
		int partidos=0;
		for (int i = 0; i < partidos2.size(); i++) {
			puntos += partidos2.get(i).getPtoLocal();
			partidos++;
		}
		return (double)puntos/(double)partidos;
	}

	public double getPuntosPorPartidoRivalVictorias() {
		int puntos = 0;
		int partidos=0;
		for (int i = 0; i < partidos1.size(); i++) {
			if (partidos1.get(i).ganaLocal()) {
				puntos += partidos1.get(i).getPtoVisit();
				partidos++;
			}
		}
		for (int i = 0; i < partidos2.size(); i++) {
			if (partidos2.get(i).ganaVisit()) {
				puntos += partidos2.get(i).getPtoLocal();
				partidos++;
			}
		}
		return (double)puntos/(double)partidos;
	}

	public double getPuntosPorPartidoRivalDerrotas() {
		int puntos = 0;
		int partidos=0;
		for (int i = 0; i < partidos1.size(); i++) {
			if (partidos1.get(i).ganaVisit()) {
				puntos += partidos1.get(i).getPtoVisit();
				partidos++;
			}
		}
		for (int i = 0; i < partidos2.size(); i++) {
			if (partidos2.get(i).ganaLocal()) {
				puntos += partidos2.get(i).getPtoLocal();
				partidos++;
			}
		}
		return (double)puntos/(double)partidos;
	}

	public int getPuntosRival() {
		int puntos = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			puntos += partidos1.get(i).getPtoVisit();
		}
		for (int i = 0; i < partidos2.size(); i++) {
			puntos += partidos2.get(i).getPtoLocal();
		}
		return puntos;
	}

	public int getPuntosRivalLocal() {
		int puntos = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			puntos += partidos1.get(i).getPtoVisit();
		}
		return puntos;
	}

	public int getPuntosRivalVisit() {
		int puntos = 0;
		for (int i = 0; i < partidos2.size(); i++) {
			puntos += partidos2.get(i).getPtoLocal();
		}
		return puntos;
	}

	public int getPuntosRivalVictorias() {
		int puntos = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			if (partidos1.get(i).ganaLocal()) {
				puntos += partidos1.get(i).getPtoVisit();
			}
		}
		for (int i = 0; i < partidos2.size(); i++) {
			if (partidos2.get(i).ganaVisit()) {
				puntos += partidos2.get(i).getPtoLocal();
			}
		}
		return puntos;
	}

	public int getPuntosRivalDerrotas() {
		int puntos = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			if (partidos1.get(i).ganaVisit()) {
				puntos += partidos1.get(i).getPtoVisit();
			}
		}
		for (int i = 0; i < partidos2.size(); i++) {
			if (partidos2.get(i).ganaLocal()) {
				puntos += partidos2.get(i).getPtoLocal();
			}
		}
		return puntos;
	}

	public double getEffOf() {
		return (double) getPuntos() / (double) getPosesiones();
	}

	public double getEffOfVictorias() {
		return (double) getPuntosVictorias() / (double) getPosesionesVictorias();
	}

	public double getEffOfDerrotas() {
		return (double) getPuntosDerrotas() / (double) getPosesionesDerrotas();
	}

	public double getEffOfLocal() {
		return (double) getPuntosLocal() / (double) getPosesionesLocal();
	}

	public double getEffOfVisit() {
		return (double) getPuntosVisit() / (double) getPosesionesVisit();
	}

	public double getEffDef() {
		return (double) getPuntosRival() / (double) getPosesionesRival();
	}

	public double getEffDefLocal() {
		return (double) getPuntosRivalLocal() / (double) getPosesionesRivalLocal();
	}

	public double getEffDefVisit() {
		return (double) getPuntosRivalVisit() / (double) getPosesionesRivalVisit();
	}

	public double getEffDefVictorias() {
		return (double) getPuntosRivalVictorias() / (double) getPosesionesRivalVictorias();
	}

	public double getEffDefDerrotas() {
		return (double) getPuntosRivalDerrotas() / (double) getPosesionesRivalDerrotas();
	}

	public double getNetRating() {
		return (getEffOf() - getEffDef()) * 100;
	}

	public double getNetRatingLocal() {
		return (getEffOfLocal() - getEffDefLocal()) * 100;
	}

	public double getNetRatingVisit() {
		return (getEffOfVisit() - getEffDefVisit()) * 100;
	}

	public double getNetRatingVictorias() {
		return (getEffOfVictorias() - getEffDefVictorias()) * 100;
	}

	public double getNetRatingDerrotas() {
		return (getEffOfDerrotas() - getEffDefDerrotas()) * 100;
	}

	public double getEffTc() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			pct += partidos1.get(i).pctgEffTcLocal();
			total++;
		}
		for (int i = 0; i < partidos2.size(); i++) {
			pct += partidos2.get(i).pctgEffTcVisit();
			total++;
		}
		return pct / total;
	}

	public double getEffTcLocal() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			pct += partidos1.get(i).pctgEffTcLocal();
			total++;
		}
		return pct / total;
	}

	public double getEffTcVisit() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < partidos2.size(); i++) {
			pct += partidos2.get(i).pctgEffTcVisit();
			total++;
		}
		return pct / total;
	}

	public double getEffTcVictorias() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			if (partidos1.get(i).ganaLocal()) {
				pct += partidos1.get(i).pctgEffTcLocal();
				total++;
			}
		}
		for (int i = 0; i < partidos2.size(); i++) {
			if (partidos2.get(i).ganaVisit()) {
				pct += partidos2.get(i).pctgEffTcVisit();
				total++;
			}
		}
		return pct / total;
	}
	
	public double getEffTcDerrotas() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			if (partidos1.get(i).ganaVisit()) {
				pct += partidos1.get(i).pctgEffTcLocal();
				total++;
			}
		}
		for (int i = 0; i < partidos2.size(); i++) {
			if (partidos2.get(i).ganaLocal()) {
				pct += partidos2.get(i).pctgEffTcVisit();
				total++;
			}
		}
		return pct / total;
	}
	
	public double getEffTcRival() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			pct += partidos1.get(i).pctgEffTcVisit();
			total++;
		}
		for (int i = 0; i < partidos2.size(); i++) {
			pct += partidos2.get(i).pctgEffTcLocal();
			total++;
		}
		return pct / total;
	}

	public double getEffTcRivalLocal() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			pct += partidos1.get(i).pctgEffTcVisit();
			total++;
		}
		return pct / total;
	}

	public double getEffTcRivalVisit() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < partidos2.size(); i++) {
			pct += partidos2.get(i).pctgEffTcLocal();
			total++;
		}
		return pct / total;
	}

	public double getEffTcRivalVictorias() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			if (partidos1.get(i).ganaLocal()) {
				pct += partidos1.get(i).pctgEffTcVisit();
				total++;
			}
		}
		for (int i = 0; i < partidos2.size(); i++) {
			if (partidos2.get(i).ganaVisit()) {
				pct += partidos2.get(i).pctgEffTcLocal();
				total++;
			}
		}
		return pct / total;
	}
	
	public double getEffTcRivalDerrotas() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			if (partidos1.get(i).ganaVisit()) {
				pct += partidos1.get(i).pctgEffTcVisit();
				total++;
			}
		}
		for (int i = 0; i < partidos2.size(); i++) {
			if (partidos2.get(i).ganaLocal()) {
				pct += partidos2.get(i).pctgEffTcLocal();
				total++;
			}
		}
		return pct / total;
	}
	
	public double getPctgROf() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			pct += partidos1.get(i).pctgROfLocal();
			total++;
		}
		for (int i = 0; i < partidos2.size(); i++) {
			pct += partidos2.get(i).pctgROfVisit();
			total++;
		}
		return pct / total;
	}

	public double getPctgROfLocal() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			pct += partidos1.get(i).pctgROfLocal();
			total++;
		}
		return pct / total;
	}

	public double getPctgROfVisit() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < partidos2.size(); i++) {
			pct += partidos2.get(i).pctgROfVisit();
			total++;
		}
		return pct / total;
	}

	public double getPctgROfVictorias() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			if (partidos1.get(i).ganaLocal()) {
				pct += partidos1.get(i).pctgROfLocal();
				total++;
			}
		}
		for (int i = 0; i < partidos2.size(); i++) {
			if (partidos2.get(i).ganaVisit()) {
				pct += partidos2.get(i).pctgROfVisit();
				total++;
			}
		}
		return pct / total;
	}
	
	public double getPctgROfDerrotas() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			if (partidos1.get(i).ganaVisit()) {
				pct += partidos1.get(i).pctgROfLocal();
				total++;
			}
		}
		for (int i = 0; i < partidos2.size(); i++) {
			if (partidos2.get(i).ganaLocal()) {
				pct += partidos2.get(i).pctgROfVisit();
				total++;
			}
		}
		return pct / total;
	}
	
	public double getPctgROfRival() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			pct += partidos1.get(i).pctgROfVisit();
			total++;
		}
		for (int i = 0; i < partidos2.size(); i++) {
			pct += partidos2.get(i).pctgROfLocal();
			total++;
		}
		return pct / total;
	}

	public double getPctgROfRivalLocal() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			pct += partidos1.get(i).pctgROfVisit();
			total++;
		}
		return pct / total;
	}

	public double getPctgROfRivalVisit() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < partidos2.size(); i++) {
			pct += partidos2.get(i).pctgROfLocal();
			total++;
		}
		return pct / total;
	}

	public double getPctgROfRivalVictorias() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			if (partidos1.get(i).ganaLocal()) {
				pct += partidos1.get(i).pctgROfVisit();
				total++;
			}
		}
		for (int i = 0; i < partidos2.size(); i++) {
			if (partidos2.get(i).ganaVisit()) {
				pct += partidos2.get(i).pctgROfLocal();
				total++;
			}
		}
		return pct / total;
	}
	
	public double getPctgROfRivalDerrotas() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			if (partidos1.get(i).ganaVisit()) {
				pct += partidos1.get(i).pctgROfVisit();
				total++;
			}
		}
		for (int i = 0; i < partidos2.size(); i++) {
			if (partidos2.get(i).ganaLocal()) {
				pct += partidos2.get(i).pctgROfLocal();
				total++;
			}
		}
		return pct / total;
	}
	
	public double getPctgRDef() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			pct += partidos1.get(i).pctgRDefLocal();
			total++;
		}
		for (int i = 0; i < partidos2.size(); i++) {
			pct += partidos2.get(i).pctgRDefVisit();
			total++;
		}
		return pct / total;
	}

	public double getPctgRDefLocal() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			pct += partidos1.get(i).pctgRDefLocal();
			total++;
		}
		return pct / total;
	}

	public double getPctgRDefVisit() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < partidos2.size(); i++) {
			pct += partidos2.get(i).pctgRDefVisit();
			total++;
		}
		return pct / total;
	}

	public double getPctgRDefVictorias() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			if (partidos1.get(i).ganaLocal()) {
				pct += partidos1.get(i).pctgRDefLocal();
				total++;
			}
		}
		for (int i = 0; i < partidos2.size(); i++) {
			if (partidos2.get(i).ganaVisit()) {
				pct += partidos2.get(i).pctgRDefVisit();
				total++;
			}
		}
		return pct / total;
	}
	
	public double getPctgRDefDerrotas() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			if (partidos1.get(i).ganaVisit()) {
				pct += partidos1.get(i).pctgRDefLocal();
				total++;
			}
		}
		for (int i = 0; i < partidos2.size(); i++) {
			if (partidos2.get(i).ganaLocal()) {
				pct += partidos2.get(i).pctgRDefVisit();
				total++;
			}
		}
		return pct / total;
	}
	
	public double getPctgRDefRival() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			pct += partidos1.get(i).pctgRDefVisit();
			total++;
		}
		for (int i = 0; i < partidos2.size(); i++) {
			pct += partidos2.get(i).pctgRDefLocal();
			total++;
		}
		return pct / total;
	}

	public double getPctgRDefRivalLocal() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			pct += partidos1.get(i).pctgRDefVisit();
			total++;
		}
		return pct / total;
	}

	public double getPctgRDefRivalVisit() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < partidos2.size(); i++) {
			pct += partidos2.get(i).pctgRDefLocal();
			total++;
		}
		return pct / total;
	}

	public double getPctgRDefRivalVictorias() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			if (partidos1.get(i).ganaLocal()) {
				pct += partidos1.get(i).pctgRDefVisit();
				total++;
			}
		}
		for (int i = 0; i < partidos2.size(); i++) {
			if (partidos2.get(i).ganaVisit()) {
				pct += partidos2.get(i).pctgRDefLocal();
				total++;
			}
		}
		return pct / total;
	}
	
	public double getPctgRDefRivalDerrotas() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			if (partidos1.get(i).ganaVisit()) {
				pct += partidos1.get(i).pctgRDefVisit();
				total++;
			}
		}
		for (int i = 0; i < partidos2.size(); i++) {
			if (partidos2.get(i).ganaLocal()) {
				pct += partidos2.get(i).pctgRDefLocal();
				total++;
			}
		}
		return pct / total;
	}
	
	public double getROf() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			pct += partidos1.get(i).getRboLocal();
			total++;
		}
		for (int i = 0; i < partidos2.size(); i++) {
			pct += partidos2.get(i).getRboVisit();
			total++;
		}
		return (double)pct / (double)total;
	}

	public double getROfLocal() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			pct += partidos1.get(i).getRboLocal();
			total++;
		}
		return pct / total;
	}

	public double getROfVisit() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < partidos2.size(); i++) {
			pct += partidos2.get(i).getRboVisit();
			total++;
		}
		return pct / total;
	}

	public double getROfVictorias() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			if (partidos1.get(i).ganaLocal()) {
				pct += partidos1.get(i).getRboLocal();
				total++;
			}
		}
		for (int i = 0; i < partidos2.size(); i++) {
			if (partidos2.get(i).ganaVisit()) {
				pct += partidos2.get(i).getRboVisit();
				total++;
			}
		}
		return pct / total;
	}
	
	public double getROfDerrotas() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			if (partidos1.get(i).ganaVisit()) {
				pct += partidos1.get(i).getRboLocal();
				total++;
			}
		}
		for (int i = 0; i < partidos2.size(); i++) {
			if (partidos2.get(i).ganaLocal()) {
				pct += partidos2.get(i).getRboVisit();
				total++;
			}
		}
		return pct / total;
	}
	
	public double getROfRival() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			pct += partidos1.get(i).getRboVisit();
			total++;
		}
		for (int i = 0; i < partidos2.size(); i++) {
			pct += partidos2.get(i).getRboLocal();
			total++;
		}
		return pct / total;
	}

	public double getROfRivalLocal() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			pct += partidos1.get(i).getRboVisit();
			total++;
		}
		return pct / total;
	}

	public double getROfRivalVisit() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < partidos2.size(); i++) {
			pct += partidos2.get(i).getRboLocal();
			total++;
		}
		return pct / total;
	}

	public double getROfRivalVictorias() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			if (partidos1.get(i).ganaLocal()) {
				pct += partidos1.get(i).getRboVisit();
				total++;
			}
		}
		for (int i = 0; i < partidos2.size(); i++) {
			if (partidos2.get(i).ganaVisit()) {
				pct += partidos2.get(i).getRboLocal();
				total++;
			}
		}
		return pct / total;
	}
	
	public double getROfRivalDerrotas() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			if (partidos1.get(i).ganaVisit()) {
				pct += partidos1.get(i).getRboVisit();
				total++;
			}
		}
		for (int i = 0; i < partidos2.size(); i++) {
			if (partidos2.get(i).ganaLocal()) {
				pct += partidos2.get(i).pctgROfLocal();
				total++;
			}
		}
		return pct / total;
	}
	
	public double getRDef() {
		int total = 0;
		double pct = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			pct += partidos1.get(i).getRbdLocal();
			total++;
		}
		for (int i = 0; i < partidos2.size(); i++) {
			pct += partidos2.get(i).getRbdVisit();
			total++;
		}
		return pct / total;
	}

	public double getRDefLocal() {
		int total = 0;
		double pct = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			pct += partidos1.get(i).getRbdLocal();
			total++;
		}
		return pct / total;
	}

	public double getRDefVisit() {
		int total = 0;
		double pct = 0;
		for (int i = 0; i < partidos2.size(); i++) {
			pct += partidos2.get(i).getRbdVisit();
			total++;
		}
		return pct / total;
	}

	public double getRDefVictorias() {
		int total = 0;
		double pct = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			if (partidos1.get(i).ganaLocal()) {
				pct += partidos1.get(i).getRbdLocal();
				total++;
			}
		}
		for (int i = 0; i < partidos2.size(); i++) {
			if (partidos2.get(i).ganaVisit()) {
				pct += partidos2.get(i).getRbdVisit();
				total++;
			}
		}
		return pct / total;
	}
	
	public double getRDefDerrotas() {
		int total = 0;
		double pct = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			if (partidos1.get(i).ganaVisit()) {
				pct += partidos1.get(i).getRbdLocal();
				total++;
			}
		}
		for (int i = 0; i < partidos2.size(); i++) {
			if (partidos2.get(i).ganaLocal()) {
				pct += partidos2.get(i).getRbdVisit();
				total++;
			}
		}
		return pct / total;
	}
	
	public double getRDefRival() {
		int total = 0;
		double pct = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			pct += partidos1.get(i).getRbdVisit();
			total++;
		}
		for (int i = 0; i < partidos2.size(); i++) {
			pct += partidos2.get(i).getRbdLocal();
			total++;
		}
		return pct / total;
	}

	public double getRDefRivalLocal() {
		int total = 0;
		double pct = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			pct += partidos1.get(i).getRbdVisit();
			total++;
		}
		return pct / total;
	}

	public double getRDefRivalVisit() {
		int total = 0;
		double pct = 0;
		for (int i = 0; i < partidos2.size(); i++) {
			pct += partidos2.get(i).getRbdLocal();
			total++;
		}
		return pct / total;
	}

	public double getRDefRivalVictorias() {
		int total = 0;
		double pct = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			if (partidos1.get(i).ganaLocal()) {
				pct += partidos1.get(i).getRbdVisit();
				total++;
			}
		}
		for (int i = 0; i < partidos2.size(); i++) {
			if (partidos2.get(i).ganaVisit()) {
				pct += partidos2.get(i).getRbdLocal();
				total++;
			}
		}
		return pct / total;
	}
	
	public double getRDefRivalDerrotas() {
		int total = 0;
		double pct = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			if (partidos1.get(i).ganaVisit()) {
				pct += partidos1.get(i).getRbdVisit();
				total++;
			}
		}
		for (int i = 0; i < partidos2.size(); i++) {
			if (partidos2.get(i).ganaLocal()) {
				pct += partidos2.get(i).getRbdLocal();
				total++;
			}
		}
		return pct / total;
	}
	
	

	public List<Jugador> getJugadores() {
		List<Jugador> jugadores = new ArrayList<>();
		for (int i = 0; i < lineapartidos.size(); i++) {
			if (!jugadores.contains(lineapartidos.get(i).getJugador())) {
				jugadores.add(lineapartidos.get(i).getJugador());
			}
		}
		return jugadores;
	}
}