#include <stdio.h>
#include <stdlib.h>
#include <string.h>


typedef struct alarma{
    char hora[6];
    char medicina[15];
}alarma;

typedef struct nodo{
    alarma datos;
    int numNodo;
    struct nodo *sig;
} node;

//Prototipos
node *agregar(node *lista);
void *recorrerNodo(node *lista);
node *borrarEspecifico(node *lista, int numeroNodo);
void imprimirNodos(node *lista);

int main(){
    //Inicializamos los nodos
    node *lista =  NULL;
    alarma datos;
    int numeNodo;
    int opcion = 1;

    while(opcion ==1){
        printf("Ingrese 1 para agregar un nodo \n");
        printf("Ingrese 2 para imprimir todos los nodos \n");
        printf("Ingrese 3 para recorrer los nodos  \n");
        printf("Ingrese 4 para eliminar un nodo especifico \n");
        printf("Ingrese otro numero para terminar \n");
        scanf("%d", &opcion);
        switch (opcion){
            case 1: 
                lista = agregar(lista);
                break;
            case 2: 
                imprimirNodos(lista);
                opcion =1;
                break;
            case 3: 
                recorrerNodo(lista);
                break;
            case 4:
                printf("Ingrese el nodo numero de nodo a borrar\n");
                scanf("%d", &numeNodo);
                printf("Nodos antes de borrar\n");
                imprimirNodos(lista);
                borrarEspecifico(lista, numeNodo);
                printf("Nodos despues de borrar\n");
                imprimirNodos(lista);
                opcion =1;
                break;
            default:
                break;
        }
    }

    return 0;
}

node *agregar(node *lista){
    node *nuevoNodo, *ultimoNodo;
    int numeroNodo = 1;
    char medicina[15], tiempo[6];
    //Reservo la memoria para el nuevo nodo
    nuevoNodo = (node *) malloc(sizeof(node));
    //Asignamos los datos al nodo
    printf("Ingrese el nombre de la medicina\n");
    scanf("%s", &medicina);
    strcpy(nuevoNodo->datos.medicina, medicina);
    printf("Ingrese la hora de forma: 00:00\n");
    scanf("%s", &tiempo);
    strcpy(nuevoNodo ->datos.hora, tiempo);
    nuevoNodo->sig = NULL; //El ultimo nodo no puede apuntar a otro nodo

    if(lista == NULL){
        //Si no existen nodos, se crea uno nuevo
        lista = nuevoNodo;
        nuevoNodo->numNodo = numeroNodo;
    }else{
        //Cuando ya existan nodos, se agregan al final
        //ultimoNodo es una variable temporal para estar en el ultimo nodo
        ultimoNodo = lista; //Lo situamos en el primer nodo
        //Vemos si estamos en el ultimo nodo (condicion)
        while(ultimoNodo->sig != NULL){
            //Si no estamos en el ultimo nodo, nos pocisionamos en el siguiente nodo
            ultimoNodo = ultimoNodo ->sig;
            numeroNodo++; 
        }
        //Una vez en el ultimo nodo, asignamos la direccion del nuevo nodo al ultimo nodo
        ultimoNodo->sig = nuevoNodo;
        //Al verificar que esta en el ultimo nodo, no actualzia correctamente el numero del nodo
        nuevoNodo->numNodo = numeroNodo+1; 
    }

    return lista;
}
void *recorrerNodo(node *lista){
    int respuesta =1;
    while(respuesta = 1){
        if(lista != NULL){
                    printf("Nodo: %d  Medicina: %s Hora: %s \n ingrese 1 para continuar con el siguiente elementos \n", lista->numNodo, lista->datos.medicina,  lista->datos.hora);
        //Nos pasamos a la direccion del siguiente nodo
        lista = lista->sig;
        scanf("%d", &respuesta);
        }else{
            printf("No hay mas nodos para mostrar\n");
        }

    }

    return lista;
}

node *borrarEspecifico(node *lista, int numeroNodo){
    node *temporal;
    temporal = lista;

    //Si el primer elemento que queremos borrar es el primero, a lista le asignamos la direccion del sig nodo
    if(temporal->numNodo == numeroNodo){
        lista = lista->sig;
        free(temporal); //Liberamos la memoria del nodo borrado
    }else{
        //Con este ciclo encontramos el nodo anterior del que va a ser borrado
        while((temporal ->sig)->numNodo != numeroNodo){
            temporal = temporal->sig;
        }
        /*
        al momento de cambiar las direcciones de memoria del nodo anterior y el siguiente del nodo
        a eliminar se pierde la direccion de memoria del nodo a eliminar, lo que hace que no podamos
        liberar la memoria de ese nodo eliminado asi que temporal2 sera la direccion 
        del nodo que va a ser eliminado/liberado
        */
        node *temporal2 = temporal->sig;
        temporal->sig = (temporal->sig)->sig;
        free(temporal2);
    }
    return lista;
}

void imprimirNodos(node *lista){
    //Mientras la direccion no sea nula (ya no hay mas nodos), imprimos los datos del nodo
    while (lista != NULL){
        printf("Nodo: %d  Medicina: %s Hora: %s \n", lista->numNodo, lista->datos.medicina,  lista->datos.hora);
        //Nos pasamos a la direccion del siguiente nodo
        lista = lista->sig;
    }
    printf("\n\n\n");
}