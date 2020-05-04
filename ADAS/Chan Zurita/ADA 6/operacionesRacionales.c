#include <stdio.h>
#include <math.h>

typedef struct Racional{
	int numerador;
	int denominador;
} racional;

void leerRacional(racional *r1);
racional sumarRacionales(racional r1, racional r2);
racional restarRacionales(racional r1, racional r2);
racional multiplicarRacionales(racional r1, racional r2);
racional dividirRacionales(racional r1, racional r2);
void simplificarRacional(racional *r3);
void imprimirRacional(racional r3);

int main(){
	
	racional r1, r2;
		
	leerRacional(&r1);
	leerRacional(&r2);
		
	sumarRacionales(r1,r2);
	restarRacionales(r1,r2);
	multiplicarRacionales(r1,r2);
	dividirRacionales(r1,r2);
	
	printf("Suma: \n");
	imprimirRacional(sumarRacionales(r1,r2));
	printf("Resta: \n");
	imprimirRacional(restarRacionales(r1,r2));
	printf("Multiplicación: \n");
	imprimirRacional(multiplicarRacionales(r1,r2));
	printf("División: \n");
	imprimirRacional(dividirRacionales(r1,r2));
	
	return 0;
}

void leerRacional(racional *r1){
	do{
		printf("Racional Numerador: ");
		scanf("%d", &r1->numerador);
		printf("Racional Denominador: ");
		scanf("%d", &r1->denominador);
	}while(r1->denominador == 0 || r1->numerador == 0);
}


racional sumarRacionales(racional r1, racional r2){
	racional r3;
	r3.numerador = (r1.numerador * r2.denominador) + (r1.denominador * r2.numerador);
	r3.denominador = (r1.denominador * r2.denominador);
	simplificarRacional(&r3);
	return r3; 
}


racional restarRacionales(racional r1, racional r2){
	racional r3;
	r3.numerador = (r1.numerador * r2.denominador) - (r1.denominador * r2.numerador);
	r3.denominador = (r1.denominador * r2.denominador);
	simplificarRacional(&r3);
	return r3;
}

racional multiplicarRacionales(racional r1, racional r2){
	racional r3;
	r3.numerador = r1.numerador * r2.numerador;
	r3.denominador = r1.denominador * r2.denominador;
	simplificarRacional(&r3);
	return r3;
}

racional dividirRacionales(racional r1, racional r2){
	racional r3;
	r3.numerador = r1.numerador * r2.denominador;
	r3.denominador = r1.denominador * r2.numerador;
	simplificarRacional(&r3);
	return r3;
} 

void simplificarRacional(racional *r3){
	for(int i=0; i<r3->denominador; i++){
		if(r3->numerador%i==0 && r3->denominador%i==0){
			r3->numerador = r3->numerador/i;
			r3->denominador = r3->denominador/i;
			i=1;
		}
	}
}

void imprimirRacional(racional r3){
	printf("%d/%d \n", r3.numerador, r3.denominador);
}
