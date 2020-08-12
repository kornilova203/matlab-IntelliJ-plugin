try
    a = 1 
    b = 2
catch
    a = 2 
    b = 1
end

try 
    a = 1 
    try 
        b = 2 
        c = 3
    catch ME
        disp(ME)
    end
    d = 4
catch 
    disp("Error")
end
