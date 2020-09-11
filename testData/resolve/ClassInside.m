classdef ClassInside
    properties
        <decl>Prop
    end
    methods
        function obj = foo(obj)
            obj.<ref>Prop
        end
    end
end