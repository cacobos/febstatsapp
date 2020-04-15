package es.iesvjp.proyecto.controller;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.DefaultXYDataset;
import org.springframework.util.ResourceUtils;

import es.iesvjp.proyecto.model.Equipo;
import es.iesvjp.proyecto.model.Partido;

public class Utilidades {
	public static List<Equipo> ordenarEquiposPorRitmo(List<Equipo> equipos) {

		Collections.sort(equipos, (o1, o2) -> {

			Double x1 = ((Equipo) o1).getRitmo();
			Double x2 = ((Equipo) o2).getRitmo();
			int sComp = x1.compareTo(x2);

			if (sComp != 0) {
				return -sComp;
			} else {
				String s1 = ((Equipo) o1).getNombre();
				String s2 = ((Equipo) o2).getNombre();
				return s1.compareTo(s2);
			}
		});
		return equipos;
	}
	
	public static List<Partido> ordenarPartidosPorFecha(List<Partido> partidos) {

		Collections.sort(partidos, (o1, o2) -> {

			Date x1 = ((Partido) o1).getFecha();
			Date x2 = ((Partido) o2).getFecha();
			int sComp = x1.before(x2)?1:-1;

			if (sComp != 0) {
				return -sComp;
			} else {
				String s1 = ((Partido) o1).getJornada();
				String s2 = ((Partido) o2).getJornada();
				return s1.compareTo(s2);
			}
		});
		return partidos;
	}

	public static File cargarGrafico() throws FileNotFoundException {
		return ResourceUtils.getFile("classpath:static/grafico.png");
	}
	
	public static void dibujarGrafico() {
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
