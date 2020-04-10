package es.iesvjp.proyecto.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the jugador database table.
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
	
	

}