package Test;

import is2011.reproductor.modelo.Cancion;
import is2011.reproductor.modelo.CancionMP3;

import java.awt.FileDialog;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

public class testCancionMP3 {

	public static void main(String[] args){
		

        JFileChooser fileChooser = new JFileChooser();
        
        //Lo configuramos para que solo permita la apertura de ficheros
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(true);
        
        //Añadimos un filtro para permitir solo apertura de tipo plg
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
        		"mp3", "mp3");
        //FileNameExtensionFilter filter = new FileNameExtensionFilter("ogg", "ogg");
        fileChooser.setFileFilter(filter);

        
        //Abrimos el fichero
        int seleccion;

        seleccion =fileChooser.showOpenDialog(null);

        if (seleccion == JFileChooser.APPROVE_OPTION)
        {
            
			Cancion c = new CancionMP3(fileChooser.getSelectedFile().getAbsolutePath());
			System.out.println("Album "+c.getAlbum());
			System.out.println("Nombre "+c.getName());
			System.out.println("Pista "+c.getPista());
			System.out.println("Compositor "+c.getCompositor());
			c.getInfo();
        }
	}
}
