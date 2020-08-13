function foo(x, y, <weak_warning descr="Parameter 'a' is never used">a<caret></weak_warning>)
    <weak_warning descr="Variable 'y' is never used">y</weak_warning> = x + 1
end