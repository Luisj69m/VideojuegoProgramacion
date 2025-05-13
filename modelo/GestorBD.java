package modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Autores: Daniel Moñino, Luis Marcano e Ivan Rubio
 * Licencia GPL V3.0
 */
public class GestorBD {
    private final String url = "jdbc:sqlite:puntuaciones.db";

    /**
     * Constructor que crea la tabla de puntuaciones si no existe.
     */
    public GestorBD() {
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()) {
            // Definición SQL para la tabla de puntuaciones
            String sql = "CREATE TABLE IF NOT EXISTS puntuaciones (" +
                         " id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                         " nombre TEXT NOT NULL, " +
                         " puntuacion INTEGER NOT NULL" +
                         ")";
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error al crear la tabla de puntuaciones: " + e.getMessage());
        }
    }

    /**
     * Establece y devuelve una conexión a la base de datos SQLite.
     * @return Connection a la base de datos, o null si falla.
     */
    private Connection connect() {
        try {
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());
            return null;
        }
    }

    /**
     * Obtiene el Top 10 de jugadores ordenados por puntuación descendente.
     * @return Lista de objetos Jugador con nombre y puntuación.
     */
    public List<Jugador> obtenerTop10() {
        List<Jugador> top10 = new ArrayList<>();
        String sql = "SELECT nombre, puntuacion FROM puntuaciones ORDER BY puntuacion DESC LIMIT 10";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Jugador jugador = new Jugador(rs.getString("nombre"), "");
                jugador.setPuntuacion(rs.getInt("puntuacion"));
                top10.add(jugador);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return top10;
    }

    /**
     * Inserta o actualiza la puntuación de un jugador, guardando solo su mejor marca.
     * @param jugador Instancia de Jugador con nombre y puntuación actual.
     */
    public void guardarSiTop10(Jugador jugador) {
        String selectSql = "SELECT puntuacion FROM puntuaciones WHERE nombre = ?";
        String insertSql = "INSERT INTO puntuaciones(nombre, puntuacion) VALUES(?, ?)";
        String updateSql = "UPDATE puntuaciones SET puntuacion = ? WHERE nombre = ?";

        try (Connection conn = this.connect();
             PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
            selectStmt.setString(1, jugador.getNombre());
            ResultSet rs = selectStmt.executeQuery();
            if (rs.next()) {
                int existente = rs.getInt("puntuacion");
                // Solo actualiza si la nueva puntuación es mayor
                if (jugador.getPuntuacion() > existente) {
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setInt(1, jugador.getPuntuacion());
                        updateStmt.setString(2, jugador.getNombre());
                        updateStmt.executeUpdate();
                    }
                }
            } else {
                // Inserta si no existe el jugador en la tabla
                try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                    insertStmt.setString(1, jugador.getNombre());
                    insertStmt.setInt(2, jugador.getPuntuacion());
                    insertStmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
