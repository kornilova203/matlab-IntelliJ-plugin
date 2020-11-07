if false
    <warning descr="Unreachable code">a = 1
</warning>end

if true
    a = 1
<warning descr="Unreachable code">else
    b = 2
</warning>end

if a < b
    a = 1
elseif false
    <warning descr="Unreachable code">b = 2
</warning>elseif true
    c = 3
<warning descr="Unreachable code">else
    d = 4
</warning>end

if [1,2](1)
    a = 1
else
    b = 2
end