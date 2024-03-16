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
 */

public interface IParque {


	/**
	 * Método: entrarAlParque(String puerta)
	 * 
	 * Descripción: Incrementar las personas que entran el Parque en una
	 * 
	 * @param puerta 
	 * 
	 */
	public abstract void entrarAlParque(String puerta);

	/**
	 * Método: salirDelParque(String puerta
	 * )
	 * Descripción: Disminuir las personas que salen del parque en una
	 * 
	 * @param puerta 
	 * 
	 */
	public abstract void salirDelParque(String puerta);

}
