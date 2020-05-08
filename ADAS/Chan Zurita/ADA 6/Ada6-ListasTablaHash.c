#include <stdio.h>
#include <stdlib.h>
#include <string.h>

//ESTRUCTURA
typedef struct alarma{
    char hora[6];
    char medicina[15];
}alarma;

typedef struct nodo{
    alarma *datos;
    int numNodo;
    struct nodo *sig;
} Nodo;

//PROTOTIPOS
int funcionHash(char cadena[], int size);
Nodo *insertarAlarma(Nodo *tabla,int size);
void *borrarHash(Nodo *tabla, int size);
void imprimirNodo(Nodo *tabla, int size);

//MAIN
int main(){
    alarma datos;
    int numeNodo;
    int opcion = 1;

    //Creacion de tabla hash
    int tamanio;
    Nodo *tablaHash;
    printf("Tamaño de la tabla hash: \n");
    scanf("%d", &tamanio);
    tablaHash = (Nodo*)malloc(tamanio*(sizeof(Nodo)));
    if(tablaHash == NULL){
        printf("Memoria insuficiente");
    }else{
        for(int i=0; i<tamanio; i++){
            tablaHash[i].numNodo=-1;
            tablaHash[i].datos=(alarma*)malloc(sizeof(alarma));
        }
    }

    while(opcion ==1){
        printf("Ingrese 1 para agregar alarma \n");
        printf("Ingrese 2 para imprimir una alarma \n");
        printf("Ingrese 3 para eliminar una alarma \n");
        printf("Ingrese otro numero para terminar \n");
        scanf("%d", &opcion);
        switch (opcion){
            case 1: 
                tablaHash = insertarAlarma(tablaHash, tamanio);
                break;
            case 2: 
                imprimirNodo(tablaHash, tamanio);
                opcion =1;
                break;
            case 3:
                borrarHash(tablaHash, tamanio);
                opcion =1;
                break;
            default:
                break;
        }
    }

    printf("\nFin de operacion");

    return 0;
}

//FUNCIONES
int funcionHash(char cadena[], int size){
    //Obtener codigo ASCII del nombre del medicamento
    int tamanioCadena,posicion,valor;
    tamanioCadena = strlen(cadena);
    for(int i=0; i<=tamanioCadena; i++){
        valor+=cadena[i];
    }
    //funcion hash
    posicion = valor % size;
    return posicion;
}

Nodo *insertarAlarma(Nodo *tabla,int size){
    Nodo *nuevoNodo, *ultimoNodo;
    char medicina[15], tiempo[6];
    nuevoNodo=(Nodo*)malloc(sizeof(Nodo));
    printf("Ingrese el nombre de la medicina\n");
    scanf("%s", &medicina);
    strcpy(nuevoNodo->datos->medicina, medicina);
    printf("Ingrese la hora de forma: 01:00\n");
    scanf("%s", &tiempo);
    strcpy(nuevoNodo->datos->hora, tiempo);
    nuevoNodo->sig=NULL;

    int posicion = funcionHash(medicina, size);


    if (tabla[posicion].numNodo==-1){
        strcpy(tabla[posicion].datos->medicina, medicina);
        strcpy(tabla[posicion].datos->hora, tiempo);
        tabla[posicion].numNodo=0;
        tabla[posicion].sig=NULL;
    }
    
    if(tabla[posicion].sig==NULL){
        tabla[posicion].sig=nuevoNodo;
        nuevoNodo->numNodo=1;
    }else{
        int acumulador=0;
        ultimoNodo=tabla[posicion].sig;
        while(ultimoNodo->sig != NULL){
            ultimoNodo=ultimoNodo->sig;
            acumulador++;
        }
        ultimoNodo->sig=nuevoNodo;
        nuevoNodo->numNodo=2+acumulador;
    }
    return tabla;
}

void *borrarHash(Nodo *tabla, int size){
    Nodo *temporal, *anterior;
    char medicina[15];
    printf("Ingrese el nombre de la alarma que desea borrar");
    scanf("%s", &medicina);
    int posicion = funcionHash(medicina, size);

    if(tabla[posicion].numNodo==-1){
        printf("Alarma no encontrada");
    }

    if(tabla[posicion].sig==NULL){
        if(strcmp(tabla[posicion].datos->medicina,medicina)==0){
            tabla[posicion].numNodo=-1;
            printf("Borrado");
        }
    }else{
        temporal=tabla[posicion].sig;
        while(temporal->datos->medicina != medicina){
            temporal=temporal->sig;
        }
        Nodo *temporal2 = temporal;
        temporal = temporal->sig;
        free(temporal2);
        printf("Borrado");
    }

}

void imprimirNodo(Nodo *tabla, int size){
    Nodo *temporal;
    char medicina[15];
    printf("Introduzca le nombre de la alarma a imprimir");
    scanf("%s",&medicina);
    int posicion = funcionHash(medicina, size);

if(tabla[posicion].numNodo==-1){
        printf("Alarma no encontrada");
    }

    if(tabla[posicion].sig==NULL){
        if(strcmp(tabla[posicion].datos->medicina,medicina)==0){
            printf("Medicina: %s Hora: %s", tabla[posicion].datos->medicina, tabla[posicion].datos->hora);
        }
    }else if(tabla[posicion].sig!=NULL){
        temporal=tabla[posicion].sig;
        while(temporal->datos->medicina != medicina){
            temporal=temporal->sig;
        }
        printf("Medicina: %s Hora: %s", temporal->datos->medicina, temporal->datos->hora);
    }else{
        printf("Alarma no encontrada");
    }
    
}

/*
Mario Jesus Chan Zurita
*/

//checar si puedo imprimir todos los nodos 
//checar funcionamiento de arreglos dinamicos 



