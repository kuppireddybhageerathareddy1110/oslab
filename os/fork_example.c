#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>

int main() {
    fork();  // Create a new process
    printf("Welcome to VITAP \n");
    return 0;
}
