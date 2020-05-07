package es.iesvjp.proyecto.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * Clase que gestiona la persistencia de la tabla jugador
 * 
 * @author Carlos Cobos
 *
 */
@Entity
@Table(name = "jugador")
@NamedQuery(name="Jugador.findAll", query="SELECT j FROM Jugador j")
public class Jugador implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String url;

	private String nombre;
	
	
	private String urlFoto;
	
	private String puesto;
	
	private int altura;

	//bi-directional many-to-one association to Lineapartido
	@OneToMany(mappedBy="jugador")
	private List<Lineapartido> lineapartidos;

	public Jugador() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Lineapartido> getLineapartidos() {
		return this.lineapartidos;
	}

	public void setLineapartidos(List<Lineapartido> lineapartidos) {
		this.lineapartidos = lineapartidos;
	}

	public Lineapartido addLineapartido(Lineapartido lineapartido) {
		getLineapartidos().add(lineapartido);
		lineapartido.setJugador(this);

		return lineapartido;
	}

	public Lineapartido removeLineapartido(Lineapartido lineapartido) {
		getLineapartidos().remove(lineapartido);
		lineapartido.setJugador(null);

		return lineapartido;
	}
	@Column(name = "urlFoto")
	public String getUrlFoto() {
		return urlFoto;
	}

	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}

	public String getPuesto() {
		return puesto;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	public int getAltura() {
		return altura;
	}

	public void setAltura(int altura) {
		this.altura = altura;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * Método que devuelve el número de minutos del jugador
	 * @return
	 */
	private double getMinutos() {
		double minutos=0;
		for (int i = 0; i < lineapartidos.size(); i++) {
			minutos+=lineapartidos.get(i).getMinutos().toLocalTime().getMinute()*60;
			minutos+=lineapartidos.get(i).getMinutos().toLocalTime().getSecond();
		}
		return minutos/60;
	}
	/**
	 * Método que devuelve el número de minutos por partido del jugador del jugador
	 * @return
	 */
	public double getMinutosPorPartido() {
		return getMinutos()/(double)lineapartidos.size();
	}
	/**
	 * Método que devuelve el número de puntos del jugador
	 * @return
	 */
	private double getPuntos() {
		double puntos=0;
		for (int i = 0; i < lineapartidos.size(); i++) {
			puntos+=lineapartidos.get(i).getPuntos();
		}
		return puntos;
	}
	/**
	 * Método que devuelve el número de puntos por partido del jugador
	 * @return
	 */
	public double getPuntosPorPartido() {
		return getPuntos()/(double)lineapartidos.size();
	}
	/**
	 * Método que devuelve el número de puntos por minuto del jugador
	 * @return
	 */
	public double getPuntosPorMinuto() {
		return getPuntos()/getMinutos()*40;
	}
	/**
	 * Método que devuelve el número de puntos por tiro de 2 del jugador
	 * @return
	 */
	public double getPuntosPorTiro2() {
		double tiros=0, puntos=0;
		for (int i = 0; i < lineapartidos.size(); i++) {
			tiros+=lineapartidos.get(i).getT2i();
			puntos+=lineapartidos.get(i).getT2a()*2;
		}
		return puntos/tiros;
	}
	/**
	 * Método que devuelve el número de puntos por tiro de 3 del jugador
	 * @return
	 */
	public double getPuntosPorTiro3() {
		double tiros=0, puntos=0;
		for (int i = 0; i < lineapartidos.size(); i++) {
			tiros+=lineapartidos.get(i).getT3i();
			puntos+=lineapartidos.get(i).getT3a()*3;
		}
		return puntos/tiros;
	}
	/**
	 * Método que devuelve el número de tiros de campo del jugador
	 * @return
	 */
	private double getTirosCampo() {
		double tiros=0;
		for (int i = 0; i < lineapartidos.size(); i++) {
			tiros+=lineapartidos.get(i).getT3i();
			tiros+=lineapartidos.get(i).getT2i();
		}
		return tiros;
	}
	/**
	 * Método que devuelve el número de puntos por tiro de campo del jugador
	 * @return
	 */
	public double getPuntosPorTiro() {
		double tiros=0, puntos=0;
		for (int i = 0; i < lineapartidos.size(); i++) {
			tiros+=lineapartidos.get(i).getT2i();
			tiros+=lineapartidos.get(i).getT3i();
			puntos+=lineapartidos.get(i).getT3a()*3;
			puntos+=lineapartidos.get(i).getT2a()*2;
		}
		return puntos/tiros;
	}
	/**
	 * Método que devuelve el número de rebotes ofensivos del jugador
	 * @return
	 */
	private double getRebOf() {
		double rebotes=0;
		for (int i = 0; i < lineapartidos.size(); i++) {
			rebotes+=lineapartidos.get(i).getRbo();			
		}
		return rebotes;
	}
	/**
	 * Método que devuelve el número de rebotes defensivos del jugador
	 * @return
	 */
	private double getRebDef() {
		double rebotes=0;
		for (int i = 0; i < lineapartidos.size(); i++) {
			rebotes+=lineapartidos.get(i).getRbd();			
		}
		return rebotes;
	}
	/**
	 * Método que devuelve el número de rebotes del jugador
	 * @return
	 */
	private double getRebotes() {
		double rebotes=0;
		for (int i = 0; i < lineapartidos.size(); i++) {
			rebotes+=lineapartidos.get(i).getRbd();	
			rebotes+=lineapartidos.get(i).getRbo();	
		}
		return rebotes;
	}
	/**
	 * Método que devuelve el número de rebotes por 40 minutos del jugador
	 * @return
	 */
	public double getRebotesPorMinuto() {
		return getRebotes()/getMinutos()*40;
	}
	/**
	 * Método que devuelve el número de rebotes ofensivos por 40 minutos del jugador
	 * @return
	 */
	public double getRebotesOfPorMinuto() {
		return getRebOf()/getMinutos()*40;
	}
	/**
	 * Método que devuelve el número de rebotes defensivos por 40 minutos del jugador
	 * @return
	 */
	public double getRebotesDefPorMinuto() {
		return getRebDef()/getMinutos()*40;
	}
	/**
	 * Método que devuelve el número de tiros de campo por 40 minutos del jugador
	 * @return
	 */
	public double getTirosPorMinuto() {
		return getTirosCampo()/getMinutos()*40;
	}
	/**
	 * Método que devuelve el número de tiros de 2 por 40 minutos del jugador
	 * @return
	 */
	public double getTiros2PorMinuto() {
		return getTiros2()/getMinutos()*40;
	}
	/**
	 * Método que devuelve el número de tiros de 2 del jugador
	 * @return
	 */
	private double getTiros2() {
		double tiros=0;
		for (int i = 0; i < lineapartidos.size(); i++) {
			tiros+=lineapartidos.get(i).getT2i();
		}
		return tiros;
	}
	/**
	 * Método que devuelve el número de tiros de 3 por 40 minutos del jugador
	 * @return
	 */
	public double getTiros3PorMinuto() {
		return getTiros3()/getMinutos()*40;
	}
	/**
	 * Método que devuelve el número de tiros de 3 del jugador
	 * @return
	 */
	private double getTiros3() {
		double tiros=0;
		for (int i = 0; i < lineapartidos.size(); i++) {
			tiros+=lineapartidos.get(i).getT3i();
		}
		return tiros;
	}
	/**
	 * Método que devuelve el número de rebotes ofensivos por partido del jugador
	 * @return
	 */
	public double getRebOfPorPartido() {
		return getRebOf()/lineapartidos.size();
	}
	/**
	 * Método que devuelve el número de rebotes defensivos por partido del jugador
	 * @return
	 */
	public double getRebDefPorPartido() {
		return getRebDef()/lineapartidos.size();
	}
	/**
	 * Método que devuelve el número de rebotes por partido del jugador
	 * @return
	 */
	public double getRebotesPorPartido() {
		return getRebotes()/lineapartidos.size();
	}
	/**
	 * Método que devuelve el porcentaje en tiros de 2 del jugador
	 * @return
	 */
	public double getPctgT2() {
		double t2a=0, t2i=0;
		for (int i = 0; i < lineapartidos.size(); i++) {
			t2a+=lineapartidos.get(i).getT2a();
			t2i+=lineapartidos.get(i).getT2i();
		}
		return t2a/t2i*100;
	}
	/**
	 * Método que devuelve el porcentaje en tiros de 3 del jugador
	 * @return
	 */
	public double getPctgT3() {
		double t3a=0, t3i=0;
		for (int i = 0; i < lineapartidos.size(); i++) {
			t3a+=lineapartidos.get(i).getT3a();
			t3i+=lineapartidos.get(i).getT3i();
		}
		return t3a/t3i*100;
	}
	/**
	 * Método que devuelve el porcentaje en tiros libres del jugador
	 * @return
	 */
	public double getPctgTl() {
		double tla=0, tli=0;
		for (int i = 0; i < lineapartidos.size(); i++) {
			tla+=lineapartidos.get(i).getTla();
			tli+=lineapartidos.get(i).getTli();
		}
		System.out.println(tla + " / "+ tli + " * 100");
		return tla/tli*100;
	}
	/**
	 * Método que devuelve el número de asistencias por partido del jugador
	 * @return
	 */
	public double getAsistenciasPorPartido() {
		double asis=0;
		for (int i = 0; i < lineapartidos.size(); i++) {
			asis+=lineapartidos.get(i).getAsist();
		}
		return asis/lineapartidos.size();
	}
	/**
	 * Método que devuelve el número de faltas recibidas por 40 minutos del jugador
	 * @return
	 */
	public double getFaltasRecibidasPorMinuto() {
		double faltas=0;
		for (int i = 0; i < lineapartidos.size(); i++) {
			faltas+=lineapartidos.get(i).getFr();
		}
		return faltas/getMinutos()*40;
	}
	/**
	 * Método que devuelve el número de faltas recibidas por partido del jugador
	 * @return
	 */
	public double getFaltasRecibidasPorPartido() {
		double faltas=0;
		for (int i = 0; i < lineapartidos.size(); i++) {
			faltas+=lineapartidos.get(i).getFr();
		}
		return faltas/lineapartidos.size();
	}
	/**
	 * Método que devuelve el porcentaje efectivo en tiros de campo del jugador
	 * @return
	 */
	public double getPctgEfTC() {
		double t2a=0, t3a=0, t3i=0, t2i=0;
		for (int i = 0; i < lineapartidos.size(); i++) {
			t2a+=lineapartidos.get(i).getT2a();
			t2i+=lineapartidos.get(i).getT2i();
			t3a+=lineapartidos.get(i).getT3a();
			t3i+=lineapartidos.get(i).getT3i();
		}
		return (t2a+t3a*1.5)/(t2i+t3i)*100;
	}
	/**
	 * Método que devuelve el nombre del equipo del jugador
	 * @return
	 */
	public String getNombreEquipo() {
		return lineapartidos.size()>0?lineapartidos.get(0).getEquipo().getNombre():"";
	}
}