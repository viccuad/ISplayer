package Test;

import is2011.biblioteca.BibliotecaMusical;

import java.io.FileNotFoundException;
import java.util.ArrayList;


public class TestActualizarBiblioteca {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ArrayList<String> dir = new ArrayList<String>();
		//creamos un directorio a indexar
		dir.add("/Users/david/Desktop/pruebaIS/nivel2");
		
		BibliotecaMusical bib = BibliotecaMusical.getInstacia();
		
		bib.actualizarDirectorios(dir);
		
		try {
			//genera el fichero XML del nivel inferior
			bib.guardarXML("/Users/david/Desktop/pruebaIS/listaNivel2.xml");
			
			// carga el fichero XML a memoria
			bib.cargarXML("/Users/david/Desktop/pruebaIS/listaNivel2.xml");
			
			dir.add("/Users/david/Desktop/pruebaIS");
			
			bib.actualizarDirectorios(dir);
			
			//genera el fichero XML
			bib.guardarXML("/Users/david/Desktop/pruebaIS/lista.xml");
		
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
