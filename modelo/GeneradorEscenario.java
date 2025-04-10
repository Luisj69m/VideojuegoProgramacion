package modelo;

import java.io.*;
import java.util.*;

public class GeneradorEscenario {

    public static List<String> generarDesdeArchivo(String rutaArchivo) {
        List<String> mapa = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            // Leer dimensiones: ejemplo "10x10"
            String linea = br.readLine();
            if (linea == null || !linea.matches("\\d+x\\d+")) {
                throw new IllegalArgumentException("Formato de dimensiones incorrecto.");
            }

            String[] partes = linea.split("x");
            int alto = Integer.parseInt(partes[0]);
            int ancho = Integer.parseInt(partes[1]);

            // Borde superior
            mapa.add("+" + "-".repeat(ancho) + "+");

            for (int i = 0; i < alto; i++) {
                String contenido = br.readLine();
                String lineaMapa = "";

                if (contenido != null && !contenido.isEmpty()) {
                    String[] bloques = contenido.split(",");

                    for (String bloque : bloques) {
                        bloque = bloque.trim();
                        int cantidad = Integer.parseInt(bloque.replaceAll("[^0-9]", ""));
                        char tipo = bloque.replaceAll("[0-9]", "").charAt(0);

                        char simbolo;
                        if (tipo == 'E') simbolo = ' ';
                        else if (tipo == 'O') simbolo = 'x';
                        else simbolo = '?'; // símbolo desconocido

                        lineaMapa += String.valueOf(simbolo).repeat(cantidad);
                    }

                    // Rellenar si es más corto
                    if (lineaMapa.length() < ancho) {
                        lineaMapa += " ".repeat(ancho - lineaMapa.length());
                    }

                    // Cortar si se pasa
                    if (lineaMapa.length() > ancho) {
                        lineaMapa = lineaMapa.substring(0, ancho);
                    }
                } else {
                    // Línea vacía: todo espacio
                    lineaMapa = " ".repeat(ancho);
                }

                mapa.add("|" + lineaMapa + "|");
            }

            // Borde inferior
            mapa.add("+" + "-".repeat(ancho) + "+");

        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }

        return mapa;
    }
}
