package net.leludo.iotqvt.bootstrap;

/**
 * Exception dédiée à la gestion des propriétés applicative
 *
 */
@SuppressWarnings("serial")
public class ApplicationPropertiesException extends Exception {

	/**
	 * Conctructeur
	 * @param message message de l'exception
	 */
	public ApplicationPropertiesException(String message) {
		super(message) ;
	}
}
