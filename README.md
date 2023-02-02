# Taller 1

Aplicación que permite consultar la información de películas almacenadas en la API 
https://www.omdbapi.com/ por medio del nombre de las mismas, el proyecto incluye 
un servicio de cache el cual almacena los datos de las películas con el fin de 
evitar llamados constantes a la API

## Getting Started

### Prerequisites

Git: permite el control de versiones del proyecto

Java: lenguaje en el cual esta desarrollado la totalidad del proyecto

Maven: Software que gestiona proyectos java 


### Installing

Clonamos el repositorio

```
git clone https://github.com/ser0710/tallerArep1.git
```

Entramos en la carpeta donde se encuentra el pom.xml
y ejecutamos

```
mvn clean package exec:java -D"exec.mainClass"="edu.escuelaing.arep.app.HttpServer"
```

Una vez veamos el mensaje de "Listo para recbir ..."
entramos al buscador de preferencia y entramos al link
http://localhost:35000 alli podremos ingresar
el nombre de una película


## Running the tests

Para correr los tests basta con emplear el comando

```
mvn test
```

### Tests

Se cuenta con 2 tests

* El primero permite verificar el correcto funcionamiento del 
cache
* El segundo permite verificar que al momento de realizar 
la consulta a la API, esta arroje la respuesta esperada


### And coding style tests

Principalmente los tests se centran en el correcto funcionamiento 
del cache, que este no guarde duplicados de la información solicitada 
y también se enfocan en el llamado a la API, que este efectivamente 
retorne los datos solicitados.

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management


## Versioning

version 1.0

## Authors

Sergio Andres Rozo Pulido


## Extra description

* Extensibilidad: Principalmente se podría tener criterios 
extras en la búsqueda de la película, para ello basta 
con cambiar el link ya sea agregando o quitan lo que sea 
necesario, en caso dado de querer agregar cosas al 
html basta con cambiar el string.
* Patrones: Principalmente el patrón fachada, 
gracias al cache que recibe toda la información 
que se solicita y este analiza si ya cuenta con 
dicha información o si es necesario consultar a la API