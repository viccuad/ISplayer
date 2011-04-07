/**
 * 
 */
package is2011.principal;

import is2011.app.controlador.AppController;
import is2011.app.controlador.IAppController;
import is2011.app.vista.VistaPrincipal;

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
		
		IAppController controlador = new AppController();
		
	}

}
