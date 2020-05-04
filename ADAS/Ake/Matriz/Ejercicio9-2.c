#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#define N 1000

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
    for(int i =0; i<N; i++){
        for(int j=0; j<N; j++){

        //Usa la funcion srand para sacar los numeros
        arre[i][j]= rand() %50;
        }
    }
}

void imprimirArreglo(int arre[][N]){
    for(int i =(N-1); i>=0; i--){
      for(int j=(N-1); j>=0; j--){
          if(i<=j) {
              printf("%d,%d:%d ",i,j, arre[i][j]);
            }
          }
        printf("\n");
    }
}