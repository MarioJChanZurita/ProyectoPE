//Diego De Gante Pérez
//Imprime diagonales principales e invertidas
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#define N 3

void asignarArreglo(int arr[][N]);
void imprimirArreglo(int arr[][N]);
void recorrerDiagonalPrincipal(int arr[][N]);
void recorrerDiagonalInvertida(int arr[][N]);


int main (){
	
	int arreglo[N][N];
	time_t t;
	
	/* Intializes random number generator */
	srand((unsigned) time(&t));
	
	asignarArreglo(arreglo);
	imprimirArreglo(arreglo);
	recorrerDiagonalPrincipal(arreglo);
	recorrerDiagonalInvertida(arreglo);
	
	
	return 0;
}
	
	//Asigna valores aleatorios de 0-9
	void asignarArreglo(int arr[][N]) {
		for(int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				arr[i][j] = rand() % 9;
			}
		}
	}
	
	//Imprime tabla de las dimensiones declaradas con los números ya asignados
	void imprimirArreglo(int arr[][N]) {
		for(int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++){
				printf("%d ", arr[i][j]);
			}
			printf("\n");
		}  
		printf("\n");
	}

    //Ejercicio 17
	void recorrerDiagonalPrincipal(int arr[][N]) {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++){
				if(j-i == 0){
					printf("%d", arr[i][i]);
				} else {
					printf(" ");
				}
			}
			printf("\n");
		}
		printf("\n");
	}

	//Ejercicio 18
	void recorrerDiagonalInvertida(int arr[][N]) {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++){
				if(i+j == N-1){
					printf("%d", arr[i][i]);
				} else {
					printf(" ");
				}
			}
			printf("\n");
		}
	}
