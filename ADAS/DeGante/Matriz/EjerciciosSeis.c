//Diego De Gante Pérez
//Imprime sectore 2 ascedente y descendente
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#define N 3

void asignarArreglo(int arr[][N]);
void imprimirArreglo(int arr[][N]);
void recorrerMatriz2Ascendente(int arr[][N]);
void recorrerMatriz2Descendente(int arr[][N]);


int main (){
	
	int arreglo[N][N];
	time_t t;
	
	/* Intializes random number generator */
	srand((unsigned) time(&t));
	
	asignarArreglo(arreglo);
	imprimirArreglo(arreglo);
	recorrerMatriz2Ascendente(arreglo);
	recorrerMatriz2Descendente(arreglo);
	
	
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

    //Ejercicio 11 (2)
	void recorrerMatriz2Ascendente(int arr[][N]) {
		for(int i = 0; i < N; i++) {
			for (int j = i; j < N && j < (N-i); j++){
				printf("%d ", arr[i][j]);
			} 
			printf("\n");
		}
		printf("\n");
	}
	
	//Ejercicio 12 (2)
	void recorrerMatriz2Descendente(int arr[][N]) {
		for(int i = N-1; i >= 0; i--) {
			for (int j = i; j < N && j <= (N-1-i); j++){
				printf("%d ", arr[i][j]);
			} 
			printf("\n");
		}
		printf("\n");
	}