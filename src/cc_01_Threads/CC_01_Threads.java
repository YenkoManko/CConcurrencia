package cc_01_Threads;

/**
 * @author s100278
 * 
 * 
 * Lo que se pide es un programa que arranque N Threads y termine cuando terminen los
 * N threads arrancados. Todos los threads deben imprimir una linea que los identifi-
 * que y los distinga, dormir durante T milisegundos y volver a imprimir una linea que
 * indique que ha terminado y lo distinga de los demas. El thread principal debe im-
 * primir una linea avisando de que han terminado todos los demás threads.
 * 
 */

public class CC_01_Threads implements Runnable {

	int id;
	public CC_01_Threads(int id) { 
		this.id=id;
	}

	public void run(){
		long T=3000;
		System.out.println("Soy el Thread " + id + " y empiezo ahora"); //Imprime que empieza el thread
		try {
			Thread.sleep(T);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Soy el Thread " + id + " y termino ya mismo"); 
	}

	public static void main (String args []) throws InterruptedException{ 
		System.out.println("Soy el Thread principal del main y empiezo ya"); 
		int N=8; 
		Thread t [] = new Thread [N]; 
		for (int i=0; i<N; i++){  
			t [i]= new Thread (new CC_01_Threads(i)); 
			t [i].start(); 
		}
		for (int i=0; i<N; i++){
			t[i].join();        
		}						
		System.out.println("Soy el Thread principal del main y termino ya "); 
		// calm our titties
	}
}
