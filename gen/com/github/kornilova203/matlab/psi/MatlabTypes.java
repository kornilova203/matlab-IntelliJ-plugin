// This is a generated file. Not intended for manual editing.
package com.github.kornilova203.matlab.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import com.github.kornilova203.matlab.psi.impl.*;
import com.intellij.psi.impl.source.tree.CompositePsiElement;

public interface MatlabTypes {

  IElementType ALL_ITEMS_RANGE = new MatlabElementType("ALL_ITEMS_RANGE");
  IElementType AND_EXPR = new MatlabElementType("AND_EXPR");
  IElementType ARGUMENTS = new MatlabElementType("ARGUMENTS");
  IElementType ASSIGN_EXPR = new MatlabElementType("ASSIGN_EXPR");
  IElementType ATTRIBUTE = new MatlabElementType("ATTRIBUTE");
  IElementType ATTRIBUTES = new MatlabElementType("ATTRIBUTES");
  IElementType BLOCK = new MatlabElementType("BLOCK");
  IElementType CASE_BLOCK = new MatlabElementType("CASE_BLOCK");
  IElementType CASE_EXPRESSION = new MatlabElementType("CASE_EXPRESSION");
  IElementType CATCH_BLOCK = new MatlabElementType("CATCH_BLOCK");
  IElementType CELL_ARRAY_ACCESS_EXPR = new MatlabElementType("CELL_ARRAY_ACCESS_EXPR");
  IElementType CELL_ARRAY_ITEM = new MatlabElementType("CELL_ARRAY_ITEM");
  IElementType CELL_ARRAY_LITERAL = new MatlabElementType("CELL_ARRAY_LITERAL");
  IElementType CELL_ARRAY_ROW = new MatlabElementType("CELL_ARRAY_ROW");
  IElementType CLASS_ATTRIBUTES = new MatlabElementType("CLASS_ATTRIBUTES");
  IElementType CLASS_ATTRIBUTE_LIST_ITEM = new MatlabElementType("CLASS_ATTRIBUTE_LIST_ITEM");
  IElementType CLASS_DECLARATION = new MatlabElementType("CLASS_DECLARATION");
  IElementType CONDITION = new MatlabElementType("CONDITION");
  IElementType CTRANSPOSE_EXPR = new MatlabElementType("CTRANSPOSE_EXPR");
  IElementType ELEMENT_WISE_LDIV_EXPR = new MatlabElementType("ELEMENT_WISE_LDIV_EXPR");
  IElementType ELEMENT_WISE_MUL_EXPR = new MatlabElementType("ELEMENT_WISE_MUL_EXPR");
  IElementType ELEMENT_WISE_POW_EXPR = new MatlabElementType("ELEMENT_WISE_POW_EXPR");
  IElementType ELEMENT_WISE_RDIV_EXPR = new MatlabElementType("ELEMENT_WISE_RDIV_EXPR");
  IElementType ELSEIF_BLOCK = new MatlabElementType("ELSEIF_BLOCK");
  IElementType ELSE_BLOCK = new MatlabElementType("ELSE_BLOCK");
  IElementType ENUMERATION_BLOCK = new MatlabElementType("ENUMERATION_BLOCK");
  IElementType EQUAL_EXPR = new MatlabElementType("EQUAL_EXPR");
  IElementType EVENTS_BLOCK = new MatlabElementType("EVENTS_BLOCK");
  IElementType EVENTS_LIST = new MatlabElementType("EVENTS_LIST");
  IElementType EXPR = new MatlabElementType("EXPR");
  IElementType FILE_OPERATION = new MatlabElementType("FILE_OPERATION");
  IElementType FOR_LOOP = new MatlabElementType("FOR_LOOP");
  IElementType FOR_LOOP_RANGE = new MatlabElementType("FOR_LOOP_RANGE");
  IElementType FUNCTION_DECLARATION = new MatlabElementType("FUNCTION_DECLARATION");
  IElementType FUNCTION_EXPR = new MatlabElementType("FUNCTION_EXPR");
  IElementType FUNCTION_REF_EXPR = new MatlabElementType("FUNCTION_REF_EXPR");
  IElementType GETTER_OR_SETTER_MODIFIER = new MatlabElementType("GETTER_OR_SETTER_MODIFIER");
  IElementType GLOBAL_VARIABLE_DECLARATION = new MatlabElementType("GLOBAL_VARIABLE_DECLARATION");
  IElementType IF_BLOCK = new MatlabElementType("IF_BLOCK");
  IElementType LAMBDA_BODY = new MatlabElementType("LAMBDA_BODY");
  IElementType LAMBDA_EXPR = new MatlabElementType("LAMBDA_EXPR");
  IElementType LDIV_EXPR = new MatlabElementType("LDIV_EXPR");
  IElementType LESS_EXPR = new MatlabElementType("LESS_EXPR");
  IElementType LESS_OR_EQUAL_EXPR = new MatlabElementType("LESS_OR_EQUAL_EXPR");
  IElementType LITERAL_EXPR = new MatlabElementType("LITERAL_EXPR");
  IElementType MATRIX_AND_EXPR = new MatlabElementType("MATRIX_AND_EXPR");
  IElementType MATRIX_ITEM = new MatlabElementType("MATRIX_ITEM");
  IElementType MATRIX_LITERAL = new MatlabElementType("MATRIX_LITERAL");
  IElementType MATRIX_OR_EXPR = new MatlabElementType("MATRIX_OR_EXPR");
  IElementType MATRIX_ROW = new MatlabElementType("MATRIX_ROW");
  IElementType META_CLASS_EXPR = new MatlabElementType("META_CLASS_EXPR");
  IElementType METHODS_BLOCK = new MatlabElementType("METHODS_BLOCK");
  IElementType MINUS_EXPR = new MatlabElementType("MINUS_EXPR");
  IElementType MORE_EXPR = new MatlabElementType("MORE_EXPR");
  IElementType MORE_OR_EQUAL_EXPR = new MatlabElementType("MORE_OR_EQUAL_EXPR");
  IElementType MUL_EXPR = new MatlabElementType("MUL_EXPR");
  IElementType NOT_EQUAL_EXPR = new MatlabElementType("NOT_EQUAL_EXPR");
  IElementType OR_EXPR = new MatlabElementType("OR_EXPR");
  IElementType OTHERWISE_BLOCK = new MatlabElementType("OTHERWISE_BLOCK");
  IElementType PARAMETER = new MatlabElementType("PARAMETER");
  IElementType PARAMETERS = new MatlabElementType("PARAMETERS");
  IElementType PAREN_EXPR = new MatlabElementType("PAREN_EXPR");
  IElementType PLUS_EXPR = new MatlabElementType("PLUS_EXPR");
  IElementType POW_EXPR = new MatlabElementType("POW_EXPR");
  IElementType PROPERTIES_BLOCK = new MatlabElementType("PROPERTIES_BLOCK");
  IElementType QUALIFIED_EXPR = new MatlabElementType("QUALIFIED_EXPR");
  IElementType RANGE_EXPR = new MatlabElementType("RANGE_EXPR");
  IElementType RDIV_EXPR = new MatlabElementType("RDIV_EXPR");
  IElementType REF_EXPR = new MatlabElementType("REF_EXPR");
  IElementType RETURN_VALUES = new MatlabElementType("RETURN_VALUES");
  IElementType RET_VALUE = new MatlabElementType("RET_VALUE");
  IElementType SUPER_CLASSES = new MatlabElementType("SUPER_CLASSES");
  IElementType SWITCH_BLOCK = new MatlabElementType("SWITCH_BLOCK");
  IElementType SWITCH_EXPRESSION = new MatlabElementType("SWITCH_EXPRESSION");
  IElementType TRANSPOSE_EXPR = new MatlabElementType("TRANSPOSE_EXPR");
  IElementType TRY_BLOCK = new MatlabElementType("TRY_BLOCK");
  IElementType UNARY_DEC_EXPR = new MatlabElementType("UNARY_DEC_EXPR");
  IElementType UNARY_INC_EXPR = new MatlabElementType("UNARY_INC_EXPR");
  IElementType UNARY_MIN_EXPR = new MatlabElementType("UNARY_MIN_EXPR");
  IElementType UNARY_NEGATION_EXPR = new MatlabElementType("UNARY_NEGATION_EXPR");
  IElementType UNARY_PLUS_EXPR = new MatlabElementType("UNARY_PLUS_EXPR");
  IElementType UNARY_PREFIX_DEC_EXPR = new MatlabElementType("UNARY_PREFIX_DEC_EXPR");
  IElementType UNARY_PREFIX_INC_EXPR = new MatlabElementType("UNARY_PREFIX_INC_EXPR");
  IElementType VARIABLES = new MatlabElementType("VARIABLES");
  IElementType WHILE_LOOP = new MatlabElementType("WHILE_LOOP");
  IElementType WHILE_LOOP_CONDITION = new MatlabElementType("WHILE_LOOP_CONDITION");

