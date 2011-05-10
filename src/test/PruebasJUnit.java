package test;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class PruebasJUnit extends TestCase{
	
	private static testCancionMP3JUnit testCancionMp3;
	private static testEscrituraXMLJUnit testEscrituraXml;
	private static JUnitBibliotecaMusical testBibliotecaMusical;
	private static JUnitBibliotecaContainer testBiliotecaContainer;
	private static TestDirectorioContainerJUnit testDirectorioContainer;
	
	public PruebasJUnit(){
		testCancionMp3 = new testCancionMP3JUnit("");
		testEscrituraXml = new testEscrituraXMLJUnit("");
		testBibliotecaMusical = new JUnitBibliotecaMusical("");
		testBiliotecaContainer = new JUnitBibliotecaContainer("");
		testDirectorioContainer = new TestDirectorioContainerJUnit("");
	}
	public static TestSuite suite(){
		
		TestSuite s = new TestSuite("Pruebas");
		s.addTest(testCancionMp3.suite());
		s.addTest(testEscrituraXml.suite());
		s.addTest(testBibliotecaMusical.suite());
		s.addTest(testBiliotecaContainer.suite());
		s.addTest(testDirectorioContainer.suite());
		return s;
		
	}
}