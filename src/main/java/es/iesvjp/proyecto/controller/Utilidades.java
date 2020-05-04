package es.iesvjp.proyecto.controller;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
import es.iesvjp.proyecto.model.Jugador;
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
			int sComp = x1.before(x2) ? 1 : -1;

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
	
	public static List<Jugador> ordenarJugadoresPorMinutos(List<Jugador> jugadores) {

		Collections.sort(jugadores, (o1, o2) -> {

			Double x1 = ((Jugador) o1).getMinutosPorPartido();
			Double x2 = ((Jugador) o2).getMinutosPorPartido();
			int sComp = x1.compareTo(x2);

			if (sComp != 0) {
				return -sComp;
			} else {
				Double s1 = ((Jugador) o1).getPuntosPorMinuto();
				Double s2 = ((Jugador) o2).getPuntosPorMinuto();
				return s1.compareTo(s2);
			}
		});
		return jugadores;
	}

	public static File cargarGrafico() throws FileNotFoundException {
		return ResourceUtils.getFile("classpath:static/grafico.png");
	}
	
	public static File cargarGrafico2() throws FileNotFoundException {
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
	
	public static void dibujarGraficoRBDResultado(Equipo e) {
		DefaultXYDataset dataset = new DefaultXYDataset();
		double[][] datos=new double[2][e.getPartidos().size()];
		for (int i = 0; i < datos[1].length; i++) {
			if(e.getPartidos().get(i).getEquipo1()==e) {
				datos[0][i]=(double)e.getPartidos().get(i).getRbdLocal()/((double)e.getPartidos().get(i).getRbdLocal()+(double)e.getPartidos().get(i).getRboVisit())*100;
						datos[1][i]=e.getPartidos().get(i).getPtoLocal()-e.getPartidos().get(i).getPtoVisit();
			}else {

				datos[0][i]=(double)e.getPartidos().get(i).getRbdVisit()/((double)e.getPartidos().get(i).getRbdVisit()+(double)e.getPartidos().get(i).getRboLocal())*100;
						datos[1][i]=e.getPartidos().get(i).getPtoVisit()-e.getPartidos().get(i).getPtoLocal();
			}
			
		}
		dataset.addSeries("firefox",
				datos);
		/*dataset.addSeries("ie", new double[][] { { 2007, 2008, 2009, 2010, 2011, 2012, 2013, 2014, 2015, 2016, 2017 },
				{ 67.7, 63.1, 60.2, 50.6, 41.1, 31.8, 27.6, 20.4, 17.3, 12.3, 8.1 } });
		dataset.addSeries("chrome", new double[][] { { 2009, 2010, 2011, 2012, 2013, 2014, 2015, 2016, 2017 },
				{ 0.2, 6.4, 14.6, 25.3, 30.1, 34.3, 43.2, 47.3, 58.4 } });

		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesPaint(0, Color.ORANGE);
		renderer.setSeriesPaint(1, Color.BLUE);
		renderer.setSeriesPaint(2, Color.GREEN);
		renderer.setSeriesStroke(0, new BasicStroke(2));
		renderer.setSeriesStroke(1, new BasicStroke(2));
		renderer.setSeriesStroke(2, new BasicStroke(2));*/

		JFreeChart grafico=ChartFactory.createScatterPlot("Relación % rebote defensivo - resultado", "% rbd", "Resultado", dataset);
		//JFreeChart chart = ChartFactory.createXYLineChart("Título del gráfico", "Year", "Quota", dataset);
		//chart.getXYPlot().getRangeAxis().setRange(0, 100);
		//((NumberAxis) chart.getXYPlot().getRangeAxis()).setNumberFormatOverride(new DecimalFormat("#'%'"));
		//chart.getXYPlot().setRenderer(renderer);

		//BufferedImage image = chart.createBufferedImage(600, 400);
		BufferedImage image = grafico.createBufferedImage(600, 400);
		try {
			ImageIO.write(image, "png", Utilidades.cargarGrafico());
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}

	// funcion y= ax+b
	public static double[] calcRectaRegresionYsobreX(double[] lasX, double[] lasY) {
		double[] retVal = new double[3];
		double mediaX = calcMedia(lasX);
		double mediaY = calcMedia(lasY);
		double varianzaX = (calcMediaDeLosCuadrados(lasX) - Math.pow(mediaX, 2));
		double varianzaY = (calcMediaDeLosCuadrados(lasY) - Math.pow(mediaY, 2));
		double covarianza = calcMediaDeLosProductos(lasX, lasY) - (mediaX * mediaY);
		double diviCovaX = covarianza / varianzaX;

		retVal[0] = diviCovaX;
// aqui devuelve la pendiente de la recta
		retVal[1] = -(diviCovaX * mediaX) + mediaY;
// aqui devuelve el parametro independiente
		if ((Math.sqrt(varianzaX) * Math.sqrt(varianzaY)) == 0) {
			retVal[2] = 1;
		} else {
			retVal[2] = covarianza / (Math.sqrt(varianzaX) * Math.sqrt(varianzaY)); // esto es la correlacion r
		}
		return retVal;
	}

	// funcion x= ay + b
	public static double[] calcRectaRegresionXsobreY(double[] lasX, double[] lasY) {
		double[] retVal = new double[3];
		double mediaX = calcMedia(lasX);
		double mediaY = calcMedia(lasY);
		double varianzaX = (calcMediaDeLosCuadrados(lasX) - Math.pow(mediaX, 2));
		double varianzaY = (calcMediaDeLosCuadrados(lasY) - Math.pow(mediaY, 2));
		double covarianza = calcMediaDeLosProductos(lasX, lasY) - (mediaX * mediaY);
		double diviCovaY = covarianza / varianzaY;

		retVal[0] = diviCovaY;
// aqui devuelve la pendiente de la recta
		retVal[1] = -(diviCovaY * mediaY) + mediaX;
// aqui devuelve el parametro independiente
		retVal[2] = covarianza / (Math.sqrt(varianzaX) * Math.sqrt(varianzaY)); // esta es la correlacion r
		return retVal;
	}

	public static double calcMedia(double[] valores) {
		double retVal = 0;
		for (int i = 0; i < valores.length; i++) {
			retVal += valores[i];
		}
		return retVal / valores.length;
	}

	public static double calcMediaDeLosCuadrados(double[] valores) {
		double retVal = 0;
		for (int i = 0; i < valores.length; i++) {
			retVal += Math.pow(valores[i], 2);
		}
		return retVal / valores.length;
	}

	public static double calcMediaDeLosProductos(double[] valores1, double[] valores2) {
		double retVal = 0;
		for (int i = 0; i < valores1.length; i++) {
			retVal += valores1[i] * valores2[i];
		}
		return retVal / valores1.length;
	}
	
	

	public static Jugador getJugadorMasMinutos(List<Equipo> equipos) {
		double max = 0;
		List<Jugador> jugadores=new ArrayList<>();
		for (int i = 0; i < equipos.size(); i++) {
			jugadores.add(equipos.get(i).getJugadorMasMinutos());
		}
		for (int i = 0; i < jugadores.size(); i++) {
			if(jugadores.get(i).getMinutosPorPartido()>max && jugadores.get(i).getMinutosPorPartido()>10) {
				max=jugadores.get(i).getMinutosPorPartido();
			}
		}
		for (int i = 0; i < jugadores.size(); i++) {
			if(jugadores.get(i).getMinutosPorPartido()==max) {
				return jugadores.get(i);
			}
		}
		return null;
	}
	
	public static Jugador getJugadorMaxPtosPartido(List<Equipo> equipos) {
		double max = 0;
		List<Jugador> jugadores=new ArrayList<>();
		for (int i = 0; i < equipos.size(); i++) {
			jugadores.add(equipos.get(i).getJugadorMaxPtosPartido());
		}
		for (int i = 0; i < jugadores.size(); i++) {
			if(jugadores.get(i).getPuntosPorPartido()>max && jugadores.get(i).getMinutosPorPartido()>10) {
				max=jugadores.get(i).getPuntosPorPartido();
			}
		}
		for (int i = 0; i < jugadores.size(); i++) {
			if(jugadores.get(i).getPuntosPorPartido()==max) {
				return jugadores.get(i);
			}
		}
		return null;
	}

	public static Jugador getMaximoAnotador(List<Equipo> equipos) {
		double max = 0;
		List<Jugador> jugadores=new ArrayList<>();
		for (int i = 0; i < equipos.size(); i++) {
			jugadores.add(equipos.get(i).getMaximoAnotador());
		}
		for (int i = 0; i < jugadores.size(); i++) {
			if(jugadores.get(i).getPuntosPorMinuto()>max && jugadores.get(i).getMinutosPorPartido()>10) {
				max=jugadores.get(i).getPuntosPorMinuto();
			}
		}
		for (int i = 0; i < jugadores.size(); i++) {
			if(jugadores.get(i).getPuntosPorMinuto()==max) {
				return jugadores.get(i);
			}
		}
		return null;
	}
	
	public static Jugador getJugadorMasEficiente(List<Equipo> equipos) {
		double max = 0;
		List<Jugador> jugadores=new ArrayList<>();
		for (int i = 0; i < equipos.size(); i++) {
			jugadores.add(equipos.get(i).getJugadorMasEficiente());
		}
		for (int i = 0; i < jugadores.size(); i++) {
			if(jugadores.get(i).getPuntosPorTiro()>max && jugadores.get(i).getMinutosPorPartido()>15) {
				max=jugadores.get(i).getPuntosPorTiro();
			}
		}
		for (int i = 0; i < jugadores.size(); i++) {
			if(jugadores.get(i).getPuntosPorTiro()==max && jugadores.get(i).getMinutosPorPartido()>15) {
				return jugadores.get(i);
			}
		}
		return null;
	}
	
	public static Jugador getJugadorMejorPctgEf(List<Equipo> equipos) {
		double max = 0;
		List<Jugador> jugadores=new ArrayList<>();
		for (int i = 0; i < equipos.size(); i++) {
			jugadores.add(equipos.get(i).getJugadorMejorPctgEf());
		}
		for (int i = 0; i < jugadores.size(); i++) {
			if(jugadores.get(i).getPctgEfTC()>max && jugadores.get(i).getMinutosPorPartido()>15) {
				max=jugadores.get(i).getPctgEfTC();
			}
		}
		for (int i = 0; i < jugadores.size(); i++) {
			if(jugadores.get(i).getPctgEfTC()==max && jugadores.get(i).getMinutosPorPartido()>15) {
				System.out.println("Minutos por partido " + jugadores.get(i).getMinutosPorPartido());
				return jugadores.get(i);
			}
		}
		return null;
	}
	
	
	public static Jugador getJugadorMasEficienteT2(List<Equipo> equipos) {
		double max = 0;
		List<Jugador> jugadores=new ArrayList<>();
		for (int i = 0; i < equipos.size(); i++) {
			jugadores.add(equipos.get(i).getJugadorMasEficienteT2());
		}
		for (int i = 0; i < jugadores.size(); i++) {
			if(jugadores.get(i).getPuntosPorTiro2()>max && jugadores.get(i).getMinutosPorPartido()>10) {
				max=jugadores.get(i).getPuntosPorTiro2();
			}
		}
		for (int i = 0; i < jugadores.size(); i++) {
			if(jugadores.get(i).getPuntosPorTiro2()==max) {
				return jugadores.get(i);
			}
		}
		return null;
	}
	
	public static Jugador getJugadorMasEficienteT3(List<Equipo> equipos) {
		double max = 0;
		List<Jugador> jugadores=new ArrayList<>();
		for (int i = 0; i < equipos.size(); i++) {
			jugadores.add(equipos.get(i).getJugadorMasEficienteT2());
		}
		for (int i = 0; i < jugadores.size(); i++) {
			if(jugadores.get(i).getPuntosPorTiro3()>max && jugadores.get(i).getMinutosPorPartido()>10) {
				max=jugadores.get(i).getPuntosPorTiro3();
			}
		}
		for (int i = 0; i < jugadores.size(); i++) {
			if(jugadores.get(i).getPuntosPorTiro3()==max) {
				return jugadores.get(i);
			}
		}
		return null;
	}
	
	public static Jugador getJugadorMasConsumo(List<Equipo> equipos) {
		double max = 0;
		List<Jugador> jugadores=new ArrayList<>();
		for (int i = 0; i < equipos.size(); i++) {
			jugadores.add(equipos.get(i).getJugadorMasConsumo());
		}
		for (int i = 0; i < jugadores.size(); i++) {
			if(jugadores.get(i).getTirosPorMinuto()>max && jugadores.get(i).getMinutosPorPartido()>10) {
				max=jugadores.get(i).getTirosPorMinuto();
			}
		}
		for (int i = 0; i < jugadores.size(); i++) {
			if(jugadores.get(i).getTirosPorMinuto()==max) {
				return jugadores.get(i);
			}
		}
		return null;
	}
	
	public static Jugador getMaximoReboteador(List<Equipo> equipos) {
		double max = 0;
		List<Jugador> jugadores=new ArrayList<>();
		for (int i = 0; i < equipos.size(); i++) {
			jugadores.add(equipos.get(i).getMaximoReboteador());
		}
		for (int i = 0; i < jugadores.size(); i++) {
			if(jugadores.get(i).getRebotesPorPartido()>max && jugadores.get(i).getMinutosPorPartido()>10) {
				max=jugadores.get(i).getRebotesPorPartido();
			}
		}
		for (int i = 0; i < jugadores.size(); i++) {
			if(jugadores.get(i).getRebotesPorPartido()==max) {
				return jugadores.get(i);
			}
		}
		return null;
	}
	public static Jugador getMaximoAsistente(List<Equipo> equipos) {
		double max = 0;
		List<Jugador> jugadores=new ArrayList<>();
		for (int i = 0; i < equipos.size(); i++) {
			jugadores.add(equipos.get(i).getMaximoAsistente());
		}
		for (int i = 0; i < jugadores.size(); i++) {
			if(jugadores.get(i).getAsistenciasPorPartido()>max && jugadores.get(i).getMinutosPorPartido()>10) {
				max=jugadores.get(i).getAsistenciasPorPartido();
			}
		}
		for (int i = 0; i < jugadores.size(); i++) {
			if(jugadores.get(i).getAsistenciasPorPartido()==max) {
				return jugadores.get(i);
			}
		}
		return null;
	}
	
	public static List<Jugador> getJugadores(List<Equipo> equipos){
		List<Jugador> jugadores=new ArrayList<>();
		for (int i = 0; i < equipos.size(); i++) {
			jugadores.addAll(equipos.get(i).getJugadores());
		}
		return jugadores;
	}
}
