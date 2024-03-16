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
//Importaciones

import java.util.Random;

import java.util.concurrent.TimeUnit;

import java.util.logging.Level;

import java.util.logging.Logger;

public class ActividadSalidaPuerta implements Runnable {

	private static final int NUMSALIDAS = 20; // Requisito de salidas definido en el documento de la práctica

	// Declaración de variables

	private String puerta; // Identifica a la puerta

	private IParque parque; // Identifica al parque

	/**
	 * Constructor de la clase
	 * 
	 * @param puerta
	 * 
	 * @param parque
	 */
	public ActividadSalidaPuerta(String puerta, IParque parque) {

		this.puerta = puerta;

		this.parque = parque;

	} // fin constructor

	@Override

	public void run() {

		for (int i = 0; i < NUMSALIDAS; i++) {

			try {

				parque.salirDelParque(puerta);

				TimeUnit.MILLISECONDS.sleep(new Random().nextInt(5) * 1000);

			} catch (InterruptedException e) {

				Logger.getGlobal().log(Level.INFO, "Salida interrumpida");

				Logger.getGlobal().log(Level.INFO, e.toString());

				return;

			} // fin try catch

		} // fin for

	}// void run

}// fin clase
