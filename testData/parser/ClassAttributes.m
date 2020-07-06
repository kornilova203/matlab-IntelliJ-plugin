classdef (Abstract = true) BasicClass
end

classdef (Abstract) AbstractClass
end

classdef (~Abstract) NotAbstractClass
end

classdef (AllowedSubclasses = true, Abstract, ~Hidden) MixAttributesClass
end
