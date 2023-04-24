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
	public Collection<Ronda> leerRondas(Collection<Partido> partidos, String pathPronostico) {
		Collection<Ronda> rondas = new ArrayList<Ronda>();
		// "C:\\Trabajo Practico\\Pronosticos.csv"
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

			Equipo equipo1 = new Equipo(campos[0].trim());
			Equipo equipo2 = new Equipo(campos[4].trim());
			Participante participante = new Participante(campos[5].trim());

			Ronda ronda = null;
			// Busco si ya tengo la ronda almacenada de la lista
			for (Ronda r : rondas) {
				// Verifico que el nombre de la ronda coincida con el archivo.

				if (r.getNombreRonda().equals(campos[6].trim())) {
					ronda = r;
					System.out.println(r.getNombreRonda());
				}
			}
			// Si la ronda esta en null, no se encontro la ronda, y creo una nueva
			if (ronda == null) {
				ronda = new Ronda(campos[6].trim());
				rondas.add(ronda);

			}

			Partido partido = null;

			for (Partido partidoCol : partidos) {
				if (partidoCol.getEquipo1().getNombre().trim().equals(equipo1.getNombre().trim())
						&& partidoCol.getEquipo2().getNombre().trim().equals(equipo2.getNombre().trim())) {
					partido = partidoCol;
				}
			}

			Apuesta resultado;
			if ("x".equals(campos[1])) {
				resultado = Apuesta.GANADOR;
			} else if ("x".equals(campos[2])) {
				resultado = Apuesta.EMPATE;
			} else {
				resultado = Apuesta.PERDEDOR;
			}

			Pronostico pronostico = new Pronostico(equipo1, partido, resultado, participante);
			ronda.getPronosticos().add(pronostico);
		}
		return rondas;

	}

	public void ejecutar(String pathResultado, String pathPronostico) {

		Collection<Partido> partidos = leerPartido(pathResultado);
		Collection<Ronda> rondas = leerRondas(partidos, pathPronostico);

		Collection<Resultado> resultados = calcularResultados(rondas);

		imprimirResultado(resultados);

	}

	private void imprimirResultado(Collection<Resultado> resultados) {

		for (Resultado resultado : resultados) {

			System.out.println(resultado.getParticipante().getNombre());
			System.out.println("Puntos: " + resultado.getPuntos());
			System.out.println("se veran reflejados los puntos extra si los tiene : " + resultado.getAciertos());

		}

	}

	/**
	 * El metodo Calcular Resultados Devuelve una coleccion de Resultados y recibe
	 * por parametro una coleccion de Pronosticos.
	 * 
	 * @param rondas
	 * @return
	 */
	private Collection<Resultado> calcularResultados(Collection<Ronda> rondas) {

		Collection<Resultado> resultados = new ArrayList<Resultado>();

		for (Ronda ronda : rondas) {

			for (Pronostico pronostico : ronda.getPronosticos()) {
				Participante participante = pronostico.getParticipante();

				Resultado resultadoParticipante = null;

				for (Resultado resultado : resultados) {
					if (participante.getNombre().equals(resultado.getParticipante().getNombre())) {
						resultadoParticipante = resultado;
						resultadoParticipante.setPuntos(resultadoParticipante.getPuntos() + pronostico.puntos());
						if (pronostico.puntos() != 0) {
							resultadoParticipante.setAciertos(resultadoParticipante.getAciertos() + 1);

							if (resultadoParticipante.getPuntos() >= 6) {
								resultadoParticipante.setAciertos(resultadoParticipante.getAciertos() + 2);

							}

						}
					}

				}

				if (resultadoParticipante == null) {
					resultadoParticipante = new Resultado();
					resultadoParticipante.setParticipante(participante);
					resultadoParticipante.setPuntos(pronostico.puntos());
					// Voy sumando los aciertos ..
					if (pronostico.puntos() != 0) {
						resultadoParticipante.setAciertos(1);
					}

					resultados.add(resultadoParticipante);

				}

			}
		}
		return resultados;

	}
}
