"""
Ventas mensuales por departamento (arreglo bidimensional).

Filas    -> los 12 meses (Enero ... Diciembre)
Columnas -> los 3 departamentos (Ropa, Deportes, Juguetería)
Una celda vacía (None) significa que todavía no hay venta registrada.
"""

MESES = ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio",
         "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"]
DEPARTAMENTOS = ["Ropa", "Deportes", "Juguetería"]


def crear_arreglo():
    """Crea la matriz de 12 filas x 3 columnas, sin ventas."""
    return [[None for _ in DEPARTAMENTOS] for _ in MESES]


def insertar(ventas, mes, depto, monto):
    """Guarda la venta `monto` en [mes][depto].
    Devuelve el valor anterior de esa celda (None si estaba vacía)."""
    anterior = ventas[mes][depto]
    ventas[mes][depto] = monto
    return anterior


def buscar(ventas, monto):
    """Recorre toda la matriz y devuelve una lista de (mes, depto)
    donde la venta es igual a `monto`."""
    encontrados = []
    for m in range(len(ventas)):
        for d in range(len(ventas[m])):
            if ventas[m][d] is not None and round(ventas[m][d], 2) == round(monto, 2):
                encontrados.append((m, d))
    return encontrados


def eliminar(ventas, mes, depto):
    """Borra la venta de [mes][depto] (la deja vacía).
    Devuelve el monto eliminado, o None si no había venta."""
    eliminado = ventas[mes][depto]
    ventas[mes][depto] = None
    return eliminado


def mostrar(ventas):
    """Imprime la tabla completa."""
    print(f"\n{'Mes':<12}" + "".join(f"{d:>14}" for d in DEPARTAMENTOS))
    print("-" * (12 + 14 * len(DEPARTAMENTOS)))
    for m, nombre in enumerate(MESES):
        fila = "".join(f"{'--':>14}" if v is None else f"{v:>14,.2f}" for v in ventas[m])
        print(f"{nombre:<12}{fila}")


# ---------- Entrada de datos ----------
def leer_opcion(texto, nombres):
    """Pide un número de la lista `nombres` (1..n) y devuelve el índice 0..n-1."""
    print(texto)
    for i, n in enumerate(nombres, 1):
        print(f"  {i}. {n}")
    while True:
        try:
            op = int(input("Opción: "))
            if 1 <= op <= len(nombres):
                return op - 1
        except ValueError:
            pass
        print("Opción inválida, intenta de nuevo.")


def leer_monto(texto):
    while True:
        try:
            monto = float(input(texto).replace(",", "."))
            if monto >= 0:
                return monto
        except ValueError:
            pass
        print("Escribe un número válido (mayor o igual a 0).")


def cargar_ejemplo(ventas):
    datos = [[12500, 8300, 5400], [11800, 7900, 4900], [13200, 8800, 5100],
             [12100, 9100, 5600], [13900, 9500, 6000], [12700, 8700, 5800],
             [14100, 9900, 6300], [15200, 10400, 7100], [13500, 9300, 6600],
             [14800, 10100, 7400], [18900, 12300, 14200], [22400, 14800, 21500]]
    for m in range(12):
        for d in range(3):
            insertar(ventas, m, d, float(datos[m][d]))


def main():
    ventas = crear_arreglo()
    while True:
        print("\n===== VENTAS MENSUALES =====")
        print("1. Insertar venta\n2. Buscar venta\n3. Eliminar venta")
        print("4. Mostrar tabla\n5. Cargar datos de ejemplo\n0. Salir")
        try:
            op = input("Elige una opción: ").strip()
        except EOFError:
            break
        if op == "1":
            m = leer_opcion("Mes:", MESES)
            d = leer_opcion("Departamento:", DEPARTAMENTOS)
            monto = leer_monto("Monto de la venta: $")
            anterior = insertar(ventas, m, d, monto)
            if anterior is not None:
                print(f"Se reemplazó la venta anterior de ${anterior:,.2f}.")
            print(f"Venta de ${monto:,.2f} guardada en {MESES[m]} / {DEPARTAMENTOS[d]}.")
        elif op == "2":
            monto = leer_monto("Monto a buscar: $")
            res = buscar(ventas, monto)
            if res:
                for m, d in res:
                    print(f"Encontrado: {MESES[m]} / {DEPARTAMENTOS[d]} (${monto:,.2f})")
            else:
                print("No se encontró ninguna venta con ese monto.")
        elif op == "3":
            m = leer_opcion("Mes:", MESES)
            d = leer_opcion("Departamento:", DEPARTAMENTOS)
            eliminado = eliminar(ventas, m, d)
            if eliminado is None:
                print("Esa celda no tenía ninguna venta registrada.")
            else:
                print(f"Se eliminó la venta de ${eliminado:,.2f} de {MESES[m]} / {DEPARTAMENTOS[d]}.")
        elif op == "4":
            mostrar(ventas)
        elif op == "5":
            cargar_ejemplo(ventas)
            print("Datos de ejemplo cargados.")
        elif op == "0":
            print("¡Hasta luego!")
            break
        else:
            print("Opción inválida.")


if __name__ == "__main__":
    main()
