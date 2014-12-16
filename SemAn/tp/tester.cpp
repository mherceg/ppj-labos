#include<iostream>
#include<cstdio>
#include<vector>
#include<cstdlib>
using namespace std;

int main(int argc, char **argv){
	vector<int> v;
	if (argc < 2){
		for (int i=0; i<31; ++i){
			v.push_back(i);
		}
	}
	else{
		for (int i = 1; i < argc; ++i){
			v.push_back(atoi(argv[i]));
		}
	}

	//system("cd ..\njavac SemantickiAnalizator.java\n");
	for (int j = 0; j < v.size(); ++j){
		int i = v[j];
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
		
