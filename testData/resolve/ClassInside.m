classdef ClassInside
    properties
        <decl>Prop
    end
    methods
        function foo(obj)
            obj.<ref>Prop
        end
    end
end