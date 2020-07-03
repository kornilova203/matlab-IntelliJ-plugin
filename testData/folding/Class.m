classdef BasicClass < OtherClass<fold text='...'>
   properties<fold text='...'>
      Value
   </fold>end
   methods (Static)<fold text='...'>
      function r = roundOff(obj)<fold text='...'>
         r = round([obj.Value],2);
      </fold>end
      function r = multiplyBy(obj,n)<fold text='...'>
         r = [obj.Value] * n;
      </fold>end
   </fold>end
   events (NotifyAccess = protected)<fold text='...'>
        ToggledState
   </fold>end
</fold>end