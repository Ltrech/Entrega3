package PaqueteUTN;

public class Pronostico {
	
	private Equipo equipo;
	private Partido partido;
	private String resultado;
	private Participante participante;
	
	

	public Pronostico(Equipo equipo, Partido partido, String resultado, Participante participante) {
		super();
		this.equipo = equipo;
		this.partido = partido;
		this.resultado = resultado;
		this.participante = participante;
		
		
		
	}
	
	public Equipo getEquipo() {
		return equipo;
	}
	public Partido getPartido() {
		return partido;
	}
	public String getResultado() {
		return resultado;
	}
	
	public Participante getParticipante() {
		return participante;
	}
	public void setParticipante(Participante participante) {
		this.participante = participante;
	}
	public int puntos () {
		String resultadoReal = this.partido.resultado(this.equipo);
		if(this.resultado.equals(resultadoReal)) {
		return 1;
		}else {
			return 0;
		}
				
	}

}
