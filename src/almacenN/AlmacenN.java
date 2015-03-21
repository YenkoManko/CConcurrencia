package almacenN;

import es.upm.babel.cclib.Producto;
import es.upm.babel.cclib.Almacen;
import es.upm.babel.cclib.Semaphore;


/**
 * Implementación de la clase Almacen que permite el almacenamiento
 * FIFO de hasta un determinado número de productos y el uso
 * simultáneo del almacén por varios threads.
 */
@SuppressWarnings("unused")
class AlmacenN implements Almacen {
   private int capacidad = 0;
   private Producto[] almacenado = null;
   private int nDatos = 0;
   private int aExtraer = 0;
   private int aInsertar = 0;

   // TODO: declaración de los semáforos necesarios
Semaphore lleno;
Semaphore mutex;
Semaphore vacio;
   
   public AlmacenN(int n) {
      capacidad = n;
      almacenado = new Producto[capacidad];
      nDatos = 0;
      aExtraer = 0;
      aInsertar = 0;

      // TODO: inicialización de los semáforos
      lleno = new Semaphore(0);
      mutex = new Semaphore(1); //es para garantizar que solo un proceso hace algo, pero creo que no es necesario.
      vacio = new Semaphore(capacidad);
   }

   public void almacenar(Producto producto) {
      // TODO: protocolo de acceso a la sección crítica y código de
      // sincronización para poder almacenar.
	   
	   vacio.await(); 	//cada vez que se almacena un producto, se disminuye el semaforo vacio, hasta que llegue
	   					//a 0 y se bloqueen los productos a almacenar.
	   mutex.await();  	//asegura que solo un producto es almacenado a la vez, pero creo que no es necesario
      // Sección crítica
      almacenado[aInsertar] = producto;
      nDatos++;
      aInsertar++;
      aInsertar %= capacidad;

      // TODO: protocolo de salida de la sección crítica y código de
      // sincronización para poder extraer.
      
      lleno.signal(); //se aumenta el contador de  lleno, que indicará a los consumidores que pueden extraer.
      mutex.signal(); //libera el semaforo para que otro proceso pueda ejecutar, pero creo que no es necesario.
   }

   public Producto extraer() {
      Producto result;

      // TODO: protocolo de acceso a la sección crítica y código de
      // sincronización para poder extraer.
      
      lleno.await(); 	//si lleno es 0, se bloquea y no puede extraer nada, puesto que no hay
      mutex.await();	//se asegura de que solo hay un extractor; creo que no es necesario.
      // Sección crítica
      result = almacenado[aExtraer];
      almacenado[aExtraer] = null;
      nDatos--;
      aExtraer++;
      aExtraer %= capacidad;

      // TODO: protocolo de salida de la sección crítica y código de
      // sincronización para poder almacenar.
      
      vacio.signal(); 	//se aumenta el semaforo de vacío, puesto que al extraer, queda un puesto más vacío.
      mutex.signal();	//libera el semaforo para que pueda extraer otro
      return result;
   }
}
