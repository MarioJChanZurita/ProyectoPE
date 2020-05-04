#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#define N 3

void asignarArreglo(int arr[][N]);
void imprimirArreglo(int arr[][N]);

void recorrerMatriz24Ascendente(int arr[][N]);
void recorrerMatriz13Ascendente(int arr[][N]);


int main (){
	
	int arreglo[N][N];
	time_t t;
	
	/* Intializes random number generator */
	srand((unsigned) time(&t));
	
	asignarArreglo(arreglo);
	imprimirArreglo(arreglo);
	
	recorrerMatriz24Ascendente(arreglo);
	recorrerMatriz13Ascendente(arreglo);
	
	return 0;
}
	
	void asignarArreglo(int arr[][N]) {
		for(int i = 0; i < N; i++) {
			for (int j = 0; j<N; j++) {
				arr[i][j] = rand() % 9;
			}
		}
	}
	
	void imprimirArreglo(int arr[][N]) {
		for(int i = 0; i < N; i++) {
			for (int j = 0; j<N; j++)
			{
				printf("%d ", arr[i][j]);
			}
			printf("\n");
		}  
		printf("\n");
	}
	
	void recorrerMatriz24Ascendente(int arr[][N]) {
		for(int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++){
				if(i+j <= N-1 && j-i >= 0 || j-i <= 0 && i+j >= N-1){
					printf("%d", arr[i][j]);
				} else {
					printf(" ");
				}
			}
			printf("\n");
		}
		printf("\n");
	}
	
	void recorrerMatriz13Ascendente(int arr[][N]) {
		for(int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++){
				if(i+j <= N-1 && j-i <= 0 || i+j >= N-1 && j-i >= 0){
					printf("%d", arr[i][j]);
				} else {
					printf(" ");
				}
			}
			printf("\n");
		}
		printf("\n");
	}