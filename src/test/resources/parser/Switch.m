switch lower(method)
    case 'stirling'
        nout=(nin+.5).*log(nin) - nin + .5*log(2*pi);
    case 'gosper'
        nout=nin.*log(nin)-nin+0.5*log((2*nin+1/3)*pi);
end

a = 1;
switch ...
    a
    case
    1
        fprintf("1")
    otherwise
        fprintf("not 1")
end
