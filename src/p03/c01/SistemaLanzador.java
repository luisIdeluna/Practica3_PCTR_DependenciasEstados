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

public class SistemaLanzador {

	private static final int nPuertas = 5;

	public static void main(String[] args) {
		
		IParque parque = new Parque(); // TODO

		char letra_puerta = 'A';
		
		System.out.println("¡Parque abierto!");
		
		//for (int i = 0; i < Integer.parseInt(args[0]); i++) {

		for (int i=0; i< nPuertas; i++){
			
			String puerta = ""+((char) (letra_puerta++));
			
			// Creación de hilos de entrada
			ActividadEntradaPuerta entradas = new ActividadEntradaPuerta(puerta, parque);
		
			new Thread (entradas).start();
			
			// Creación de hilos de salida
			ActividadEntradaPuerta salidas = new ActividadEntradaPuerta(puerta, parque);
		
			new Thread (salidas).start();
				
		}//fin for

	}	
}