package Test;

import java.awt.FileDialog;

import javax.swing.JFrame;

import is2011.reproductor.modelo.CancionOGG;

public class TestCancion {

	public static void main(String[] args){
		
		FileDialog fd = new FileDialog(new JFrame(), "Prueba", FileDialog.LOAD);
		fd.setVisible(true);
		String p = fd.getDirectory()+fd.getFile();
		CancionOGG c = new CancionOGG(p);
		System.out.println("Album "+c.getAlbum());
		System.out.println("Nombre "+c.getName());
		System.out.println("Pista "+c.getPista());
		System.out.println("Compositor "+c.getCompositor());
	}
}
