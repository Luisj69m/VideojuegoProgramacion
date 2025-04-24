package modelo;

import java.io.Serializable;

public class Jugador implements Serializable {
    private String nombre;
    private String email;

    public Jugador(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }
}
