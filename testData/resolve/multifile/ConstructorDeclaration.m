classdef ConstructorDeclaration
    properties
        <decl>Prop
    end
    methods
        function obj = ConstructorDeclaration(x)
            obj.Prop = x;
        end
    end
end