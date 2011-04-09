package Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;

import com.thoughtworks.xstream.XStream;

import is2011.biblioteca.contenedores.*;

public class TestEscrituraXML {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		XStream stream = new XStream();
		stream.alias("biblioteca", BibliotecaContainer.class);
		stream.alias("cancion", CancionContainer.class);
		stream.alias("dir", DirectorioContainer.class);
		
		// IMPORTANTE para que no aparezca en el fichero XML
		stream.omitField(BibliotecaContainer.class, "modificado");
		stream.omitField(CancionContainer.class, "totalPath");
		
		BibliotecaContainer bib = new BibliotecaContainer();
		new CancionContainer("AAAA","AAAA","AAAA","AAAA","AAAA",0);
		HashMap<String,CancionContainer> listaCanciones = new HashMap<String,CancionContainer>();
		for(int i = 0; i< 10; i++)
			listaCanciones.put(""+i, new CancionContainer("AAAA","AAAA","AAAA","AAAA","AAAA",0));
		
		//TODO bib.addDir(new DirectorioContainer("ruta", listaCanciones));
		
		try {
			stream.toXML(bib, new FileOutputStream("src/Recursos/testXML1.xml"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}

}
