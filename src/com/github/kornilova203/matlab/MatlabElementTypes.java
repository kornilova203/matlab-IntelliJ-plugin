package com.github.kornilova203.matlab;

import com.github.kornilova203.matlab.psi.MatlabElementType;

/**
 * @author Liudmila Kornilova
 **/
public interface MatlabElementTypes {
    MatlabElementType METHODS     = new MatlabElementType("methods");
    MatlabElementType PROPERTIES  = new MatlabElementType("properties");
    MatlabElementType EVENTS      = new MatlabElementType("events");
    MatlabElementType ENUMERATION = new MatlabElementType("enumeration");
}
