package es.udc.sistemasinteligentes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
//Tal y como nos pide el ejercicio debemos crear una clase nueva que se llame ProblemaCuadradoMagico y que sea subclase de ProblemaBusqueda

public class ProblemaCuadradoMagico extends ProblemaBusqueda {

    //Creamos ahora una clase estática dentero que se llame EstadoCuadrado, va a funcionar parecido a nuestra clase Estado
    public static class EstadoCuadrado extends Estado {
        //Va a tener de parámetros lo siguiente: Por una parte, un int que va a ser n que va a representar los numeros y una madtriz cuadrado
        private final int n;
        private final int[][] cuadrado;

        //Vamos a crear el constructor del mismo.

        public EstadoCuadrado(int n, int[][] cuadrado) {
            this.n = n;
            this.cuadrado = cuadrado;
        }

        //Métodos getters
        public int getN() {
            return n;
        }


        public int[][] getCuadrado() {
            return cuadrado;
        }



        //Override del toString que represente visualmente la creacion del cuadrado
        @Override
        public String toString() {

            //El motivo del uso de un StringBuilder aquí no es más que su eficiencia
            StringBuilder str = new StringBuilder();
            str.append("{"); //Abrimos un corchete para represnetar la mariz
            for(int i=0;i<n;i++) {//Bucle que recorre filas
                str.append("{"); //Otro corchete
                for (int j = 0; j < n; j++) {//Bucle de las columnas
                    str.append(cuadrado[i][j]).append(","); //Comas entre cada valor de cada celda
                }
                str.append("}");//Cerramos el corchete filas
            }
            str.append("}");//Cerramos corchete principal
            return str.toString();//Devolvemos la cadena
        }

        @Override
        public boolean equals(Object o) {
            //Es el mismo objeto? Si true
            if (this == o) return true;
            //Es el mismo tipo, es decir es tipo EstadoCuadrado? si no es va a false
            //Si o (objeto digo eh) sí es un EstadoCuadrado, lo convierte a un objeto de tipo EstadoCuadrado mediante type casting.
            if (!(o instanceof EstadoCuadrado that)) return false;
            //Aquí comprobamos si son los dos estados iguales, tiene que ser n y los cuadrados
            //Es importante usar el deepEquals porque compara contenido interno de matrices bidimensionales y no solo verifica si
            //Ambas matrices son la misma referencia en memoria
            return n == that.n && Arrays.deepEquals(cuadrado, that.cuadrado);
        }

        @Override
        public int hashCode() {
            //Calcula el hash inicial basado únicamente en el valor de n
            int result = Objects.hash(n);
            //Combina el hash de la matriz cuadrado.
            result = 31 * result + Arrays.deepHashCode(cuadrado);//Usamos el 31 comunmente como factor multiplicador porque es un número primo
            //Y reduce las colisiones así
            //Devuelve el hash resultante
            return result;
        }
    }
    //Creamos un AcciónCuadrado que sea un subtipo de Acción
    public static class AccionCuadrado extends Accion{
        //Declaramos los atributos
        int x;
        int y;
        int num;

    //Constructor

        public AccionCuadrado(int x, int y, int num) {
            this.x = x;
            this.y = y;
            this.num = num;
        }

        @Override
        //Con esto simplemente definimos el stringBuilder
        public String toString() {
            return "(" + "(" + x + "," + y + ")" + num + ")";
        }

        @Override
        //Hacemos una función booleana que se encargue de comprobar si cierto cuadrado mágico es aplicable o lo que es lo mismo,
        //Que está creado con la lógica del problema
        public boolean esAplicable(Estado es) {
        //Convertimos el estado actual en un EstadoCuadrado después de aplicar la acción
            EstadoCuadrado esC = (EstadoCuadrado) aplicaA(es);
        //Verificamos si la posición (x,y) del cuadrado ya está ocupada
            //Si el valor xy es distino a 0 no podemos añadir más número
            if (((EstadoCuadrado)es).cuadrado[x][y] != 0) return false;
        //Obtebemso tamaño cuadradomagico
            int num = esC.getN();
            //Calculamos el valor máximo permitido para la suma de una fila, columna o diagonal nos lo da el ej
            int maxN = (num*((num*num)+1))/2;
            //Inicializamos variables para acumular las sumas parciales

            int sumd1 = 0,sumd2=0;
            int rowSum = 0, colSum = 0;

            //Iteramos sobre todas las celdas del cuadrado

            for (int i = 0; i < esC.n; i++) {
                //Diagonal
                if (maxN < (sumd1 += esC.cuadrado[i][i])) return false;
                //Diagonal secundaria
                if (maxN < (sumd2 += esC.cuadrado[i][esC.n-1-i])) return false;
                //Columna i
                if (maxN < (rowSum += esC.cuadrado[x][i])) return false;
                //Fila i
                if (maxN < (colSum += esC.cuadrado[i][y])) return false;
            }
            return true;
        }

