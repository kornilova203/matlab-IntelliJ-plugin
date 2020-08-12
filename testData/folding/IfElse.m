if x > 0<fold text='...'>
    "+"
</fold>else<fold text='...'>
    "-"
</fold>end

if r == c<fold text='...'>
    A(r,c) = 2;
</fold>elseif abs(r-c) == 1<fold text='...'>
    A(r,c) = -1;
</fold>else<fold text='...'>
    A(r,c) = 0;
</fold>end
