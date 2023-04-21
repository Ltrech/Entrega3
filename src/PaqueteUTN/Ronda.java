package PaqueteUTN;

import java.util.ArrayList;
import java.util.Collection;

public class Ronda {
	
	private String nombreRonda;
	private Collection <Pronostico> pronosticos= new ArrayList<>();
	
	
	
	public Ronda(String nombreRonda) {
		super();
		this.nombreRonda = nombreRonda;
	}
	public Ronda(String nombreRonda, Collection<Pronostico> pronosticos) {
		super();
		this.nombreRonda = nombreRonda;
		this.pronosticos = pronosticos;
	}
	public String getNombreRonda() {
		return nombreRonda;
	}
	public void setNombreRonda(String nombreRonda) {
		this.nombreRonda = nombreRonda;
	}
	public Collection<Pronostico> getPronosticos() {
		return pronosticos;
	}
	public void setPronosticos(Collection<Pronostico> pronosticos) {
		this.pronosticos = pronosticos;
	}
	
	
	

	
	
}
