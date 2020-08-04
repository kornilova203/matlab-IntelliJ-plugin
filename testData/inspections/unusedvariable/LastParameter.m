function foo(x, y, <weak_warning descr="Parameter 'a' is never used">a<caret></weak_warning>)
    y = x + 1
end