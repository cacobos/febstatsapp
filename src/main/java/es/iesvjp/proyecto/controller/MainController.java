package es.iesvjp.proyecto.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

	@GetMapping(value = { "", "/", "/index" })
	private ModelAndView inicioGet(@RequestParam(name = "idEquipo", required = false) Integer idEquipo,
			@RequestParam(name = "idJugador", required = false) Integer idJugador) {
		LOG.info("METHOD: inicioGet -- PARAMS: idEquipo: " + idEquipo+" idJugador: " + idJugador );
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("equipos", equipoService.listAllEquipo());
		if (idEquipo == null) {
			idEquipo = 0;
		}
		if (idEquipo != 0) {
			mav.addObject("equipoModel", equipoService.findEquipoById(idEquipo));
			mav.addObject("jugadores", equipoService.getJugadoresEquipo(idEquipo));
		} else {

		}
		if (idJugador == null) {
			idJugador = 0;
		}
		if (idJugador != 0) {
			Jugador j = jugadorService.findJugadorById(idJugador);
			mav.addObject("jugadorModel", j);
		}
		
		LOG.info("METHOD: inicioGet -- PARAMS: " + mav.getModel());
		return mav;
	}
	/*
	 * @GetMapping(value = { "/detail" }) private ModelAndView
	 * detail(@RequestParam(name = "id", required = true) Integer id, Model model) {
	 * LOG.info("METHOD: detail -- PARAMS: id: " + id +" "+ model); Producto
	 * producto=productoService.findProductoById(id); ModelAndView mav=new
	 * ModelAndView("detail.html");
	 * 
	 * mav.addObject("producto", producto); return mav; }
	 */
}