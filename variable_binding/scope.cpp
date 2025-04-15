#include <iostream>

int main() {
    int x = 10;
    {
        int x = 20;
        {
            double x = 2.5;
            std::cout << x << std::endl;
        }
        std::cout << x << std::endl;
    }
    std::cout << x << std::endl;

    // here's a puzzle
    for(int x=1; x<=12; x++) {
        std::cout << x << std::endl;
    }
    std::cout << x << std::endl;
    return 0;
}