/**
 * 
 */
package is2011.principal;



import java.io.IOException;

import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoDataSourceException;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.media.protocol.DataSource;
import javax.swing.UIManager;

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
	 * @param args
	 */
	public static void main(String[] args) {

		 /**
         * Muy importante para mostrarlo con el look and feel del sistema operativo!!
         */
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception ignored) {}
    	finally {
    		
		VistaPrincipal vista = new VistaPrincipal();
		VistaReproduccion vr = new VistaReproduccion();
		VistaBiblioteca vb = new VistaBiblioteca();
		VistaListaReproduccion vlr = new VistaListaReproduccion();
		VistaPreferencias vp = new VistaPreferencias();
		
		
		ControladorReproductor controlador = new ControladorReproductor();
		ReproductorIS rep = new ReproductorIS();
		rep.addBasicPlayerListener(vr);
		rep.addBasicPlayerListener(vista);
		controlador.setReproductor(rep);
		
		ListaReproduccion lr = new ListaReproduccion();
		lr.addListaReproduccionListener(vlr);
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

}
