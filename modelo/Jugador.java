package modelo;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.IOException;

public class Jugador {
    private String nombre;
    private int puntuacion;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.puntuacion = 0; // Por defecto en 0
        cargar();
    }

    public void guardar() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("jugadores/jugador.dat"))) {
            bw.write(nombre + "," + puntuacion);
        } catch (IOException e) {
            System.out.println("Error al guardar jugador: " + e.getMessage());
        }
    }

    private void cargar() {
        try (BufferedReader br = new BufferedReader(new FileReader("jugadores/jugador.dat"))) {
            String[] datos = br.readLine().split(",");
            this.nombre = datos[0];
            this.puntuacion = Integer.parseInt(datos[1]);
        } catch (IOException e) {
            System.out.println("No se encontró archivo de jugador, iniciando nuevo.");
        }
    }

    public String getNombre() { 
        return nombre; 
    }
    public int getPuntuacion() { 
        return puntuacion;
    }
}