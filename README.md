# Algoritmo de Dijkstra en Java

Una aplicación web simple desarrollada en Java con Spring Boot para visualizar y experimentar con el algoritmo de Dijkstra en un grafo interactivo.

## ¿Qué hace este proyecto?

Esta app permite:
- Crear nodos en un grafo.
- Conectar nodos con aristas de peso.
- Visualizar el grafo en una interfaz web.
- Calcular el camino más corto entre dos nodos usando Dijkstra.
- Interactuar con el grafo de forma sencilla desde el navegador.

## Tecnologías utilizadas

- Java
- Spring Boot
- Thymeleaf
- HTML, CSS y JavaScript
- Maven

## Estructura del proyecto

- `src/main/java` : lógica del algoritmo, modelos del grafo y controladores.
- `src/main/resources/templates` : interfaz web.
- `src/test` : pruebas del proyecto.

## Cómo ejecutar

1. Clona este repositorio.
2. Abre la carpeta del proyecto.
3. Ejecuta:

```bash
./mvnw spring-boot:run
```

4. Abre en tu navegador:

```text
http://localhost:8080
```

## Ejemplo de uso

1. Agrega nodos al grafo.
2. Conecta los nodos con pesos.
3. Selecciona un nodo inicial y uno final.
4. La aplicación mostrará el camino más corto.

## Objetivo del proyecto

Este proyecto sirve como una demo educativa para entender cómo funciona el algoritmo de Dijkstra de forma visual y práctica.

## Autor

Proyecto desarrollado para mostrar una implementación básica del algoritmo de Dijkstra en Java.
