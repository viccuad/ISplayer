package is2011.utilidades;
import is2011.utilidades.estrategias.EstrategiaFichero;


import java.io.File;




/**
 * Clase a la que se le pasa en el constructor un directorio, y recorre todos
 * sus directorios hijos u archivos recursivamente para llegar a todos los 
 * hijos (archivos).
 * 
 * Ejemplo de uso.
 * public static void main(String args[]) {
			RecorreFicheros recorre = new RecorreFicheros("c:\\");
			recorre.setEstrategia(new MuestraNombres());
			recorre.recorre();
		}
	Mostraria el nombre de todos los archivos del ordenador.
 * @author jmartin
 *
 */
public class RecorreFicheros {

		private String directorioBase;
		
		private EstrategiaFichero estrategiaFichero;
		
		//Por defecto
		private String[] noAbrirFicheros = null;
		private String[] noAbrirDirectorios = null;
		
		private String[] tratarSoloFicheros = {"mp3", "ogg", "pdf"};
		private String[] tratarSoloDirectorios = null ;
		
	
		
		/**
		 * Constructora.  No tiene estrategia definida. 
		 * 
		 * @param ruta La ruta raiz
		 */
		public RecorreFicheros(String ruta) {
			this.directorioBase = ruta;
			this.estrategiaFichero = null;
			
		}
		
		
		public void setRestriccionesFicheros(String[] restriccionesFicheros) {
			this.noAbrirFicheros = restriccionesFicheros;
		}


		public void setRestriccionesDirectorios
		(String[] restriccionesDirectorios) {
			this.noAbrirDirectorios = restriccionesDirectorios;
		}



		/**
		 * Recorre todo el arbol de directorios.
		 */
		public void recorre(){
			if (estrategiaFichero!= null) {
				File f = new File(directorioBase);
				directorioBase = f.getAbsolutePath();
				
				recorre("");
			}else {
				System.out.println("No has  asignado " +
						"ninguna estrategia todavia");
			}
			
		}
		
		public void setEstrategia(EstrategiaFichero estrategia) {
			this.estrategiaFichero = estrategia;
		}
		
		
		/**
		 *  recorre la subcarpeta y todos sus directorios hijos u archivos 
		 *  recursivamente para llegar a todos los hijos (archivos).
		 * 
		 * @param subcarpeta
		 */
		private void recorre(String subcarpeta){
			String dir = directorioBase + subcarpeta;
			
			File fichero = new File(dir);
			
			//Si es directorio
			if(fichero.isDirectory()  ){
		    	//Vemos si cumple las restricciones.
				
				if(cumpleRestriccionesDirectorios(fichero) || 
						fichero.getAbsolutePath().equals(directorioBase)) {
					String[] s = fichero.list();
					for( String nombre : s) {
						recorre(subcarpeta+"//"+nombre);
					}
				}
				
		    } else {
		    	if(cumpleRestriccionesFicheros(fichero)) {
					estrategiaFichero.trataFichero(fichero);
				}
		    	
		    }
		}
		
		
		
		/**
		 * 
		 * @param fichero
		 * 
		 * @return true si el fichero cumple las restricciones de no abrir 
		 * directorios y las propias de los ficheros, false e.o.c
		 */
		private boolean cumpleRestriccionesDirectorios(File fichero) {
			boolean cumpleRestricciones = true;
			if(this.noAbrirDirectorios != null) {
				for( String restriccion : this.noAbrirDirectorios) {
					cumpleRestricciones &= 
						!fichero.getAbsolutePath().contains(restriccion);
					if(!cumpleRestricciones) {
						return false;
					}
				}
			}
			
			if(this.tratarSoloDirectorios == null) {
				return cumpleRestricciones;
			}else {
				cumpleRestricciones = false;
				for( String restriccion : this.tratarSoloDirectorios) {
					cumpleRestricciones |=
						fichero.getAbsolutePath().contains(restriccion);
				}
			}
			return cumpleRestricciones;
		}
		
		/**
		 * 
		 * @param fichero
		 * 
		 * @return si el fichero cumple las restricciones de no abrir 
		 * ficheros y las propias de los ficheros, false e.o.c
		 */
		private boolean cumpleRestriccionesFicheros(File fichero) {
			boolean cumpleRestricciones = true;
			if(this.noAbrirFicheros != null) {
				for( String restriccion : this.noAbrirFicheros) {
					cumpleRestricciones &= 
						!fichero.getName().contains(restriccion);
					if(!cumpleRestricciones) {
						return false;
					}
				}
			}


			if(this.tratarSoloFicheros == null) {
				return cumpleRestricciones;
			}else {
				cumpleRestricciones = false;
				for( String restriccion : this.tratarSoloFicheros) {
					cumpleRestricciones |= 
						fichero.getName().contains(restriccion);
				}
			}
			return cumpleRestricciones;
		}
		
		
		
		public void setNoAbrirFicheros(String[] noAbrirFicheros) {
			this.noAbrirFicheros = noAbrirFicheros;
		}





		public void setNoAbrirDirectorios(String[] noAbrirDirectorios) {
			this.noAbrirDirectorios = noAbrirDirectorios;
		}





		public void setTratarSoloFicheros(String[] tratarSoloFicheros) {
			this.tratarSoloFicheros = tratarSoloFicheros;
		}





		public void setTratarSoloDirectorios(String[] tratarSoloDirectorios) {
			this.tratarSoloDirectorios = tratarSoloDirectorios;
		}


		
}
