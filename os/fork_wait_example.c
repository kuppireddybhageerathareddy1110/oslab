#include <stdio.h>
#include <sys/wait.h>
#include <unistd.h>

int main() {
    pid_t q;
    q = fork(); // Create a new process

    if (q < 0) {
        // Fork failed
        printf("Child process not created \n");
    } else if (q == 0) {
        // Child process
        printf("Child process Id : %d \n", getpid());
        printf("Parent process Id : %d \n", getppid());
    } else {
        // Parent process
        wait(NULL); // Wait for the child process to finish
        printf("Parent Id : %d \n", getpid());
        printf("Child is : %d \n", q);
    }

    return 0;
}
