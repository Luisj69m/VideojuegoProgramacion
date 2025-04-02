package modelo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * Clase que representa un jugador con nombre y email.
 * Permite guardar y cargar los datos del jugador en un archivo binario.
 * @Autor:Luis Marcano
 * @Autor:Daniel Moñino
 * @Autor:Ivan Rubio
 */
public class Jugador implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nombre;
    private String email;

    /**
     * Constructor que crea un nuevo objeto Jugador con el nombre y el email especificados.
     *
     * @param nombre El nombre del jugador.
     * @param email El email del jugador.
     */
    public Jugador(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }

    /**
     * Obtiene el nombre del jugador.
     *
     * @return El nombre del jugador.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el email del jugador.
     *
     * @return El email del jugador.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Guarda el jugador en un archivo binario dentro del directorio "jugadores".
     * Si el directorio no existe, se crea.
     * El archivo se guarda con el nombre del jugador como nombre del archivo y extensión ".dat".
     */
    public void guardar() {
        // Crear el directorio "jugadores" si no existe
        File directorio = new File("jugadores");
        if (!directorio.exists()) {
            directorio.mkdir();
        }

        // Guardar el objeto Jugador en un archivo binario
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("jugadores/" + nombre + ".dat"))) {
            oos.writeObject(this);
            System.out.println("Jugador guardado correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar el jugador: " + e.getMessage());
        }
    }

    /**
     * Carga un jugador desde un archivo binario.
     * Busca el archivo con el nombre del jugador y lo carga.
     *
     * @param nombre El nombre del jugador a cargar.
     * @return El objeto Jugador cargado, o null si no se encuentra o ocurre un error.
     */
    public static Jugador cargar(String nombre) {
        // Buscar el archivo con el nombre del jugador
        File archivo = new File("jugadores/" + nombre + ".dat");
        if (!archivo.exists()) {
            return null; // Si no existe, retornar null
        }

        // Cargar el objeto Jugador desde el archivo
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            return (Jugador) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar el jugador: " + e.getMessage());
            return null; // Si ocurre un error, retornar null
        }
    }
}
