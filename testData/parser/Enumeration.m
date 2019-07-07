classdef TimeUnit < double & Unit

  enumeration
    millisecond    (1/1000)
    second         (1)
    minute         (60)
    hour           (60*60)
    day            (60*60*24)
    week           (60*60*24*7)
  end

  methods
    function d = asDuration( self)
      d = seconds( self);
    end
  end

end

classdef WeekDays
   enumeration
      Monday, Tuesday, Wednesday, Thursday, Friday
   end
end

today = WeekDays.Tuesday;

classdef Bearing < uint32
   enumeration
      North (0)
      East  (90)
      South (180)
      West  (270)
   end
end

classdef WeekDays
   enumeration
      Monday, Tuesday, Wednesday, Thursday, Friday
   end
   methods
      function tf = isMeetingDay(obj)
         tf = ~(WeekDays.Tuesday == obj);
      end
   end
end

classdef SyntaxColors
   properties
      R
      G
      B
   end
   methods
      function c = SyntaxColors(r, g, b)
         c.R = r; c.G = g; c.B = b;
      end
   end
   enumeration
      Error   (1, 0, 0)
      Comment (0, 1, 0)
      Keyword (0, 0, 1)
      String  (1, 0, 1)
   end
end

classdef Bool < logical
   enumeration
      No  (0)
      Yes (1)
   end
end

function obj = Bool(val)
   obj@logical(val)
end
