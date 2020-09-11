classdef NestedQualifiedAdd
    properties
        Prop NestedQualifiedDeclaration
    end
    methods
        function obj = withX(obj, x)
            obj.Prop = x
        end
    end
end