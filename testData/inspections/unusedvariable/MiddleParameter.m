function foo(x, <weak_warning descr="Parameter 'a' is never used">a<caret></weak_warning>, y)
    y = x + 1
end