  IElementType AND = new MatlabTokenType("&&");
  IElementType ASSIGN = new MatlabTokenType("=");
  IElementType AT = new MatlabTokenType("@");
  IElementType CASE = new MatlabTokenType("case");
  IElementType CATCH = new MatlabTokenType("catch");
  IElementType CD = new MatlabTokenType("CD");
  IElementType CLASSDEF = new MatlabTokenType("classdef");
  IElementType COLON = new MatlabTokenType(":");
  IElementType COMMA = new MatlabTokenType(",");
  IElementType COMMENT = new MatlabTokenType("COMMENT");
  IElementType CTRANS = new MatlabTokenType("'");
  IElementType DIR = new MatlabTokenType("DIR");
  IElementType DOT = new MatlabTokenType(".");
  IElementType DOT_LDIV = new MatlabTokenType(".\\");
  IElementType DOT_MUL = new MatlabTokenType(".*");
  IElementType DOT_POW = new MatlabTokenType(".^");
  IElementType DOT_RDIV = new MatlabTokenType("./");
  IElementType DOUBLE_QUOTE_STRING = new MatlabTokenType("DOUBLE_QUOTE_STRING");
  IElementType ELLIPSIS = new MatlabTokenType("...");
  IElementType ELSE = new MatlabTokenType("else");
  IElementType ELSEIF = new MatlabTokenType("elseif");
  IElementType END = new MatlabTokenType("end");
  IElementType EQUAL = new MatlabTokenType("==");
  IElementType FALSE = new MatlabTokenType("false");
  IElementType FILE_NAME = new MatlabTokenType("FILE_NAME");
  IElementType FLOAT = new MatlabTokenType("FLOAT");
  IElementType FLOAT_EXPONENTIAL = new MatlabTokenType("FLOAT_EXPONENTIAL");
  IElementType FOR = new MatlabTokenType("for");
  IElementType FUNCTION = new MatlabTokenType("function");
  IElementType GLOBAL = new MatlabTokenType("global");
  IElementType IDENTIFIER = new MatlabTokenType("IDENTIFIER");
  IElementType IF = new MatlabTokenType("if");
  IElementType INTEGER = new MatlabTokenType("INTEGER");
  IElementType LBRACE = new MatlabTokenType("{");
  IElementType LBRACKET = new MatlabTokenType("[");
  IElementType LDIV = new MatlabTokenType("\\");
  IElementType LESS = new MatlabTokenType("<");
  IElementType LESS_OR_EQUAL = new MatlabTokenType("<=");
  IElementType LOAD = new MatlabTokenType("LOAD");
  IElementType LPARENTH = new MatlabTokenType("(");
  IElementType LS = new MatlabTokenType("LS");
  IElementType MATRIX_AND = new MatlabTokenType("&");
  IElementType MATRIX_OR = new MatlabTokenType("|");
  IElementType MINUS = new MatlabTokenType("-");
  IElementType MINUSMINUS = new MatlabTokenType("--");
  IElementType MORE = new MatlabTokenType(">");
  IElementType MORE_OR_EQUAL = new MatlabTokenType(">=");
  IElementType MUL = new MatlabTokenType("*");
  IElementType NEWLINE = new MatlabTokenType("NEWLINE");
  IElementType NOT_EQUAL = new MatlabTokenType("~=");
  IElementType OR = new MatlabTokenType("||");
  IElementType OTHERWISE = new MatlabTokenType("otherwise");
  IElementType PLUS = new MatlabTokenType("+");
  IElementType PLUSPLUS = new MatlabTokenType("++");
  IElementType POW = new MatlabTokenType("^");
  IElementType QUESTION_MARK = new MatlabTokenType("?");
  IElementType RBRACE = new MatlabTokenType("}");
  IElementType RBRACKET = new MatlabTokenType("]");
  IElementType RDIV = new MatlabTokenType("/");
  IElementType RPARENTH = new MatlabTokenType(")");
  IElementType SEMICOLON = new MatlabTokenType(";");
  IElementType SINGLE_QUOTE_STRING = new MatlabTokenType("SINGLE_QUOTE_STRING");
  IElementType SWITCH = new MatlabTokenType("switch");
  IElementType TILDA = new MatlabTokenType("~");
  IElementType TRANS = new MatlabTokenType(".'");
  IElementType TRUE = new MatlabTokenType("true");
  IElementType TRY = new MatlabTokenType("try");
  IElementType WHILE = new MatlabTokenType("while");

