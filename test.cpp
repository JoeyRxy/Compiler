#include <iostream>
#include <string>
#include <algorithm>
#include <vector>
#include <cmath>
using namespace std;

int main(int argc, char const *argv[])
{
    for (int i = 0; i < 6; i++)
    {
        cout << i << " : " << tgamma(i) << endl;
        double x = i + 0.3;
        cout << "\t" << x << " : " << tgamma(x) << endl;
        x += 0.3;
        cout << "\t" << x << " : " << tgamma(x) << endl;
        x += 0.3;
        cout << "\t" << x << " : " << tgamma(x) << endl;
    }
    system("pause");
    return 0;
}
