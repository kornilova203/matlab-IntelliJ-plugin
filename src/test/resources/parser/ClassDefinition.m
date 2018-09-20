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
   properties
         Value
   end
end
