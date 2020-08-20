function foo(<weak_warning descr="Parameter 'a' is never used"><caret>a</weak_warning>, x, y)
    <weak_warning descr="Variable 'y' is never used">y</weak_warning> = x + 1
end