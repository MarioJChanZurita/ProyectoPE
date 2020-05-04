#include <stdio.h>
#include <stdlib.h>
#include <time.h>

//ESTRUCTURAS
typedef struct tableroMenor{
    int matrices[3][3];
} tableroMenor;

typedef struct sudoku{
    tableroMenor tableroCompleto[3][3];
} sudoku;

//PROTOTIPOS 
void llenarSudoku(sudoku *sudokuCompleto);
void imprimirSudoku(sudoku *sudokuCompleto);
void verificarTablero(sudoku *sudokuCompleto);


//MAIN
int main(){

    sudoku tablero;
    srand(time(NULL));
    llenarSudoku(&tablero);
    imprimirSudoku(&tablero);
    verificarTablero(&tablero);

    return 0;
}

//FUNCIONES
void llenarSudoku(sudoku *sudokuCompleto){

    for(int z=0; z<3; z++){
        for(int w=0; w<3; w++){

            for(int i=0; i<3; i++){
                for(int j=0; j<3; j++){
                    (*sudokuCompleto).tableroCompleto[z][w].matrices[i][j] = rand() %9 + 1;
                }
            } 

        }
    }
    
}

void imprimirSudoku(sudoku *sudokuCompleto){

    for(int z=0; z<3; z++){
        for(int w=0; w<3; w++){

            for(int i=0; i<3; i++){
                for(int j=0; j<3; j++){
                    printf("%d  ", (*sudokuCompleto).tableroCompleto[z][w].matrices[i][j]);
                }
                printf("    ");
            } 
			printf("\n");
        }
        printf("\n");
    }

}

void verificarTablero(sudoku *sudokuCompleto){
    int correcto=1;

    while(correcto=1){
    
    //COMPROBAR MATRIZ 3X3
    for(int z=0; z<3; z++){
    	for(int w=0; w<3; w++){

            int n = 0;
            int matriz3x3[9];
            //METER LOS VALORES DE LA MATRIZ DENTRO DE UN ARREGLO
            for(int i=0; i<3; i++){
    			for(int j=0; j<3; j++){

                    matriz3x3[n]=(*sudokuCompleto).tableroCompleto[z][w].matrices[i][j];
                    n+=1;
    				
                }
    				
    		}
            //VERIFICAR VALORES 
            for(int x=0; x<9; x++){
                for(int y=0; y<9; y++){
                    if(matriz3x3[x]==matriz3x3[y] && x!=y){
                        correcto=0;
                    }  
                    if(correcto==0){
                        break;
                    }
                }
            }

        }
    		
    }

    
    //COMPROBAR FILA
    for(int z=0; z<3; z++){
    	for(int w=0; w<3; w++){

            int n = 0;
            int fila[9];
            //METER LOS VALORES DE LA FILA DENTRO DE UN ARREGLO
            for(int i=0; i<3; i++){
    			for(int j=0; j<3; j++){

                    fila[n]=(*sudokuCompleto).tableroCompleto[z][i].matrices[w][j];
                    n+=1;
    				
                }
    				
    		}
            //VERIFICAR VALORES 
            for(int x=0; x<9; x++){
                for(int y=0; y<9; y++){
                    if(fila[x]==fila[y] && x!=y){
                        correcto=0;
                    } 
                    if(correcto==0){
                        break;
                    }
                }
            }

        }
    		
    }

            
    //COMPROBAR COLUMNA 
    for(int z=0; z<3; z++){
    	for(int w=0; w<3; w++){

            int n = 0;
            int columna[9];
            //METER LOS VALORES DE LA COLUMNA DENTRO DE UN ARREGLO
            for(int i=0; i<3; i++){
    			for(int j=0; j<3; j++){

                    columna[n]=(*sudokuCompleto).tableroCompleto[w][z].matrices[j][i];
                    n+=1;
    				
                }
    				
    		}
            //VERIFICAR VALORES 
            for(int x=0; x<9; x++){
                for(int y=0; y<9; y++){
                    if(columna[x]==columna[y] && x!=y){
                        correcto=0;
                    }  
                    if(correcto==0){
                        break;
                    }
                }
            }

        }
    		
    }

    break;
    }


	//SALIDA
	if(correcto==1){
		printf("Es un sudoku");
	}
	else if (correcto==0){
        printf("No es un sudoku");
    }
    
	
}

/*

Mario Jesús Chan Zurita 

*/