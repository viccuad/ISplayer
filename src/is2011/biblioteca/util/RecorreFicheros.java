package is2011.biblioteca.util;




import java.util.ArrayList;
import java.util.Iterator;



public class RecorreFicheros{

		/** Canciones y directorios pendientes para actualizar la biblioteca */
		private ArrayList<String> pendientesActualizar;
		
		/** Estrategia de actualización de la biblioteca */
		private EstrategiaActualizacionBiblioteca estrategia;
		
	
		/**
		 * 
		 * @param porActualizar
		 */
		public RecorreFicheros(ArrayList<String> porActualizar) {
			this.pendientesActualizar = porActualizar;
		}
		
		
		/**
		 * 
		 */
		public void recorre(){
			Iterator<String> it = this.pendientesActualizar.iterator();
			while(it.hasNext())
				this.estrategia.actualiza(it.next());
		}
		
		
		/**
		 * 
		 * @param estrategia
		 */
		public void setEstrategia(EstrategiaActualizacionBiblioteca estrategia) {
			this.estrategia = estrategia;
		}
		
}
