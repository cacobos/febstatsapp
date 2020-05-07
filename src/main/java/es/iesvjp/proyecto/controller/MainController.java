package es.iesvjp.proyecto.controller;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.DefaultXYDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.iesvjp.proyecto.model.Equipo;
import es.iesvjp.proyecto.model.Jugador;
import es.iesvjp.proyecto.service.IEquipoService;
import es.iesvjp.proyecto.service.IJugadorService;

/**
 * Clase Controller que gestiona el controlador principal y que mostrará los
 * recursos de la página principal, la de búsqueda de jugador y la de
 * diccionario
 * 
 * @author Carlos Cobos
 *
 */
@Controller
@RequestMapping("/")
public class MainController {

	private static final Log LOG = LogFactory.getLog(MainController.class);

	@Autowired
	@Qualifier("equipoService")
	private IEquipoService equipoService;
	@Autowired
	@Qualifier("jugadorService")
	private IJugadorService jugadorService;

	/**
	 * Método que gestiona el controlador de la página principal
	 * 
	 * @param idEquipo
	 * @param idJugador
	 * @return
	 */
	@GetMapping(value = { "", "/", "/index" })
	private ModelAndView inicioGet() {
		LOG.info("METHOD: inicioGet ");
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("equipos", equipoService.listAllEquipo());
		mav.addObject("competiciones", equipoService.getCompeticiones());
		mav.addObject("equiposOro", equipoService.getEquiposCompeticion("LIGA LEB ORO"));
		mav.addObject("equiposPlata", equipoService.getEquiposCompeticion("LIGA LEB PLATA"));
		mav.addObject("equiposLF", equipoService.getEquiposCompeticion("LF ENDESA"));
		mav.addObject("searchJugador", new Jugador());
		LOG.info("METHOD: inicioGet -- PARAMS: " + mav.getModel());
		return mav;
	}

	/**
	 * Método que gestiona el controlador de la página dediccionario
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/diccionario")
	private ModelAndView diccionario() {
		LOG.info("METHOD: diccionario");
		ModelAndView mav = new ModelAndView("diccionario");
		mav.addObject("equipos", equipoService.listAllEquipo());
		mav.addObject("competiciones", equipoService.getCompeticiones());
		mav.addObject("equiposOro", equipoService.getEquiposCompeticion("LIGA LEB ORO"));
		mav.addObject("equiposPlata", equipoService.getEquiposCompeticion("LIGA LEB PLATA"));
		mav.addObject("equiposLF", equipoService.getEquiposCompeticion("LF ENDESA"));
		mav.addObject("searchJugador", new Jugador());

		LOG.info("METHOD: inicioGet -- PARAMS: " + mav.getModel());
		return mav;
	}

	/**
	 * Método que gestiona el controlador de la página de búsqueda de un jugador.
	 * Recibe el un jugador vacío cuyo nombre es el texto de búsqueda
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/buscarJugador")
	private ModelAndView buscarJugador(Model model, @ModelAttribute("searchJugador") Jugador searchJugador,
			BindingResult result) {
		LOG.info("METHOD: buscarJugador-- PARAMS: texto: " + searchJugador.getNombre());
		List<Jugador> jugadores = jugadorService.buscarJugador(searchJugador.getNombre().toUpperCase());
		ModelAndView mav = new ModelAndView("buscarJugador");
		/*
		 * while (jugadores.size()>20) { jugadores.remove(20); }
		 */
		mav.addObject("equipos", equipoService.listAllEquipo());
		mav.addObject("competiciones", equipoService.getCompeticiones());
		mav.addObject("equiposOro", equipoService.getEquiposCompeticion("LIGA LEB ORO"));
		mav.addObject("equiposPlata", equipoService.getEquiposCompeticion("LIGA LEB PLATA"));
		mav.addObject("equiposLF", equipoService.getEquiposCompeticion("LF ENDESA"));
		mav.addObject("jugadores", jugadores);
		mav.addObject("searchJugador", new Jugador());
		LOG.info("METHOD: inicioGet -- PARAMS: jugadores: " + searchJugador.getNombre());
		LOG.info("METHOD: inicioGet -- PARAMS: " + mav.getModel());
		return mav;
	}

	/**
	 * Método que gestiona el controlador de la página de selección de un jugador.
	 * Recibe el id del equipo y muestra sus jugadores
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(value = { "/selectplayer" })
	private ModelAndView selectJugador(@RequestParam(name = "id", required = true) Integer id) {
		LOG.info("METHOD: inicioGet -- PARAMS: idEquipo: " + id);
		ModelAndView mav = new ModelAndView("tablajugadores");
		mav.addObject("equipos", equipoService.listAllEquipo());
		mav.addObject("competiciones", equipoService.getCompeticiones());
		mav.addObject("equiposOro", equipoService.getEquiposCompeticion("LIGA LEB ORO"));
		mav.addObject("equiposPlata", equipoService.getEquiposCompeticion("LIGA LEB PLATA"));
		mav.addObject("equiposLF", equipoService.getEquiposCompeticion("LF ENDESA"));
		mav.addObject("equipo", equipoService.findEquipoById(id));
		mav.addObject("searchJugador", new Jugador());

		LOG.info("METHOD: inicioGet -- PARAMS: " + mav.getModel());
		return mav;
	}
}