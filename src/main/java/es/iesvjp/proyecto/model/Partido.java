package es.iesvjp.proyecto.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Clase que gestiona la persistencia de la tabla partido
 * 
 * @author Carlos Cobos
 *
 */
@Entity
@Table(name = "partido")
@NamedQuery(name = "Partido.findAll", query = "SELECT p FROM Partido p")
public class Partido implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private int asLocal;

	private int asVisit;

	private int bpLocal;

	private int bpVisit;

	private int brLocal;

	private int brVisit;

	private String competicion;

	private String fase;

	private int fcLocal;

	private int fcVisit;

	@Temporal(TemporalType.DATE)
	private Date fecha;

	private int frLocal;

	private int frVisit;

	private String jornada;

	private int prorroga;

	private int ptoLocal;

	private int ptoVisit;

	private int punt1qLocal;

	private int punt1qVisit;

	private int punt2qLocal;

	private int punt2qVisit;

	private int punt3qLocal;

	private int punt3qVisit;

	private int punt4qLocal;

	private int punt4qVisit;

	private int rbdLocal;

	private int rbdVisit;

	private int rboLocal;

	private int rboVisit;

	private int t2aLocal;

	private int t2aVisit;

	private int t2iLocal;

	private int t2iVisit;

	private int t3aLocal;

	private int t3aVisit;

	private int t3iLocal;

	private int t3iVisit;

	private String temporada;

	private int tlaLocal;

	private int tlaVisit;

	private int tliLocal;

	private int tliVisit;

	private String url;

	private int valLocal;

	private int valVisit;

	// bi-directional many-to-one association to Lineapartido
	@OneToMany(mappedBy = "partido")
	private List<Lineapartido> lineapartidos;

	// bi-directional many-to-one association to Equipo
	@ManyToOne
	@JoinColumn(name = "equipovisitante")
	private Equipo equipo1;

	// bi-directional many-to-one association to Equipo
	@ManyToOne
	@JoinColumn(name = "fk_equipolocal")
	private Equipo equipo2;

	public Partido() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getAsLocal() {
		return this.asLocal;
	}

	public void setAsLocal(int asLocal) {
		this.asLocal = asLocal;
	}

	public int getAsVisit() {
		return this.asVisit;
	}

	public void setAsVisit(int asVisit) {
		this.asVisit = asVisit;
	}

	public int getBpLocal() {
		return this.bpLocal;
	}

	public void setBpLocal(int bpLocal) {
		this.bpLocal = bpLocal;
	}

	public int getBpVisit() {
		return this.bpVisit;
	}

	public void setBpVisit(int bpVisit) {
		this.bpVisit = bpVisit;
	}

	public int getBrLocal() {
		return this.brLocal;
	}

	public void setBrLocal(int brLocal) {
		this.brLocal = brLocal;
	}

	public int getBrVisit() {
		return this.brVisit;
	}

	public void setBrVisit(int brVisit) {
		this.brVisit = brVisit;
	}

	public String getCompeticion() {
		return this.competicion;
	}

	public void setCompeticion(String competicion) {
		this.competicion = competicion;
	}

	public String getFase() {
		return this.fase;
	}

	public void setFase(String fase) {
		this.fase = fase;
	}

	public int getFcLocal() {
		return this.fcLocal;
	}

	public void setFcLocal(int fcLocal) {
		this.fcLocal = fcLocal;
	}

	public int getFcVisit() {
		return this.fcVisit;
	}

	public void setFcVisit(int fcVisit) {
		this.fcVisit = fcVisit;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public int getFrLocal() {
		return this.frLocal;
	}

	public void setFrLocal(int frLocal) {
		this.frLocal = frLocal;
	}

	public int getFrVisit() {
		return this.frVisit;
	}

	public void setFrVisit(int frVisit) {
		this.frVisit = frVisit;
	}

	public String getJornada() {
		return this.jornada;
	}

	public void setJornada(String jornada) {
		this.jornada = jornada;
	}

	public int getProrroga() {
		return this.prorroga;
	}

	public void setProrroga(int prorroga) {
		this.prorroga = prorroga;
	}

	public int getPtoLocal() {
		return this.ptoLocal;
	}

	public void setPtoLocal(int ptoLocal) {
		this.ptoLocal = ptoLocal;
	}

	public int getPtoVisit() {
		return this.ptoVisit;
	}

	public void setPtoVisit(int ptoVisit) {
		this.ptoVisit = ptoVisit;
	}

	public int getPunt1qLocal() {
		return this.punt1qLocal;
	}

	public void setPunt1qLocal(int punt1qLocal) {
		this.punt1qLocal = punt1qLocal;
	}

	public int getPunt1qVisit() {
		return this.punt1qVisit;
	}

	public void setPunt1qVisit(int punt1qVisit) {
		this.punt1qVisit = punt1qVisit;
	}

	public int getPunt2qLocal() {
		return this.punt2qLocal;
	}

	public void setPunt2qLocal(int punt2qLocal) {
		this.punt2qLocal = punt2qLocal;
	}

	public int getPunt2qVisit() {
		return this.punt2qVisit;
	}

	public void setPunt2qVisit(int punt2qVisit) {
		this.punt2qVisit = punt2qVisit;
	}

	public int getPunt3qLocal() {
		return this.punt3qLocal;
	}

	public void setPunt3qLocal(int punt3qLocal) {
		this.punt3qLocal = punt3qLocal;
	}

	public int getPunt3qVisit() {
		return this.punt3qVisit;
	}

	public void setPunt3qVisit(int punt3qVisit) {
		this.punt3qVisit = punt3qVisit;
	}

	public int getPunt4qLocal() {
		return this.punt4qLocal;
	}

	public void setPunt4qLocal(int punt4qLocal) {
		this.punt4qLocal = punt4qLocal;
	}

	public int getPunt4qVisit() {
		return this.punt4qVisit;
	}

	public void setPunt4qVisit(int punt4qVisit) {
		this.punt4qVisit = punt4qVisit;
	}

	public int getRbdLocal() {
		return this.rbdLocal;
	}

	public void setRbdLocal(int rbdLocal) {
		this.rbdLocal = rbdLocal;
	}

	public int getRbdVisit() {
		return this.rbdVisit;
	}

	public void setRbdVisit(int rbdVisit) {
		this.rbdVisit = rbdVisit;
	}

	public int getRboLocal() {
		return this.rboLocal;
	}

	public void setRboLocal(int rboLocal) {
		this.rboLocal = rboLocal;
	}

	public int getRboVisit() {
		return this.rboVisit;
	}

	public void setRboVisit(int rboVisit) {
		this.rboVisit = rboVisit;
	}

	public int getT2aLocal() {
		return this.t2aLocal;
	}

	public void setT2aLocal(int t2aLocal) {
		this.t2aLocal = t2aLocal;
	}

	public int getT2aVisit() {
		return this.t2aVisit;
	}

	public void setT2aVisit(int t2aVisit) {
		this.t2aVisit = t2aVisit;
	}

	public int getT2iLocal() {
		return this.t2iLocal;
	}

	public void setT2iLocal(int t2iLocal) {
		this.t2iLocal = t2iLocal;
	}

	public int getT2iVisit() {
		return this.t2iVisit;
	}

	public void setT2iVisit(int t2iVisit) {
		this.t2iVisit = t2iVisit;
	}

	public int getT3aLocal() {
		return this.t3aLocal;
	}

	public void setT3aLocal(int t3aLocal) {
		this.t3aLocal = t3aLocal;
	}

	public int getT3aVisit() {
		return this.t3aVisit;
	}

	public void setT3aVisit(int t3aVisit) {
		this.t3aVisit = t3aVisit;
	}

	public int getT3iLocal() {
		return this.t3iLocal;
	}

	public void setT3iLocal(int t3iLocal) {
		this.t3iLocal = t3iLocal;
	}

	public int getT3iVisit() {
		return this.t3iVisit;
	}

	public void setT3iVisit(int t3iVisit) {
		this.t3iVisit = t3iVisit;
	}

	public String getTemporada() {
		return this.temporada;
	}

	public void setTemporada(String temporada) {
		this.temporada = temporada;
	}

	public int getTlaLocal() {
		return this.tlaLocal;
	}

	public void setTlaLocal(int tlaLocal) {
		this.tlaLocal = tlaLocal;
	}

	public int getTlaVisit() {
		return this.tlaVisit;
	}

	public void setTlaVisit(int tlaVisit) {
		this.tlaVisit = tlaVisit;
	}

	public int getTliLocal() {
		return this.tliLocal;
	}

	public void setTliLocal(int tliLocal) {
		this.tliLocal = tliLocal;
	}

	public int getTliVisit() {
		return this.tliVisit;
	}

	public void setTliVisit(int tliVisit) {
		this.tliVisit = tliVisit;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getValLocal() {
		return this.valLocal;
	}

	public void setValLocal(int valLocal) {
		this.valLocal = valLocal;
	}

	public int getValVisit() {
		return this.valVisit;
	}

	public void setValVisit(int valVisit) {
		this.valVisit = valVisit;
	}

	public List<Lineapartido> getLineapartidos() {
		return this.lineapartidos;
	}

	public void setLineapartidos(List<Lineapartido> lineapartidos) {
		this.lineapartidos = lineapartidos;
	}

	public Lineapartido addLineapartido(Lineapartido lineapartido) {
		getLineapartidos().add(lineapartido);
		lineapartido.setPartido(this);

		return lineapartido;
	}

	public Lineapartido removeLineapartido(Lineapartido lineapartido) {
		getLineapartidos().remove(lineapartido);
		lineapartido.setPartido(null);

		return lineapartido;
	}

	public Equipo getEquipo1() {
		return this.equipo1;
	}

	public void setEquipo1(Equipo equipo1) {
		this.equipo1 = equipo1;
	}

	public Equipo getEquipo2() {
		return this.equipo2;
	}

	public void setEquipo2(Equipo equipo2) {
		this.equipo2 = equipo2;
	}

	public int getFgaLocal() {
		return t2iLocal + t3iLocal;
	}

	public int getFgaVisit() {
		return t2iVisit + t3iVisit;
	}

	public int getFgmLocal() {
		return t2aLocal + t3aLocal;
	}

	public int getFgmVisit() {
		return t2aVisit + t3aVisit;
	}

	/**
	 * Método que devuelve el número de posesiones del equipo local
	 * 
	 * @return
	 */
	public double getPosesionesLocal() {
		return (double) getFgaLocal() - (double) rboLocal + (double) bpLocal + ((double) tliLocal * 0.4);
	}

	/**
	 * Método que devuelve el número de posesiones del equipo visitante
	 * 
	 * @return
	 */
	public double getPosesionesVisit() {
		return (double) getFgaVisit() - (double) rboVisit + (double) bpVisit + ((double) tliVisit * 0.4);
	}

	/**
	 * Método que devuelve el ritmo del equipo local
	 * 
	 * @return
	 */
	public double getRitmoLocal() {
		return (double) getPosesionesLocal() / (double) getMinutos();
	}

	/**
	 * Método que devuelve el ritmo del equipo visitante
	 * 
	 * @return
	 */
	public double getRitmoVisit() {
		return (double) getPosesionesVisit() / (double) getMinutos();
	}

	/**
	 * Método que devuelve el número de minutos del partido
	 * 
	 * @return
	 */
	public int getMinutos() {
		if (prorroga == 4) {
			return 40;
		} else {
			return 40 + (prorroga - 4) * 5;
		}
	}

	/**
	 * Método que devuelve la eficiencia del equipo local
	 * 
	 * @return
	 */
	public double getEfficLocal() {
		return (double) ptoLocal / (double) getPosesionesLocal() * 100;
	}

	/**
	 * Método que devuelve la eficiencia del equipo visitante
	 * 
	 * @return
	 */
	public double getEfficVisit() {
		return (double) ptoVisit / (double) getPosesionesVisit() * 100;
	}

	/**
	 * Método que devuelve el porcentaje efectivo en tiros de campo del equipo local
	 * 
	 * @return
	 */
	public double pctgEffTcLocal() {
		return ((double) getFgmLocal() + 0.5 * (double) t3aLocal) / (double) getFgaLocal();
	}

	/**
	 * Método que devuelve el porcentaje efectivo en tiros de campo del equipo
	 * visitante
	 * 
	 * @return
	 */
	public double pctgEffTcVisit() {
		return ((double) getFgmVisit() + 0.5 * (double) t3aLocal) / (double) getFgaVisit();
	}

	/**
	 * Método que devuelve el porcentaje de rebotes ofensivos del equipo local
	 * 
	 * @return
	 */
	public double pctgROfLocal() {
		return ((double) rboLocal / ((double) rboLocal + (double) rbdVisit)) * 100;
	}

	/**
	 * Método que devuelve el porcentaje de rebotes ofensivos del equipo visitante
	 * 
	 * @return
	 */
	public double pctgROfVisit() {
		return ((double) rboVisit / ((double) rboVisit + (double) rbdLocal)) * 100;
	}

	/**
	 * Método que devuelve el porcentaje de rebotes defensivos del equipo local
	 * 
	 * @return
	 */
	public double pctgRDefLocal() {
		return ((double) rbdLocal / ((double) rbdLocal + (double) rboVisit)) * 100;
	}

	/**
	 * Método que devuelve el porcentaje de rebotes defensivos del equipo visitante
	 * 
	 * @return
	 */
	public double pctgRDefVisit() {
		return ((double) rbdVisit / ((double) rbdVisit + (double) rboLocal)) * 100;
	}

	/**
	 * Método que devuelve el porcentaje de asistencias defensivos del equipo local
	 * 
	 * @return
	 */
	public double pctgAssLocal() {
		return (double) asLocal / getPosesionesLocal() * 100;
	}

	/**
	 * Método que devuelve el porcentaje de rebotes defensivos del equipo visitante
	 * 
	 * @return
	 */
	public double pctgAssVisit() {
		return (double) asVisit / getPosesionesVisit() * 100;
	}

	/**
	 * Método que devuelve el porcentaje de pérdidas del equipo local
	 * 
	 * @return
	 */
	public double pctgPerLocal() {
		return (double) bpLocal / getPosesionesLocal() * 100;
	}

	/**
	 * Método que devuelve el porcentaje de pérdidas del equipo visitante
	 * 
	 * @return
	 */
	public double pctgPerVisit() {
		return (double) bpVisit / getPosesionesVisit() * 100;
	}

	/**
	 * Método que devuelve si gana el equipo local
	 * 
	 * @return
	 */
	public boolean ganaLocal() {
		return ptoLocal > ptoVisit;
	}

	/**
	 * Método que devuelve si gana el equipo visitante
	 * 
	 * @return
	 */
	public boolean ganaVisit() {
		return ptoLocal < ptoVisit;
	}

	/**
	 * Método que devuelve el nombre del máximo anotador del partido
	 * 
	 * @return
	 */
	public String getMaxAnotador() {
		int max = 0;
		String txt = "", sep = "";
		for (int i = 0; i < lineapartidos.size(); i++) {
			if (lineapartidos.get(i).getPuntos() > max) {
				max = lineapartidos.get(i).getPuntos();
			}
		}
		for (int i = 0; i < lineapartidos.size(); i++) {
			if (lineapartidos.get(i).getPuntos() == max) {
				txt += sep + lineapartidos.get(i).getJugador().getNombre().split(",")[0] + " ("
						+ lineapartidos.get(i).getPuntos() + ")";
				sep = ", ";
			}
		}
		return txt;
	}
	/**
	 * Método que devuelve el nombre del máximo reboteador del partido
	 * 
	 * @return
	 */
	public String getMaxReboteador() {
		int max = 0;
		String txt = "", sep = "";
		for (int i = 0; i < lineapartidos.size(); i++) {
			if (lineapartidos.get(i).getRebotes() > max) {
				max = lineapartidos.get(i).getRebotes();
			}
		}
		for (int i = 0; i < lineapartidos.size(); i++) {
			if (lineapartidos.get(i).getRebotes() == max) {
				txt += sep + lineapartidos.get(i).getJugador().getNombre().split(",")[0] + " ("
						+ lineapartidos.get(i).getRebotes() + ")";
				sep = ", ";
			}
		}
		return txt;
	}
	/**
	 * Método que devuelve el nombre del máximo asistente del partido
	 * 
	 * @return
	 */
	public String getMaxAsistente() {
		int max = 0;
		String txt = "", sep = "";
		for (int i = 0; i < lineapartidos.size(); i++) {
			if (lineapartidos.get(i).getAsist() > max) {
				max = lineapartidos.get(i).getAsist();
			}
		}
		for (int i = 0; i < lineapartidos.size(); i++) {
			if (lineapartidos.get(i).getAsist() == max) {
				txt += sep + lineapartidos.get(i).getJugador().getNombre().split(",")[0] + " ("
						+ lineapartidos.get(i).getAsist() + ")";
				sep = ", ";
			}
		}
		return txt;
	}
	/**
	 * Método que devuelve el nombre del más valorado del partido
	 * 
	 * @return
	 */
	public String getMaxVal() {
		int max = 0;
		String txt = "", sep = "";
		for (int i = 0; i < lineapartidos.size(); i++) {
			if (lineapartidos.get(i).getVal() > max) {
				max = lineapartidos.get(i).getVal();
			}
		}
		for (int i = 0; i < lineapartidos.size(); i++) {
			if (lineapartidos.get(i).getVal() == max) {
				txt += sep + lineapartidos.get(i).getJugador().getNombre().split(",")[0] + " ("
						+ lineapartidos.get(i).getVal() + ")";
				sep = ", ";
			}
		}
		return txt;
	}
	/**
	 * Método que devuelve el nombre del jugador con mejor +/-del partido
	 * 
	 * @return
	 */
	public String getMaxMasMenos() {
		int max = 0;
		String txt = "", sep = "";
		for (int i = 0; i < lineapartidos.size(); i++) {
			if (lineapartidos.get(i).getMasMenos() > max) {
				max = lineapartidos.get(i).getMasMenos();
			}
		}
		for (int i = 0; i < lineapartidos.size(); i++) {
			if (lineapartidos.get(i).getMasMenos() == max) {
				txt += sep + lineapartidos.get(i).getJugador().getNombre().split(",")[0] + " ("
						+ lineapartidos.get(i).getMasMenos() + ")";
				sep = ", ";
			}
		}
		return txt;
	}
	/**
	 * Método que devuelve la los puntos del equipo local menos los del visitante
	 * 
	 * @return
	 */
	public int getValorResultado() {
		return ptoLocal - ptoVisit;
	}
}