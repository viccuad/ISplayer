/**
 * 
 */
package is2011.principal;

import java.awt.event.MouseEvent;

import is2011.app.controlador.AppController;
import is2011.app.controlador.IAppController;
import is2011.app.vista.VistaPrincipal;
import is2011.reproductor.controlador.Controlador;
import is2011.reproductor.modelo.ReproductorIS;
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

		VistaPrincipal vista = new VistaPrincipal();
		VistaReproduccion vr = new VistaReproduccion();
		
		//IAppController controlador = new AppController();
		
		Controlador controlador = new Controlador();
		ReproductorIS rep = new ReproductorIS();
		rep.addBasicPlayerListener(vr);
		controlador.setReproductor(rep);
		
		vista.setVistaReproductor(vr);
		vista.setVisible(true);
	}

}
