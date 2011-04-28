package Test;

import is2011.biblioteca.contenedores.BibliotecaContainer;
import is2011.biblioteca.contenedores.CancionContainer;
import is2011.biblioteca.contenedores.DirectorioContainer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;


public class testLecturaXML {

	public static void main(String[] args) {
		BibliotecaContainer bib;
		
		XStream stream = new XStream(new DomDriver());
		stream.alias("biblioteca", BibliotecaContainer.class);
		stream.alias("cancion", CancionContainer.class);
		stream.alias("dir", DirectorioContainer.class);
		
		// IMPORTANTE para que no aparezca en el fichero XML
		stream.omitField(BibliotecaContainer.class, "modificado");
		stream.omitField(CancionContainer.class, "totalPath");
		
		
		try {
			//Leemos del fihero XML y lo cargamos en la estructura de biblioteca
			bib = (BibliotecaContainer) stream.fromXML(new FileInputStream("src/Recursos/testXML1.xml"));
			
			//Mostramos por patalla el objeto biblioteca reconvertido a XML
			System.out.println(stream.toXML(bib));
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}
}