  class Factory {
    public static CompositePsiElement createElement(IElementType type) {
       if (type == ALL_ITEMS_RANGE) {
        return new MatlabAllItemsRangeImpl(type);
      }
      else if (type == AND_EXPR) {
        return new MatlabAndExprImpl(type);
      }
      else if (type == ARGUMENTS) {
        return new MatlabArgumentsImpl(type);
      }
      else if (type == ASSIGN_EXPR) {
        return new MatlabAssignExprImpl(type);
      }
      else if (type == ATTRIBUTE) {
        return new MatlabAttributeImpl(type);
      }
      else if (type == ATTRIBUTES) {
        return new MatlabAttributesImpl(type);
      }
      else if (type == BLOCK) {
        return new MatlabBlockImpl(type);
      }
      else if (type == CASE_BLOCK) {
        return new MatlabCaseBlockImpl(type);
      }
      else if (type == CASE_EXPRESSION) {
        return new MatlabCaseExpressionImpl(type);
      }
      else if (type == CATCH_BLOCK) {
        return new MatlabCatchBlockImpl(type);
      }
      else if (type == CELL_ARRAY_ACCESS_EXPR) {
        return new MatlabCellArrayAccessExprImpl(type);
      }
      else if (type == CELL_ARRAY_ITEM) {
        return new MatlabCellArrayItemImpl(type);
      }
      else if (type == CELL_ARRAY_LITERAL) {
        return new MatlabCellArrayLiteralImpl(type);
      }
      else if (type == CELL_ARRAY_ROW) {
        return new MatlabCellArrayRowImpl(type);
      }
      else if (type == CLASS_ATTRIBUTES) {
        return new MatlabClassAttributesImpl(type);
      }
      else if (type == CLASS_ATTRIBUTE_LIST_ITEM) {
        return new MatlabClassAttributeListItemImpl(type);
      }
      else if (type == CLASS_DECLARATION) {
        return new MatlabClassDeclarationImpl(type);
      }
      else if (type == CONDITION) {
        return new MatlabConditionImpl(type);
      }
      else if (type == CTRANSPOSE_EXPR) {
        return new MatlabCtransposeExprImpl(type);
      }
      else if (type == ELEMENT_WISE_LDIV_EXPR) {
        return new MatlabElementWiseLdivExprImpl(type);
      }
      else if (type == ELEMENT_WISE_MUL_EXPR) {
        return new MatlabElementWiseMulExprImpl(type);
      }
      else if (type == ELEMENT_WISE_POW_EXPR) {
        return new MatlabElementWisePowExprImpl(type);
      }
      else if (type == ELEMENT_WISE_RDIV_EXPR) {
        return new MatlabElementWiseRdivExprImpl(type);
      }
      else if (type == ELSEIF_BLOCK) {
        return new MatlabElseifBlockImpl(type);
      }
      else if (type == ELSE_BLOCK) {
        return new MatlabElseBlockImpl(type);
      }
      else if (type == ENUMERATION_BLOCK) {
        return new MatlabEnumerationBlockImpl(type);
      }
      else if (type == EQUAL_EXPR) {
        return new MatlabEqualExprImpl(type);
      }
      else if (type == EVENTS_BLOCK) {
        return new MatlabEventsBlockImpl(type);
      }
      else if (type == EVENTS_LIST) {
        return new MatlabEventsListImpl(type);
      }
      else if (type == FILE_OPERATION) {
        return new MatlabFileOperationImpl(type);
      }
      else if (type == FOR_LOOP) {
        return new MatlabForLoopImpl(type);
      }
      else if (type == FOR_LOOP_RANGE) {
        return new MatlabForLoopRangeImpl(type);
      }
      else if (type == FUNCTION_DECLARATION) {
        return new MatlabFunctionDeclarationImpl(type);
      }
      else if (type == FUNCTION_EXPR) {
        return new MatlabFunctionExprImpl(type);
      }
      else if (type == FUNCTION_REF_EXPR) {
        return new MatlabFunctionRefExprImpl(type);
      }
      else if (type == GETTER_OR_SETTER_MODIFIER) {
        return new MatlabGetterOrSetterModifierImpl(type);
      }
      else if (type == GLOBAL_VARIABLE_DECLARATION) {
        return new MatlabGlobalVariableDeclarationImpl(type);
      }
      else if (type == IF_BLOCK) {
        return new MatlabIfBlockImpl(type);
      }
      else if (type == LAMBDA_BODY) {
        return new MatlabLambdaBodyImpl(type);
      }
      else if (type == LAMBDA_EXPR) {
        return new MatlabLambdaExprImpl(type);
      }
      else if (type == LDIV_EXPR) {
        return new MatlabLdivExprImpl(type);
      }
      else if (type == LESS_EXPR) {
        return new MatlabLessExprImpl(type);
      }
      else if (type == LESS_OR_EQUAL_EXPR) {
        return new MatlabLessOrEqualExprImpl(type);
      }
      else if (type == LITERAL_EXPR) {
        return new MatlabLiteralExprImpl(type);
      }
      else if (type == MATRIX_AND_EXPR) {
        return new MatlabMatrixAndExprImpl(type);
      }
      else if (type == MATRIX_ITEM) {
        return new MatlabMatrixItemImpl(type);
      }
      else if (type == MATRIX_LITERAL) {
        return new MatlabMatrixLiteralImpl(type);
      }
      else if (type == MATRIX_OR_EXPR) {
        return new MatlabMatrixOrExprImpl(type);
      }
      else if (type == MATRIX_ROW) {
        return new MatlabMatrixRowImpl(type);
      }
      else if (type == META_CLASS_EXPR) {
        return new MatlabMetaClassExprImpl(type);
      }
      else if (type == METHODS_BLOCK) {
        return new MatlabMethodsBlockImpl(type);
      }
      else if (type == MINUS_EXPR) {
        return new MatlabMinusExprImpl(type);
      }
      else if (type == MORE_EXPR) {
        return new MatlabMoreExprImpl(type);
      }
      else if (type == MORE_OR_EQUAL_EXPR) {
        return new MatlabMoreOrEqualExprImpl(type);
      }
      else if (type == MUL_EXPR) {
        return new MatlabMulExprImpl(type);
      }
      else if (type == NOT_EQUAL_EXPR) {
        return new MatlabNotEqualExprImpl(type);
      }
      else if (type == OR_EXPR) {
        return new MatlabOrExprImpl(type);
      }
      else if (type == OTHERWISE_BLOCK) {
        return new MatlabOtherwiseBlockImpl(type);
      }
      else if (type == PARAMETER) {
        return new MatlabParameterImpl(type);
      }
      else if (type == PARAMETERS) {
        return new MatlabParametersImpl(type);
      }
      else if (type == PAREN_EXPR) {
        return new MatlabParenExprImpl(type);
      }
      else if (type == PLUS_EXPR) {
        return new MatlabPlusExprImpl(type);
      }
      else if (type == POW_EXPR) {
        return new MatlabPowExprImpl(type);
      }
      else if (type == PROPERTIES_BLOCK) {
        return new MatlabPropertiesBlockImpl(type);
      }
      else if (type == QUALIFIED_EXPR) {
        return new MatlabQualifiedExprImpl(type);
      }
      else if (type == RANGE_EXPR) {
        return new MatlabRangeExprImpl(type);
      }
      else if (type == RDIV_EXPR) {
        return new MatlabRdivExprImpl(type);
      }
      else if (type == REF_EXPR) {
        return new MatlabRefExprImpl(type);
      }
      else if (type == RETURN_VALUES) {
        return new MatlabReturnValuesImpl(type);
      }
      else if (type == RET_VALUE) {
        return new MatlabRetValueImpl(type);
      }
      else if (type == SUPER_CLASSES) {
        return new MatlabSuperClassesImpl(type);
      }
      else if (type == SWITCH_BLOCK) {
        return new MatlabSwitchBlockImpl(type);
      }
      else if (type == SWITCH_EXPRESSION) {
        return new MatlabSwitchExpressionImpl(type);
      }
      else if (type == TRANSPOSE_EXPR) {
        return new MatlabTransposeExprImpl(type);
      }
      else if (type == TRY_BLOCK) {
        return new MatlabTryBlockImpl(type);
      }
      else if (type == UNARY_DEC_EXPR) {
        return new MatlabUnaryDecExprImpl(type);
      }
      else if (type == UNARY_INC_EXPR) {
        return new MatlabUnaryIncExprImpl(type);
      }
      else if (type == UNARY_MIN_EXPR) {
        return new MatlabUnaryMinExprImpl(type);
      }
      else if (type == UNARY_NEGATION_EXPR) {
        return new MatlabUnaryNegationExprImpl(type);
      }
      else if (type == UNARY_PLUS_EXPR) {
        return new MatlabUnaryPlusExprImpl(type);
      }
      else if (type == UNARY_PREFIX_DEC_EXPR) {
        return new MatlabUnaryPrefixDecExprImpl(type);
      }
      else if (type == UNARY_PREFIX_INC_EXPR) {
        return new MatlabUnaryPrefixIncExprImpl(type);
      }
      else if (type == VARIABLES) {
        return new MatlabVariablesImpl(type);
      }
      else if (type == WHILE_LOOP) {
        return new MatlabWhileLoopImpl(type);
      }
      else if (type == WHILE_LOOP_CONDITION) {
        return new MatlabWhileLoopConditionImpl(type);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
