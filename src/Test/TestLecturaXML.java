package Test;

import is2011.biblioteca.contenedores.BibliotecaContainer;
import is2011.biblioteca.contenedores.CancionContainer;
import is2011.biblioteca.contenedores.Directorio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.thoughtworks.xstream.XStream;

public class TestLecturaXML {

	public static void main(String[] args) {
		BibliotecaContainer bib;
		
		XStream stream = new XStream();
		stream.alias("biblioteca", BibliotecaContainer.class);
		stream.alias("cancion", CancionContainer.class);
		stream.alias("dir", Directorio.class);
		
		// IMPORTANTE para que no aparezca en el fichero XML
		stream.omitField(BibliotecaContainer.class, "modificado");
		
		
		try {
			//Leemos del fihero XML y lo cargamos en la estructura de biblioteca
			bib = (BibliotecaContainer) stream.fromXML(new FileInputStream("/Users/david/Documents/workspace/ISGrupo12/src/Recursos/testXML1.xml"));
			
			//Mostramos por patalla el objeto biblioteca reconvertido a XML
			System.out.println(stream.toXML(bib));
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}
}
