/**
 * 
 */
package is2011.principal;


import javax.swing.UIManager;

import de.javasoft.plaf.synthetica.*;

import is2011.app.controlador.AppController;
import is2011.app.controlador.IAppController;
import is2011.app.preferencias.Preferencias;
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
        	
        	//UIManager.setLookAndFeel(new SyntheticaSimple2DLookAndFeel());
        	String look = Preferencias.getInstance().getNombreLook();
			if (look.equals("SyntheticaSimple2DLookAndFeel")) {
				UIManager.setLookAndFeel(new SyntheticaSimple2DLookAndFeel());
			}else if(look.equals("SyntheticaBlackEyeLookAndFeel")) {
				UIManager.setLookAndFeel(new SyntheticaBlackEyeLookAndFeel());
			}else if(look.equals("SyntheticaBlackMoonLookAndFeel")) {
				UIManager.setLookAndFeel(new SyntheticaBlackMoonLookAndFeel());
			}else if(look.equals("SyntheticaBlackStarLookAndFeel")) {
				UIManager.setLookAndFeel(new SyntheticaBlackStarLookAndFeel());
			}else if(look.equals("SyntheticaBlueIceLookAndFeel")) {
				UIManager.setLookAndFeel(new SyntheticaBlueIceLookAndFeel());
			}else if(look.equals("SyntheticaBlueMoonLookAndFeel")) {
				UIManager.setLookAndFeel(new SyntheticaBlueMoonLookAndFeel());
			}else if(look.equals("SyntheticaClassyLookAndFeel")) {
				UIManager.setLookAndFeel(new SyntheticaClassyLookAndFeel());
			}else if(look.equals("SyntheticaGreenDreamLookAndFeel")) {
				UIManager.setLookAndFeel(new SyntheticaGreenDreamLookAndFeel());
			}else if(look.equals("SyntheticaMauveMetallicLookAndFeel")) {
				UIManager.setLookAndFeel(new SyntheticaMauveMetallicLookAndFeel());
			}else if(look.equals("SyntheticaOrangeMetallicLookAndFeel")) {
				UIManager.setLookAndFeel(new SyntheticaOrangeMetallicLookAndFeel());
			}else if(look.equals("SyntheticaSilverMoonLookAndFeel")) {
				UIManager.setLookAndFeel(new SyntheticaSilverMoonLookAndFeel());
			}else if(look.equals("SyntheticaSkyMetallicLookAndFeel")) {
				UIManager.setLookAndFeel(new SyntheticaSkyMetallicLookAndFeel());
			}else if (look.equals("SyntheticaWhiteVisionLookAndFeel")) {
				UIManager.setLookAndFeel(new SyntheticaWhiteVisionLookAndFeel());	
			}else {
				System.out.println("Esto no deberia pasar!!");
			}

        	
        }
        catch (Exception ignored) {}
    	finally {
    		
		VistaPrincipal vista = new VistaPrincipal();
		VistaReproduccion vr = new VistaReproduccion(vista);
		VistaBiblioteca vb = new VistaBiblioteca();
		VistaListaReproduccion vlr = new VistaListaReproduccion();
		//VistaPreferencias vp = new VistaPreferencias();
		
		
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
		//vista.setVistaPreferencias(vp);
		vista.setVisible(true);
		
		IAppController appController = new AppController(controlador, bib);
		vista.setControlador(appController);
		vr.setControlador(appController);
		vlr.setControlador(appController);
		vb.setControlador(appController);
		//vp.setControlador(appController);
		appController.cargarArchivosPreferencias();

	
    	}
	}

    
}
