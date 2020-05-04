#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#define N 10

//Prototipo
void llenadoArreglo(int arre[][N]);
void imprimirParte1(int arre[][N]);
void imprimirParte2(int arre[][N]);

int main(){
    int array[N][N];
    time_t t;

//Generador de la funcion de la funcion
srand((unsigned) time(&t));

llenadoArreglo(array);

printf("Matriz zona 1 y 3\n");
imprimirParte1(array);

printf("\nMatriz zona 2 y 4\n");
imprimirParte2(array);


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

void imprimirParte1(int arre[][N]){
    //Recibe la matriz como parametro
  for(int i =0; i<N; i++){
      for( int j=0; j<N; j++){
          //La primera parte izquierda del || es para la zona 1 de la matriz, el otro lado para la zona 3
            if((j <= i && i+j <= N-1) || (j>=i && i+j >= N-1) ){
                printf("%d,%d:%d ",i,j, arre[i][j]);
            }else{
                printf("       ");
            }
        }
        printf("\n");
    }
}

void imprimirParte2(int arre[][N]){
    //Recibe la matriz como parametro
  for(int i =0; i<N; i++){
      for( int j=0; j<N; j++){
          //La primera parte izquierda del || es para la zona 2 de la matriz, el otro lado para la zona 4
            if((j>=i && i+j<=N-1) || (i>=j && i+j>=N-1)) {
              printf("%d,%d:%d ",i,j, arre[i][j]);
            }else{
              printf("       ");
            }
        }
        printf("\n");
    }
}