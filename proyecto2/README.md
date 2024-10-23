# Ordenador lexicográfico

Este proyecto implementa un ordenador lexicográfico con el algoritmo **merge sort**, simula el comportamiento del comando `sort` de Unix, pero con características personalizadas. 
El programa funciona con uno o más archivos de texto o la entrada estándar e imprime su salida en la salida estándar.

La complejidad en tiempo del programa es **O(n log n)**, siendo n el número de líneas en el o los archivos de entrada.

## Características

- Los archivos se ordenan por líneas, no por palabras; las líneas
vacías se ordenan antes que las no vacías; los espacios y caracteres
distintos a letras son ignorados al ordenar pero preservados al imprimir; y
 los acentos, diéresis y eñes se toman como vocales y la letra n
respectivamente. Esto es extrapolado a cualquier acento o símbolo de
cualquier sistema de escritura incorporado a Unicode en la codificación UTF-8.
- El programa puede recibir varios archivos, ya sea en la línea de comandos o
por la entrada estándar, en cuyo caso los trata a todos como un único archivo
grande.
- Si el usuario pasa la bandera `-r`, el programa debe imprimir las líneas en
orden inverso.
- Si el usuario pasa la bandera `-o` seguida de un identificador, el programa
debe guardar su salida en un archivo llamado como el identificador. La
operación es destructiva y supone que el usuario sabe lo que hace.
- Las banderas `-r` y `-o` deben aparecer a lo más una vez.
- Las banderas son como en los comandos de Unix, argumentos de la línea de
comandos, y pueden ir en cualquier orden.

## Uso

El programa está desarrollado en Java y utiliza **Maven** para la gestión de dependencias y la compilación.

```sh
$ mvn compile # compila el código
$ mvn test    # corre las pruebas unitarias (opcional)
$ mvn install # genera el archivo proyecto1.jar en el subdirectorio target
```

Suponiendo que tenemos el archivo `hombres.txt`

```
Hombres necios que acusáis
    a la mujer sin razón,
sin ver que sois la ocasión
    de lo mismo que culpáis.

Si con ansia sin igual
    solicitáis su desdén,
¿por qué queréis que obren bien
    si las incitáis al mal?

Combatís su resistencia
    y luego con gravedad
decís que fue liviandad
    lo que hizo la diligencia.

Parecer quiere el denuedo
    de vuestro parecer loco
al niño que pone el coco
    y luego le tiene miedo.

Queréis con presunción necia
    hallar a la que buscáis,
para pretendida, Tais,
    y en la posesión, Lucrecia.

¿Qué humor puede ser más raro
    que el que, falto de consejo,
él mismo empaña el espejo
    y siente que no esté claro?
```

Entonces al correr el proyecto de la siguiente manera:

```sh
$ java -jar target/proyecto1.jar hombres.txt
```

La salida estándar del programa debe ser:

```





    a la mujer sin razón,
al niño que pone el coco
Combatís su resistencia
decís que fue liviandad
    de lo mismo que culpáis.
    de vuestro parecer loco
él mismo empaña el espejo
    hallar a la que buscáis,
Hombres necios que acusáis
    lo que hizo la diligencia.
para pretendida, Tais,
Parecer quiere el denuedo
¿por qué queréis que obren bien
    que el que, falto de consejo,
¿Qué humor puede ser más raro
Queréis con presunción necia
Si con ansia sin igual
    si las incitáis al mal?
sin ver que sois la ocasión
    solicitáis su desdén,
    y en la posesión, Lucrecia.
    y luego con gravedad
    y luego le tiene miedo.
    y siente que no esté claro?
```

La misma salida debe ocurrir si le damos al programa el archivo a través de la
entrada estándar:

```sh
$ cat hombres.txt | java -jar target/proyecto1.jar
```
