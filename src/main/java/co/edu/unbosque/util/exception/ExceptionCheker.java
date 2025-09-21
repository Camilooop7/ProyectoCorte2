package co.edu.unbosque.util.exception;

import java.util.InputMismatchException;



/**
 * Clase ExceptionCheker. Proporciona métodos para validar contraseñas según
 * diferentes criterios.
 */
public class ExceptionCheker {
	/**
	 * Verifica que una contraseña cumpla con todos los criterios de validación.
	 * 
	 * @param a La contraseña a validar.
	 * @throws CapitalException   Si no contiene al menos una letra mayúscula.
	 * @throws CharacterException Si no tiene al menos 8 caracteres.
	 * @throws NumberException    Si no contiene al menos un número.
	 * @throws SymbolException    Si no contiene al menos un símbolo especial.
	 * @throws SmallException     Si no contiene al menos una letra minúscula.
	 */
	public static void checkerPasword(String a)
			throws CapitalException, CharacterException, NumberException, SymbolException, SmallException {
		checkerCapital(a);	
		checkerCharacter(a);
		checkerNumber(a);
		checkerSymbol(a);
		checkerSmall(a);

	}

	public static void checkerMail(String a) throws InputMismatchException, MailException {
		if (!a.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
			throw new MailException();
		}
	}

	/**
	 * Verifica que la contraseña tenga al menos 8 caracteres.
	 * 
	 * @param a La contraseña a validar.
	 * @throws CharacterException Si la longitud es menor a 8 caracteres.
	 */
	public static void checkerCharacter(String a) throws CharacterException {
		if (a.length() < 8) {
			throw new CharacterException();
		}
	}

	/**
	 * Verifica que la contraseña contenga al menos una letra mayúscula.
	 * 
	 * @param a La contraseña a validar.
	 * @throws CapitalException Si no contiene letras mayúsculas.
	 */

	public static void checkerEqualPassword(String a, String b) throws EqualPasswordException {
		if (!a.equals(b)) {
			throw new EqualPasswordException();
		}
	}

	/**
	 * Verifica que la contraseña contenga al menos una letra mayúscula.
	 * 
	 * @param a La contraseña a validar.
	 * @throws CapitalException Si no contiene letras mayúsculas.
	 */

	public static void checkerCapital(String a) throws CapitalException {
		if (!a.matches(".*[A-Z].*")) {
			throw new CapitalException();
		}
	}

	/**
	 * Verifica que la contraseña contenga al menos una letra minúscula.
	 * 
	 * @param a La contraseña a validar.
	 * @throws SmallException Si no contiene letras minúsculas.
	 */

	public static void checkerSmall(String a) throws SmallException {
		if (!a.matches(".*[a-z].*")) {
			throw new SmallException();
		}
	}

	/**
	 * Verifica que la contraseña contenga al menos un número.
	 * 
	 * @param a La contraseña a validar.
	 * @throws NumberException Si no contiene números.
	 */
	public static void checkerNumber(String a) throws NumberException {

		if (!a.matches(".*\\d.*")) {
			throw new NumberException();
		}
	}

	/**
	 * Verifica que la contraseña contenga al menos un símbolo especial.
	 * 
	 * @param a La contraseña a validar.
	 * @throws SymbolException Si no contiene símbolos especiales.
	 */

	public static void checkerSymbol(String a) throws SymbolException {

		if (!a.matches(".*[^A-Za-z0-9].*")) {
			throw new SymbolException();
		}
	}

	/**
	 * Verifica que el usuario no exita dentro de la lista
	 * 
	 * @param validar si el usuario existe.
	 * @throws UsernameException si el usuario no exite.
	 */

	public static void checkerUsername(boolean a, boolean b, boolean c) throws UsernameException {
		if (a || b || c) {
			throw new UsernameException();
		}
	}

	/**
	 * Verifica que el valor no sea neagtico
	 * 
	 * @param validar si el valor es menor a 0.
	 * @throws checkerNegativeNumber si el usurio no es negativo.
	 */
	public static void checkerNegativeNumber(int a) throws NegativeNumberException {
		if (a < 0) {
			throw new NegativeNumberException();
		}
	}

	/**
	 * Verifica que solo reciba letras
	 * 
	 * @param validar si lo ingresado son solo letras.
	 * @throws checkerText si lo ingresado no tiene letras.
	 */

	public static void checkerText(String a) throws TextException {
		if (!a.matches("[A-Za-záéíóúÁÉÍÓÚñÑ]+")) {
			throw new TextException();
		}
	}

	/**
	 * Verifica que no halla nada vacio
	 * 
	 * @param validar si falta por completaar algo.
	 * @throws checkerIsBlank si lo ingresado tiene algo sin completar.
	 */

	public static void checkerIsBlank(String a) throws IsBlackException {
		if (a.length() < 1) {
			throw new IsBlackException();
		}
	}
	

	/**
	 * Verifica que no falte nada por llenar
	 * 
	 * @throws IsBlackException si los datos pedidos tienen algo sin completar.
	 */

	public static void checkerIsEmpty() throws IsBlackException {
		throw new IsBlackException();

	}

	/**
	 * Verifica que lo ingreasado si sea una imagen
	 * 
	 * @throws checkerImage si lo ingresado es diferente a una imagen.
	 */

	public static void checkerImage() throws ImageException {

		throw new ImageException();

	}

	/**
	 * Verifica que lo ingresado sea solo numeros
	 * 
	 * @throws checkerNumber si lo ingresado es diferente a un numero.
	 */
	public static void checkerNumber(int a) throws InputMismatchException {

		throw new InputMismatchException();

	}

}
