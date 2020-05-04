#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#define N 3

//PROTOTIPOS
void asignarMatriz(int arreglo[][N]);
void imprimirMatriz(int arreglo[][N]);
//EJERCICIOS
void recorrerMatriz1_2Ascendente(int arreglo[][N]);
void recorrerMatriz1_2Descendente(int arreglo[][N]);
void recorrerMatriz3_4Ascendente(int arreglo[][N]);
void recorrerMatriz3_4Descendente(int arreglo[][N]);
void recorrerMatriz1_4Ascendente(int arreglo[][N]);
void recorrerMatriz1_4Descendente(int arreglo[][N]);
void recorrerMatriz2_3Ascendente(int arreglo[][N]);
void recorrerMatriz2_3Descendente(int arreglo[][N]);
void recorrerMatriz1Ascendente(int arreglo[][N]);
void recorrerMatriz1Descendente(int arreglo[][N]);
void recorrerMatriz2Ascendente(int arreglo[][N]);
void recorrerMatriz2Descendente(int arreglo[][N]);
void recorrerMatriz3Ascendente(int arreglo[][N]);
void recorrerMatriz3Descendente(int arreglo[][N]);
void recorrerMatriz4Ascendente(int arreglo[][N]);
void recorrerMatriz4Descendente(int arreglo[][N]);
void recorrerDiagonalPrincipal(int arreglo[][N]);
void recorrerDiagonalInvertida(int arreglo[][N]);

