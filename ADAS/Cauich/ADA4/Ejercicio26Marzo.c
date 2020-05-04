#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <stdbool.h>
#define N 9

void llenadoArreglo(int arr[][N]);
void imprimirArreglo(int arr[][N]);
void imprimirArreglo2(int arr[][N]);

int main(){
    int array[N][N];
    time_t t;


srand((unsigned) time(&t));

llenarArreglo(array);
imprimirArreglo(array);
imprimirArreglo2(array);


return 0;
}

//Asigna valores a la matriz
void llenarArreglo(int arr[][N]){
    for(int i =0; i<N; i++){
        for(int j=0; j<N; j++){
        arr[i][j]= rand() %10; 
        }
    }
}

//Imprime la matriz (Sección 2 y 4)
void imprimirArreglo(int arr[][N]){
    int contador = 0;
    bool aux = true;
  for(int i=0; i<N; i++){
      int aux1 = 0;
      while (aux1<contador)
      {
          printf("  ");
          aux1++;
      }
      
      for(int j=contador; j<N-(contador); j++){
          printf("%d ", arr[i][j]);
          if(i==(N/2) && j==(N/2)){
              aux = false;
          }
        }
        printf("\n");
        if(aux){
            contador++;
        }else {
            contador--;
        }
    }
}

void imprimirArreglo2(int arr[][N]){
  for(int i =0; i<N; i++){
      for( int j=0; j<N; j++){
            if((j <= i && i+j <= N-1) || (j>=i && i+j >= N-1) ){
                printf("%d ", arr[i][j]);
            }else{
                printf("  ");
            }
        }
        printf("\n");
    }
}