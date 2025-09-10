# Documentación del Sistema de Comandas y Facturación para Restaurante

## Descripción General

Este sistema es una aplicación de consola en Java para la gestión de comandas y facturación en un restaurante. Permite registrar pedidos por mesa y comensal, generar facturas detalladas, buscar información por nombre o mesa, y mantener un registro persistente en archivos de texto.

---

## Estructura de Carpetas

- **data/**: Archivos de facturas generadas.
- **logs/**: Archivo de logs del sistema.
- **main/**: Punto de entrada principal del sistema.
- **process/**: Lógica de procesamiento de datos y generación/búsqueda de facturas.
- **restaurant/**: Lógica de interacción con el usuario y gestión de mesas/comandas.
- **results/**: Resultados de búsquedas por nombre o mesa.
- **utils/**: Utilidades generales (manejo de archivos, validaciones, logging).

---

## Clases y Funciones

### 1. `main.Main`

- **main(String[] args)**
  Punto de entrada del sistema. Inicializa el sistema, ejecuta el menú principal y maneja errores fatales.

---

### 2. `restaurant.RestaurantMain`

- **init()**
  Inicializa los arrays principales, muestra el menú principal y limpia los arrays al salir.

- **showMenu()**
  Muestra el menú principal y gestiona la interacción con el usuario.

- **addOrder(Scanner scanner)**
  Flujo para agregar una nueva comanda: selecciona mesa, comensales, items y genera la factura.

- **showTables()**
  Muestra el estado (ocupada/libre) de todas las mesas.

- **searchInfo(Scanner scanner, String criteria)**
  Permite buscar información por nombre o por mesa, mostrando los resultados o errores correspondientes.

---

### 3. `restaurant.Restaurant`

#### Agregar Comanda

- **setTableNumber(Scanner, boolean[], int)**
  Solicita y valida el número de mesa, asegurando que esté libre.

- **setPersonsInTable(Scanner, int, int[], int)**
  Solicita y valida la cantidad de comensales para una mesa.

- **namesInTable(Scanner, int, int, String[][])**
  Solicita y valida los nombres de los comensales de una mesa.

- **setItemsPerson(Scanner, String[], int[], int, int)**
  Solicita y valida la cantidad de items para cada comensal.

- **setItemName(Scanner, String[], int)**
  Solicita y valida el nombre de un item.

- **setItemQuant(Scanner, int[], int)**
  Solicita y valida la cantidad de un item.

- **setItemPrice(Scanner, double[], int)**
  Solicita y valida el precio de un item.

- **generateInvoice(...)**
  Genera el texto de la factura y la guarda en un archivo.

#### Mostrar Mesas

- **showStatusTable(int, boolean)**
  Muestra el estado de una mesa específica.

#### Búsqueda

- **getTotalInvoices()**
  Obtiene todos los archivos de facturas generadas.

- **searchInfoByName(Scanner, File[])**
  Busca información de un cliente por nombre en las facturas.

- **searchInfoByTable(Scanner, File[], int)**
  Busca información de una mesa específica en las facturas.

---

### 4. `process.ProcessMain`

- **initAllArrays(...)**
  Inicializa todos los arrays/matrices utilizados en el sistema.

- **clearAllArrays(...)**
  Limpia las referencias de los arrays (para liberar memoria).

- **buildInvoiceText(...)**
  Construye el texto de la factura a partir de los datos de la comanda.

- **searchNameInFile(File, String)**
  Busca la sección de un cliente específico en una factura.

- **searchTableInFile(File, int)**
  Busca la información de una mesa específica en una factura.

---

### 5. `process.Process`

#### Inicialización de Matrices

- **initMatrix(...)**
  Sobrecargada para distintos tipos de arrays (int[], boolean[], String[][], etc.), inicializa los valores a cero, falso o vacío.

#### Generación de Factura

- **buildInvoiceHeader(StringBuilder, int)**
  Agrega el encabezado de la factura.

- **buildClientInvoiceHeader(StringBuilder, String)**
  Agrega el encabezado para cada cliente.

- **buildInvoiceBody(StringBuilder, String[], int[], double[], int)**
  Agrega la línea de cada item (nombre, cantidad, precio, subtotal).

- **buildClientInvoiceFooter(StringBuilder, String, double)**
  Agrega el total por cliente.

- **buildInvoiceFooter(StringBuilder, double)**
  Agrega el total general de la mesa.

#### Búsqueda

- **searchNameInText(String, String)**
  Busca recursivamente la sección de un cliente en el texto de la factura.

- **searchTableInText(String, int)**
  Busca recursivamente la información de una mesa en el texto de la factura y resume la cantidad de personas, items y total.

---

### 6. `utils.FileManager`

- **getInvoiceDirectory()**
  Devuelve la ruta del directorio de facturas, creándolo si no existe.

- **getInvoiceFileName()**
  Genera un nombre único para una nueva factura.

- **getResultFileName(String, String)**
  Genera un nombre único para un archivo de resultados de búsqueda.

- **writeInFile(String, String)**
  Escribe contenido en un archivo (append).

- **getFilesInDirectory(String)**
  Devuelve los archivos de facturas en un directorio.

- **listFiles(File[])**
  Muestra en consola la lista de archivos de facturas.

- **getTextFromFile(File)**
  Lee y devuelve el contenido de un archivo de texto.

- **verifyFilePath(String)**
  Verifica y crea un directorio si no existe.

---

### 7. `utils.LoggerUtil`

- **log(String)**
  Registra un mensaje informativo en el archivo de logs.

- **logWarn(String)**
  Registra un mensaje de advertencia en el archivo de logs.

- **logError(String)**
  Registra un mensaje de error en el archivo de logs.

- **verifyLogPath()**
  Verifica y crea el directorio de logs si no existe.

---

### 8. `utils.Validate`

- **scanValidInteger(Scanner, String, int)**
  Solicita y valida la entrada de un número entero, con o sin límite.

- **scanValidDouble(Scanner, String, int)**
  Solicita y valida la entrada de un número decimal, con o sin límite.

- **scanValidString(Scanner, String)**
  Solicita y valida la entrada de un string (sin caracteres especiales ni vacío).

- **checkDirectory(String)**
  Verifica que una ruta de directorio exista.

- **isValidArr(...)**
  Sobrecargada para varios tipos de arrays, verifica que no sean nulos ni vacíos.

- **hasSpecChars(String)**
  Verifica si un string contiene caracteres no permitidos.

---

## Flujo General del Sistema

1. **Inicio**: El sistema inicia y muestra el menú principal.
2. **Agregar Comanda**: El usuario registra una nueva comanda (mesa, comensales, items), se genera y guarda la factura.
3. **Ver Estado de Mesas**: Muestra el estado de todas las mesas.
4. **Buscar Información**: Permite buscar información por nombre de cliente o por número de mesa en las facturas generadas.
5. **Logs**: Todas las acciones y errores relevantes se registran en el archivo de logs.

---

## Persistencia

- Las facturas se guardan en archivos de texto en `src/data`.
- Los resultados de búsquedas se guardan en `src/results`.
- Los logs del sistema se guardan en `src/logs/Logs.log`.

---

## Validaciones

- Todas las entradas del usuario son validadas para evitar errores de formato, rangos inválidos o caracteres no permitidos.
- El sistema maneja errores de archivos y directorios, registrando los problemas en los logs.

---

## Enlaces a Archivos y Clases

- `main.Main`
- `restaurant.RestaurantMain`
- `restaurant.Restaurant`
- `process.ProcessMain`
- `process.Process`
- `utils.FileManager`
- `utils.LoggerUtil`
- `utils.Validate`

---

Esta documentación cubre la estructura, el propósito y el funcionamiento de cada componente