package almacen1;

import es.upm.babel.cclib.Producto;
import es.upm.babel.cclib.Almacen;
import es.upm.babel.cclib.Semaphore;


/**
 * Implementación de la clase Almacen que permite el almacenamiento
 * de producto y el uso simultáneo del almacen por varios threads.
 */
class Almacen1 implements Almacen {
   // Producto a almacenar: null representa que no hay producto
   private Producto almacenado = null;

   // TODO: declaración e inicialización de los semáforos
   // necesarios
   
   Semaphore lleno = new Semaphore(0);
   Semaphore vacio = new Semaphore(1);

   public Almacen1() {
   }

   public void almacenar(Producto producto) {
      // TODO: protocolo de acceso a la sección crítica y código de
      // sincronización para poder almacenar.
	  
	   vacio.await(); //Como incializo vacio a 1, hago await para que se meta un almacenador.
	   
      // Sección crítica
      almacenado = producto;

      // TODO: protocolo de salida de la sección crítica y código de
      // sincronización para poder extraer.
      
      lleno.signal(); //pongo a 1 el de lleno, para que el consumidor sepa que puede coger
   }

   public Producto extraer() {
      Producto result;

      // TODO: protocolo de acceso a la sección crítica y código de
      // sincronización para poder extraer.
      
      lleno.await(); //si está a 1, un consumidor podrá consumir, y si vacío está a 0, se esperará  
      
      // Sección crítica
      result = almacenado;
      almacenado = null;

      // TODO: protocolo de salida de la sección crítica y código de
      // sincronización para poder almacenar.
      
      vacio.signal(); //se pone a 1 el semaforo que indica que esta vacio y se puede almacenar
      
      return result;
   }
}
