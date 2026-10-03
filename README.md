# Ventas mensuales por departamento

Programa hecho en **Java** y en **Python** para la materia de Estructura de Datos.
Guarda las ventas de cada mes de una tienda en un **arreglo bidimensional (matriz)**
y permite **insertar**, **buscar** y **eliminar** ventas desde un menú en consola.

## ¿En qué consiste?

La matriz tiene **12 filas** (los meses, de Enero a Diciembre) y **3 columnas**
(los departamentos: Ropa, Deportes y Juguetería). Cada celda guarda el monto vendido
en ese mes por ese departamento. Una celda vacía significa que aún no hay venta registrada
(`None` en Python y `null` en Java).

| Mes       | Ropa | Deportes | Juguetería |
|-----------|------|----------|------------|
| Enero     |      |          |            |
| Febrero   |      |          |            |
| ...       |      |          |            |
| Diciembre |      |          |            |

Se accede a una venta con `ventas[mes][departamento]`, donde `mes` va de 0 (Enero) a 11
(Diciembre) y `departamento` va de 0 (Ropa) a 2 (Juguetería).

## Archivos

| Archivo | Descripción |
|---------|-------------|
| `VentasMensuales.java` | Versión en Java |
| `ventas_mensuales.py`  | Versión en Python |

## Cómo ejecutarlo

**Python** (3.8 o superior):

```bash
python ventas_mensuales.py
```

**Java** (JDK 11 o superior):

```bash
javac VentasMensuales.java
java VentasMensuales
```

> Si en Windows los acentos se ven raros en la consola, ejecuta antes `chcp 65001`
> o corre el programa con `java -Dfile.encoding=UTF-8 VentasMensuales`.

## Menú

```
1. Insertar venta
2. Buscar venta
3. Eliminar venta
4. Mostrar tabla
5. Cargar datos de ejemplo
0. Salir
```

La opción 5 llena la tabla con datos de prueba para poder probar rápido la búsqueda y la eliminación.

## ¿Cómo funciona cada método?

### `insertar(mes, depto, monto)`
Guarda el monto en la posición `[mes][depto]` de la matriz. Es acceso directo por índices,
por lo que no necesita recorrer nada (complejidad **O(1)**). Si la celda ya tenía una venta,
la reemplaza y devuelve el valor anterior para avisarle al usuario; si estaba vacía, devuelve `None`/`null`.

### `buscar(monto)`
Busca un monto en particular. Recorre la matriz completa con dos ciclos anidados
(meses y departamentos) y compara cada celda con el monto buscado, ignorando las celdas vacías.
Devuelve **todas** las posiciones `(mes, departamento)` donde aparece ese monto, porque el mismo valor
puede repetirse. Si no hay coincidencias, devuelve una lista vacía y el programa avisa que no se encontró.
Como revisa las 12 × 3 celdas, su complejidad es **O(n · m)**.

### `eliminar(mes, depto)`
Elimina la venta de un departamento en un mes específico: deja la celda `[mes][depto]` vacía
(`None`/`null`) y devuelve el monto que había para confirmarlo. Si la celda ya estaba vacía,
avisa que no había ninguna venta registrada. También es acceso directo, **O(1)**.
Se vacía la celda en lugar de recorrer los datos porque un arreglo tiene tamaño fijo;
así el resto de la tabla no se mueve.

### `mostrar()`
Imprime la tabla completa con los meses como filas y los departamentos como columnas.
Las celdas sin venta se muestran como `--`.

## Ejemplo de uso

```
Mes:
  1. Enero ... 12. Diciembre
Opción: 2
Departamento:
  1. Ropa  2. Deportes  3. Juguetería
Opción: 3
Monto de la venta: $1500.50
Venta de $1,500.50 guardada en Febrero / Juguetería.
```

## Autor

Tzab Irabien Christopher Giovanni, Estructura de Datos, grupo 3ZA, Instituto Tecnológico de Mérida.
