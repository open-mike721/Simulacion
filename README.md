# Generadores de Números Pseudoaleatorios (RNG)

![Java](https://img.shields.io/badge/Java-17%2B-orange?logo=java)
![Plataforma](https://img.shields.io/badge/Plataforma-CLI-lightgrey)
![Licencia](https://img.shields.io/badge/Licencia-MIT-blue)

Aplicación de consola en Java para generar secuencias de números
pseudoaleatorios mediante los métodos clásicos de la simulación discreta, con
interfaz interactiva por menú, tabla de resultados y exportación a **CSV**.

---

## ✨ Características

- **Menú principal interactivo** con opciones numeradas y validación de errores
  de entrada (nunca se cae por un dato incorrecto).
- **Cuatro generadores clásicos**:
  - 🟦 Método de **Cuadrados Medios**
  - 🟨 Método de **Productos Medios**
  - 🟪 Método de **Multiplicación Constante**
  - 🟩 Método **Congruencial Lineal**
- **Semillas flexibles**: cualquier número de máximo 9 dígitos (en Productos
  Medios, ambas semillas deben tener el mismo tamaño).
- **Dígitos adaptativos**: los métodos de dígitos centrales ajustan el número
  de dígitos al tamaño de la semilla.
- **Regla especial N = 0**: generación continua con **detección de ciclos**
  (se detiene al detectar el primer número repetido).
- **Tabla de resultados** de 4 columnas alineada automáticamente.
- **Exportación a CSV** opcional por cada secuencia.
- **Arquitectura POO**: cada generador es una clase independiente bajo una
  clase base común, fácil de extender con nuevos métodos.

---

## 📁 Estructura del proyecto

```
src/
├── Main.java                                          # Punto de entrada
├── MenuTerminal.java                                  # Menú e interfaz de terminal
├── EntradaConsola.java                                # Lectura y validación de entradas
├── GeneradorSecuencia.java                            # Generación de filas y detección de ciclos
├── TablaResultados.java                               # Tabla en consola y exportación CSV
├── Fila.java                                          # Registro de una fila de la tabla
└── GeneradoresDeNumerosPseudoaleatorios/
    ├── GeneradorBase.java                             # Clase base abstracta
    ├── CuadradosMedios.java                           # Método de Cuadrados Medios
    ├── ProductosMedios.java                           # Método de Productos Medios
    ├── MultiplicacionConstante.java                   # Método de Multiplicación Constante
    ├── CongruencialLineal.java                        # Método Congruencial Lineal
    ├── NumerosUtil.java                               # Utilidades comunes (relleno, dígitos centrales…)
    ├── SemillaInvalidaException.java                  # Excepción: semilla no válida
    └── SemillasDiferenteLongitudException.java        # Excepción: semillas de tamaño distinto
```

---

## 📋 Requisitos

- **JDK 17 o superior** (recomendado: JDK 21 LTS o el instalado, p. ej. JDK 27).
  El programa usa `record`, expresiones `switch` y `String.repeat`, por lo que
  se requiere Java 17+.

Verifica tu instalación:

```bash
java -version
javac -version
```

---

## 🚀 Uso rápido (compilar y ejecutar)

### Linux / macOS

```bash
# 1. Compilar
javac -encoding UTF-8 -d out/production $(find src -name '*.java')

# 2. Ejecutar
java -cp out/production Main
```

### Windows (PowerShell)

```powershell
# 1. Compilar
javac -encoding UTF-8 -d out $(Get-ChildItem src -Recurse -Name -Filter *.java | ForEach-Object { "src/$_" })

# 2. Ejecutar
java -cp out Main
```

---

## 🏗️ Guía de instalación: obtener un JAR ejecutable

Puedes generar un archivo `simulacion-rng.jar` de dos maneras.

### Opción A — Línea de comandos

#### Linux / macOS

```bash
# 1. Compilar a una carpeta de clases
mkdir -p build/classes
javac -encoding UTF-8 -d build/classes $(find src -name '*.java')

# 2. Empaquetar el JAR (cfe = clase principal + archivo + entrada)
jar cfe simulacion-rng.jar Main -C build/classes .

# 3. Ejecutar el JAR
java -jar simulacion-rng.jar
```

#### Windows (PowerShell)

```powershell
# 1. Compilar a una carpeta de clases
mkdir build\classes
javac -encoding UTF-8 -d build\classes $((Get-ChildItem src -Recurse -Filter *.java).FullName)

# 2. Empaquetar el JAR
jar cfe simulacion-rng.jar Main -C build\classes .

# 3. Ejecutar el JAR
java -jar simulacion-rng.jar
```

### Opción B — IntelliJ IDEA

1. Abre el proyecto (`File > Open…`).
2. Ve a `File > Project Structure > Artifacts > + > JAR > From modules with dependencies`.
3. En **Main Class** selecciona `Main` y pulsa **OK**.
4. Construye el artefacto: `Build > Build Artifacts > simulacion-rng:jar > Build`.
5. El JAR aparecerá en `out/artifacts/simulacion_rng_jar/simulacion-rng.jar`.

> 💡 **Consejo:** ya compilado, puedes correr el JAR desde cualquier carpeta con
> `java -jar simulacion-rng.jar`. Los archivos CSV se guardan en la carpeta de
> donde se ejecuta el comando.

---

## 🎮 Cómo usar el programa

Al ejecutar verás el menú principal:

```
======================================
 ¡Bienvenido! Generadores de Números
     Pseudoaleatorios (RNG)
======================================

--- MENÚ PRINCIPAL ---
1) Método de Cuadrados Medios
2) Método de Productos Medios
3) Método de Multiplicación Constante
4) Método Congruencial Lineal
5) Salir
Elige una opción (1-5):
```

Pasos típicos:

1. Elige un generador (1–5).
2. Introduce la **semilla** (y parámetros propios del método, p. ej. la constante
   o los coeficientes `a`, `c`, `m` del congruencial).
3. Introduce la **cantidad N** de números:
   - `N > 0` → genera exactamente `N` números.
   - `N = 0` → genera en bucle hasta detectar un número repetido (**ciclo**).
4. Revisa la tabla de resultados:

```
>> Cuadrados Medios
+----------+----------------------+------------------+--------------------+
| Índice   | Estado anterior      | Nuevo estado     | Número aleatorio   |
+----------+----------------------+------------------+--------------------+
| 1        | 123                  | 151              | 0.151000000        |
| 2        | 151                  | 228              | 0.228000000        |
...
```

   > En **Productos Medios** la columna de estados anteriores muestra los **dos**
   > valores previos que se multiplican.

5. ¿Guardar la tabla en **CSV**? Responde `s` o `n`.
6. ¿Generar **otra secuencia** con el mismo generador? `s` → pide una nueva
   semilla y `N`; `n` → vuelve al menú principal.

---

## 🧮 Los generadores

| Método                            | Fórmula                                               | Parámetros                          |
| --------------------------------- | ----------------------------------------------------- | ----------------------------------- |
| Cuadrados Medios                  | Xᵢ = dígitos centrales de Xᵢ₋₁²                      | semilla                            |
| Productos Medios                  | Xᵢ = dígitos centrales de Xᵢ₋₂ · Xᵢ₋₁                | semilla₁, semilla₂ (mismo tamaño)  |
| Multiplicación Constante          | Xᵢ = dígitos centrales de k · Xᵢ₋₁                    | semilla, constante                  |
| Congruencial Lineal               | Xᵢ = (a · Xᵢ₋₁ + c) mod m                             | semilla, a, c, m                    |

- **Cuadrados, Productos y Multiplicación Constante** normalizan con `10^d`,
  siendo `d` el número de dígitos de la semilla (dígitos adaptativos).
- **Congruencial Lineal** admite **cualquier módulo `m` positivo**; el cálculo
  `(a·X + c) mod m` se hace a prueba de desbordamiento.

---

## 🛠️ Extender el proyecto

Para agregar un nuevo generador:

1. Crea una clase que extienda `GeneradorBase` e implementa `siguiente()`.
2. (Opcional) Sobrescribe `estadosAnteriores()` si el método usa más de un
   estado previo.
3. Añade una opción en el menú (`MenuTerminal.ejecutar()` +
   `mostrarMenu()`) siguiendo el patrón de los métodos existentes.

---

## 📄 Licencia

MIT — libre de usar para tareas académicas y proyectos personales.