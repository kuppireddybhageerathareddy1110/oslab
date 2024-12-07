#include <stdio.h>
#include <sys/wait.h>
#include <unistd.h>

int main() {
    int i, q;
    q = fork(); // Create a new process

    if (q == 0) {
        // This block is executed by the child process
        sleep(2); // Simulate some work with a delay
        printf("Child process started \n");
        for (i = 0; i < 10; i++) {
            printf("%d \n", i); // Print numbers from 0 to 9
        }
        printf("Child process terminated \n");
    } else {
        // This block is executed by the parent process
        wait(NULL); // Wait for the child process to finish
        printf("Parent process started \n");
        printf("Parent process terminated \n");
    }

    return 0;
}
