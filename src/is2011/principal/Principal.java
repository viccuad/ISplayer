/**
 * 
 */
package is2011.principal;



import javax.swing.UIManager;

import is2011.app.controlador.AppController;
import is2011.app.controlador.IAppController;
import is2011.app.vista.VistaPrincipal;
import is2011.reproductor.controlador.ControladorReproductor;
import is2011.reproductor.modelo.ListaReproduccion;
import is2011.reproductor.modelo.ReproductorIS;
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
		VistaListaReproduccion vlr = new VistaListaReproduccion();
		
		
		ControladorReproductor controlador = new ControladorReproductor();
		ReproductorIS rep = new ReproductorIS();
		rep.addBasicPlayerListener(vr);
		controlador.setReproductor(rep);
		
		ListaReproduccion lr = new ListaReproduccion();
		lr.addListaReproduccionListener(vlr);
		controlador.setListaReproduccion(lr);
		
		
		vista.setVistaReproductor(vr);
		vista.setVistaListaReproduccion(vlr);
		vista.setVisible(true);
		
		IAppController appController = new AppController(controlador);
		vista.setControlador(appController);
		vr.setControlador(appController);
		vlr.setControlador(appController);
    	}
	}

}
