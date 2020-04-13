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
		LOG.info("METHOD: inicioGet -- PARAMS: idEquipo: " + idEquipo + " idJugador: " + idJugador);
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("equipos", equipoService.listAllEquipo());
		mav.addObject("competiciones", equipoService.getCompeticiones());
		mav.addObject("equiposOro", equipoService.getEquiposCompeticion("LIGA LEB ORO"));
		mav.addObject("equiposPlata", equipoService.getEquiposCompeticion("LIGA LEB PLATA"));
		mav.addObject("equiposLF", equipoService.getEquiposCompeticion("LF ENDESA"));
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

	@GetMapping(value = "/plantilla")
	private ModelAndView plantillaGet(@RequestParam(name = "idEquipo", required = false) Integer idEquipo,
			@RequestParam(name = "idJugador", required = false) Integer idJugador) {
		LOG.info("METHOD: inicioGet -- PARAMS: idEquipo: " + idEquipo + " idJugador: " + idJugador);
		ModelAndView mav = new ModelAndView("blank");
		List<Equipo> equipos=equipoService.getEquiposCompeticion("LIGA LEB ORO");
		ordenarPorRitmo(equipos);
		mav.addObject("equipos", equipoService.listAllEquipo());
		mav.addObject("competiciones", equipoService.getCompeticiones());
		mav.addObject("equiposOro", equipos);
		mav.addObject("equiposPlata", equipoService.getEquiposCompeticion("LIGA LEB PLATA"));
		mav.addObject("equiposLF", equipoService.getEquiposCompeticion("LF ENDESA"));
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
		dibujarGrafico();
		LOG.info("METHOD: inicioGet -- PARAMS: " + mav.getModel());
		return mav;
	}

	private void dibujarGrafico() {
		DefaultXYDataset dataset = new DefaultXYDataset();
		dataset.addSeries("firefox",
				new double[][] { { 2007, 2008, 2009, 2010, 2011, 2012, 2013, 2014, 2015, 2016, 2017 },
						{ 25, 29.1, 32.1, 32.9, 31.9, 25.5, 20.1, 18.4, 15.3, 11.4, 9.5 } });
		dataset.addSeries("ie", new double[][] { { 2007, 2008, 2009, 2010, 2011, 2012, 2013, 2014, 2015, 2016, 2017 },
				{ 67.7, 63.1, 60.2, 50.6, 41.1, 31.8, 27.6, 20.4, 17.3, 12.3, 8.1 } });
		dataset.addSeries("chrome", new double[][] { { 2009, 2010, 2011, 2012, 2013, 2014, 2015, 2016, 2017 },
				{ 0.2, 6.4, 14.6, 25.3, 30.1, 34.3, 43.2, 47.3, 58.4 } });

		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesPaint(0, Color.ORANGE);
		renderer.setSeriesPaint(1, Color.BLUE);
		renderer.setSeriesPaint(2, Color.GREEN);
		renderer.setSeriesStroke(0, new BasicStroke(2));
		renderer.setSeriesStroke(1, new BasicStroke(2));
		renderer.setSeriesStroke(2, new BasicStroke(2));

		JFreeChart chart = ChartFactory.createXYLineChart("Título del gráfico", "Year", "Quota", dataset);
		chart.getXYPlot().getRangeAxis().setRange(0, 100);
		((NumberAxis) chart.getXYPlot().getRangeAxis()).setNumberFormatOverride(new DecimalFormat("#'%'"));
		chart.getXYPlot().setRenderer(renderer);

		BufferedImage image = chart.createBufferedImage(600, 400);
		
		try {
			ImageIO.write(image, "png", Utilidades.cargarGrafico());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

	
}