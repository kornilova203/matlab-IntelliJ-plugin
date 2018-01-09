classdef BasicClass
   properties
      Value1 Value2
   end
   methods
      function r = fun(obj)
         a = 1
      end
      function r = fun(obj, n)
         a = 1
      end
   end
end

classdef BasicClass
   methods
      function r = fun(obj)
         a = 1
      end
      function r = fun(obj, n)
         a = 1
      end
   end
end

classdef BasicClass properties Value1 Value2 end end