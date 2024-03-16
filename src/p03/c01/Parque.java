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
	 */
	public void entrarAlParque(String puerta){		// TODO
		
		// Si no hay entradas por esa puerta, inicializamos
		if (contadoresPersonasPuerta.get(puerta) == null){
		
			contadoresPersonasPuerta.put(puerta, 0);
		
		}
		
		// Hay que comprobar antes que entrar
				
		
		// Aumentamos el contador total y el individual
		contadorPersonasTotales++;		
		
		contadoresPersonasPuerta.put(puerta, contadoresPersonasPuerta.get(puerta)+1);
		
		// Imprimimos el estado del parque
		
		imprimirInfo(puerta, "Entrada");
		
		checkInvariante(); //comprobamos que se cumple esto
		
	}
	
	/**
	 * 
	 * @param puerta
	 * 
	 * @param movimiento
	 */
	private void imprimirInfo (String puerta, String movimiento){

		System.out.println(movimiento + " por puerta " + puerta);

		System.out.println("--> Personas en el parque " + contadorPersonasTotales); //+ " tiempo medio de estancia: "  + tmedio);
		
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

	private int sumarPersonasPuerta(){

		int personasPuerta = 0;
		
		Enumeration<Integer> nPuertas = contadoresPersonasPuerta.elements();

		//Creo el iterador
		while (nPuertas.hasMoreElements()){

			personasPuerta += nPuertas.nextElement();

		}//fin while

		return personasPuerta; 

	}//fin sumarPersonasPuerta

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

				wait(); 

			}//fin try

			catch( InterruptedException e){

				e.printStackTrace();

			}//fin catch

		}//fin while

	}//fin comprobarAntesDeEntrar

	//Comporbamos si existe gente dentro del parque antes de salir
	protected void comprobarAntesDeSalir(){
		
		while (contadorPersonasTotales <=0){

			try{

				wait(); 

			}//fin try

			catch( InterruptedException e){

				e.printStackTrace();

			}//fin catch

		}//fin while
	}

	@Override
	/**
	 * @param puerta
	 * 
	 */
	public void salirDelParque(String puerta) {

		// Si no hay entradas por esa puerta, inicializamos
		if (contadoresPersonasPuerta.get(puerta) == null){
		
			contadoresPersonasPuerta.put(puerta, 0);
		
		}

		contadorPersonasTotales--; //Reduzco en una unidad el contador de personas que hay en el parque
	
		contadoresPersonasPuerta.put(puerta, null);

		imprimirInfo(puerta, puerta);

		checkInvariante(); // hay que verificar que se cumple el invariante

	}

}//fin clase
