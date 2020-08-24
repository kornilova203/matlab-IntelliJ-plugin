classdef FindClassInMethodDeclaration
    properties
        <decl>Prop
    end
    methods
        function obj = withX(obj, x)
            obj.Prop = x
        end
    end
end