package Test;

import is2011.biblioteca.contenedores.CancionContainer;
import is2011.reproductor.modelo.ListaReproduccion;
import is2011.reproductor.modelo.ListaReproduccion.ModoReproduccionEnum;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class TestListaReproduccion {

	public static void main(String[] argv){
		
		System.out.println("Lista de reproduccion creada");

		ListaReproduccion lr = new ListaReproduccion();
		
		System.out.println("Añadiendo tres canciones");

		lr.addCancion(new CancionContainer(abrirArchivo()));
		lr.addCancion(new CancionContainer(abrirArchivo()));
		lr.addCancion(new CancionContainer(abrirArchivo()));
		
		System.out.println("Tres canciones añadidas");
		System.out.println("Mostrando todas");

		System.out.println(lr.toString());
		System.out.println("Mostrando la primera");
		
		System.out.println("Total canciones");
		System.out.println(lr.getNumeroCanciones());
		
		System.out.println("Cambiando modo");
		lr.setModoReproduccion(ModoReproduccionEnum.ALEATORIO);
		System.out.println("Modo Actual?");
		System.out.println(lr.getModoReproduccion());
		System.out.println("Borrando la primera");
		lr.removeCancion(0);
		System.out.println("Listar todas");
		System.out.println(lr.toString());		
		System.out.println("Vaciar entera");
		lr.reiniciar(true);
		System.out.println("Listar todas");
		System.out.println(lr.toString());




   
	}
	
	public static String abrirArchivo(){
	     	JFileChooser fileChooser = new JFileChooser();
	        
	        //Lo configuramos para que solo permita la apertura de ficheros
	        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	        fileChooser.setAcceptAllFileFilterUsed(true);
	        
	        //Añadimos un filtro para permitir solo apertura de tipo plg
	        FileNameExtensionFilter filter = new FileNameExtensionFilter(
	        		"mp3 & ogg", "mp3", "ogg");
	        //FileNameExtensionFilter filter = new FileNameExtensionFilter("ogg", "ogg");
	        fileChooser.setFileFilter(filter);

	        
	        //Abrimos el fichero
	        int seleccion;
	        seleccion =fileChooser.showOpenDialog(null);

	        if (seleccion == JFileChooser.APPROVE_OPTION){
	        	System.out.println(fileChooser.getSelectedFile().getAbsolutePath());
	        	return fileChooser.getSelectedFile().getAbsolutePath();
	        }
			else return "Error";
	}
}
