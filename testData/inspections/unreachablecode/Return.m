a = 1
return
<warning descr="Unreachable code">b = 1</warning>
function foo
    x = 1
    return
    <warning descr="Unreachable code">x = 2</warning>
end
<warning descr="Unreachable code">c = 1</warning>