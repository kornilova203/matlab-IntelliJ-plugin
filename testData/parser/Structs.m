myStruct = struct;
myStruct.a = 'hello';
myStruct.a
myStruct.('a')
C = {
'variable1' 'hello';
'variable2' 'world';
'variable3' 3.14159;
'variable4' [42 24]
};
Ct = C';
S = struct(Ct{:})
S.('variable1')
