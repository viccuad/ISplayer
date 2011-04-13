package Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;

import is2011.biblioteca.BibliotecaMusical;
import is2011.biblioteca.contenedores.CancionContainer;

public class TestBibliotecaMusical {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ArrayList<String> dir = new ArrayList<String>();
		dir.add("src/recursos");
		
		BibliotecaMusical bib = BibliotecaMusical.getInstacia();
		
		bib.actualizarDirectorios(dir);
		
		try {
			//genera el fichero XML
			bib.guardarXML("src/Recursos/testXML2.xml");
			
			// carga el fichero XML a memoria
			bib.cargarXML("src/Recursos/testXML2.xml");
			
			//recuperamos las canciones y las mostramos por consola
			Iterator<CancionContainer> it = bib.getCanciones().iterator();
			while(it.hasNext()){
				CancionContainer c = it.next();
				System.out.println("---------------------------------");
				System.out.println("Titulo: " + c.getTitulo());
				System.out.println("TrackPath: " + c.getTrackPath());
				System.out.println("TotalPath: " + c.getTotalPath());
				System.out.println("---------------------------------");
			}
			
		
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		

	}

}
