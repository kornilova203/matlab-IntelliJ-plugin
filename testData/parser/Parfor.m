parfor i=1:5
    disp(i)
end

parfor (i=1:5, 3)
    disp(i)
end

parfor i=1:3, c(:,i) = eig(rand(1000)); end