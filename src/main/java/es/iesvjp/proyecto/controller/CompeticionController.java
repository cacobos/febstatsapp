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

@Controller
@RequestMapping("/competicion")
public class CompeticionController {

	private static final Log LOG = LogFactory.getLog(CompeticionController.class);

	@Autowired
	@Qualifier("equipoService")
	private IEquipoService equipoService;
	@Autowired
	@Qualifier("jugadorService")
	private IJugadorService jugadorService;

	@GetMapping(value = { "", "/" })
	private ModelAndView inicioGet(@RequestParam(name = "id", required = true) String id) {
		LOG.info("METHOD: inicioGet -- PARAMS: idEquipo: " + id);
		ModelAndView mav = new ModelAndView("competicion");
		mav.addObject("equipos", equipoService.listAllEquipo());
		mav.addObject("competiciones", equipoService.getCompeticiones());
		mav.addObject("equiposOro", equipoService.getEquiposCompeticion("LIGA LEB ORO"));
		mav.addObject("equiposPlata", equipoService.getEquiposCompeticion("LIGA LEB PLATA"));
		mav.addObject("equiposLF", equipoService.getEquiposCompeticion("LF ENDESA"));
		mav.addObject("searchJugador", new Jugador());
		mav.addObject("competicion", id);
		List<Equipo> equiposLiga= equipoService.getEquiposCompeticion(id);
		mav.addObject("jugadorMasUtilizado",Utilidades.getJugadorMasMinutos(equiposLiga) );
		mav.addObject("masRentable",Utilidades.getMasRentable(equiposLiga) );
		mav.addObject("maximoReboteador",Utilidades.getMaximoReboteador(equiposLiga) );
		mav.addObject("maximoAsistente",Utilidades.getMaximoAsistente(equiposLiga) );
		mav.addObject("maximoProductor",Utilidades.getMaximoAnotador(equiposLiga) );
		mav.addObject("maximoEficiente",Utilidades.getJugadorMasEficienteT2(equiposLiga) );
		mav.addObject("maximoPctgEffTc",Utilidades.getJugadorMasEficienteT3(equiposLiga) );
		mav.addObject("maximoConsumo",Utilidades.getJugadorMasConsumo(equiposLiga) );
		mav.addObject("equiposLiga",equiposLiga);
		
		LOG.info("METHOD: inicioGet -- PARAMS: " + mav.getModel());
		return mav;
	}

}