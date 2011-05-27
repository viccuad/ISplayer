package Test;
/*
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFileChooser;

import is2011.app.preferencias.Preferencias;
import is2011.biblioteca.BibliotecaMusical;
import is2011.biblioteca.contenedores.CancionContainer;
import is2011.reproductor.modelo.ListaReproduccion.ModoReproduccionEnum;*/

public class TestPreferenciasSistema {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

		Preferencias preferencias = Preferencias.getInstance();
		
		
			//genera el fichero XML
			preferencias.guardarXML();
			//bib.guardarXML( "src/Recursos/testXML2.xml");
			//bib.guardarXML("/Users/david/Desktop/pruebaIS/lista.xml");
			
			// carga el fichero XML a memoria
			//preferencias.cargarXML();
			//bib.cargarXML("/Users/david/Desktop/pruebaIS/lista.xml");
			
			
			System.out.println(preferencias.getPathBiblioteca());
			//System.out.println(preferencias.getPathListaReproduccion());
			System.out.println(preferencias.getPathPreferenciasSistema());
			System.out.println(preferencias.getVolumen());
			System.out.println(preferencias.isAbrirUltimaLista());
	}

}