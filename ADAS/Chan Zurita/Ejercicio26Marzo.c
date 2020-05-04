#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#define N 3

//PROTOTIPOS
void asignarMatriz(int arreglo[][N]);
void imprimirMatriz(int arreglo[][N]);
//Ejercicios
void recorrerMatriz2_4Ascendente(int arreglo[][N]);
void recorrerMatriz1_3Ascendente(int arreglo[][N]);

//MAIN
int main() {
	
	int opcion;
	int matriz[N][N];
	time_t t;
	srand((unsigned) time(&t));
	
	asignarMatriz(matriz);
	imprimirMatriz(matriz);
	printf("\n");

	printf("1.recorrerMatriz2_4Ascendente \n");
	printf("2.recorrerMatriz1_3Ascendente \n");
	scanf("%d", &opcion);
	
	switch(opcion){
		case 1: 
			recorrerMatriz2_4Ascendente(matriz);
			break;
		case 2:
			recorrerMatriz1_3Ascendente(matriz);
			break;
	}
	
	return 0;
}

//FUNCIONES
void asignarMatriz(int arreglo[][N]){
	for(int i=0; i<N; i++){
		for(int j=0; j<N; j++){
			arreglo[i][j] = rand() % 10;
		}
	}
}

void imprimirMatriz(int arreglo[][N]){
	for(int i=0; i<N; i++){
		for(int j=0; j<N; j++){
			printf("%d	", arreglo[i][j]);
		}
		printf("\n");
	}
}

void recorrerMatriz2_4Ascendente(int arreglo[][N]){
	for(int i=0; i<(N-1)/2; i++){
		for(int z=0; z<i; z++){
			printf("	");
		}
		for(int j=i; j<N-i; j++){
			printf("%d	", arreglo[i][j]);
		}
		printf("\n");
	}
	
	for(int i=(N-1)/2; i<N; i++){
		for(int z=0; z<N-1-i; z++){
			printf("	");
		}
		for(int j=N-1-i; j<=i; j++){
			printf("%d	", arreglo[i][j]);
		}
		printf("\n");
	}
}



void recorrerMatriz1_3Ascendente(int arreglo[][N]){
	for(int i=0; i<(N-1)/2; i++){
		for(int j=0; j<=i; j++){
			printf("%d	", arreglo[i][j]);
		}
		for(int z=i+1; z<N-1-i; z++){
			printf("	");
		}
		for(int j=N-1-i; j<N; j++){
			printf("%d	", arreglo[i][j]);
		}
		printf("\n");
	}

	for(int i=(N-1)/2; i<N; i++){
		for(int j=0; j<N-i; j++){
			printf("%d	", arreglo[i][j]);
		}
		for(int z=N-i; z<i; z++){
			printf("	");
		}
		for(int j=i; j<N; j++){
			printf("%d	", arreglo[i][j]);
		}
		printf("\n");
	}
} 

/*
Mario Jesús Chan Zurita 
*/