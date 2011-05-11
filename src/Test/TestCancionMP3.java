package Test;

import is2011.biblioteca.contenedores.CancionContainer;

import javax.swing.JFileChooser;

import javax.swing.filechooser.FileNameExtensionFilter;

public class TestCancionMP3 {

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
            
        	CancionContainer c = new CancionContainer(fileChooser.getSelectedFile().getAbsolutePath());
			System.out.println("Album "+c.getAlbum());
			System.out.println("Nombre "+c.getTitulo());
			System.out.println("Pista "+c.getPista());
			System.out.println("Compositor "+c.getArtista());
        }
	}
}
