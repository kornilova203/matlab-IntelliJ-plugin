function foo1
    while true
        a = 1
    end
    <warning descr="Unreachable code">c = 1</warning>
end

function foo2
    while true
        a = 1
        if a < b
            break
        end
    end
    c = 1
end

function foo3
    while true
        a = 1
        if false
            <warning descr="Unreachable code">break
</warning>end
    end
    <warning descr="Unreachable code">c = 1</warning>
end

function foo4
    while false
        <warning descr="Unreachable code">a = 1
</warning>end
    c = 1
end

function foo4
    while a < b
        if true
            continue
        end
        <warning descr="Unreachable code">c = 1</warning>
    end
end