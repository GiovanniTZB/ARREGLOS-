import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Ventas mensuales por departamento (arreglo bidimensional).
 * Filas    -> los 12 meses (Enero ... Diciembre)
 * Columnas -> los 3 departamentos (Ropa, Deportes, Juguetería)
 * Una celda null significa que todavía no hay venta registrada.
 */
public class VentasMensuales {

    static final String[] MESES = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio",
            "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
    static final String[] DEPARTAMENTOS = {"Ropa", "Deportes", "Juguetería"};

    static Double[][] ventas = new Double[MESES.length][DEPARTAMENTOS.length];
    static Scanner sc = new Scanner(System.in);

    /** Guarda la venta en [mes][depto]. Devuelve el valor anterior (null si estaba vacía). */
    static Double insertar(int mes, int depto, double monto) {
        Double anterior = ventas[mes][depto];
        ventas[mes][depto] = monto;
        return anterior;
    }

    /** Recorre toda la matriz y devuelve las posiciones {mes, depto} cuya venta es igual a monto. */
    static List<int[]> buscar(double monto) {
        List<int[]> encontrados = new ArrayList<>();
        for (int m = 0; m < ventas.length; m++) {
            for (int d = 0; d < ventas[m].length; d++) {
                if (ventas[m][d] != null && Math.abs(ventas[m][d] - monto) < 0.005) {
                    encontrados.add(new int[]{m, d});
                }
            }
        }
        return encontrados;
    }

    /** Borra la venta de [mes][depto]. Devuelve el monto eliminado (null si no había venta). */
    static Double eliminar(int mes, int depto) {
        Double eliminado = ventas[mes][depto];
        ventas[mes][depto] = null;
        return eliminado;
    }

    /** Imprime la tabla completa. */
    static void mostrar() {
        System.out.printf("%n%-12s", "Mes");
        for (String d : DEPARTAMENTOS) System.out.printf("%14s", d);
        System.out.println();
        System.out.println("-".repeat(12 + 14 * DEPARTAMENTOS.length));
        for (int m = 0; m < MESES.length; m++) {
            System.out.printf("%-12s", MESES[m]);
            for (int d = 0; d < DEPARTAMENTOS.length; d++) {
                if (ventas[m][d] == null) System.out.printf("%14s", "--");
                else System.out.printf("%,14.2f", ventas[m][d]);
            }
            System.out.println();
        }
    }

    // ---------- Entrada de datos ----------
    static String leerLinea(String texto) {
        System.out.print(texto);
        if (!sc.hasNextLine()) System.exit(0);
        return sc.nextLine().trim();
    }

    /** Muestra la lista numerada y devuelve el índice elegido (0..n-1). */
    static int leerOpcion(String texto, String[] nombres) {
        System.out.println(texto);
        for (int i = 0; i < nombres.length; i++) System.out.println("  " + (i + 1) + ". " + nombres[i]);
        while (true) {
            try {
                int op = Integer.parseInt(leerLinea("Opción: "));
                if (op >= 1 && op <= nombres.length) return op - 1;
            } catch (NumberFormatException e) { /* se repite la pregunta */ }
            System.out.println("Opción inválida, intenta de nuevo.");
        }
    }

    static double leerMonto(String texto) {
        while (true) {
            try {
                double monto = Double.parseDouble(leerLinea(texto).replace(",", "."));
                if (monto >= 0) return monto;
            } catch (NumberFormatException e) { /* se repite la pregunta */ }
            System.out.println("Escribe un número válido (mayor o igual a 0).");
        }
    }

    static void cargarEjemplo() {
        double[][] datos = {{12500, 8300, 5400}, {11800, 7900, 4900}, {13200, 8800, 5100},
                {12100, 9100, 5600}, {13900, 9500, 6000}, {12700, 8700, 5800},
                {14100, 9900, 6300}, {15200, 10400, 7100}, {13500, 9300, 6600},
                {14800, 10100, 7400}, {18900, 12300, 14200}, {22400, 14800, 21500}};
        for (int m = 0; m < 12; m++)
            for (int d = 0; d < 3; d++) insertar(m, d, datos[m][d]);
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== VENTAS MENSUALES =====");
            System.out.println("1. Insertar venta\n2. Buscar venta\n3. Eliminar venta");
            System.out.println("4. Mostrar tabla\n5. Cargar datos de ejemplo\n0. Salir");
            String op = leerLinea("Elige una opción: ");
            switch (op) {
                case "1": {
                    int m = leerOpcion("Mes:", MESES);
                    int d = leerOpcion("Departamento:", DEPARTAMENTOS);
                    double monto = leerMonto("Monto de la venta: $");
                    Double anterior = insertar(m, d, monto);
                    if (anterior != null) System.out.printf("Se reemplazó la venta anterior de $%,.2f.%n", anterior);
                    System.out.printf("Venta de $%,.2f guardada en %s / %s.%n", monto, MESES[m], DEPARTAMENTOS[d]);
                    break;
                }
                case "2": {
                    double monto = leerMonto("Monto a buscar: $");
                    List<int[]> res = buscar(monto);
                    if (res.isEmpty()) System.out.println("No se encontró ninguna venta con ese monto.");
                    for (int[] pos : res)
                        System.out.printf("Encontrado: %s / %s ($%,.2f)%n", MESES[pos[0]], DEPARTAMENTOS[pos[1]], monto);
                    break;
                }
                case "3": {
                    int m = leerOpcion("Mes:", MESES);
                    int d = leerOpcion("Departamento:", DEPARTAMENTOS);
                    Double eliminado = eliminar(m, d);
                    if (eliminado == null) System.out.println("Esa celda no tenía ninguna venta registrada.");
                    else System.out.printf("Se eliminó la venta de $%,.2f de %s / %s.%n", eliminado, MESES[m], DEPARTAMENTOS[d]);
                    break;
                }
                case "4": mostrar(); break;
                case "5": cargarEjemplo(); System.out.println("Datos de ejemplo cargados."); break;
                case "0": System.out.println("¡Hasta luego!"); return;
                default: System.out.println("Opción inválida.");
            }
        }
    }
}
