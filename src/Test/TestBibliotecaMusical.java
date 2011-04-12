package Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import is2011.biblioteca.BibliotecaMusical;

public class TestBibliotecaMusical {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ArrayList<String> dir = new ArrayList<String>();
		dir.add("/Users/david/Desktop/pruebaIS");
		
		BibliotecaMusical bib = BibliotecaMusical.getInstacia();
		
		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		bib.actualizarDirectorios(dir);
		System.out.println("YYYYYYYYYYYYYYYYYYYYYYYYYYY");
		
		try {
			bib.guardarXML("/Users/david/Desktop/pruebaIS/lista.xml");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

}
