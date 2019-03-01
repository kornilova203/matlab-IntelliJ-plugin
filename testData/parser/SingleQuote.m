A = [1, 2]

function f(some)
    some
end

B = [A']
B = [1, A']
B = {A'}

% okay
f(A,'')
f(A ')
f(1, A ')
f(1')
B = [1 '']
a = [true']
a = ["abc"']
f([1, 2, 3])'

% errors
B = [1, ']
B = [1,']
B = [A ']
B = [1, A ']
B = {A '}
a = ['abc'']
f([1, 2, 3]) ' var'
