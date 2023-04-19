package PaqueteUTN;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Procesador {

	/**
	 * lee el resultado
	 * 
	 * @param path
	 * @return
	 */
	public Collection<Partido> leerPartido(String pathResultado) {
		
		
		Collection<Partido> partidos = new ArrayList<Partido>();
		// Leo archivos de Resltado
		// "C:\\Trabajo Practico\\Resultado.csv"
		Path pathResultados = Paths.get(pathResultado);
		List<String> lineasResultados = null;
		try {
			lineasResultados = Files.readAllLines(pathResultados);
		} catch (IOException e) {

			System.out.println("No se PUede leer Linea Resultados");
			System.exit(1);
		}

		for (String lineaResultado : lineasResultados) {
			String[] campos = lineaResultado.split(",");
			Equipo equipo1 = new Equipo(campos[0]);
			Equipo equipo2 = new Equipo(campos[3]);

			Partido partido = new Partido(equipo1, equipo2);

			try {
				partido.setGolesEq1(Integer.parseInt(campos[1]));
				partido.setGolesEq2(Integer.parseInt(campos[2]));
			} catch (NumberFormatException e) {
			 System.out.println("La Cantidad de goles no es un numero Entero");
			 System.exit(1);
			}
			partidos.add(partido);
			
		}
		return partidos;
	}

	/**
	 * Lee la coleccion de Pronosticos
	 * 
	 * @param partidos
	 * @param pathPronostico 
	 * @return
	 */
	public Collection<Pronostico> leerPronosticos(Collection<Partido> partidos, String pathPronostico) {
		Collection<Pronostico> pronosticos = new ArrayList<Pronostico>();
		//"C:\\Trabajo Practico\\Pronosticos.csv"
		Path pathPronosticos = Paths.get(pathPronostico);
		List<String> lineasPronosticos = null;
		try {
			lineasPronosticos = Files.readAllLines(pathPronosticos);
		} catch (IOException e) {

			System.out.println("No se PUede leer Pronosticos");

		}

		for (String lineaPronostico : lineasPronosticos) {

			if (lineaPronostico.startsWith("*"))
				continue;

			String[] campos = lineaPronostico.split(",");
			Equipo equipo1 = new Equipo(campos[0]);
			Equipo equipo2 = new Equipo(campos[4]);
			Participante participante = new Participante(campos[5]);
			Partido partido = null;

			for (Partido partidoCol : partidos) {
				if (partidoCol.getEquipo1().getNombre().trim().equals(equipo1.getNombre().trim())
						&& partidoCol.getEquipo2().getNombre().trim().equals(equipo2.getNombre().trim())) {

					partido = partidoCol;
				}

			}

			Equipo equipo = null;
			String resultado = null;

			if ("x".equals(campos[1])) {
				equipo = equipo1;
				resultado = "GANADOR";

			}
			if ("x".equals(campos[2])) {
				equipo = equipo1;
				resultado = "EMPATE";
			}

			if ("x".equals(campos[3])) {
				equipo = equipo1;
				resultado = "PERDEDOR";
			}

			Pronostico pronostico = new Pronostico(equipo, partido, resultado, participante);
			pronosticos.add(pronostico);

		}
		return pronosticos;

	}

	
	
	public void ejecutar(String pathResultado, String pathPronostico) {

		Collection<Partido> partidos = leerPartido(pathResultado);
		Collection<Pronostico> pronosticos = leerPronosticos(partidos, pathPronostico);

		Collection<Resultado> resultados = calcularResultados(pronosticos);
		
		imprimirResultado(resultados);

	}

	private void imprimirResultado(Collection<Resultado> resultados) {
		
		for (Resultado resultado : resultados){
			
			System.out.println(resultado.getParticipante().getNombre());
			System.out.println(resultado.getPuntos());
		}
		
		
		
	}

	/**
	 * El metodo Calcular Resultados Devuelve una coleccion de Resultados y recibe
	 * por parametro una coleccion de Pronosticos.
	 * 
	 * @param pronosticos
	 * @return
	 */
	private Collection<Resultado> calcularResultados(Collection<Pronostico> pronosticos) {

		Collection<Resultado> resultados = new ArrayList<Resultado>();

		for (Pronostico pronostico : pronosticos) {
			Participante participante = pronostico.getParticipante();
			pronostico.puntos();
			Resultado resultadoParticipante = null;

			for (Resultado resultado : resultados) {
				if (participante.getNombre().equals(resultado.getParticipante().getNombre())) {
					resultadoParticipante = resultado;
					resultadoParticipante.setPuntos(resultadoParticipante.getPuntos() + pronostico.puntos());
				}

			}
			if (resultadoParticipante == null) {
				resultadoParticipante = new Resultado();
				resultadoParticipante.setParticipante(participante);
				resultadoParticipante.setPuntos(pronostico.puntos());
				resultados.add(resultadoParticipante);

			}

			
		}
	
		return resultados;
	
	}
}
