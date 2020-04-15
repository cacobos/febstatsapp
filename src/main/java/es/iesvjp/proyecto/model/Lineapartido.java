package es.iesvjp.proyecto.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Time;

/**
 * The persistent class for the lineapartido database table.
 * 
 */
@Entity
@Table(name = "lineapartido")
@NamedQuery(name = "Lineapartido.findAll", query = "SELECT l FROM Lineapartido l")
public class Lineapartido implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private int asist;

	private int bp;

	private int br;

	private int fc;

	private int fr;

	private int masMenos;

	private Time minutos;

	private int puntos;

	private int rbd;

	private int rbo;

	private int t2a;

	private int t2i;

	private int t3a;

	private int t3i;

	private int tla;

	private int tli;

	private int val;

	// bi-directional many-to-one association to Equipo
	@ManyToOne
	@JoinColumn(name = "fk_equipo")
	private Equipo equipo;

	// bi-directional many-to-one association to Jugador
	@ManyToOne
	@JoinColumn(name = "fk_jugador")
	private Jugador jugador;

	// bi-directional many-to-one association to Partido
	@ManyToOne
	@JoinColumn(name = "fk_partido")
	private Partido partido;

	public Lineapartido() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getAsist() {
		return this.asist;
	}

	public void setAsist(int asist) {
		this.asist = asist;
	}

	public int getBp() {
		return this.bp;
	}

	public void setBp(int bp) {
		this.bp = bp;
	}

	public int getBr() {
		return this.br;
	}

	public void setBr(int br) {
		this.br = br;
	}

	public int getFc() {
		return this.fc;
	}

	public void setFc(int fc) {
		this.fc = fc;
	}

	public int getFr() {
		return this.fr;
	}

	public void setFr(int fr) {
		this.fr = fr;
	}

	public int getMasMenos() {
		return this.masMenos;
	}

	public void setMasMenos(int masMenos) {
		this.masMenos = masMenos;
	}

	public Time getMinutos() {
		return this.minutos;
	}

	public void setMinutos(Time minutos) {
		this.minutos = minutos;
	}

	public int getPuntos() {
		return this.puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public int getRbd() {
		return this.rbd;
	}

	public void setRbd(int rbd) {
		this.rbd = rbd;
	}

	public int getRbo() {
		return this.rbo;
	}

	public void setRbo(int rbo) {
		this.rbo = rbo;
	}

	public int getT2a() {
		return this.t2a;
	}

	public void setT2a(int t2a) {
		this.t2a = t2a;
	}

	public int getT2i() {
		return this.t2i;
	}

	public void setT2i(int t2i) {
		this.t2i = t2i;
	}

	public int getT3a() {
		return this.t3a;
	}

	public void setT3a(int t3a) {
		this.t3a = t3a;
	}

	public int getT3i() {
		return this.t3i;
	}

	public void setT3i(int t3i) {
		this.t3i = t3i;
	}

	public int getTla() {
		return this.tla;
	}

	public void setTla(int tla) {
		this.tla = tla;
	}

	public int getTli() {
		return this.tli;
	}

	public void setTli(int tli) {
		this.tli = tli;
	}

	public int getVal() {
		return this.val;
	}

	public void setVal(int val) {
		this.val = val;
	}

	public Equipo getEquipo() {
		return this.equipo;
	}

	public void setEquipo(Equipo equipo) {
		this.equipo = equipo;
	}

	public Jugador getJugador() {
		return this.jugador;
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}

	public Partido getPartido() {
		return this.partido;
	}

	public void setPartido(Partido partido) {
		this.partido = partido;
	}

	public int getRebotes() {
		return rbo + rbd;
	}

	public boolean isLocal() {
		return partido.getEquipo1() == equipo;
	}

	public boolean isGanador() {
		if (isLocal()) {
			return partido.ganaLocal();
		}
		return partido.ganaVisit();
	}
}