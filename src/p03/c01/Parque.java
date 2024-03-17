package src.p03.c01;

/**
 * 
 * @autor Luis Ignacio de Luna Gómez
 * 
 * @email ldg1008@alu.ubu.es
 * 
 * @date 19/03/2024
 * 
 * @version 1.0
 * 
 * @Descripction: Práctica 3 Programación concurrente Curso 2023/2024
 * 
 */

import java.util.Enumeration;

import java.util.Hashtable;

public class Parque implements IParque{

	/*Requisito de capacidad del parque */

	private static final int MAXPERSONAS =50;

	private int contadorPersonasTotales; // Total de persona dentro del parque

	private Hashtable<String, Integer> contadoresPersonasPuerta; //Tabla a la que le paso una puerta y el número de personas
	
	private long tiempoInicial = 0;

	private double tiempoMedio = 0.0;
	/**
	 * Constructor
	 */
	public Parque() {

		contadorPersonasTotales = 0;

		contadoresPersonasPuerta = new Hashtable<String, Integer>();

	}

	@Override

	/**
	 * @param puerta
	 * 
	 * @precondición: Número total de personas en el parque debe ser menor que MAXPERSONAS
	 * 
	 * @postcondición: Núermo total de personas en el parque se debe de incrementar en uno y el contador de la puerta también
	 */
	public synchronized void entrarAlParque(String puerta){		// TODO
		
		// Si no hay entradas por esa puerta, inicializamos
		if (contadoresPersonasPuerta.get(puerta) == null){
		
			contadoresPersonasPuerta.put(puerta, 0);
		
		}

		 // Reiniciar el tiempo inicial si el parque estaba vacío
		 if (contadorPersonasTotales == 0){

			tiempoInicial = System.currentTimeMillis();
		
		}
		
		// Hay que comprobar antes que entrar
		comprobarAntesDeEntrar(); //Espera si el parque esta lleno
		
		// Aumentamos el contador total y el individual
		contadorPersonasTotales++;		
		
		contadoresPersonasPuerta.put(puerta, contadoresPersonasPuerta.get(puerta)+1);
		
		//Llevar a cabo el cálculo de tiempos

		long tiempoActual = System.currentTimeMillis();

		tiempoMedio = (tiempoMedio + (tiempoActual - tiempoInicial))/2.0;

		// Imprimimos el estado del parque
		
		imprimirInfo(puerta, "Entrada");
		
		checkInvariante(); //comprobamos que se cumple esto

		notifyAll(); // Notifico a los hilos que esperan para salir
		
	}
	
	/**
	 * 
	 * @param puerta
	 * 
	 * @param movimiento
	 */
	private void imprimirInfo (String puerta, String movimiento){

		System.out.println(movimiento + " por puerta " + puerta);

		System.out.println("--> Personas en el parque: " + contadorPersonasTotales + " tiempo medio de estancia: " + obtenerMediaTiempo());
		
		// Iteramos por todas las puertas e imprimimos sus entradas

		for(String p: contadoresPersonasPuerta.keySet()){

			System.out.println("----> Por puerta " + p + " " + contadoresPersonasPuerta.get(p));

		}

		System.out.println(" ");

	}
	/**
	 * 
	 * @return sumaContadoresPuerta
	 */
	private int sumarContadoresPuerta() {

		int sumaContadoresPuerta = 0;

		Enumeration<Integer> iterPuertas = contadoresPersonasPuerta.elements();

		while (iterPuertas.hasMoreElements()) {

			sumaContadoresPuerta += iterPuertas.nextElement();

		}//fin while

		return sumaContadoresPuerta;

	}//fin sumarContadoresPuerta
	
	protected void checkInvariante() {

		assert sumarContadoresPuerta() == contadorPersonasTotales : "INV: La suma de contadores de las puertas debe ser igual al valor del contador del parte";

		//Verifico el aforo máximo del parque

		assert contadorPersonasTotales <= MAXPERSONAS : "Áforo máximo del parque alcanzado"; 

		//Verifico que no se ha colado nadie en el parque

		assert contadorPersonasTotales > 0: "Se ha colado gente";
		
	}//checkinVariante


	/**
	 * Método compobarAntesDeEntrar
	 * 
	 * Se debe de comprobar que se permite el acceso de personas al parque por no llegar al áforo máximo
	 *
	 *  @throws InterruptedException 
	 */
	protected void comprobarAntesDeEntrar() {

		while (contadorPersonasTotales >= MAXPERSONAS){

			try{

				wait(); //Espera hasta que se notifiquen cambios

			}//fin try

			catch( InterruptedException e){
				
				Thread.currentThread().interrupt(); //Interrupción
                
				return;

			}//fin catch

		}//fin while

	}//fin comprobarAntesDeEntrar

	//Comporbamos si existe gente dentro del parque antes de salir
	protected void comprobarAntesDeSalir(String puerta){
		
		//Compruebo que las personas o las personas por puerta son menores o iguales a cero
		while (contadorPersonasTotales <=0 || contadoresPersonasPuerta.getOrDefault(puerta, 0) <= 0) {

			try{

				wait(); //Espera hasta que se notifiquen cambios

			}//fin try

			catch( InterruptedException e){

				Thread.currentThread().interrupt(); //Interrupción

                return;

			}//fin catch

		}//fin while
	}

	@Override
	/**
	 * @param puerta
	 *
	 * @precondición: El número de personas en el parque debe ser menor que MAXPERSONAS 
	 * 
	 * @postcondición: El número total de personas el parque se decrementa en una unidad y el contador de la puerta igual
	 */
	public synchronized void salirDelParque(String puerta) {

		// Si no hay entradas por esa puerta, inicializamos
		if (contadoresPersonasPuerta.get(puerta) == null){
		
			contadoresPersonasPuerta.put(puerta, 0);
		
		}

		comprobarAntesDeSalir(puerta); //Espera si el parque está vacío

		contadorPersonasTotales--; //Reduzco en una unidad el contador de personas que hay en el parque
	
		contadoresPersonasPuerta.put(puerta, contadoresPersonasPuerta.get(puerta)-1);

		//Llevar a cabo el cálculo de tiempos

		long tiempoActual = System.currentTimeMillis();

		tiempoMedio = (tiempoMedio + (tiempoActual - tiempoInicial))/2.0;

		imprimirInfo(puerta, "Salida"); //Imprimo la salida  

		checkInvariante(); // hay que verificar que se cumple el invariante

		notifyAll(); // Notifico a los hilos que esperan para salir

	} //fin salirDelParque

	 /**
	  * 
	  */
	  private synchronized String obtenerMediaTiempo(){

		long tiempoActual = System.currentTimeMillis();

		if(contadorPersonasTotales == 0) return "0.0 minutos" ;
		
		long timempoTotalEstancia = tiempoActual - tiempoInicial;

		// Convierto los milisegundos a minutos para mejorar la legibilidad.

		double tiempoEnMinutos = (timempoTotalEstancia  / 60.0) / contadorPersonasTotales;

		return String.format("%.2f minutos", tiempoEnMinutos);

	  }//fin obtenerMediaTiempo


	  

}//fin clase
