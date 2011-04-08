package Test;

import is2011.biblioteca.contenedores.BibliotecaContainer;
import is2011.biblioteca.contenedores.Cancion;
import is2011.biblioteca.contenedores.Directorio;

import java.util.HashMap;

import com.thoughtworks.xstream.XStream;

public class TestLecturaXML {

	public static void main(String[] args) {
		XStream stream = new XStream();
		stream.alias("biblioteca", BibliotecaContainer.class);
		stream.alias("cancion", Cancion.class);
		stream.alias("dir", Directorio.class);
		
		BibliotecaContainer bib = new BibliotecaContainer();
		
		new Cancion("AAAA","AAAA","AAAA","AAAA","AAAA",0);
		HashMap<String,Cancion> listaCanciones = new HashMap<String,Cancion>();
		for(int i = 0; i< 10; i++)
			listaCanciones.put(""+i, new Cancion("AAAA","AAAA","AAAA","AAAA","AAAA",0));
		
		new Directorio("ruta", listaCanciones);
		bib.addDir(new Directorio("ruta", listaCanciones));
		
		System.out.println(stream.toXML(bib));
		
		
	}
}
