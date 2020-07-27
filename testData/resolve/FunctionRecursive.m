function res = <decl>fib(n)
    if n <= 1
      res = n;
    else
      res = <ref>fib(n-1) + fib(n-2);
    end
end