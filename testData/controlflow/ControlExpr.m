while a < 10
    if a == 5
        a = a + 2
        continue
    end
    a = a + 1
end

while a < 10
    if a == 5
        break
    end
    a = a + 1
end

while a < 10
    if a == 5
        return
    end
    a = a + 1
end

function foo
    if a < b 
        return
    end
    a = b
end