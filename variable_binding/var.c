#include <stdio.h>

int main()
{
    int x;
    int ar[10];

    x = 10;
    // Cannot do this, ar is statically bound ar = &x
    // Cannot do this: &x = ar
    // addresses are statically bound in C/C++
    // Static: Names, Addresses, Types
    // Dynamic: Values
    printf("%d\n", x);
}