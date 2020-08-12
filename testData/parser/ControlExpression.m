while a < b
    if a < b - 1
        a = a + 2
        continue
    end
    a = a + 1
    if a == b
        break
    end
end

if a > b
    return
end