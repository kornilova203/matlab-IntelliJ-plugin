classdef (AllowedSubclasses = true) PositiveInteger < Integers & Positives
   properties (Access = private, Constant = true)
       value
   end
   methods (Access = protected)
      function r = fun(obj)
         a = 1
      end
      function r = fun(obj, n)
         a = 1
      end
   end
   events (NotifyAccess = protected)
       ToggledState
   end
end

classdef BasicClass properties Value1 Value2 end end

classdef ClassWithChangedOrder
   methods
      function obj = ClassWithChangedOrder(val)
        obj.Value = val
      end
      function r = multiplyBy(obj,n)
         r = [obj.Value] * n;
      end
   end
   properties (Dependent)
         Value
   end
end

classdef C
    properties (SetAccess = ?EnvelopeStageManager, GetAccess=public)
        % empty
    end
end

classdef SquareArea
   properties
      Width
      Height
   end
   properties (Dependent)
      Area
   end
   methods
      function a = get.Area(obj)
         a = obj.Width * obj.Height;
      end
   end
end

classdef MyClass
   properties
      Prop1
   end
   methods
      function obj = set.Prop1(obj,value)
         if (value > 0)
            obj.Prop1 = value;
         else
            error('Property value must be positive')
         end
      end
   end
end
