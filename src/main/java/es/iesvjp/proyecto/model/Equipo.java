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
@Table(name = "Equipo")
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
		List<Partido> devolver=new ArrayList<>();
		for (int i = 0; i < partidos1.size(); i++) {
			if(partidos1.get(i).getLineapartidos().size()>0) {
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
		List<Partido> devolver=new ArrayList<>();
		for (int i = 0; i < partidos2.size(); i++) {
			if(partidos2.get(i).getLineapartidos().size()>0) {
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
		int partidos = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			puntos += partidos1.get(i).getPtoLocal();
			partidos++;
		}
		for (int i = 0; i < partidos2.size(); i++) {
			puntos += partidos2.get(i).getPtoVisit();
			partidos++;
		}
		return (double) puntos / (double) partidos;
	}

	public double getPuntosPorPartidoLocal() {
		int puntos = 0;
		int partidos = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			puntos += partidos1.get(i).getPtoLocal();
			partidos++;
		}
		return (double) puntos / (double) partidos;
	}

	public double getPuntosPorPartidoVisit() {
		int puntos = 0;
		int partidos = 0;
		for (int i = 0; i < partidos2.size(); i++) {
			puntos += partidos2.get(i).getPtoVisit();
			partidos++;
		}
		return (double) puntos / (double) partidos;
	}

	public double getPuntosPorPartidoVictorias() {
		int puntos = 0;
		int partidos = 0;
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
		return (double) puntos / (double) partidos;
	}

	public double getPuntosPorPartidoDerrotas() {
		int puntos = 0;
		int partidos = 0;
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
		return (double) puntos / (double) partidos;
	}

	public double getPuntosPorPartidoRival() {
		int puntos = 0;
		int partidos = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			puntos += partidos1.get(i).getPtoVisit();
			partidos++;
		}
		for (int i = 0; i < partidos2.size(); i++) {
			puntos += partidos2.get(i).getPtoLocal();
			partidos++;
		}
		return (double) puntos / (double) partidos;
	}

	public double getPuntosPorPartidoRivalLocal() {
		int puntos = 0;
		int partidos = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			puntos += partidos1.get(i).getPtoVisit();
			partidos++;
		}
		return (double) puntos / (double) partidos;
	}

	public double getPuntosPorPartidoRivalVisit() {
		int puntos = 0;
		int partidos = 0;
		for (int i = 0; i < partidos2.size(); i++) {
			puntos += partidos2.get(i).getPtoLocal();
			partidos++;
		}
		return (double) puntos / (double) partidos;
	}

	public double getPuntosPorPartidoRivalVictorias() {
		int puntos = 0;
		int partidos = 0;
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
		return (double) puntos / (double) partidos;
	}

	public double getPuntosPorPartidoRivalDerrotas() {
		int puntos = 0;
		int partidos = 0;
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
		return (double) puntos / (double) partidos;
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
		return (double) getPuntos() / (double) getPosesiones() * 100;
	}

	public double getEffOfVictorias() {
		return (double) getPuntosVictorias() / (double) getPosesionesVictorias() * 100;
	}

	public double getEffOfDerrotas() {
		return (double) getPuntosDerrotas() / (double) getPosesionesDerrotas() * 100;
	}

	public double getEffOfLocal() {
		return (double) getPuntosLocal() / (double) getPosesionesLocal() * 100;
	}

	public double getEffOfVisit() {
		return (double) getPuntosVisit() / (double) getPosesionesVisit() * 100;
	}

	public double getEffDef() {
		return (double) getPuntosRival() / (double) getPosesionesRival() * 100;
	}

	public double getEffDefLocal() {
		return (double) getPuntosRivalLocal() / (double) getPosesionesRivalLocal() * 100;
	}

	public double getEffDefVisit() {
		return (double) getPuntosRivalVisit() / (double) getPosesionesRivalVisit() * 100;
	}

	public double getEffDefVictorias() {
		return (double) getPuntosRivalVictorias() / (double) getPosesionesRivalVictorias() * 100;
	}

	public double getEffDefDerrotas() {
		return (double) getPuntosRivalDerrotas() / (double) getPosesionesRivalDerrotas() * 100;
	}

	public double getNetEff() {
		return getEffOf() - getEffDef();
	}

	public double getNetEffLocal() {
		return getEffOfLocal() - getEffDefLocal();
	}

	public double getNetEffVisit() {
		return getEffOfVisit() - getEffDefVisit();
	}

	public double getNetEffVictorias() {
		return getEffOfVictorias() - getEffDefVictorias();
	}

	public double getNetEffDerrotas() {
		return getEffOfDerrotas() - getEffDefDerrotas();
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
		return pct / total * 100;
	}

	public double getEffTcLocal() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			pct += partidos1.get(i).pctgEffTcLocal();
			total++;
		}
		return pct / total * 100;
	}

	public double getEffTcVisit() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < partidos2.size(); i++) {
			pct += partidos2.get(i).pctgEffTcVisit();
			total++;
		}
		return pct / total * 100;
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
		return pct / total * 100;
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
		return pct / total * 100;
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
		return pct / total * 100;
	}

	public double getEffTcRivalLocal() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			pct += partidos1.get(i).pctgEffTcVisit();
			total++;
		}
		return pct / total * 100;
	}

	public double getEffTcRivalVisit() {
		double total = 0;
		double pct = 0;
		for (int i = 0; i < partidos2.size(); i++) {
			pct += partidos2.get(i).pctgEffTcLocal();
			total++;
		}
		return pct / total * 100;
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
		return pct / total * 100;
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
		return pct / total * 100;
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
		return (double) pct / (double) total;
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

	public double getAsistenciasPorPartido() {
		int puntos = 0;
		int partidos = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			puntos += partidos1.get(i).getAsLocal();
			partidos++;
		}
		for (int i = 0; i < partidos2.size(); i++) {
			puntos += partidos2.get(i).getAsVisit();
			partidos++;
		}
		return (double) puntos / (double) partidos;
	}

	public double getAsistenciasPorPartidoLocal() {
		int puntos = 0;
		int partidos = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			puntos += partidos1.get(i).getAsLocal();
			partidos++;
		}
		return (double) puntos / (double) partidos;
	}

	public double getAsistenciasPorPartidoVisit() {
		int puntos = 0;
		int partidos = 0;
		for (int i = 0; i < partidos2.size(); i++) {
			puntos += partidos2.get(i).getAsVisit();
			partidos++;
		}
		return (double) puntos / (double) partidos;
	}

	public double getAsistenciasPorPartidoVictorias() {
		int puntos = 0;
		int partidos = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			if (partidos1.get(i).ganaLocal()) {
				puntos += partidos1.get(i).getAsLocal();
				partidos++;
			}
		}
		for (int i = 0; i < partidos2.size(); i++) {
			if (partidos2.get(i).ganaVisit()) {
				puntos += partidos2.get(i).getAsVisit();
				partidos++;
			}
		}
		return (double) puntos / (double) partidos;
	}

	public double getAsistenciasPorPartidoDerrotas() {
		int puntos = 0;
		int partidos = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			if (partidos1.get(i).ganaVisit()) {
				puntos += partidos1.get(i).getAsLocal();
				partidos++;
			}
		}
		for (int i = 0; i < partidos2.size(); i++) {
			if (partidos2.get(i).ganaLocal()) {
				puntos += partidos2.get(i).getAsVisit();
				partidos++;
			}
		}
		return (double) puntos / (double) partidos;
	}

	public double getPerdidasPorPartido() {
		int puntos = 0;
		int partidos = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			puntos += partidos1.get(i).getBpLocal();
			partidos++;
		}
		for (int i = 0; i < partidos2.size(); i++) {
			puntos += partidos2.get(i).getBpVisit();
			partidos++;
		}
		return (double) puntos / (double) partidos;
	}

	public double getPerdidasPorPartidoLocal() {
		int puntos = 0;
		int partidos = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			puntos += partidos1.get(i).getBpLocal();
			partidos++;
		}
		return (double) puntos / (double) partidos;
	}

	public double getPerdidasPorPartidoVisit() {
		int puntos = 0;
		int partidos = 0;
		for (int i = 0; i < partidos2.size(); i++) {
			puntos += partidos2.get(i).getBpVisit();
			partidos++;
		}
		return (double) puntos / (double) partidos;
	}

	public double getPerdidasPorPartidoVictorias() {
		int puntos = 0;
		int partidos = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			if (partidos1.get(i).ganaLocal()) {
				puntos += partidos1.get(i).getBpLocal();
				partidos++;
			}
		}
		for (int i = 0; i < partidos2.size(); i++) {
			if (partidos2.get(i).ganaVisit()) {
				puntos += partidos2.get(i).getBpVisit();
				partidos++;
			}
		}
		return (double) puntos / (double) partidos;
	}

	public double getPerdidasPorPartidoDerrotas() {
		int puntos = 0;
		int partidos = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			if (partidos1.get(i).ganaVisit()) {
				puntos += partidos1.get(i).getBpLocal();
				partidos++;
			}
		}
		for (int i = 0; i < partidos2.size(); i++) {
			if (partidos2.get(i).ganaLocal()) {
				puntos += partidos2.get(i).getBpVisit();
				partidos++;
			}
		}
		return (double) puntos / (double) partidos;
	}

	public int getT2a() {
		int puntos = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			puntos += partidos1.get(i).getT2aLocal();
		}
		for (int i = 0; i < partidos2.size(); i++) {
			puntos += partidos2.get(i).getT2aVisit();
		}
		return puntos;
	}

	public int getT2aLocal() {
		int puntos = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			puntos += partidos1.get(i).getT2aLocal();
		}
		return puntos;
	}

	public int getT2aVisit() {
		int puntos = 0;
		for (int i = 0; i < partidos2.size(); i++) {
			puntos += partidos2.get(i).getT2aVisit();
		}
		return puntos;
	}

	public int getT2aVictorias() {
		int puntos = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			if (partidos1.get(i).ganaLocal()) {
				puntos += partidos1.get(i).getT2aLocal();
			}
		}
		for (int i = 0; i < partidos2.size(); i++) {
			if (partidos2.get(i).ganaVisit()) {
				puntos += partidos2.get(i).getT2aVisit();
			}
		}
		return puntos;
	}

	public int getT2aDerrotas() {
		int puntos = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			if (partidos1.get(i).ganaVisit()) {
				puntos += partidos1.get(i).getT2aLocal();
			}
		}
		for (int i = 0; i < partidos2.size(); i++) {
			if (partidos2.get(i).ganaLocal()) {
				puntos += partidos2.get(i).getT2aVisit();
			}
		}
		return puntos;
	}

	public int getT2i() {
		int puntos = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			puntos += partidos1.get(i).getT2iLocal();
		}
		for (int i = 0; i < partidos2.size(); i++) {
			puntos += partidos2.get(i).getT2iVisit();
		}
		return puntos;
	}

	public int getT2iLocal() {
		int puntos = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			puntos += partidos1.get(i).getT2iLocal();
		}
		return puntos;
	}

	public int getT2iVisit() {
		int puntos = 0;
		for (int i = 0; i < partidos2.size(); i++) {
			puntos += partidos2.get(i).getT2iVisit();
		}
		return puntos;
	}

	public int getT2iVictorias() {
		int puntos = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			if (partidos1.get(i).ganaLocal()) {
				puntos += partidos1.get(i).getT2iLocal();
			}
		}
		for (int i = 0; i < partidos2.size(); i++) {
			if (partidos2.get(i).ganaVisit()) {
				puntos += partidos2.get(i).getT2iVisit();
			}
		}
		return puntos;
	}

	public int getT2iDerrotas() {
		int puntos = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			if (partidos1.get(i).ganaVisit()) {
				puntos += partidos1.get(i).getT2iLocal();
			}
		}
		for (int i = 0; i < partidos2.size(); i++) {
			if (partidos2.get(i).ganaLocal()) {
				puntos += partidos2.get(i).getT2iVisit();
			}
		}
		return puntos;
	}

	public int getT3a() {
		int puntos = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			puntos += partidos1.get(i).getT3aLocal();
		}
		for (int i = 0; i < partidos2.size(); i++) {
			puntos += partidos2.get(i).getT3aVisit();
		}
		return puntos;
	}

	public int getT3aLocal() {
		int puntos = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			puntos += partidos1.get(i).getT3aLocal();
		}
		return puntos;
	}

	public int getT3aVisit() {
		int puntos = 0;
		for (int i = 0; i < partidos2.size(); i++) {
			puntos += partidos2.get(i).getT3aVisit();
		}
		return puntos;
	}

	public int getT3aVictorias() {
		int puntos = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			if (partidos1.get(i).ganaLocal()) {
				puntos += partidos1.get(i).getT3aLocal();
			}
		}
		for (int i = 0; i < partidos2.size(); i++) {
			if (partidos2.get(i).ganaVisit()) {
				puntos += partidos2.get(i).getT3aVisit();
			}
		}
		return puntos;
	}

	public int getT3aDerrotas() {
		int puntos = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			if (partidos1.get(i).ganaVisit()) {
				puntos += partidos1.get(i).getT3aLocal();
			}
		}
		for (int i = 0; i < partidos2.size(); i++) {
			if (partidos2.get(i).ganaLocal()) {
				puntos += partidos2.get(i).getT3aVisit();
			}
		}
		return puntos;
	}

	public int getT3i() {
		int puntos = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			puntos += partidos1.get(i).getT3iLocal();
		}
		for (int i = 0; i < partidos2.size(); i++) {
			puntos += partidos2.get(i).getT3iVisit();
		}
		return puntos;
	}

	public int getT3iLocal() {
		int puntos = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			puntos += partidos1.get(i).getT3iLocal();
		}
		return puntos;
	}

	public int getT3iVisit() {
		int puntos = 0;
		for (int i = 0; i < partidos2.size(); i++) {
			puntos += partidos2.get(i).getT3iVisit();
		}
		return puntos;
	}

	public int getT3iVictorias() {
		int puntos = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			if (partidos1.get(i).ganaLocal()) {
				puntos += partidos1.get(i).getT3iLocal();
			}
		}
		for (int i = 0; i < partidos2.size(); i++) {
			if (partidos2.get(i).ganaVisit()) {
				puntos += partidos2.get(i).getT3iVisit();
			}
		}
		return puntos;
	}

	public int getT3iDerrotas() {
		int puntos = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			if (partidos1.get(i).ganaVisit()) {
				puntos += partidos1.get(i).getT3iLocal();
			}
		}
		for (int i = 0; i < partidos2.size(); i++) {
			if (partidos2.get(i).ganaLocal()) {
				puntos += partidos2.get(i).getT3iVisit();
			}
		}
		return puntos;
	}

	public int getTla() {
		int puntos = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			puntos += partidos1.get(i).getTlaLocal();
		}
		for (int i = 0; i < partidos2.size(); i++) {
			puntos += partidos2.get(i).getTlaVisit();
		}
		return puntos;
	}

	public int getTlaLocal() {
		int puntos = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			puntos += partidos1.get(i).getTlaLocal();
		}
		return puntos;
	}

	public int getTlaVisit() {
		int puntos = 0;
		for (int i = 0; i < partidos2.size(); i++) {
			puntos += partidos2.get(i).getTlaVisit();
		}
		return puntos;
	}

	public int getTlaVictorias() {
		int puntos = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			if (partidos1.get(i).ganaLocal()) {
				puntos += partidos1.get(i).getTlaLocal();
			}
		}
		for (int i = 0; i < partidos2.size(); i++) {
			if (partidos2.get(i).ganaVisit()) {
				puntos += partidos2.get(i).getTlaVisit();
			}
		}
		return puntos;
	}

	public int getTlaDerrotas() {
		int puntos = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			if (partidos1.get(i).ganaVisit()) {
				puntos += partidos1.get(i).getTlaLocal();
			}
		}
		for (int i = 0; i < partidos2.size(); i++) {
			if (partidos2.get(i).ganaLocal()) {
				puntos += partidos2.get(i).getTlaVisit();
			}
		}
		return puntos;
	}

	public int getTli() {
		int puntos = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			puntos += partidos1.get(i).getTliLocal();
		}
		for (int i = 0; i < partidos2.size(); i++) {
			puntos += partidos2.get(i).getTliVisit();
		}
		return puntos;
	}

	public int getTliLocal() {
		int puntos = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			puntos += partidos1.get(i).getTliLocal();
		}
		return puntos;
	}

	public int getTliVisit() {
		int puntos = 0;
		for (int i = 0; i < partidos2.size(); i++) {
			puntos += partidos2.get(i).getTliVisit();
		}
		return puntos;
	}

	public int getTliVictorias() {
		int puntos = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			if (partidos1.get(i).ganaLocal()) {
				puntos += partidos1.get(i).getTliLocal();
			}
		}
		for (int i = 0; i < partidos2.size(); i++) {
			if (partidos2.get(i).ganaVisit()) {
				puntos += partidos2.get(i).getTliVisit();
			}
		}
		return puntos;
	}

	public int getTliDerrotas() {
		int puntos = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			if (partidos1.get(i).ganaVisit()) {
				puntos += partidos1.get(i).getTliLocal();
			}
		}
		for (int i = 0; i < partidos2.size(); i++) {
			if (partidos2.get(i).ganaLocal()) {
				puntos += partidos2.get(i).getTliVisit();
			}
		}
		return puntos;
	}

	public double getPctgT2() {
		return (double) getT2a() / (double) getT2i() * 100;
	}

	public double getPctgT2Local() {
		return (double) getT2aLocal() / (double) getT2iLocal() * 100;
	}

	public double getPctgT2Visit() {
		return (double) getT2aVisit() / (double) getT2iVisit() * 100;
	}

	public double getPctgT2Victorias() {
		return (double) getT2aVictorias() / (double) getT2iVictorias() * 100;
	}

	public double getPctgT2Derrotas() {
		return (double) getT2aDerrotas() / (double) getT2iDerrotas() * 100;
	}

	public double getPctgT3() {
		return (double) getT3a() / (double) getT3i() * 100;
	}

	public double getPctgT3Local() {
		return (double) getT3aLocal() / (double) getT3iLocal() * 100;
	}

	public double getPctgT3Visit() {
		return (double) getT3aVisit() / (double) getT3iVisit() * 100;
	}

	public double getPctgT3Victorias() {
		return (double) getT3aVictorias() / (double) getT3iVictorias() * 100;
	}

	public double getPctgT3Derrotas() {
		return (double) getT3aDerrotas() / (double) getT3iDerrotas() * 100;
	}

	public double getPctgTl() {
		return (double) getTla() / (double) getTli() * 100;
	}

	public double getPctgTlLocal() {
		return (double) getTlaLocal() / (double) getTliLocal() * 100;
	}

	public double getPctgTlVisit() {
		return (double) getTlaVisit() / (double) getTliVisit() * 100;
	}

	public double getPctgTlVictorias() {
		return (double) getTlaVictorias() / (double) getTliVictorias() * 100;
	}

	public double getPctgTlDerrotas() {
		return (double) getTlaDerrotas() / (double) getTliDerrotas() * 100;
	}

	public double getPerdidas() {
		double per = 0;
		for (int i = 0; i < partidos1.size(); i++) {
			per += partidos1.get(i).getBpLocal();
		}
		for (int i = 0; i < partidos2.size(); i++) {
			per += partidos2.get(i).getBpVisit();
		}
		return per;
	}

	public double getPctgPer() {
		return getPerdidas() / (double) getPosesiones() * 100;
	}

	public double getRatioPerdidas() {
		return getPerdidasPorPartido() / (getT2i() + getT3i() + 0.44 * getTli() + getPerdidasPorPartido());
	}

	public double getRatioPerdidasLocal() {
		return getPerdidasPorPartidoLocal()
				/ (getT2iLocal() + getT3iLocal() + 0.44 * getTliLocal() + getPerdidasPorPartidoLocal());
	}

	public double getRatioPerdidasVisit() {
		return getPerdidasPorPartidoVisit()
				/ (getT2iVisit() + getT3iVisit() + 0.44 * getTliVisit() + getPerdidasPorPartidoVisit());
	}

	public double getRatioPerdidasVictorias() {
		return getPerdidasPorPartidoVictorias()
				/ (getT2iVictorias() + getT3iVictorias() + 0.44 * getTliVictorias() + getPerdidasPorPartidoVictorias());
	}

	public double getRatioPerdidasDerrotas() {
		return getPerdidasPorPartidoDerrotas()
				/ (getT2iDerrotas() + getT3iDerrotas() + 0.44 * getTliDerrotas() + getPerdidasPorPartidoDerrotas());
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

	public Jugador getJugadorMasMinutos() {
		double max = 0;
		for (int i = 0; i < getJugadores().size(); i++) {
			if(getJugadores().get(i).getMinutosPorPartido()>max && getJugadores().get(i).getMinutosPorPartido()>10) {
				max=getJugadores().get(i).getMinutosPorPartido();
			}
		}
		for (int i = 0; i < getJugadores().size(); i++) {
			if(getJugadores().get(i).getMinutosPorPartido()==max) {
				return getJugadores().get(i);
			}
		}
		return null;
	}
	
	public Jugador getJugadorMaxPtosPartido() {
		double max = 0;
		for (int i = 0; i < getJugadores().size(); i++) {
			if(getJugadores().get(i).getPuntosPorPartido()>max && getJugadores().get(i).getMinutosPorPartido()>10) {
				max=getJugadores().get(i).getPuntosPorPartido();
			}
		}
		for (int i = 0; i < getJugadores().size(); i++) {
			if(getJugadores().get(i).getPuntosPorPartido()==max) {
				return getJugadores().get(i);
			}
		}
		return null;
	}

	public Jugador getMaximoAnotador() {
		double max = 0;
		for (int i = 0; i < getJugadores().size(); i++) {
			if(getJugadores().get(i).getPuntosPorMinuto()>max && getJugadores().get(i).getMinutosPorPartido()>10) {
				max=getJugadores().get(i).getPuntosPorMinuto();
			}
		}
		for (int i = 0; i < getJugadores().size(); i++) {
			if(getJugadores().get(i).getPuntosPorMinuto()==max) {
				return getJugadores().get(i);
			}
		}
		return null;
	}
	
	public Jugador getJugadorMasEficiente() {
		double max = 0;
		for (int i = 0; i < getJugadores().size(); i++) {
			if(getJugadores().get(i).getPuntosPorTiro()>max && getJugadores().get(i).getMinutosPorPartido()>10) {
				max=getJugadores().get(i).getPuntosPorTiro();
			}
		}
		for (int i = 0; i < getJugadores().size(); i++) {
			if(getJugadores().get(i).getPuntosPorTiro()==max) {
				return getJugadores().get(i);
			}
		}
		return null;
	}
	
	public Jugador getJugadorMejorPctgEf() {
		double max = 0;
		for (int i = 0; i < getJugadores().size(); i++) {
			if(getJugadores().get(i).getPctgEfTC()>max && getJugadores().get(i).getMinutosPorPartido()>10) {
				max=getJugadores().get(i).getPctgEfTC();
			}
		}
		for (int i = 0; i < getJugadores().size(); i++) {
			if(getJugadores().get(i).getPctgEfTC()==max) {
				return getJugadores().get(i);
			}
		}
		return null;
	}
	
	
	public Jugador getJugadorMasEficienteT2() {
		double max = 0;
		for (int i = 0; i < getJugadores().size(); i++) {
			if(getJugadores().get(i).getPuntosPorTiro2()>max && getJugadores().get(i).getMinutosPorPartido()>10) {
				max=getJugadores().get(i).getPuntosPorTiro2();
			}
		}
		for (int i = 0; i < getJugadores().size(); i++) {
			if(getJugadores().get(i).getPuntosPorTiro2()==max) {
				return getJugadores().get(i);
			}
		}
		return null;
	}
	
	public Jugador getJugadorMasEficienteT3() {
		double max = 0;
		for (int i = 0; i < getJugadores().size(); i++) {
			if(getJugadores().get(i).getPuntosPorTiro3()>max && getJugadores().get(i).getMinutosPorPartido()>10) {
				max=getJugadores().get(i).getPuntosPorTiro3();
			}
		}
		for (int i = 0; i < getJugadores().size(); i++) {
			if(getJugadores().get(i).getPuntosPorTiro3()==max) {
				return getJugadores().get(i);
			}
		}
		return null;
	}
	
	public Jugador getJugadorMasConsumo() {
		double max = 0;
		for (int i = 0; i < getJugadores().size(); i++) {
			if(getJugadores().get(i).getTirosPorMinuto()>max && getJugadores().get(i).getMinutosPorPartido()>10) {
				max=getJugadores().get(i).getTirosPorMinuto();
			}
		}
		for (int i = 0; i < getJugadores().size(); i++) {
			if(getJugadores().get(i).getTirosPorMinuto()==max) {
				return getJugadores().get(i);
			}
		}
		return null;
	}
	
	public Jugador getMaximoReboteador() {
		double max = 0;
		for (int i = 0; i < getJugadores().size(); i++) {
			if(getJugadores().get(i).getRebotesPorPartido()>max && getJugadores().get(i).getMinutosPorPartido()>10) {
				max=getJugadores().get(i).getRebotesPorPartido();
			}
		}
		for (int i = 0; i < getJugadores().size(); i++) {
			if(getJugadores().get(i).getRebotesPorPartido()==max) {
				return getJugadores().get(i);
			}
		}
		return null;
	}
	public Jugador getMaximoAsistente() {
		double max = 0;
		for (int i = 0; i < getJugadores().size(); i++) {
			if(getJugadores().get(i).getAsistenciasPorPartido()>max && getJugadores().get(i).getMinutosPorPartido()>10) {
				max=getJugadores().get(i).getAsistenciasPorPartido();
			}
		}
		for (int i = 0; i < getJugadores().size(); i++) {
			if(getJugadores().get(i).getAsistenciasPorPartido()==max) {
				return getJugadores().get(i);
			}
		}
		return null;
	}
}