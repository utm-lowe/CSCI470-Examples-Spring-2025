def f():
    k = 1
    g()

def g():
    print(k)

for i in range(1,13):
    j = i*2
    print(i)
print(i)
print(j)
f()
#print(k)