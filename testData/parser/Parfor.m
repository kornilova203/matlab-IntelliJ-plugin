parfor i=1:5
    disp(i)
end

parfor (i=1:5, 3)
    disp(i)
end

parfor i=1:3, c(:,i) = eig(rand(1000)); end

parfor (i=1:numel(values),opts)
    out(i) = norm(pinv(rand(values(i)*1e3)));
end

parfor(i=1:10,parforOptions(parcluster)); out(i)=i; end