package cc_02_Carrera;

/**
 *@author s100278 
 * 
 * Este ejercicio consiste en escribir un programa concurrente en el que multiples threads com-
partan y modifiquen una variable de tipo int de forma que el resultado final de la variable una
vez que los threads terminan no sea el valor esperado. Seamos mas concretos. Tendremos dos
tipos de procesos, decrementadores e incrementadores que realizan N decrementos e incremen-
tos, respectivamente, sobre una misma variable ( n ) de tipo int inicializada a 0. El programa
concurrente pondra en marcha M procesos de cada tipo y una vez que todos los threads han
terminado imprimira el valor de la variable compartida.
El valor final de la variable deberia ser 0 ya que se habran producido M × N decrementos
( n-- ) y M × N incrementos ( n++ ), sin embargo, si dos operaciones (tanto de decremento como
de incremento) se realizan a la vez el resultado puede no ser el esperado (por ejemplo, dos
incrementos podrian terminar por no incrementar la variable en 2).
El alumno no deberia realizar la entrega hasta que no vea que el valor final de la variable
puede ser distinto de 0 (aunque esto no garantiza que haya una condicion de carrera).
 */

public class CC_02_Carrera implements Runnable {


	static int n = 0; //contador de incrementos y decrementos
	final int N = 15; //cantidad de veces que cada proceso va a incrementar/decrementar
	final static int M = 13; //cantidad de procesos incrementador/decrementador respectivamente
	int id;

	public CC_02_Carrera(int id){
		this.id=id;
	}

	public void run() {
		if(id==1){
			this.incrementar();
		}else{
			this.decrementar();
		}
	}
	public void decrementar(){
		for (int i=0; i<N;i++){
			n=n-1;
		}
	}

	public void incrementar(){
		for (int i=0; i<N;i++){
			n=n+1;	
		}
	}

	public static void main(String[] args) throws InterruptedException {


		Thread incre [] = new Thread [M]; //se crea un array para poder almacenar los incrementadores
		Thread decre [] = new Thread [M]; //se crea un array para poder almacenar los decrementadores
		for (int i=0; i<M; i++){  
			incre [i]= new Thread (new CC_02_Carrera(1)); //se llena el array de los incrementadores
			incre [i].start(); //se lanza cada uno de los threads creados desde el array.
			decre [i]= new Thread (new CC_02_Carrera(2)); //se llena el array de los decrementadores
			decre [i].start();
		}
		for (int i=0; i<M; i++){//se hace el join a cada uno de los procesos que hay en el array id
			incre [i].join();        //Se hace en un bucle a parte porque si se hace en el bucle de arriba
			decre [i].join();
		}
		System.out.println("El valor final de la variable n es " + n);

	}
}
