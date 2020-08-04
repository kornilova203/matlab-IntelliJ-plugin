<weak_warning descr="Variable 'a' is never used">a</weak_warning> = 1
function foo
    b = a + 1
    <weak_warning descr="Variable 'c' is never used">c<caret></weak_warning> = b + 2
end
<weak_warning descr="Variable 'c' is never used">c</weak_warning> = 3