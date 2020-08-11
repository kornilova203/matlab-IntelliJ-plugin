function foo
    disp("a")
    <warning descr="'return' statement is unnecessary"><caret>return</warning>;
end

function foo
    if 1
        a = 1
        <warning descr="'return' statement is unnecessary">return</warning>
    else
        b = 1
        <warning descr="'return' statement is unnecessary">return</warning>
    end
end