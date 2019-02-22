try
   C = [A; B];
catch ME
   if (strcmp(ME.identifier,'MATLAB:catenate:dimensionMismatch'))
      msg = ['Dimension mismatch occurred: First argument has ', ...
            num2str(size(A,2)),' columns while second has ', ...
            num2str(size(B,2)),' columns.'];
        causeException = MException('MATLAB:myCode:dimensions',msg);
        ME = addCause(ME,causeException);
   end
   rethrow(ME)
end

try
    a = notaFunction(5,6);
catch
    warning('Problem using function.  Assigning a value of 0.');
    a = 0;
end

try
    a = notaFunction(5,6);
catch ME
    switch ME.identifier
        case 'MATLAB:UndefinedFunction'
            warning('Function is undefined.  Assigning a value of NaN.');
            a = NaN;
        case 'MATLAB:scriptNotAFunction'
            warning(['Attempting to execute script as function. '...
                'Running script and assigning output a value of 0.']);
            notaFunction;
            a = 0;
        otherwise
            rethrow(ME)
    end
end

try
   ME = MException('MyComponent:noSuchVariable', ...
           'Variable not found');
       throw(ME);
catch ME

   ...
   ME
   42
end

try
   ME = MException('MyComponent:noSuchVariable', ...
           'Variable not found');
       throw(ME);
catch ...
ME

   ...
   ME
   42
end