        @Override
        public Estado aplicaA(Estado es) {
            //Convertimos el estado genérico recibido (Estado) en un EstadoCuadrado para procesarlo
            EstadoCuadrado esC = ((EstadoCuadrado) es);
            //Creamos una nueva Matriz para la copia del cuadrado actual
            int[][] matriz = new int[esC.n][esC.n];

            //Copiamos todos los valores de la matriz del estado actual al nuevo
            //Estado
            //Esto asegura que no modifiquemos directamente la matriz original
            for(int i = 0 ; i < esC.n ; i++)
                System.arraycopy(esC.cuadrado[i], 0, matriz[i], 0, esC.n);

            matriz[x][y] = num;

            return new EstadoCuadrado(esC.n, matriz);
        }
    }


    public ProblemaCuadradoMagico(EstadoCuadrado estadoInicial) {
        super(estadoInicial);
    }

    public Accion[] acciones(Estado es){
        //Clasico invocar el estado normal pero pasandolo a modo cuadrado
        EstadoCuadrado esC = (EstadoCuadrado) es;
        //Creamos una lista para almacenar acciones que se pueden aplicar
        ArrayList<Accion> listaAcciones = new ArrayList<>();
        //En esta lista guardaremos los numeros que ya estén presentes en el cuadrado
        ArrayList<Integer> listA = new ArrayList<>();
        //En esta otra los números que faltan por colocar en el cuadrado
        ArrayList<Integer> listB = new ArrayList<>();

        //recolectamos los números que ya están en el cuadrado
        for (int i = 0; i < esC.n; i++) {
            for (int j = 0; j < esC.n; j++) {
                //Los añadimos a la listsA
                listA.add(esC.cuadrado[i][j]);
            }
        }
        //Determinamos aquellos que nos faltan
        for(int i=1;i<= (esC.n * esC.n);i++){
            if(!listA.contains(i))
                //Si un número no está presente en el cuadrado (listA), lo agregamos a listB

                listB.add(i);
        }
        //generamos todas las acciones posibles
        for (int i = 0; i < esC.n; i++) {
            for (int j = 0; j < esC.n; j++) {
                // Si la celda (i, j) está vacía (es 0), intentamos colocar los números de listB
                if(esC.cuadrado[i][j] == 0){
                    // Recorremos cada número que falta por colocar

                    for(Integer item : listB){
                        // Creamos una acción para colocar el número en la celda (i, j)
                        Accion a = new AccionCuadrado(i,j,item);
                        // Verificamos si esta acción es aplicable al estado actual
                        if (a.esAplicable(esC))
                            // Si es aplicable, añadimos la acción a la lista de acciones
                            listaAcciones.add(a);
                    }
                }
            }
        }
        // Convertimos la lista de acciones en un array y lo devolvemos

        return listaAcciones.toArray(new Accion[0]);
    }

    @Override
    public boolean esMeta(Estado es) {
        //Classic genérico
        EstadoCuadrado esC = (EstadoCuadrado) es;
        //Variables para calcular las sumas de las diagonales
        int sumd1 = 0,sumd2=0;
        //Calculamos la suma de las dos diagonales
        for (int i = 0; i < esC.n; i++) {
            //suma diagonal principal
            sumd1 += esC.cuadrado[i][i];
            //suma secundaria
            sumd2 += esC.cuadrado[i][esC.n-1-i];
        }
        //Si las sumas de las diagonales no coinciden, el estado no es meta

        if(sumd1!=sumd2)
            return false;
        //Verificamos filas y columnas
        for (int i = 0; i < esC.n; i++) {
            //Variables locales para acumular la suma de cada fila y columna

            int rowSum = 0, colSum = 0;
            //Recorrer elementos de la fila y columna
            for (int j = 0; j < esC.n; j++) {
                //fila i
                rowSum += esC.cuadrado[i][j];
                //Columna i
                colSum += esC.cuadrado[j][i];
            }
            //Si las sumas de una fila y su columna asociada no coinciden
            //o si no coinciden con la suma de las diagonales, no es un estado meta
            if (rowSum != colSum || colSum != sumd1)
                return false;
        }
        //Si ninguna de las condiciones falla, el estado es meta

        return true;
    }
}