package PaqueteUTN;

public class Resultado {
	
	private Participante participante;
	private int puntos;
	private int aciertos;
	
  private int cantPartidos;
   
	
	
	public int getCantPartidos() {
	return cantPartidos;
}
public void setCantPartidos(int cantPartidos) {
	this.cantPartidos = cantPartidos;
}
	public Participante getParticipante() {
		return participante;
	}
	public void setParticipante(Participante participante) {
		this.participante = participante;
	}
	public int getPuntos() {
		return puntos;
	}
	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}
	public int getAciertos() {
		return aciertos;
	}
	public void setAciertos(int aciertos) {
		this.aciertos = aciertos;
	}
	
	

}
