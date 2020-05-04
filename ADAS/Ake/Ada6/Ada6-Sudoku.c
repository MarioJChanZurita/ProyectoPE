#include <stdio.h>
#include <stdlib.h>
#include <time.h>

//Estructuras
typedef struct tableroMenor
{
    int matriz[3][3];
}tableroMenor;

typedef struct sodoku
{
    tableroMenor tableroCompleto[3][3];
}sodoku;

//Prototipos
sodoku llenadoTablero();
void imprimirSodoku(sodoku sodku);
int comprobarTableroInterior(sodoku sodku);
int comprobarFilas(sodoku sodku);
void comprobarColumnas(sodoku sodku);

int main(){


    //Variable y apuntador de tipo sodoku
    sodoku sodku1;

    sodku1 = llenadoTablero();
    imprimirSodoku(sodku1);
    printf("1 si es sodoku, 0 si no lo es\n");
    printf("Por tableros interiores: %d\n", comprobarTableroInterior(sodku1));
    printf("Por filas: %d\n", comprobarTableroInterior(sodku1));
    printf("Por columnas: %d\n", comprobarTableroInterior(sodku1));

    return 0;
}


sodoku llenadoTablero(){
    //Generador de numero aleatorios
    time_t t;
    srand((unsigned) time(&t));
    sodoku sodku;
    
    //i,j son de la tablaCompleta    
    for(int i=0; i<3; i++){
        for(int j=0; j<3; j++){
            //h,k son de la matriz interna(tabla menor)
            for(int h=0; h<3; h++){
                for(int k=0; k<3; k++){
                    sodku.tableroCompleto[i][j].matriz[h][k] = rand() %9 + 1;
                }
            }
        }
    }
    return sodku;
}

void imprimirSodoku(sodoku sodku){
    /*  i,j representan las celdas de tablaCompleta
        h,k representan las celdas de tablaMenor
    */
    for(int i=0; i<3; i++){
        for(int h=0; h<3; h++){
            for(int j=0; j<3; j++){
                for(int k=0; k<3; k++){
                    printf("%d ", sodku.tableroCompleto[i][j].matriz[h][k]);
                }
            }
            printf("\n");
        }
    }
}

int comprobarTableroInterior(sodoku sodku){
    int num1, num2, num3, num4, num5, num6, num7, num8, num9, bandera;
    bandera = 1; //Si bandera 1 si es sodoku, 0 si no es sodoku

    //i,j son de la tablaCompleta
    for(int i=0; i<3; i++){
        for(int j=0; j<3; j++){
            //Se inicializa para cada celda de tablaCompleta
            num1 =0; num2=0; num3=0; num4=0; num5=0; num6=0; num7=0; num8=0; num9=0;
            //h,k son de la matriz interna(tabla menor)
            for(int h=0; h<3; h++){
                for(int k=0; k<3; k++){
                    //Contamos las apariciones de los numeros por tabla menor
                    switch (sodku.tableroCompleto[i][j].matriz[h][k]){
                        case 1:
                            num1++;
                            break;
                        case 2:
                            num2++;
                            break;                        
                        case 3:
                            num3++;
                            break;                        
                        case 4:
                            num4++;
                            break;                        
                        case 5:
                            num5++;
                            break;                        
                        case 6:
                            num6++;
                            break;                        
                        case 7:
                            num7++;
                            break;                        
                        case 8:
                            num8++;
                            break;                        
                        case 9:
                            num9++;
                            break;                        
                    }
                }
            }
            //Comprobamos que algun numero no aparezca mas de 1 vez
            if( num1 || num2 || num3 || num4 || num5 || num6 || num7 || num8 || num9 >1){
                 //Si el numero se repitio, forzamos la salida de los ciclos
                j=3;
                i=3;
                //Declaramos que no es sodoku por tabla menor
                bandera=0;
            }
        }
    }

    return bandera;
}

int comprobarFilas(sodoku sodku){
    int num1, num2, num3, num4, num5, num6, num7, num8, num9, bandera;
    num1 =0; num2=0; num3=0; num4=0; num5=0; num6=0; num7=0; num8=0; num9=0;
    bandera = 1; //Si bandera 1 si es sodoku, 0 si no es sodoku

    /*  i,j representan las celdas de tablaCompleta
        h,k representan las celdas de tablaMenor
    */
    for(int i=0; i<3; i++){
        for(int h=0; h<3; h++){
            for(int j=0; j<3; j++){
                for(int k=0; k<3; k++){
                    //Contamos las apariciones de cada numero por fila
                    switch (sodku.tableroCompleto[i][j].matriz[h][k]){
                        case 1:
                            num1++;
                            break;
                        case 2:
                            num2++;
                            break;                        
                        case 3:
                            num3++;
                            break;                        
                        case 4:
                            num4++;
                            break;                        
                        case 5:
                            num5++;
                            break;                        
                        case 6:
                            num6++;
                            break;                        
                        case 7:
                            num7++;
                            break;                        
                        case 8:
                            num8++;
                            break;                        
                        case 9:
                            num9++;
                            break;                        
                    }
                }
            }
            //Comprobamos que algun numero no aparezca mas de 1 vez
            if( num1 || num2 || num3 || num4 || num5 || num6 || num7 || num8 || num9 >1){
                //Si durante la fila el numero se repitio, forzamos la salida de los ciclos
                h=3;
                i=3;
                //Declaramos que no es sodoku por filas
                bandera=0; 
            }else{
                //Si el numero no se repitio, se reinician los valores
                num1 =0; num2=0; num3=0; num4=0; num5=0; num6=0; num7=0; num8=0; num9=0;
            }
        }
    }
}

void comprobarColumnas(sodoku sodku){
    int num1, num2, num3, num4, num5, num6, num7, num8, num9, bandera;
    num1 =0; num2=0; num3=0; num4=0; num5=0; num6=0; num7=0; num8=0; num9=0;
    bandera = 1; //Si bandera 1 si es sodoku, 0 si no es sodoku

    /*  i,j representan las celdas de tablaCompleta
        h,k representan las celdas de tablaMenor
    */
    for(int j=0; j<3; j++){
        for(int k=0; k<3; k++){
            for(int i=0; i<3; i++){
                for(int h=0; h<3; h++){
                    //Contamos las apariciones de cada numero por fila
                    switch (sodku.tableroCompleto[i][j].matriz[h][k]){
                        case 1:
                            num1++;
                            break;
                        case 2:
                            num2++;
                            break;                        
                        case 3:
                            num3++;
                            break;                        
                        case 4:
                            num4++;
                            break;                        
                        case 5:
                            num5++;
                            break;                        
                        case 6:
                            num6++;
                            break;                        
                        case 7:
                            num7++;
                            break;                        
                        case 8:
                            num8++;
                            break;                        
                        case 9:
                            num9++;
                            break;                        
                    }
                }
            }
            //Comprobamos que algun numero no aparezca mas de 1 vez
            if( num1 || num2 || num3 || num4 || num5 || num6 || num7 || num8 || num9 >1){
                //Si durante la columna el numero se repitio, forzamos la salida de los ciclos
                k=3;
                j=3;
                //Declaramos que no es sodoku por columna
                bandera=0; 
            }else{
                //Si el numero no se repitio, se reinician los valores
                num1 =0; num2=0; num3=0; num4=0; num5=0; num6=0; num7=0; num8=0; num9=0;
            }
        }
    }
}
