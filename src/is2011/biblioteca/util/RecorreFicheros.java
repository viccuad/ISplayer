package is2011.biblioteca.util;




import java.util.ArrayList;
import java.util.Iterator;



public class RecorreFicheros{

		private ArrayList<String> pendientesActualizar;
		private EstrategiaActualizacionBiblioteca estrategia;
		
	
		
		public RecorreFicheros(ArrayList<String> porActualizar) {
			this.pendientesActualizar = porActualizar;
		}
		
		
		public void recorre(){
			Iterator<String> it = this.pendientesActualizar.iterator();
			while(it.hasNext())
				this.estrategia.actualiza(it.next());
		}
		
		

		public void setEstrategia(EstrategiaActualizacionBiblioteca estrategia) {
			this.estrategia = estrategia;
		}
		
		
		
		/*
		
		private String directorioBase;
		
		private EstrategiaActualizacionBiblioteca estrategiaFichero;
		
		//Por defecto
		private String[] noAbrirFicheros = null;
		private String[] noAbrirDirectorios = null;
		
		private String[] tratarSoloFicheros = {"mp3", "ogg"};
		private String[] tratarSoloDirectorios = null ;
		
	
	
		public RecorreFicheros(String ruta) {
			this.directorioBase = ruta;
			this.estrategiaFichero = null;
			
		}
		
		
		public void setRestriccionesFicheros(String[] restriccionesFicheros) {
			this.noAbrirFicheros = restriccionesFicheros;
		}


		public void setRestriccionesDirectorios(String[] restriccionesDirectorios) {
			this.noAbrirDirectorios = restriccionesDirectorios;
		}



		
		public void recorre(){
			if (estrategiaFichero!= null) {
				File f = new File(directorioBase);
				directorioBase = f.getAbsolutePath();
				
				recorre("");
			}else {
				System.out.println("No has  asignado ningyuna estrategia todavia");
			}
			
		}
		
		public void setEstrategia(EstrategiaActualizacionBiblioteca estrategia) {
			this.estrategiaFichero = estrategia;
		}
		
		private void recorre(String subcarpeta){
			String dir = directorioBase + subcarpeta;
			
			File fichero = new File(dir);
			
			//Si es directorio
			if(fichero.isDirectory()  ){
		    	//Vemos si cumple las restricciones.
				
				if(cumpleRestriccionesDirectorios(fichero) || fichero.getAbsolutePath().equals(directorioBase)) {
					//TODO quitar!
					//estrategiaFichero.trataDirectorio(fichero);
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
		
		
		
		
		private boolean cumpleRestriccionesDirectorios(File fichero) {
			boolean cumpleRestricciones = true;
			if(this.noAbrirDirectorios != null) {
				for( String restriccion : this.noAbrirDirectorios) {
					cumpleRestricciones &= !fichero.getAbsolutePath().contains(restriccion);
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
					cumpleRestricciones |= fichero.getAbsolutePath().contains(restriccion);
				}
			}
			return cumpleRestricciones;
		}
		
		private boolean cumpleRestriccionesFicheros(File fichero) {
			boolean cumpleRestricciones = true;
			if(this.noAbrirFicheros != null) {
				for( String restriccion : this.noAbrirFicheros) {
					cumpleRestricciones &= !fichero.getName().contains(restriccion);
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
					cumpleRestricciones |= fichero.getName().contains(restriccion);
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
		
		 
		 */

		
}