//MAIN
int main() {
	
	int opcion;
	int matriz[N][N];
	time_t t;
	srand((unsigned) time(&t));
	
	asignarMatriz(matriz);
	imprimirMatriz(matriz);
	printf("\n");

	printf("1.recorrerMatriz1_2Ascendente \n");
	printf("2.recorrerMatriz1_2Descendente \n");
	printf("3.recorrerMatriz3_4Ascendente \n");
	printf("4.recorrerMatriz3_4Descendente \n");
	printf("5.recorrerMatriz1_4Ascendente \n");
	printf("6.recorrerMatriz1_4Descendente \n");
	printf("7.recorrerMatriz2_3Ascendente \n");
	printf("8.recorrerMatriz2_3Descendente \n");
	printf("9.recorrerMatriz1Ascendente \n");
	printf("10.recorrerMatriz1Descendente \n");
	printf("11.recorrerMatriz2Ascendente \n");
	printf("12.recorrerMatriz2Descendente \n");
	printf("13.recorrerMatriz3Ascendente \n");
	printf("14.recorrerMatriz3Descendente \n");
	printf("15.recorrerMatriz4Ascendente \n");
	printf("16.recorrerMatriz4Descendente \n");
	printf("17.recorrerMatrizPrincipal \n");
	printf("18.recorrerMatrizInvertida \n");
	scanf("%d", &opcion);
	
	switch(opcion){
		case 1: 
			recorrerMatriz1_2Ascendente(matriz);
			break;
		case 2:
			recorrerMatriz1_2Descendente(matriz);
			break;
		case 3:
			recorrerMatriz3_4Ascendente(matriz);
			break;
		case 4:
			recorrerMatriz3_4Descendente(matriz);
			break;
		case 5:
			recorrerMatriz1_4Ascendente(matriz);
			break;
		case 6:
			recorrerMatriz1_4Descendente(matriz);
			break;
		case 7:
			recorrerMatriz2_3Ascendente(matriz);
			break;
		case 8:
			recorrerMatriz2_3Descendente(matriz);
			break;
		case 9:
			recorrerMatriz1Ascendente(matriz);
			break;
		case 10:
			recorrerMatriz1Descendente(matriz);
			break;
		case 11:
			recorrerMatriz2Ascendente(matriz);
			break;
		case 12:
			recorrerMatriz2Descendente(matriz);
			break;
		case 13:
			recorrerMatriz3Ascendente(matriz);
			break;
		case 14:
			recorrerMatriz3Descendente(matriz);
			break;
		case 15:
			recorrerMatriz4Ascendente(matriz);
			break;
		case 16:
			recorrerMatriz4Descendente(matriz);
			break;
		case 17:
			recorrerDiagonalPrincipal(matriz);
			break;
		case 18:
			recorrerDiagonalInvertida(matriz);
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

void recorrerMatriz1_2Ascendente(int arreglo[][N]){
	for(int i=0; i<N; i++){
		for(int j=0; j<N-i; j++){
			printf("%d	", arreglo[i][j]);
		}
		printf("\n");
	}
}

void recorrerMatriz1_2Descendente(int arreglo[][N]){
	for(int i=N-1; i>=0; i--){
		for(int j=0; j<=N-1-i; j++){
			printf("%d	", arreglo[i][j]);
		}
		printf("\n");
	}
}

void recorrerMatriz3_4Ascendente(int arreglo[][N]){
	for(int i=0; i<N; i++){
		for(int j=N-1-i; j<N; j++){
			printf("%d	", arreglo[i][j]);
		}
		printf("\n");
	}
}

void recorrerMatriz3_4Descendente(int arreglo[][N]){
	for(int i=N-1; i>=0; i--){
		for(int j=N-1-i; j<N; j++){
			printf("%d	", arreglo[i][j]);
		}
		printf("\n");
	}
}

void recorrerMatriz1_4Ascendente(int arreglo[][N]){
	for(int i=0; i<N; i++){
		for(int j=0; j<=i; j++){
			printf("%d	", arreglo[i][j]);
		}
		printf("\n");
	}
}

void recorrerMatriz1_4Descendente(int arreglo[][N]){
	for(int i=N-1; i>=0; i--){
		for(int j=0; j<=i; j++){
			printf("%d	", arreglo[i][j]);
		}
		printf("\n");
	}
}

void recorrerMatriz2_3Ascendente(int arreglo[][N]){
	for(int i=0; i<N; i++){
		for(int j=0+i; j<N; j++){
			printf("%d	", arreglo[i][j]);
		}
		printf("\n");
	}
}

void recorrerMatriz2_3Descendente(int arreglo[][N]){
	for(int i=N-1; i>=0; i--){
		for(int j=i; j<N; j++){
			printf("%d	", arreglo[i][j]);
		}
		printf("\n");
	}
}

void recorrerMatriz1Ascendente(int arreglo[][N]){
	for(int i=0; i<(N-1)/2; i++){
		for(int j=0; j<=i; j++){
			printf("%d	", arreglo[i][j]);
		}
		printf("\n");
	}

	for(int i=(N-1)/2; i<N; i++){
		for(int j=0; j<N-i; j++){
			printf("%d	", arreglo[i][j]);
		}
		printf("\n");
	}
}

void recorrerMatriz1Descendente(int arreglo[][N]){
	for(int i=N-1; i>(N-1)/2; i--){
		for(int j=0; j<N-i; j++){
			printf("%d	", arreglo[i][j]);
		}
		printf("\n");
	}
	
	for(int i=(N-1)/2; i>=0; i--){
		for(int j=0; j<=i; j++){
			printf("%d	", arreglo[i][j]);
		}
		printf("\n");
	}
}

void recorrerMatriz2Ascendente(int arreglo[][N]){
	for(int i=0; i<=(N-1)/2; i++){
		for(int j=i; j<N-i; j++){
			printf("%d	", arreglo[i][j]);
		}
		printf("\n");
	}
}

void recorrerMatriz2Descendente(int arreglo[][N]){
	for(int i=(N-1)/2; i>=0; i--){
		for(int j=i; j<N-i; j++){
			printf("%d	", arreglo[i][j]);
		}
		printf("\n");
	}
}

void recorrerMatriz3Ascendente(int arreglo[][N]){
	for(int i=0; i<(N-1)/2; i++){
		for(int j=N-1-i; j<N; j++){
			printf("%d	", arreglo[i][j]);
		}
		printf("\n");
	}

	for(int i=(N-1)/2; i<N; i++){
		for(int j=i; j<N; j++){
			printf("%d	", arreglo[i][j]);
		}
		printf("\n");
	}
}

void recorrerMatriz3Descendente(int arreglo[][N]){
	for(int i=N; i>(N-1)/2; i--){
		for(int j=i; j<N; j++){
			printf("%d	", arreglo[i][j]);
		}
		printf("\n");
	}

	for(int i=(N-1)/2; i>=0; i--){
		for(int j=N-1-i; j<N; j++){
			printf("%d	", arreglo[i][j]);
		}
		printf("\n");
	}
}

void recorrerMatriz4Ascendente(int arreglo[][N]){
	for(int i=(N-1)/2; i<N; i++){
		for(int j=N-1-i; j<=i; j++){
			printf("%d	", arreglo[i][j]);
		}
		printf("\n");
	}
}

void recorrerMatriz4Descendente(int arreglo[][N]){
	for(int i=N-1; i>=(N-1)/2; i--){
		for(int j=N-1-i; j<=i; j++){
			printf("%d	", arreglo[i][j]);
		}
		printf("\n");
	}
}

void recorrerDiagonalPrincipal(int arreglo[][N]){
	for(int i=0; i<N; i++){
		printf("%d \n", arreglo[i][i]);
	}
}

void recorrerDiagonalInvertida(int arreglo[][N]){
	for(int i=0; i<N; i++){
		for(int j=N-1-i; j<N-i; j++){
			printf("%d \n", arreglo[i][j]);
		}
	}
}
