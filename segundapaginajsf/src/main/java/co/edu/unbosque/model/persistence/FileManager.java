package co.edu.unbosque.model.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileManager {

	// Eestos son para archivsp de texto.
		private static Scanner lectorDeArchivo;
		private static File archivo;
		private static PrintWriter escritorDeArchivo;
		private static final String RUTA_CARPETA = "src/archivos";

		private static FileOutputStream fos; // sirve para ubicar el archivo
		private static ObjectOutputStream oos; // escribir objetos en el archivo
		private static FileInputStream fis; //
		private static ObjectInputStream ois; //

		public FileManager() {
			// TODO Auto-generated constructor stub
		}

		public static void crearCarpeta() {
			archivo = new File(RUTA_CARPETA);
			if(!archivo.exists() || archivo.isDirectory()) {
				archivo.mkdir();
			}
		}


		public static void escribirEnArchivoDeTexto(String nombreArchivo, String contenido) {
			try {
				archivo = new File(RUTA_CARPETA + "/" + nombreArchivo);
				if (!archivo.exists()) {
					archivo.createNewFile();

				}
				escritorDeArchivo = new PrintWriter(archivo);
				escritorDeArchivo.println(contenido);
				// cuando se abre un archivps, despues de usarlo toca cerrarlo.
				escritorDeArchivo.close();
			} catch (IOException e) {
				// si llegue hasta aquies porque el archivo no tiene permisos la url esta mal o
				// el archivo no exciste
				System.out.println("error al escribir unarchivo de texto.(creacion del archivo)");
				e.printStackTrace();
			}
		}

		public static String leerArchivoDeTexto(String nombreArchivo) {
			try {
				archivo = new File(RUTA_CARPETA + "/" + nombreArchivo);
				if (!archivo.exists()) {
					archivo.createNewFile();

				}
				lectorDeArchivo = new Scanner(archivo);
				String contenido = "";
				while (lectorDeArchivo.hasNext()) {
					contenido += lectorDeArchivo.nextLine() + "\n";
				}
				lectorDeArchivo.close();
				return contenido;

			} catch (IOException e) {
				System.out.println("error al escribir unarchivo de texto.(creacion del archivo)");
				e.printStackTrace();
			}
			return null;
		}

		public static void escribirArchivoSerializado(String nombreArchivo, Object contenido) {
			try {
				archivo = new File(RUTA_CARPETA + "/" + nombreArchivo);
				if (!archivo.exists()) {
					archivo.createNewFile();

				}
				fos = new FileOutputStream(archivo);
				oos = new ObjectOutputStream(fos);
				oos.writeObject(contenido);
				
				oos.close();
				fos.close();
			} catch (IOException e) {
				System.out.println("problemas al abrir el archivo serializado (escritura)");
				e.printStackTrace();
			}
		}

		
		
		public static Object leerArchivoSerializado(String nombreArchivo) {
			Object contenido = null;
			try {
				archivo = new File(RUTA_CARPETA + "/" + nombreArchivo);
				if(!archivo.exists()) {
					archivo.createNewFile();
				}
				fis= new FileInputStream(archivo);
				ois = new ObjectInputStream(fis);
				contenido = ois.readObject();
				
				
				ois.close();
				fis.close();
			} catch (IOException e) {
				// TODO: handle exception
				System.out.println("error al leer el archivo serializado");
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("error en los datos del archivo serializado");
				e.printStackTrace();
			}
			return contenido;
		}
}
