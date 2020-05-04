#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#define N 2

struct racional{
	int numerador;
	int denominador;
};

struct racional leerRacional();
struct racional sumarRacionales(struct racional r1,struct racional r2);
struct racional restarRacionales(struct racional r1,struct racional r2);
struct racional multiplicarRacionales(struct racional r1,struct racional r2);
struct racional divisionRacionales(struct racional r1,struct racional r2);
struct racional simplificar(struct racional r1);
void imprimirRacionales(struct racional r3);

int main(){
	
	struct racional r1, r2;
	
	r1 = leerRacional();
	r2 = leerRacional();
	
	printf("Suma \n");
	imprimirRacionales(simplificar(sumarRacionales(r1,r2)));
	
	printf("Multiplicación \n");
	imprimirRacionales(simplificar(multiplicarRacionales(r1,r2)));
	
	printf("Division \n");
	imprimirRacionales(simplificar(divisionRacionales(r1,r2)));
	
	printf("Resta \n");
	imprimirRacionales(simplificar(restarRacionales(r1,r2)));
	
	return 0;
	
}
	
struct racional leerRacional(){
	struct racional r1;
	do{
		printf("Introduzca el numerador y denominador de la primera fraccion: \n");
		scanf("%d",&r1.numerador);
		scanf("%d",&r1.denominador);
	}while(r1.denominador == 0 && &r1.denominador == 0);
	
	return r1;
	
}


struct racional sumarRacionales(struct racional r1,struct racional r2){
	struct racional r3;
	
	r3.numerador = (r1.numerador*r2.denominador) + (r2.numerador*r1.denominador);
	r3.denominador = r1.denominador*r2.denominador;
	
	return r3;
}

struct racional restarRacionales(struct racional r1,struct racional r2){
	struct racional r3;
	
	r3.numerador = (r1.numerador*r2.denominador) - (r2.numerador*r1.denominador);
	r3.denominador = r1.denominador*r2.denominador;
	
	return r3;
}
	
struct racional multiplicarRacionales(struct racional r1,struct racional r2){
	struct racional r3;
	
	r3.numerador = r1.numerador*r2.numerador;
	r3.denominador = r1.denominador*r2.denominador;
	
	return r3;
}
	
struct racional divisionRacionales(struct racional r1,struct racional r2){
	struct racional r3;
	
	r3.numerador = r1.numerador*r2.denominador;
	r3.denominador = r1.denominador*r2.numerador;
	
	return r3;
}
	
struct racional simplificar(struct racional r3){
	int i,menor;
	
	if(r3.numerador>r3.denominador){
		menor = r3.denominador;
	}else if(r3.denominador>r3.numerador){
		menor = r3.numerador;
	}
	
	for(i=menor;i<=0;i--){
		if(r3.numerador%i == 0 && r3.denominador%i == 0){
			r3.numerador=r3.numerador/i;
			r3.denominador=r3.denominador/i;
		}  
	}
	
	return r3;
}

void imprimirRacionales(struct racional r3){
	
	simplificar(r3);
	printf("%d|%d \n", r3.numerador,r3.denominador);
	
}

