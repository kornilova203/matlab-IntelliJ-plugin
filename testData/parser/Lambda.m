fun = @(x)x./(exp(x)-1);
q4 = integral(fun,0,Inf)

noop = @(x) x;
noop(@(a) a+1);
