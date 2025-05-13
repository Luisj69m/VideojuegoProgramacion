package modelo;

import java.io.Serializable;

/**
 * Autores: Daniel Moñino, Luis Marcano e Ivan Rubio
 * Licencia GPL V3.0
 */
public class Jugador implements Serializable {
    private String nombre;
    private String email;
    private Integer puntuacion;

    /**
     * Constructor: inicializa nombre y email; la puntuación se asigna posteriormente.
     */
    public Jugador(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
        this.puntuacion = 0; // inicia en 0 hasta asignar valor real al finalizar partida
    }

    /**
     * @return Nombre del jugador.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return Email registrado del jugador.
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return Última puntuación guardada del jugador.
     */
    public Integer getPuntuacion() {
        return puntuacion;
    }

    /**
     * Actualiza el nombre del jugador (no cambia en persistencia automática).
     * @param nombre Nuevo nombre.
     * @return Nombre establecido.
     */
    public String setNombre(String nombre) {
        this.nombre = nombre;
        return nombre;
    }

    /**
     * Actualiza el email del jugador (no cambia en persistencia automática).
     * @param email Nuevo email.
     * @return Email establecido.
     */
    public String setEmail(String email) {
        this.email = email;
        return email;
    }

    /**
     * Establece la puntuación máxima obtenida al terminar el juego.
     * @param puntuacion Valor a asignar.
     */
    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    /**
     * Resta puntos como penalización de colisión, sin bajar de cero.
     * @param cantidad Puntos a restar.
     */
    public void restarPuntos(int cantidad) {
        this.puntuacion -= cantidad;
        if (puntuacion < 0) puntuacion = 0;
    }
}
