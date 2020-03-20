#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#define N 10

//Prototipo
void llenadoArreglo(int arre[][N]);
void imprimirArreglo(int arre[][N]);

int main(){
    int array[N][N];
    time_t t;

//Generador de la funcion de la funcion
srand((unsigned) time(&t));

llenadoArreglo(array);
imprimirArreglo(array);


return 0;
}

void llenadoArreglo(int arre[][N]){
    int contador = 0;
    for(int i =0; i<N; i++){
        for(int j=0; j<N; j++){
        arre[i][j] = contador;
        contador++;
        /*Usa la funcion srand para sacar los numeros
        arre[i][j]= rand() %50; */
        }
    }
}

void imprimirArreglo(int arre[][N]){
  for(int i =0; i<N; i++){
        for(int j=0; j<N; j++){
            printf("%d,%d: %d ",i,j, arre[i][j]);
        }
        printf("\n");
    }
}