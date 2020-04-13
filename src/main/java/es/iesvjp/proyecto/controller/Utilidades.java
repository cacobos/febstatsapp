package es.iesvjp.proyecto.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;

import org.springframework.util.ResourceUtils;

import es.iesvjp.proyecto.model.Equipo;

public class Utilidades {
	private static void ordenarEquiposPorRitmo(List<Equipo> equipos) {

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
	}

	public static File cargarGrafico() throws FileNotFoundException {
		return ResourceUtils.getFile("classpath:static/grafico.png");
	}
}
