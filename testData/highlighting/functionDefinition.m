function [x, options, flog, pointlog] = conjgrad(f, x, options, gradf, ...
                                    varargin)
    x = 1
end

function [gradnew, d<error descr="'...' or ']' expected, got '='"> </error>= conjgrad()
    if (gradnew*d' > 0)
        d = -d;
        if options(1) >= 0
            warning('search direction uphill in conjgrad');
        end
    end
end
