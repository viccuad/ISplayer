package Test;

import is2011.biblioteca.BibliotecaMusical;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.JFileChooser;


public class testActualizarBiblioteca {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ArrayList<String> dir = new ArrayList<String>();		
		String ruta = "";
		
		//creamos un directorio a indexar
		//dir.add("/Users/david/Desktop/pruebaIS/nivel2");
		//dir.add("src/Recursos");
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setMultiSelectionEnabled(true);
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		int seleccion;
		seleccion =fileChooser.showOpenDialog(null);
		if (seleccion == JFileChooser.APPROVE_OPTION){			
			File[] files = fileChooser.getSelectedFiles();
			for (File f : files) {		
				ruta = f.getAbsolutePath();
				dir.add(ruta);
				System.out.println(f.getAbsolutePath());
			}			
		}
		
		
		
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
			//dir.add("src/Recursos/canciones");	
			int seleccion2;
			seleccion2 =fileChooser.showOpenDialog(null);
			if (seleccion2 == JFileChooser.APPROVE_OPTION){			
				File[] files = fileChooser.getSelectedFiles();
				for (File f : files) {		
					ruta = f.getAbsolutePath();
					dir.add(ruta);
					System.out.println(f.getAbsolutePath());
				}			
			}
			
			
			
			bib.actualizarDirectorios(dir);
			
			//genera el fichero XML
			//bib.guardarXML("/Users/david/Desktop/pruebaIS/lista.xml");
			bib.guardarXML("src/Recursos/testXML2.xml");
		
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
