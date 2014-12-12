#include<iostream>
#include<cstdio>
#include<cstdlib>
using namespace std;

int main(){
	system("cd ..\njavac SemantickiAnalizator.java\n");
	for (int i = 0; i < 31; ++i){
		printf("\n\nTest br %d\n",i);
		char a[100];
		sprintf(a,"java -cp .. SemantickiAnalizator > moj.out < %02d/test.in\n",i);
		system(a);
		sprintf(a,"diff moj.out %02d/test.out\n",i);
		system(a);
		printf("------------------\n");
	}
	return 0;
}
		
