package Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFileChooser;

import is2011.biblioteca.BibliotecaMusical;
import is2011.biblioteca.contenedores.CancionContainer;

public class TestBibliotecaMusical {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		String ruta = "";
		
		ArrayList<String> dir = new ArrayList<String>();
		//dir.add("src/Recursos");
		//dir.add("/Users/david/Desktop/pruebaIS");
		
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
			//genera el fichero XML
			bib.guardarXML("src/Recursos/testXML2.xml");
			//bib.guardarXML( "src/Recursos/testXML2.xml");
			//bib.guardarXML("/Users/david/Desktop/pruebaIS/lista.xml");
			
			// carga el fichero XML a memoria
			bib.cargarXML("src/Recursos/testXML2.xml");
			//bib.cargarXML("/Users/david/Desktop/pruebaIS/lista.xml");
			
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
