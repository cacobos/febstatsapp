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
	private ModelAndView inicio(@RequestParam(name="idEquipo", required = false) Integer idEquipo) {
		LOG.info("METHOD: inicio -- PARAMS: id: " + idEquipo );
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("equipos", equipoService.listAllEquipo());
		if(idEquipo==null) {
			idEquipo=0;
		}
		if(idEquipo!=0) {
			mav.addObject("jugadores", equipoService.getJugadoresEquipo(idEquipo));
		}else {
			
		}
		LOG.info("METHOD: showProductos -- PARAMS: " + mav.getModel());
		return mav;
	}
	/*
	@GetMapping(value = { "/detail" })
	private ModelAndView detail(@RequestParam(name = "id", required = true) Integer id, Model model) {
		LOG.info("METHOD: detail -- PARAMS: id: " + id +" "+  model);
		Producto producto=productoService.findProductoById(id);
		ModelAndView mav=new ModelAndView("detail.html");
		
		mav.addObject("producto", producto);
		return mav;
	}
	*/
}