package PaqueteUTN;

public class Pronostico {
	
	private Equipo equipo;
	private Partido partido;
	private Participante participante;
	private Apuesta apuesta;
	

	public Pronostico(Equipo equipo, Partido partido, Apuesta apuesta, Participante participante) {
		super();
		this.equipo = equipo;
		this.partido = partido;;
		this.participante = participante;
		this.apuesta = apuesta;
		
		
	}
	
	public Equipo getEquipo() {
		return equipo;
	}
	public Partido getPartido() {
		return partido;
	}
	
	public Participante getParticipante() {
		return participante;
	}
	public void setParticipante(Participante participante) {
		this.participante = participante;
	}
	public int puntos () {
		Apuesta resultadoReal = this.partido.resultado(this.equipo);
		if(this.getApuesta().equals(resultadoReal)) {
		return 1;
		}else {
			return 0;
		}
				
	}

	public Apuesta getApuesta() {
		return apuesta;
	}

	public void setApuesta(Apuesta apuesta) {
		this.apuesta = apuesta;
	}

}
