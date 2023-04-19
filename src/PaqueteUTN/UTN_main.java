package PaqueteUTN;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class UTN_main {

	public static void main(String[] args) {
		
	String pathResultado = args[0];
	String pathPronostico = args[1];
		
	Procesador procesador= new Procesador();
	
	procesador.ejecutar(pathResultado, pathPronostico);

}

}

