/**
 * 
 */
package is2011.app.preferencias;


/**
 * @author Administrator
 *
 */
public class Preferencias {


	Double volumen;
	
	/** Instancia única de la tabla de simbolos */
	private static Preferencias preferencias = null;
	
	
	/**
	 * Constructor privado para usar el patron Singleton
	 */
	private Preferencias(){
		//TODO, que las lea de un fichero.
		volumen = 1.0;
		
	}
	
	public static Preferencias getInstancia() {
		if(preferencias == null) {
			preferencias = new Preferencias();
		}
		
		return preferencias;
	}

	/**
	 * @return the volumen
	 */
	public float getVolumen() {
		return volumen.floatValue();
	}

	/**
	 * @param volumen the volumen to set
	 */
	public void setVolumen(Double volumen) {
		this.volumen = volumen;
	}
	
	
	
	
}
