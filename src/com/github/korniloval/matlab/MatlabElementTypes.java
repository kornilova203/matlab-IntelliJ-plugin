package com.github.korniloval.matlab;

import com.github.korniloval.matlab.psi.MatlabElementType;

/**
 * @author Liudmila Kornilova
 **/
public interface MatlabElementTypes {
    MatlabElementType METHODS     = new MatlabElementType("methods");
    MatlabElementType PROPERTIES  = new MatlabElementType("properties");
    MatlabElementType EVENTS      = new MatlabElementType("events");
    MatlabElementType ENUMERATION = new MatlabElementType("enumeration");
}
