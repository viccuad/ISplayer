package Test;

import is2011.biblioteca.BibliotecaMusical;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.JFileChooser;


public class TestActualizarBiblioteca {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ArrayList<String> dir = new ArrayList<String>();
		//creamos un directorio a indexar
		//dir.add("/Users/david/Desktop/pruebaIS/nivel2");
		dir.add("src/Recursos");
		
		/*JFileChooser fileChooser = new JFileChooser();
		fileChooser.setMultiSelectionEnabled(true);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		
		int seleccion;
		seleccion =fileChooser.showOpenDialog(null);
		if (seleccion == JFileChooser.APPROVE_OPTION){
			//System.out.println("Funciona");
			File[] files = fileChooser.getSelectedFiles();
			for (File f : files) {				
				
			}
			dir.add("src/Recursos");
			
		}*/
		
		
		
		BibliotecaMusical bib = BibliotecaMusical.getInstacia();
		
		bib.actualizarDirectorios(dir);
		
		try {
			//genera el fichero XML del nivel inferior
			//bib.guardarXML("/Users/david/Desktop/pruebaIS/listaNivel2.xml");
			bib.guardarXML("src/Recursos/testXML2.xml");
			
			// carga el fichero XML a memoria
			//bib.cargarXML("/Users/david/Desktop/pruebaIS/listaNivel2.xml");
			bib.cargarXML("src/Recursos/testXML2.xml");
			
			//dir.add("/Users/david/Desktop/pruebaIS");
			dir.add("src/Recursos/canciones");			
			
			bib.actualizarDirectorios(dir);
			
			//genera el fichero XML
			//bib.guardarXML("/Users/david/Desktop/pruebaIS/lista.xml");
			bib.guardarXML("src/Recursos/testXML2.xml");
		
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
