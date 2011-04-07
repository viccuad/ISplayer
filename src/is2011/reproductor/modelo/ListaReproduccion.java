package is2011.reproductor.modelo;


import java.util.ArrayList;
import java.util.Iterator;

public class ListaReproduccion {
	
	ArrayList<Cancion> listaReproduccion=null;

	public ListaReproduccion(){
		listaReproduccion = new ArrayList<Cancion>();
	}
	
	public void addCancion(Cancion c){
		listaReproduccion.add(c);
	}
	public boolean isVacia(){
		return listaReproduccion.isEmpty();
	}
	
	public int getNumeroCanciones(){
		return listaReproduccion.size();
	}
	public void borrarCancion(int pos){
		listaReproduccion.remove(pos);
	}
	
	public Cancion getCancionAt(int pos){
		return listaReproduccion.get(pos);
		
	}
	
	public String getInfoCancionAt(int pos){
		
		return listaReproduccion.get(pos).getInfo();
	}
	public void listarTodas(){
		
		Iterator<Cancion> itr = listaReproduccion.iterator();
		System.out.println("LISTA DE REPRODUCCION");
		int i =0;
		while (itr.hasNext()){
			System.out.println("- CANCION EN POS "+i);
			System.out.println(itr.next().getInfo());
			System.out.println("");

			i++;
		}
	}
}
