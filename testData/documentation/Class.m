classdef (AllowedSubclasses = true, ~Static) <caret>PositiveInteger  < Integers & Positives
%This class is just test class
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
