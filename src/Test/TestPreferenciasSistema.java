package Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFileChooser;

import is2011.app.preferencias.PreferenciasSistema;
import is2011.biblioteca.BibliotecaMusical;
import is2011.biblioteca.contenedores.CancionContainer;
import is2011.reproductor.modelo.ListaReproduccion.ModoReproduccionEnum;

public class TestPreferenciasSistema {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

		PreferenciasSistema preferencias = new PreferenciasSistema("/Useres/carlos/Desktop", "/Useres/carlos/Desktop", ModoReproduccionEnum.NORMAL);
		
		try {
			//genera el fichero XML
			preferencias.guardarXML("src/Recursos/testConf.xml");
			//bib.guardarXML( "src/Recursos/testXML2.xml");
			//bib.guardarXML("/Users/david/Desktop/pruebaIS/lista.xml");
			
			// carga el fichero XML a memoria
			preferencias.cargarXML("src/Recursos/testConf.xml");
			//bib.cargarXML("/Users/david/Desktop/pruebaIS/lista.xml");
			
		
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		

	}

}