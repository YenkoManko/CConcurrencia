package cc_04_MutexSEM;


import es.upm.babel.cclib.Semaphore;

/**
 * 
 *@author yenko
 * 
 * Este ejercicio, al igual que el anterior, consiste en evitar una condicion de carrera. En es-
ta ocasion tenemos el mismo numero de procesos incrementadores que decrementadores que
incrementan y decrementan, respectivamente, en un mismo numero de pasos una variable com-
partida. El objetivo es asegurar la exclusion mutua en la ejecucion de los incrementos y decre-
mentos de la variable y el objetivo es hacerlo utilizando exclusivamente un sem ́aforo de la clase
es.upm.babel.cclib.Semaphore (esta prohibido utilizar cualquier otro mecanismo de concurrencia). 
La libreria de concurrencia cclib.jar puede descargarse de la pagina web de la asignatura.
 * */


class CC_04_MutexSem {
	private static int N_THREADS = 2;
	private static int N_PASOS = 1000000;
	static volatile Semaphore mutex = new Semaphore(1);

	static class Contador {

		private volatile int n;
		public Contador() {
			this.n = 0;
		}
		public int valorContador() {
			return this.n;
		}
		public void inc () {
			//mutex.await();
			this.n++;
			// mutex.signal();
		}
		public void dec () {
			// mutex.await();
			this.n--;
			// mutex.signal();
		}
	}	

	static class Incrementador extends Thread {
		private Contador cont;
		public Incrementador (Contador c) {
			this.cont = c;
		}
		public void run() {
			for (int i = 0; i < N_PASOS; i++) {

				//aqui es donde se pone el semaforo
				mutex.await();
				this.cont.inc();
				mutex.signal();
			}
		}
	}

	static class Decrementador extends Thread {
		private Contador cont;
		public Decrementador (Contador c) {
			this.cont = c;
		}
		public void run() {
			for (int i = 0; i < N_PASOS; i++) {

				mutex.await();
				this.cont.dec();
				mutex.signal();
			}
		}
	}

	public static void main(String args[])
	{
		// Creación del objeto compartido
		Contador cont = new Contador();

		// Creación de los arrays que contendrán los threads
		Incrementador[] tInc = new Incrementador[N_THREADS];
		Decrementador[] tDec = new Decrementador[N_THREADS];

		// Creacion de los objetos threads
		for (int i = 0; i < N_THREADS; i++) {
			tInc[i] = new Incrementador(cont);
			tDec[i] = new Decrementador(cont);
		}

		// Lanzamiento de los threads
		for (int i = 0; i < N_THREADS; i++) {
			tInc[i].start();
			tDec[i].start();
		}

		// Espera hasta la terminacion de los threads
		try {
			for (int i = 0; i < N_THREADS; i++) {
				tInc[i].join();
				tDec[i].join();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit (-1);
		}

		// Simplemente se muestra el valor final de la variable:
		System.out.println(cont.valorContador());
		System.exit (0);
	}
}
