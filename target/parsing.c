/* parsing.c
 * 
 * Demonstrate alternative to using the scanning functions
 * for breaking up a fixed format text file.
 *
 * Created by Sally Goldin, 21 November 2022
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main()
{
	char inputfile[64];  // name of input file
 	FILE* pIn = NULL;    // file pointer for reading input file
	double temperature[30];
	int humidity[30];
	int i = 0;
	int count = 0;
	char input[128];
	printf("Enter name of input file: ");
	fgets(input,sizeof(input),stdin);
	sscanf(input,"%s",inputfile);
	pIn = fopen(inputfile,"r");
	if (pIn == NULL)
	{
		printf("Error! Cannot open input file %s\n",inputfile);
		exit(1);
	}
	while ((fgets(input,sizeof(input),pIn) != NULL) && 
       (i < 30))
	{
		count++;   // line counter
    	char *location = strstr(input,"Temperature=");
    	if (location != NULL)
    	{
    		sscanf(location+strlen("Temperature="),"%lf",&temperature[i]);
    	}
    	else 
    	{
    		printf("Invalid data format on line %d - missing 'Temperature='\n",count);
    		continue;
    	}
   		location = strstr(input,"Humidity=");
   		if (location != NULL)
   		{
   			sscanf(location+strlen("Humidity="),"%d",&humidity[i]);
   		}
		else 
    	{
    		printf("Invalid data format on line %d - missing 'Humidity='\n",count);
    		continue;
    	}
    	i = i+1;
	}
	printf("Read %d lines, %d valid data sets\n",count,i);
	fclose(pIn);
	count = i;
	for (i=0; i < count; i++)
		printf("%2d: %5.2lf %3d\n",i+1,temperature[i],humidity[i]);
}