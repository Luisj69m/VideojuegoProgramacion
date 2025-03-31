package modelo;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Escenario {
    private int ancho;
    private int alto;
    private List<String> mapa;

    public Escenario(String rutaArchivo) {
        this.mapa = new ArrayList<>();
        cargarEscenario(rutaArchivo);
    }

    private void cargarEscenario(String rutaArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String dimensiones = br.readLine();
            String[] partes = dimensiones.split("x");
            this.ancho = Integer.parseInt(partes[0]);
            this.alto = Integer.parseInt(partes[1]);

            String linea;
            while ((linea = br.readLine()) != null) {
                mapa.add(linea);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar el escenario: " + e.getMessage());
        }
    }

    public void mostrarEscenario() {
        for (String linea : mapa) {
            System.out.println(linea);
        }
    }

    public int getAncho() { return ancho; }
    public int getAlto() { return alto; }
    public List<String> getMapa() { return mapa; }
}

