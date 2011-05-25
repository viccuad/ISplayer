/**
 * 
 */
package is2011.principal;


import javax.swing.UIManager;

import de.javasoft.plaf.synthetica.*;

import is2011.app.controlador.AppController;
import is2011.app.controlador.IAppController;
import is2011.app.vista.VistaPreferencias;
import is2011.app.vista.VistaPrincipal;
import is2011.biblioteca.BibliotecaMusical;
import is2011.reproductor.controlador.ControladorReproductor;
import is2011.reproductor.modelo.ListaReproduccion;
import is2011.reproductor.modelo.ReproductorIS;
import is2011.reproductor.vista.VistaBiblioteca;
import is2011.reproductor.vista.VistaListaReproduccion;
import is2011.reproductor.vista.VistaReproduccion;

/**
 * @author Administrator
 *
 */
public class Principal {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {


		 /**
         * Muy importante para mostrarlo con el look and feel del sistema operativo!!
         */
        try
        {
        	// PARA LA LICENCIA DE Synthetica:
        	String[] li = {"Licensee=Victor Cuadrado Juan", "LicenseRegistrationNumber=NCVC110518", "Product=Synthetica", "LicenseType=Non Commercial", "ExpireDate=--.--.----", "MaxVersion=2.999.999"};
        	UIManager.put("Synthetica.license.info", li);
        	UIManager.put("Synthetica.license.key", "F8A52C36-F58CE8EF-8D455B99-C3456027-CF87F5BA");
        	
        	UIManager.setLookAndFeel(new SyntheticaSimple2DLookAndFeel());
	
        	
        }
        catch (Exception ignored) {}
    	finally {
    		
		VistaPrincipal vista = new VistaPrincipal();
		VistaReproduccion vr = new VistaReproduccion(vista);
		VistaBiblioteca vb = new VistaBiblioteca();
		VistaListaReproduccion vlr = new VistaListaReproduccion();
		VistaPreferencias vp = new VistaPreferencias();
		
		
		ControladorReproductor controlador = new ControladorReproductor();
		ReproductorIS rep = new ReproductorIS();
		rep.addBasicPlayerListener(vr);
		controlador.setReproductor(rep);
		
		ListaReproduccion lr = new ListaReproduccion();
		lr.addListaReproduccionListener(vlr);
		lr.addListaReproduccionListener(vr);
		controlador.setListaReproduccion(lr);
		
		BibliotecaMusical bib = BibliotecaMusical.getInstacia();
		bib.addBibliotecaListeners(vb);
		
		vista.setVistaReproductor(vr);
		vista.setVistaListaReproduccion(vlr);
		vista.setVistaBiblioteca(vb);
		vista.setVistaPreferencias(vp);
		vista.setVisible(true);
		
		IAppController appController = new AppController(controlador, bib);
		vista.setControlador(appController);
		vr.setControlador(appController);
		vlr.setControlador(appController);
		vb.setControlador(appController);
		vp.setControlador(appController);
		appController.cargarArchivosPreferencias();

	
	}

}}
