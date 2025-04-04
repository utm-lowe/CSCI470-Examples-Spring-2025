-- Variable binding and scope
function g()
    print(k)
end

function f()
    local k=1
    g()
end

-- a function which sets a variable x
function set_x()
    local x
    x = 10
end

x = 1
set_x()
print(x)

f()