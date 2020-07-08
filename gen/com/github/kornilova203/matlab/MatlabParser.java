// This is a generated file. Not intended for manual editing.
package com.github.kornilova203.matlab;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static com.github.kornilova203.matlab.psi.MatlabTypes.*;
import static com.github.kornilova203.matlab.MatlabParserUtil.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;
import static com.github.kornilova203.matlab.MatlabElementTypes.*;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class MatlabParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, EXTENDS_SETS_);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    r = parse_root_(t, b);
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b) {
    return parse_root_(t, b, 0);
  }

  static boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return root(b, l + 1);
  }

  public static final TokenSet[] EXTENDS_SETS_ = new TokenSet[] {
    create_token_set_(ALL_ITEMS_RANGE, AND_EXPR, ASSIGN_EXPR, CELL_ARRAY_ACCESS_EXPR,
      CTRANSPOSE_EXPR, ELEMENT_WISE_LDIV_EXPR, ELEMENT_WISE_MUL_EXPR, ELEMENT_WISE_POW_EXPR,
      ELEMENT_WISE_RDIV_EXPR, EQUAL_EXPR, EXPR, FUNCTION_EXPR,
      FUNCTION_REF_EXPR, LAMBDA_EXPR, LDIV_EXPR, LESS_EXPR,
      LESS_OR_EQUAL_EXPR, LITERAL_EXPR, MATRIX_AND_EXPR, MATRIX_OR_EXPR,
      META_CLASS_EXPR, MINUS_EXPR, MORE_EXPR, MORE_OR_EQUAL_EXPR,
      MUL_EXPR, NOT_EQUAL_EXPR, OR_EXPR, PAREN_EXPR,
      PLUS_EXPR, POW_EXPR, QUALIFIED_EXPR, RANGE_EXPR,
      RDIV_EXPR, REF_EXPR, TRANSPOSE_EXPR, UNARY_DEC_EXPR,
      UNARY_INC_EXPR, UNARY_MIN_EXPR, UNARY_NEGATION_EXPR, UNARY_PLUS_EXPR,
      UNARY_PREFIX_DEC_EXPR, UNARY_PREFIX_INC_EXPR),
  };

  /* ********************************************************** */
  // ':'
  public static boolean all_items_range(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "all_items_range")) return false;
    if (!nextTokenIs(b, COLON)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COLON);
    exit_section_(b, m, ALL_ITEMS_RANGE, r);
    return r;
  }

  /* ********************************************************** */
  // expr | all_items_range
  static boolean argument(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "argument")) return false;
    boolean r;
    r = expr(b, l + 1, -1);
    if (!r) r = all_items_range(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // <<withOn 'NEW_LINE_ALLOWED' (<<withOn 'ALLOW_END_AS_IDENTIFIER' (<<p_opt_list argument>>)>>)>>
  public static boolean arguments(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "arguments")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ARGUMENTS, "<arguments>");
    r = withOn(b, l + 1, NEW_LINE_ALLOWED, arguments_0_1_parser_);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // <<withOn 'ALLOW_END_AS_IDENTIFIER' (<<p_opt_list argument>>)>>
  private static boolean arguments_0_1(PsiBuilder b, int l) {
    return withOn(b, l + 1, ALLOW_END_AS_IDENTIFIER, arguments_0_1_0_1_parser_);
  }

  // <<p_opt_list argument>>
  private static boolean arguments_0_1_0_1(PsiBuilder b, int l) {
    return p_opt_list(b, l + 1, argument_parser_);
  }

  /* ********************************************************** */
  // [ '~' ] ref_expr [ sep '=' sep expr ]
  public static boolean attribute(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attribute")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ATTRIBUTE, "<attribute>");
    r = attribute_0(b, l + 1);
    r = r && ref_expr(b, l + 1);
    p = r; // pin = 2
    r = r && attribute_2(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // [ '~' ]
  private static boolean attribute_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attribute_0")) return false;
    consumeToken(b, TILDA);
    return true;
  }

  // [ sep '=' sep expr ]
  private static boolean attribute_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attribute_2")) return false;
    attribute_2_0(b, l + 1);
    return true;
  }

  // sep '=' sep expr
  private static boolean attribute_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attribute_2_0")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = sep(b, l + 1);
    r = r && consumeToken(b, ASSIGN);
    p = r; // pin = 2
    r = r && report_error_(b, sep(b, l + 1));
    r = p && expr(b, l + 1, -1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // <<p_opt_list attribute>>
  public static boolean attributes(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attributes")) return false;
    if (!nextTokenIs(b, LPARENTH)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = p_opt_list(b, l + 1, attribute_parser_);
    exit_section_(b, m, ATTRIBUTES, r);
    return r;
  }

  /* ********************************************************** */
  // (<<p>> NEWLINE *)*
  public static boolean block(PsiBuilder b, int l, Parser _p) {
    if (!recursion_guard_(b, l, "block")) return false;
    Marker m = enter_section_(b);
    while (true) {
      int c = current_position_(b);
      if (!block_0(b, l + 1, _p)) break;
      if (!empty_element_parsed_guard_(b, "block", c)) break;
    }
    exit_section_(b, m, BLOCK, true);
    return true;
  }

  // <<p>> NEWLINE *
  private static boolean block_0(PsiBuilder b, int l, Parser _p) {
    if (!recursion_guard_(b, l, "block_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = _p.parse(b, l);
    r = r && block_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // NEWLINE *
  private static boolean block_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "block_0_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, NEWLINE)) break;
      if (!empty_element_parsed_guard_(b, "block_0_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // !( NEWLINE*  ( end | methods_token | properties_token | events_token | enumeration_token ) )
  static boolean block_inside_class_recovery(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "block_inside_class_recovery")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !block_inside_class_recovery_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // NEWLINE*  ( end | methods_token | properties_token | events_token | enumeration_token )
  private static boolean block_inside_class_recovery_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "block_inside_class_recovery_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = block_inside_class_recovery_0_0(b, l + 1);
    r = r && block_inside_class_recovery_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // NEWLINE*
  private static boolean block_inside_class_recovery_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "block_inside_class_recovery_0_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, NEWLINE)) break;
      if (!empty_element_parsed_guard_(b, "block_inside_class_recovery_0_0", c)) break;
    }
    return true;
  }

  // end | methods_token | properties_token | events_token | enumeration_token
  private static boolean block_inside_class_recovery_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "block_inside_class_recovery_0_1")) return false;
    boolean r;
    r = consumeToken(b, END);
    if (!r) r = methods_token(b, l + 1);
    if (!r) r = properties_token(b, l + 1);
    if (!r) r = events_token(b, l + 1);
    if (!r) r = enumeration_token(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // <<block element>>
  static boolean block_that_recovers_until_end(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "block_that_recovers_until_end")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_);
    r = block(b, l + 1, element_parser_);
    exit_section_(b, l, m, r, false, not_end_or_oef_parser_);
    return r;
  }

  /* ********************************************************** */
  // '...' NEWLINE
  static boolean br(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "br")) return false;
    if (!nextTokenIs(b, ELLIPSIS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeTokens(b, 1, ELLIPSIS, NEWLINE);
    p = r; // pin = 1
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // case NEWLINE* case_expression NEWLINE* <<block element>>
  public static boolean case_block(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "case_block")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, CASE_BLOCK, "<case block>");
    r = consumeToken(b, CASE);
    p = r; // pin = 1
    r = r && report_error_(b, case_block_1(b, l + 1));
    r = p && report_error_(b, case_expression(b, l + 1)) && r;
    r = p && report_error_(b, case_block_3(b, l + 1)) && r;
    r = p && block(b, l + 1, element_parser_) && r;
    exit_section_(b, l, m, r, p, case_block_recovery_parser_);
    return r || p;
  }

  // NEWLINE*
  private static boolean case_block_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "case_block_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, NEWLINE)) break;
      if (!empty_element_parsed_guard_(b, "case_block_1", c)) break;
    }
    return true;
  }

  // NEWLINE*
  private static boolean case_block_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "case_block_3")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, NEWLINE)) break;
      if (!empty_element_parsed_guard_(b, "case_block_3", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // !( NEWLINE* ( case | otherwise | end ) )
  static boolean case_block_recovery(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "case_block_recovery")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !case_block_recovery_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // NEWLINE* ( case | otherwise | end )
  private static boolean case_block_recovery_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "case_block_recovery_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = case_block_recovery_0_0(b, l + 1);
    r = r && case_block_recovery_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // NEWLINE*
  private static boolean case_block_recovery_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "case_block_recovery_0_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, NEWLINE)) break;
      if (!empty_element_parsed_guard_(b, "case_block_recovery_0_0", c)) break;
    }
    return true;
  }

  // case | otherwise | end
  private static boolean case_block_recovery_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "case_block_recovery_0_1")) return false;
    boolean r;
    r = consumeToken(b, CASE);
    if (!r) r = consumeToken(b, OTHERWISE);
    if (!r) r = consumeToken(b, END);
    return r;
  }

  /* ********************************************************** */
  // expr
  public static boolean case_expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "case_expression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, CASE_EXPRESSION, "<case expression>");
    r = expr(b, l + 1, -1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // catch [ br* ident ] sep <<block element>>
  public static boolean catch_block(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "catch_block")) return false;
    if (!nextTokenIs(b, CATCH)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, CATCH_BLOCK, null);
    r = consumeToken(b, CATCH);
    p = r; // pin = 1
    r = r && report_error_(b, catch_block_1(b, l + 1));
    r = p && report_error_(b, sep(b, l + 1)) && r;
    r = p && block(b, l + 1, element_parser_) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // [ br* ident ]
  private static boolean catch_block_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "catch_block_1")) return false;
    catch_block_1_0(b, l + 1);
    return true;
  }

  // br* ident
  private static boolean catch_block_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "catch_block_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = catch_block_1_0_0(b, l + 1);
    r = r && parseIdentifier(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // br*
  private static boolean catch_block_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "catch_block_1_0_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!br(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "catch_block_1_0_0", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // <<withOff 'NEW_LINE_ALLOWED' (<<withOn 'ALLOW_END_AS_IDENTIFIER' (<<comma_list argument>>)>>)>> [ sep ';' ]
  static boolean cell_array_access_item_list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cell_array_access_item_list")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_);
    r = withOff(b, l + 1, NEW_LINE_ALLOWED, cell_array_access_item_list_0_1_parser_);
    r = r && cell_array_access_item_list_1(b, l + 1);
    exit_section_(b, l, m, r, false, not_brace_parser_);
    return r;
  }

  // <<withOn 'ALLOW_END_AS_IDENTIFIER' (<<comma_list argument>>)>>
  private static boolean cell_array_access_item_list_0_1(PsiBuilder b, int l) {
    return withOn(b, l + 1, ALLOW_END_AS_IDENTIFIER, cell_array_access_item_list_0_1_0_1_parser_);
  }

  // <<comma_list argument>>
  private static boolean cell_array_access_item_list_0_1_0_1(PsiBuilder b, int l) {
    return comma_list(b, l + 1, argument_parser_);
  }

  // [ sep ';' ]
  private static boolean cell_array_access_item_list_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cell_array_access_item_list_1")) return false;
    cell_array_access_item_list_1_0(b, l + 1);
    return true;
  }

  // sep ';'
  private static boolean cell_array_access_item_list_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cell_array_access_item_list_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = sep(b, l + 1);
    r = r && consumeToken(b, SEMICOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // cell_array_row ( sep ';' sep cell_array_row )* [sep ';']
  static boolean cell_array_content(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cell_array_content")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_);
    r = cell_array_row(b, l + 1);
    r = r && cell_array_content_1(b, l + 1);
    r = r && cell_array_content_2(b, l + 1);
    exit_section_(b, l, m, r, false, cell_array_content_recovery_parser_);
    return r;
  }

  // ( sep ';' sep cell_array_row )*
  private static boolean cell_array_content_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cell_array_content_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!cell_array_content_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "cell_array_content_1", c)) break;
    }
    return true;
  }

  // sep ';' sep cell_array_row
  private static boolean cell_array_content_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cell_array_content_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = sep(b, l + 1);
    r = r && consumeToken(b, SEMICOLON);
    r = r && sep(b, l + 1);
    r = r && cell_array_row(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // [sep ';']
  private static boolean cell_array_content_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cell_array_content_2")) return false;
    cell_array_content_2_0(b, l + 1);
    return true;
  }

  // sep ';'
  private static boolean cell_array_content_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cell_array_content_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = sep(b, l + 1);
    r = r && consumeToken(b, SEMICOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // one_line_recovery !( '}' )
  static boolean cell_array_content_recovery(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cell_array_content_recovery")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = one_line_recovery(b, l + 1);
    r = r && cell_array_content_recovery_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // !( '}' )
  private static boolean cell_array_content_recovery_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cell_array_content_recovery_1")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !consumeToken(b, RBRACE);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // expr
  public static boolean cell_array_item(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cell_array_item")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, CELL_ARRAY_ITEM, "<cell array item>");
    r = expr(b, l + 1, -1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // '{' sep cell_array_content? sep '}'
  public static boolean cell_array_literal(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cell_array_literal")) return false;
    if (!nextTokenIs(b, "<literal>", LBRACE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, CELL_ARRAY_LITERAL, "<literal>");
    r = consumeToken(b, LBRACE);
    p = r; // pin = 1
    r = r && report_error_(b, sep(b, l + 1));
    r = p && report_error_(b, cell_array_literal_2(b, l + 1)) && r;
    r = p && report_error_(b, sep(b, l + 1)) && r;
    r = p && consumeToken(b, RBRACE) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // cell_array_content?
  private static boolean cell_array_literal_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cell_array_literal_2")) return false;
    cell_array_content(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // cell_array_item ( sep (',' sep)? cell_array_item )* sep ','?
  public static boolean cell_array_row(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cell_array_row")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, CELL_ARRAY_ROW, "<cell array row>");
    r = cell_array_item(b, l + 1);
    r = r && cell_array_row_1(b, l + 1);
    r = r && sep(b, l + 1);
    r = r && cell_array_row_3(b, l + 1);
    exit_section_(b, l, m, r, false, cell_array_row_recovery_parser_);
    return r;
  }

  // ( sep (',' sep)? cell_array_item )*
  private static boolean cell_array_row_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cell_array_row_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!cell_array_row_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "cell_array_row_1", c)) break;
    }
    return true;
  }

  // sep (',' sep)? cell_array_item
  private static boolean cell_array_row_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cell_array_row_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = sep(b, l + 1);
    r = r && cell_array_row_1_0_1(b, l + 1);
    r = r && cell_array_item(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (',' sep)?
  private static boolean cell_array_row_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cell_array_row_1_0_1")) return false;
    cell_array_row_1_0_1_0(b, l + 1);
    return true;
  }

  // ',' sep
  private static boolean cell_array_row_1_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cell_array_row_1_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && sep(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ','?
  private static boolean cell_array_row_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cell_array_row_3")) return false;
    consumeToken(b, COMMA);
    return true;
  }

  /* ********************************************************** */
  // one_line_recovery !( '}' | sep ';' )
  static boolean cell_array_row_recovery(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cell_array_row_recovery")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = one_line_recovery(b, l + 1);
    r = r && cell_array_row_recovery_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // !( '}' | sep ';' )
  private static boolean cell_array_row_recovery_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cell_array_row_recovery_1")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !cell_array_row_recovery_1_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // '}' | sep ';'
  private static boolean cell_array_row_recovery_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cell_array_row_recovery_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, RBRACE);
    if (!r) r = cell_array_row_recovery_1_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // sep ';'
  private static boolean cell_array_row_recovery_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cell_array_row_recovery_1_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = sep(b, l + 1);
    r = r && consumeToken(b, SEMICOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // classdef br* attributes? br* ident br* super_classes? NEWLINE*
  //     (( properties_block | methods_block | events_block | enumeration_block ) NEWLINE*)*
  //     end
  public static boolean class_declaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "class_declaration")) return false;
    if (!nextTokenIs(b, CLASSDEF)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, CLASS_DECLARATION, null);
    r = consumeToken(b, CLASSDEF);
    p = r; // pin = 1
    r = r && report_error_(b, class_declaration_1(b, l + 1));
    r = p && report_error_(b, class_declaration_2(b, l + 1)) && r;
    r = p && report_error_(b, class_declaration_3(b, l + 1)) && r;
    r = p && report_error_(b, parseIdentifier(b, l + 1)) && r;
    r = p && report_error_(b, class_declaration_5(b, l + 1)) && r;
    r = p && report_error_(b, class_declaration_6(b, l + 1)) && r;
    r = p && report_error_(b, class_declaration_7(b, l + 1)) && r;
    r = p && report_error_(b, class_declaration_8(b, l + 1)) && r;
    r = p && consumeToken(b, END) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // br*
  private static boolean class_declaration_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "class_declaration_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!br(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "class_declaration_1", c)) break;
    }
    return true;
  }

  // attributes?
  private static boolean class_declaration_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "class_declaration_2")) return false;
    attributes(b, l + 1);
    return true;
  }

  // br*
  private static boolean class_declaration_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "class_declaration_3")) return false;
    while (true) {
      int c = current_position_(b);
      if (!br(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "class_declaration_3", c)) break;
    }
    return true;
  }

  // br*
  private static boolean class_declaration_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "class_declaration_5")) return false;
    while (true) {
      int c = current_position_(b);
      if (!br(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "class_declaration_5", c)) break;
    }
    return true;
  }

  // super_classes?
  private static boolean class_declaration_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "class_declaration_6")) return false;
    super_classes(b, l + 1);
    return true;
  }

  // NEWLINE*
  private static boolean class_declaration_7(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "class_declaration_7")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, NEWLINE)) break;
      if (!empty_element_parsed_guard_(b, "class_declaration_7", c)) break;
    }
    return true;
  }

  // (( properties_block | methods_block | events_block | enumeration_block ) NEWLINE*)*
  private static boolean class_declaration_8(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "class_declaration_8")) return false;
    while (true) {
      int c = current_position_(b);
      if (!class_declaration_8_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "class_declaration_8", c)) break;
    }
    return true;
  }

  // ( properties_block | methods_block | events_block | enumeration_block ) NEWLINE*
  private static boolean class_declaration_8_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "class_declaration_8_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = class_declaration_8_0_0(b, l + 1);
    r = r && class_declaration_8_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // properties_block | methods_block | events_block | enumeration_block
  private static boolean class_declaration_8_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "class_declaration_8_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = properties_block(b, l + 1);
    if (!r) r = methods_block(b, l + 1);
    if (!r) r = events_block(b, l + 1);
    if (!r) r = enumeration_block(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // NEWLINE*
  private static boolean class_declaration_8_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "class_declaration_8_0_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, NEWLINE)) break;
      if (!empty_element_parsed_guard_(b, "class_declaration_8_0_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // <<comma_list_item <<item>>>> (br* ',' br* <<comma_list_item <<item>>>>)*
  static boolean comma_list(PsiBuilder b, int l, Parser _item) {
    if (!recursion_guard_(b, l, "comma_list")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = comma_list_item(b, l + 1, _item);
    r = r && comma_list_1(b, l + 1, _item);
    exit_section_(b, m, null, r);
    return r;
  }

  // (br* ',' br* <<comma_list_item <<item>>>>)*
  private static boolean comma_list_1(PsiBuilder b, int l, Parser _item) {
    if (!recursion_guard_(b, l, "comma_list_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!comma_list_1_0(b, l + 1, _item)) break;
      if (!empty_element_parsed_guard_(b, "comma_list_1", c)) break;
    }
    return true;
  }

  // br* ',' br* <<comma_list_item <<item>>>>
  private static boolean comma_list_1_0(PsiBuilder b, int l, Parser _item) {
    if (!recursion_guard_(b, l, "comma_list_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = comma_list_1_0_0(b, l + 1);
    r = r && consumeToken(b, COMMA);
    r = r && comma_list_1_0_2(b, l + 1);
    r = r && comma_list_item(b, l + 1, _item);
    exit_section_(b, m, null, r);
    return r;
  }

  // br*
  private static boolean comma_list_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "comma_list_1_0_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!br(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "comma_list_1_0_0", c)) break;
    }
    return true;
  }

  // br*
  private static boolean comma_list_1_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "comma_list_1_0_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!br(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "comma_list_1_0_2", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // {} <<item>>
  static boolean comma_list_item(PsiBuilder b, int l, Parser _item) {
    if (!recursion_guard_(b, l, "comma_list_item")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = comma_list_item_0(b, l + 1);
    p = r; // pin = 1
    r = r && _item.parse(b, l);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // {}
  private static boolean comma_list_item_0(PsiBuilder b, int l) {
    return true;
  }

  /* ********************************************************** */
  // expr
  public static boolean condition(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "condition")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, CONDITION, "<condition>");
    r = expr(b, l + 1, -1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // if_block
  //   | switch_block
  //   | try_block
  //   | global br* global_variable_declaration (br* global_variable_declaration)*
  //   | file_operation
  //   | for_loop
  //   | while_loop
  //   | function_declaration
  //   | class_declaration
  //   | expr
  //   | COMMENT
  static boolean el(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "el")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = if_block(b, l + 1);
    if (!r) r = switch_block(b, l + 1);
    if (!r) r = try_block(b, l + 1);
    if (!r) r = el_3(b, l + 1);
    if (!r) r = file_operation(b, l + 1);
    if (!r) r = for_loop(b, l + 1);
    if (!r) r = while_loop(b, l + 1);
    if (!r) r = function_declaration(b, l + 1);
    if (!r) r = class_declaration(b, l + 1);
    if (!r) r = expr(b, l + 1, -1);
    if (!r) r = consumeToken(b, COMMENT);
    exit_section_(b, m, null, r);
    return r;
  }

  // global br* global_variable_declaration (br* global_variable_declaration)*
  private static boolean el_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "el_3")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeToken(b, GLOBAL);
    p = r; // pin = 1
    r = r && report_error_(b, el_3_1(b, l + 1));
    r = p && report_error_(b, global_variable_declaration(b, l + 1)) && r;
    r = p && el_3_3(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // br*
  private static boolean el_3_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "el_3_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!br(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "el_3_1", c)) break;
    }
    return true;
  }

  // (br* global_variable_declaration)*
  private static boolean el_3_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "el_3_3")) return false;
    while (true) {
      int c = current_position_(b);
      if (!el_3_3_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "el_3_3", c)) break;
    }
    return true;
  }

  // br* global_variable_declaration
  private static boolean el_3_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "el_3_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = el_3_3_0_0(b, l + 1);
    r = r && global_variable_declaration(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // br*
  private static boolean el_3_3_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "el_3_3_0_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!br(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "el_3_3_0_0", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // ( el [ ';' | ',' ] ) | ';' | ','
  static boolean element(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "element")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = element_0(b, l + 1);
    if (!r) r = consumeToken(b, SEMICOLON);
    if (!r) r = consumeToken(b, COMMA);
    exit_section_(b, m, null, r);
    return r;
  }

  // el [ ';' | ',' ]
  private static boolean element_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "element_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = el(b, l + 1);
    r = r && element_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // [ ';' | ',' ]
  private static boolean element_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "element_0_1")) return false;
    element_0_1_0(b, l + 1);
    return true;
  }

  // ';' | ','
  private static boolean element_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "element_0_1_0")) return false;
    boolean r;
    r = consumeToken(b, SEMICOLON);
    if (!r) r = consumeToken(b, COMMA);
    return r;
  }

  /* ********************************************************** */
  // else NEWLINE* if_block_body
  public static boolean else_block(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "else_block")) return false;
    if (!nextTokenIs(b, ELSE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ELSE);
    r = r && else_block_1(b, l + 1);
    r = r && if_block_body(b, l + 1);
    exit_section_(b, m, ELSE_BLOCK, r);
    return r;
  }

  // NEWLINE*
  private static boolean else_block_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "else_block_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, NEWLINE)) break;
      if (!empty_element_parsed_guard_(b, "else_block_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // elseif NEWLINE * if_block_body
  public static boolean elseif_block(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "elseif_block")) return false;
    if (!nextTokenIs(b, ELSEIF)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ELSEIF);
    r = r && elseif_block_1(b, l + 1);
    r = r && if_block_body(b, l + 1);
    exit_section_(b, m, ELSEIF_BLOCK, r);
    return r;
  }

  // NEWLINE *
  private static boolean elseif_block_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "elseif_block_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, NEWLINE)) break;
      if (!empty_element_parsed_guard_(b, "elseif_block_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // ident arguments?
  static boolean enum_item(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enum_item")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = parseIdentifier(b, l + 1);
    r = r && enum_item_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // arguments?
  private static boolean enum_item_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enum_item_1")) return false;
    arguments(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // enumeration_token NEWLINE*
  //     ( <<comma_list_item <<enum_item>>>> ( ',' <<comma_list_item <<enum_item>>>> | NEWLINE* enum_item )* ) NEWLINE *
  //     end
  public static boolean enumeration_block(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enumeration_block")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ENUMERATION_BLOCK, "<enumeration block>");
    r = enumeration_token(b, l + 1);
    p = r; // pin = 1
    r = r && report_error_(b, enumeration_block_1(b, l + 1));
    r = p && report_error_(b, enumeration_block_2(b, l + 1)) && r;
    r = p && report_error_(b, enumeration_block_3(b, l + 1)) && r;
    r = p && consumeToken(b, END) && r;
    exit_section_(b, l, m, r, p, block_inside_class_recovery_parser_);
    return r || p;
  }

  // NEWLINE*
  private static boolean enumeration_block_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enumeration_block_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, NEWLINE)) break;
      if (!empty_element_parsed_guard_(b, "enumeration_block_1", c)) break;
    }
    return true;
  }

  // <<comma_list_item <<enum_item>>>> ( ',' <<comma_list_item <<enum_item>>>> | NEWLINE* enum_item )*
  private static boolean enumeration_block_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enumeration_block_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = comma_list_item(b, l + 1, enumeration_block_2_0_0_parser_);
    r = r && enumeration_block_2_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ( ',' <<comma_list_item <<enum_item>>>> | NEWLINE* enum_item )*
  private static boolean enumeration_block_2_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enumeration_block_2_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!enumeration_block_2_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "enumeration_block_2_1", c)) break;
    }
    return true;
  }

  // ',' <<comma_list_item <<enum_item>>>> | NEWLINE* enum_item
  private static boolean enumeration_block_2_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enumeration_block_2_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = enumeration_block_2_1_0_0(b, l + 1);
    if (!r) r = enumeration_block_2_1_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ',' <<comma_list_item <<enum_item>>>>
  private static boolean enumeration_block_2_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enumeration_block_2_1_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && comma_list_item(b, l + 1, enumeration_block_2_1_0_0_1_0_parser_);
    exit_section_(b, m, null, r);
    return r;
  }

  // NEWLINE* enum_item
  private static boolean enumeration_block_2_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enumeration_block_2_1_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = enumeration_block_2_1_0_1_0(b, l + 1);
    r = r && enum_item(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // NEWLINE*
  private static boolean enumeration_block_2_1_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enumeration_block_2_1_0_1_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, NEWLINE)) break;
      if (!empty_element_parsed_guard_(b, "enumeration_block_2_1_0_1_0", c)) break;
    }
    return true;
  }

  // NEWLINE *
  private static boolean enumeration_block_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enumeration_block_3")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, NEWLINE)) break;
      if (!empty_element_parsed_guard_(b, "enumeration_block_3", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // <<parseIdentifierAsKeyword 'ENUMERATION'>>
  static boolean enumeration_token(PsiBuilder b, int l) {
    return parseIdentifierAsKeyword(b, l + 1, ENUMERATION);
  }

  /* ********************************************************** */
  // events_token br* attributes? NEWLINE*
  //     events_list NEWLINE*
  //     end
  public static boolean events_block(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "events_block")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, EVENTS_BLOCK, "<events block>");
    r = events_token(b, l + 1);
    p = r; // pin = 1
    r = r && report_error_(b, events_block_1(b, l + 1));
    r = p && report_error_(b, events_block_2(b, l + 1)) && r;
    r = p && report_error_(b, events_block_3(b, l + 1)) && r;
    r = p && report_error_(b, events_list(b, l + 1)) && r;
    r = p && report_error_(b, events_block_5(b, l + 1)) && r;
    r = p && consumeToken(b, END) && r;
    exit_section_(b, l, m, r, p, block_inside_class_recovery_parser_);
    return r || p;
  }

  // br*
  private static boolean events_block_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "events_block_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!br(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "events_block_1", c)) break;
    }
    return true;
  }

  // attributes?
  private static boolean events_block_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "events_block_2")) return false;
    attributes(b, l + 1);
    return true;
  }

  // NEWLINE*
  private static boolean events_block_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "events_block_3")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, NEWLINE)) break;
      if (!empty_element_parsed_guard_(b, "events_block_3", c)) break;
    }
    return true;
  }

  // NEWLINE*
  private static boolean events_block_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "events_block_5")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, NEWLINE)) break;
      if (!empty_element_parsed_guard_(b, "events_block_5", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // !( NEWLINE* end )
  static boolean events_block_body_recovery(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "events_block_body_recovery")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !events_block_body_recovery_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // NEWLINE* end
  private static boolean events_block_body_recovery_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "events_block_body_recovery_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = events_block_body_recovery_0_0(b, l + 1);
    r = r && consumeToken(b, END);
    exit_section_(b, m, null, r);
    return r;
  }

  // NEWLINE*
  private static boolean events_block_body_recovery_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "events_block_body_recovery_0_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, NEWLINE)) break;
      if (!empty_element_parsed_guard_(b, "events_block_body_recovery_0_0", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // (ref_expr NEWLINE*)*
  public static boolean events_list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "events_list")) return false;
    Marker m = enter_section_(b, l, _NONE_, EVENTS_LIST, "<events list>");
    while (true) {
      int c = current_position_(b);
      if (!events_list_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "events_list", c)) break;
    }
    exit_section_(b, l, m, true, false, events_block_body_recovery_parser_);
    return true;
  }

  // ref_expr NEWLINE*
  private static boolean events_list_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "events_list_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = ref_expr(b, l + 1);
    r = r && events_list_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // NEWLINE*
  private static boolean events_list_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "events_list_0_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, NEWLINE)) break;
      if (!empty_element_parsed_guard_(b, "events_list_0_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // <<parseIdentifierAsKeyword 'EVENTS'>>
  static boolean events_token(PsiBuilder b, int l) {
    return parseIdentifierAsKeyword(b, l + 1, EVENTS);
  }

  /* ********************************************************** */
  // (LOAD | DIR | LS | CD) br* FILE_NAME br* variables?
  public static boolean file_operation(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "file_operation")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, FILE_OPERATION, "<file operation>");
    r = file_operation_0(b, l + 1);
    p = r; // pin = 1
    r = r && report_error_(b, file_operation_1(b, l + 1));
    r = p && report_error_(b, consumeToken(b, FILE_NAME)) && r;
    r = p && report_error_(b, file_operation_3(b, l + 1)) && r;
    r = p && file_operation_4(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // LOAD | DIR | LS | CD
  private static boolean file_operation_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "file_operation_0")) return false;
    boolean r;
    r = consumeToken(b, LOAD);
    if (!r) r = consumeToken(b, DIR);
    if (!r) r = consumeToken(b, LS);
    if (!r) r = consumeToken(b, CD);
    return r;
  }

  // br*
  private static boolean file_operation_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "file_operation_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!br(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "file_operation_1", c)) break;
    }
    return true;
  }

  // br*
  private static boolean file_operation_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "file_operation_3")) return false;
    while (true) {
      int c = current_position_(b);
      if (!br(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "file_operation_3", c)) break;
    }
    return true;
  }

  // variables?
  private static boolean file_operation_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "file_operation_4")) return false;
    variables(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // for br* for_loop_range [ ';' | ',' ] NEWLINE*
  //     block_that_recovers_until_end
  //     end
  public static boolean for_loop(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "for_loop")) return false;
    if (!nextTokenIs(b, FOR)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, FOR_LOOP, null);
    r = consumeToken(b, FOR);
    p = r; // pin = 1
    r = r && report_error_(b, for_loop_1(b, l + 1));
    r = p && report_error_(b, for_loop_range(b, l + 1)) && r;
    r = p && report_error_(b, for_loop_3(b, l + 1)) && r;
    r = p && report_error_(b, for_loop_4(b, l + 1)) && r;
    r = p && report_error_(b, block_that_recovers_until_end(b, l + 1)) && r;
    r = p && consumeToken(b, END) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // br*
  private static boolean for_loop_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "for_loop_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!br(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "for_loop_1", c)) break;
    }
    return true;
  }

  // [ ';' | ',' ]
  private static boolean for_loop_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "for_loop_3")) return false;
    for_loop_3_0(b, l + 1);
    return true;
  }

  // ';' | ','
  private static boolean for_loop_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "for_loop_3_0")) return false;
    boolean r;
    r = consumeToken(b, SEMICOLON);
    if (!r) r = consumeToken(b, COMMA);
    return r;
  }

  // NEWLINE*
  private static boolean for_loop_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "for_loop_4")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, NEWLINE)) break;
      if (!empty_element_parsed_guard_(b, "for_loop_4", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // assign_expr
  public static boolean for_loop_range(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "for_loop_range")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, FOR_LOOP_RANGE, "<for loop range>");
    r = expr(b, l + 1, -1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // <<withOn 'ALLOW_END_AS_IDENTIFIER' ref_expr>> ws arguments
  static boolean function_call_or_matrix_element_access(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_call_or_matrix_element_access")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = withOn(b, l + 1, ALLOW_END_AS_IDENTIFIER, ref_expr_parser_);
    r = r && parseWhiteSpace(b, l + 1, br_parser_);
    r = r && arguments(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // function br* return_value_part? br* getter_or_setter_modifier? <<withOn 'ALLOW_END_AS_IDENTIFIER' ident>> br* parameters? NEWLINE*
  //     block_that_recovers_until_end
  //     NEWLINE* ( end | <<eof>> )
  public static boolean function_declaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_declaration")) return false;
    if (!nextTokenIs(b, FUNCTION)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, FUNCTION_DECLARATION, null);
    r = consumeToken(b, FUNCTION);
    p = r; // pin = 1
    r = r && report_error_(b, function_declaration_1(b, l + 1));
    r = p && report_error_(b, function_declaration_2(b, l + 1)) && r;
    r = p && report_error_(b, function_declaration_3(b, l + 1)) && r;
    r = p && report_error_(b, function_declaration_4(b, l + 1)) && r;
    r = p && report_error_(b, withOn(b, l + 1, ALLOW_END_AS_IDENTIFIER, ident_parser_)) && r;
    r = p && report_error_(b, function_declaration_6(b, l + 1)) && r;
    r = p && report_error_(b, function_declaration_7(b, l + 1)) && r;
    r = p && report_error_(b, function_declaration_8(b, l + 1)) && r;
    r = p && report_error_(b, block_that_recovers_until_end(b, l + 1)) && r;
    r = p && report_error_(b, function_declaration_10(b, l + 1)) && r;
    r = p && function_declaration_11(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // br*
  private static boolean function_declaration_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_declaration_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!br(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "function_declaration_1", c)) break;
    }
    return true;
  }

  // return_value_part?
  private static boolean function_declaration_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_declaration_2")) return false;
    return_value_part(b, l + 1);
    return true;
  }

  // br*
  private static boolean function_declaration_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_declaration_3")) return false;
    while (true) {
      int c = current_position_(b);
      if (!br(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "function_declaration_3", c)) break;
    }
    return true;
  }

  // getter_or_setter_modifier?
  private static boolean function_declaration_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_declaration_4")) return false;
    getter_or_setter_modifier(b, l + 1);
    return true;
  }

  // br*
  private static boolean function_declaration_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_declaration_6")) return false;
    while (true) {
      int c = current_position_(b);
      if (!br(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "function_declaration_6", c)) break;
    }
    return true;
  }

  // parameters?
  private static boolean function_declaration_7(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_declaration_7")) return false;
    parameters(b, l + 1);
    return true;
  }

  // NEWLINE*
  private static boolean function_declaration_8(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_declaration_8")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, NEWLINE)) break;
      if (!empty_element_parsed_guard_(b, "function_declaration_8", c)) break;
    }
    return true;
  }

  // NEWLINE*
  private static boolean function_declaration_10(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_declaration_10")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, NEWLINE)) break;
      if (!empty_element_parsed_guard_(b, "function_declaration_10", c)) break;
    }
    return true;
  }

  // end | <<eof>>
  private static boolean function_declaration_11(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_declaration_11")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, END);
    if (!r) r = eof(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ident '.'
  public static boolean getter_or_setter_modifier(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "getter_or_setter_modifier")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, GETTER_OR_SETTER_MODIFIER, "<getter or setter modifier>");
    r = parseIdentifier(b, l + 1);
    r = r && consumeToken(b, DOT);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // ident
  public static boolean global_variable_declaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "global_variable_declaration")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, GLOBAL_VARIABLE_DECLARATION, "<global variable declaration>");
    r = parseIdentifier(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // if br* condition ','? NEWLINE*
  //   if_block_body NEWLINE*
  //   elseif_block* NEWLINE*
  //   [else_block NEWLINE*]
  //   end
  public static boolean if_block(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "if_block")) return false;
    if (!nextTokenIs(b, IF)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, IF_BLOCK, null);
    r = consumeToken(b, IF);
    p = r; // pin = 1
    r = r && report_error_(b, if_block_1(b, l + 1));
    r = p && report_error_(b, condition(b, l + 1)) && r;
    r = p && report_error_(b, if_block_3(b, l + 1)) && r;
    r = p && report_error_(b, if_block_4(b, l + 1)) && r;
    r = p && report_error_(b, if_block_body(b, l + 1)) && r;
    r = p && report_error_(b, if_block_6(b, l + 1)) && r;
    r = p && report_error_(b, if_block_7(b, l + 1)) && r;
    r = p && report_error_(b, if_block_8(b, l + 1)) && r;
    r = p && report_error_(b, if_block_9(b, l + 1)) && r;
    r = p && consumeToken(b, END) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // br*
  private static boolean if_block_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "if_block_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!br(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "if_block_1", c)) break;
    }
    return true;
  }

  // ','?
  private static boolean if_block_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "if_block_3")) return false;
    consumeToken(b, COMMA);
    return true;
  }

  // NEWLINE*
  private static boolean if_block_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "if_block_4")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, NEWLINE)) break;
      if (!empty_element_parsed_guard_(b, "if_block_4", c)) break;
    }
    return true;
  }

  // NEWLINE*
  private static boolean if_block_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "if_block_6")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, NEWLINE)) break;
      if (!empty_element_parsed_guard_(b, "if_block_6", c)) break;
    }
    return true;
  }

  // elseif_block*
  private static boolean if_block_7(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "if_block_7")) return false;
    while (true) {
      int c = current_position_(b);
      if (!elseif_block(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "if_block_7", c)) break;
    }
    return true;
  }

  // NEWLINE*
  private static boolean if_block_8(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "if_block_8")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, NEWLINE)) break;
      if (!empty_element_parsed_guard_(b, "if_block_8", c)) break;
    }
    return true;
  }

  // [else_block NEWLINE*]
  private static boolean if_block_9(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "if_block_9")) return false;
    if_block_9_0(b, l + 1);
    return true;
  }

  // else_block NEWLINE*
  private static boolean if_block_9_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "if_block_9_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = else_block(b, l + 1);
    r = r && if_block_9_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // NEWLINE*
  private static boolean if_block_9_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "if_block_9_0_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, NEWLINE)) break;
      if (!empty_element_parsed_guard_(b, "if_block_9_0_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // <<block element>>
  static boolean if_block_body(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "if_block_body")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_);
    r = block(b, l + 1, element_parser_);
    exit_section_(b, l, m, r, false, not_end_or_elseif_or_else_parser_);
    return r;
  }

  /* ********************************************************** */
  // expr
  public static boolean lambda_body(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "lambda_body")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, LAMBDA_BODY, "<lambda body>");
    r = expr(b, l + 1, -1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // sep <<list_item <<item>>>> (sep ',' sep <<list_item <<item>>>>)* sep
  static boolean list(PsiBuilder b, int l, Parser _item) {
    if (!recursion_guard_(b, l, "list")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_);
    r = sep(b, l + 1);
    r = r && list_item(b, l + 1, _item);
    r = r && list_2(b, l + 1, _item);
    r = r && sep(b, l + 1);
    exit_section_(b, l, m, r, false, not_rparenth_parser_);
    return r;
  }

  // (sep ',' sep <<list_item <<item>>>>)*
  private static boolean list_2(PsiBuilder b, int l, Parser _item) {
    if (!recursion_guard_(b, l, "list_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!list_2_0(b, l + 1, _item)) break;
      if (!empty_element_parsed_guard_(b, "list_2", c)) break;
    }
    return true;
  }

  // sep ',' sep <<list_item <<item>>>>
  private static boolean list_2_0(PsiBuilder b, int l, Parser _item) {
    if (!recursion_guard_(b, l, "list_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = sep(b, l + 1);
    r = r && consumeToken(b, COMMA);
    r = r && sep(b, l + 1);
    r = r && list_item(b, l + 1, _item);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // {} <<item>>
  static boolean list_item(PsiBuilder b, int l, Parser _item) {
    if (!recursion_guard_(b, l, "list_item")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = list_item_0(b, l + 1);
    p = r; // pin = 1
    r = r && _item.parse(b, l);
    exit_section_(b, l, m, r, p, not_rparenth_or_comma_parser_);
    return r || p;
  }

  // {}
  private static boolean list_item_0(PsiBuilder b, int l) {
    return true;
  }

  /* ********************************************************** */
  // matrix_row ( sep ';' NEWLINE* sep matrix_row )* sep ';'?
  static boolean matrix_content(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "matrix_content")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_);
    r = matrix_row(b, l + 1);
    r = r && matrix_content_1(b, l + 1);
    r = r && sep(b, l + 1);
    r = r && matrix_content_3(b, l + 1);
    exit_section_(b, l, m, r, false, not_rbracket_parser_);
    return r;
  }

  // ( sep ';' NEWLINE* sep matrix_row )*
  private static boolean matrix_content_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "matrix_content_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!matrix_content_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "matrix_content_1", c)) break;
    }
    return true;
  }

  // sep ';' NEWLINE* sep matrix_row
  private static boolean matrix_content_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "matrix_content_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = sep(b, l + 1);
    r = r && consumeToken(b, SEMICOLON);
    r = r && matrix_content_1_0_2(b, l + 1);
    r = r && sep(b, l + 1);
    r = r && matrix_row(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // NEWLINE*
  private static boolean matrix_content_1_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "matrix_content_1_0_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, NEWLINE)) break;
      if (!empty_element_parsed_guard_(b, "matrix_content_1_0_2", c)) break;
    }
    return true;
  }

  // ';'?
  private static boolean matrix_content_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "matrix_content_3")) return false;
    consumeToken(b, SEMICOLON);
    return true;
  }

  /* ********************************************************** */
  // '~' | expr
  public static boolean matrix_item(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "matrix_item")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MATRIX_ITEM, "<matrix item>");
    r = consumeToken(b, TILDA);
    if (!r) r = expr(b, l + 1, -1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // '[' sep matrix_content? sep ']'
  public static boolean matrix_literal(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "matrix_literal")) return false;
    if (!nextTokenIs(b, "<literal>", LBRACKET)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, MATRIX_LITERAL, "<literal>");
    r = consumeToken(b, LBRACKET);
    p = r; // pin = 1
    r = r && report_error_(b, sep(b, l + 1));
    r = p && report_error_(b, matrix_literal_2(b, l + 1)) && r;
    r = p && report_error_(b, sep(b, l + 1)) && r;
    r = p && consumeToken(b, RBRACKET) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // matrix_content?
  private static boolean matrix_literal_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "matrix_literal_2")) return false;
    matrix_content(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // matrix_item ( sep (',' sep)?  matrix_item)* sep ','?
  public static boolean matrix_row(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "matrix_row")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MATRIX_ROW, "<matrix row>");
    r = matrix_item(b, l + 1);
    r = r && matrix_row_1(b, l + 1);
    r = r && sep(b, l + 1);
    r = r && matrix_row_3(b, l + 1);
    exit_section_(b, l, m, r, false, not_rbracket_or_semicolon_parser_);
    return r;
  }

  // ( sep (',' sep)?  matrix_item)*
  private static boolean matrix_row_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "matrix_row_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!matrix_row_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "matrix_row_1", c)) break;
    }
    return true;
  }

  // sep (',' sep)?  matrix_item
  private static boolean matrix_row_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "matrix_row_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = sep(b, l + 1);
    r = r && matrix_row_1_0_1(b, l + 1);
    r = r && matrix_item(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (',' sep)?
  private static boolean matrix_row_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "matrix_row_1_0_1")) return false;
    matrix_row_1_0_1_0(b, l + 1);
    return true;
  }

  // ',' sep
  private static boolean matrix_row_1_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "matrix_row_1_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && sep(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ','?
  private static boolean matrix_row_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "matrix_row_3")) return false;
    consumeToken(b, COMMA);
    return true;
  }

  /* ********************************************************** */
  // methods_token br* attributes? NEWLINE*
  //     methods_block_body NEWLINE*
  //     end
  public static boolean methods_block(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "methods_block")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, METHODS_BLOCK, "<methods block>");
    r = methods_token(b, l + 1);
    p = r; // pin = 1
    r = r && report_error_(b, methods_block_1(b, l + 1));
    r = p && report_error_(b, methods_block_2(b, l + 1)) && r;
    r = p && report_error_(b, methods_block_3(b, l + 1)) && r;
    r = p && report_error_(b, methods_block_body(b, l + 1)) && r;
    r = p && report_error_(b, methods_block_5(b, l + 1)) && r;
    r = p && consumeToken(b, END) && r;
    exit_section_(b, l, m, r, p, block_inside_class_recovery_parser_);
    return r || p;
  }

  // br*
  private static boolean methods_block_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "methods_block_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!br(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "methods_block_1", c)) break;
    }
    return true;
  }

  // attributes?
  private static boolean methods_block_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "methods_block_2")) return false;
    attributes(b, l + 1);
    return true;
  }

  // NEWLINE*
  private static boolean methods_block_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "methods_block_3")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, NEWLINE)) break;
      if (!empty_element_parsed_guard_(b, "methods_block_3", c)) break;
    }
    return true;
  }

  // NEWLINE*
  private static boolean methods_block_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "methods_block_5")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, NEWLINE)) break;
      if (!empty_element_parsed_guard_(b, "methods_block_5", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // <<block element>>
  static boolean methods_block_body(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "methods_block_body")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_);
    r = block(b, l + 1, element_parser_);
    exit_section_(b, l, m, r, false, block_inside_class_recovery_parser_);
    return r;
  }

  /* ********************************************************** */
  // <<parseIdentifierAsKeyword 'METHODS'>>
  static boolean methods_token(PsiBuilder b, int l) {
    return parseIdentifierAsKeyword(b, l + 1, METHODS);
  }

  /* ********************************************************** */
  // '[' br* ret_values_list? br* ']'
  public static boolean multiple_ret_values(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "multiple_ret_values")) return false;
    if (!nextTokenIs(b, LBRACKET)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, RETURN_VALUES, null);
    r = consumeToken(b, LBRACKET);
    p = r; // pin = 1
    r = r && report_error_(b, multiple_ret_values_1(b, l + 1));
    r = p && report_error_(b, multiple_ret_values_2(b, l + 1)) && r;
    r = p && report_error_(b, multiple_ret_values_3(b, l + 1)) && r;
    r = p && consumeToken(b, RBRACKET) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // br*
  private static boolean multiple_ret_values_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "multiple_ret_values_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!br(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "multiple_ret_values_1", c)) break;
    }
    return true;
  }

  // ret_values_list?
  private static boolean multiple_ret_values_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "multiple_ret_values_2")) return false;
    ret_values_list(b, l + 1);
    return true;
  }

  // br*
  private static boolean multiple_ret_values_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "multiple_ret_values_3")) return false;
    while (true) {
      int c = current_position_(b);
      if (!br(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "multiple_ret_values_3", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // multiple_ret_values br* '='
  static boolean multiple_return_values_part(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "multiple_return_values_part")) return false;
    if (!nextTokenIs(b, LBRACKET)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = multiple_ret_values(b, l + 1);
    p = r; // pin = 1
    r = r && report_error_(b, multiple_return_values_part_1(b, l + 1));
    r = p && consumeToken(b, ASSIGN) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // br*
  private static boolean multiple_return_values_part_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "multiple_return_values_part_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!br(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "multiple_return_values_part_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // !( br* '&'
  //     | br* super_classes_list_item
  //     | NEWLINE* properties_token
  //     | NEWLINE* methods_token
  //     | NEWLINE* events_token
  //     | NEWLINE* enumeration_token
  //     | NEWLINE* end )
  static boolean not_ampersand_or_super_class_or_properties_or_methods_or_end(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "not_ampersand_or_super_class_or_properties_or_methods_or_end")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !not_ampersand_or_super_class_or_properties_or_methods_or_end_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // br* '&'
  //     | br* super_classes_list_item
  //     | NEWLINE* properties_token
  //     | NEWLINE* methods_token
  //     | NEWLINE* events_token
  //     | NEWLINE* enumeration_token
  //     | NEWLINE* end
  private static boolean not_ampersand_or_super_class_or_properties_or_methods_or_end_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "not_ampersand_or_super_class_or_properties_or_methods_or_end_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = not_ampersand_or_super_class_or_properties_or_methods_or_end_0_0(b, l + 1);
    if (!r) r = not_ampersand_or_super_class_or_properties_or_methods_or_end_0_1(b, l + 1);
    if (!r) r = not_ampersand_or_super_class_or_properties_or_methods_or_end_0_2(b, l + 1);
    if (!r) r = not_ampersand_or_super_class_or_properties_or_methods_or_end_0_3(b, l + 1);
    if (!r) r = not_ampersand_or_super_class_or_properties_or_methods_or_end_0_4(b, l + 1);
    if (!r) r = not_ampersand_or_super_class_or_properties_or_methods_or_end_0_5(b, l + 1);
    if (!r) r = not_ampersand_or_super_class_or_properties_or_methods_or_end_0_6(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // br* '&'
  private static boolean not_ampersand_or_super_class_or_properties_or_methods_or_end_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "not_ampersand_or_super_class_or_properties_or_methods_or_end_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = not_ampersand_or_super_class_or_properties_or_methods_or_end_0_0_0(b, l + 1);
    r = r && consumeToken(b, MATRIX_AND);
    exit_section_(b, m, null, r);
    return r;
  }

  // br*
  private static boolean not_ampersand_or_super_class_or_properties_or_methods_or_end_0_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "not_ampersand_or_super_class_or_properties_or_methods_or_end_0_0_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!br(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "not_ampersand_or_super_class_or_properties_or_methods_or_end_0_0_0", c)) break;
    }
    return true;
  }

  // br* super_classes_list_item
  private static boolean not_ampersand_or_super_class_or_properties_or_methods_or_end_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "not_ampersand_or_super_class_or_properties_or_methods_or_end_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = not_ampersand_or_super_class_or_properties_or_methods_or_end_0_1_0(b, l + 1);
    r = r && super_classes_list_item(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // br*
  private static boolean not_ampersand_or_super_class_or_properties_or_methods_or_end_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "not_ampersand_or_super_class_or_properties_or_methods_or_end_0_1_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!br(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "not_ampersand_or_super_class_or_properties_or_methods_or_end_0_1_0", c)) break;
    }
    return true;
  }

  // NEWLINE* properties_token
  private static boolean not_ampersand_or_super_class_or_properties_or_methods_or_end_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "not_ampersand_or_super_class_or_properties_or_methods_or_end_0_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = not_ampersand_or_super_class_or_properties_or_methods_or_end_0_2_0(b, l + 1);
    r = r && properties_token(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // NEWLINE*
  private static boolean not_ampersand_or_super_class_or_properties_or_methods_or_end_0_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "not_ampersand_or_super_class_or_properties_or_methods_or_end_0_2_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, NEWLINE)) break;
      if (!empty_element_parsed_guard_(b, "not_ampersand_or_super_class_or_properties_or_methods_or_end_0_2_0", c)) break;
    }
    return true;
  }

  // NEWLINE* methods_token
  private static boolean not_ampersand_or_super_class_or_properties_or_methods_or_end_0_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "not_ampersand_or_super_class_or_properties_or_methods_or_end_0_3")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = not_ampersand_or_super_class_or_properties_or_methods_or_end_0_3_0(b, l + 1);
    r = r && methods_token(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // NEWLINE*
  private static boolean not_ampersand_or_super_class_or_properties_or_methods_or_end_0_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "not_ampersand_or_super_class_or_properties_or_methods_or_end_0_3_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, NEWLINE)) break;
      if (!empty_element_parsed_guard_(b, "not_ampersand_or_super_class_or_properties_or_methods_or_end_0_3_0", c)) break;
    }
    return true;
  }

  // NEWLINE* events_token
  private static boolean not_ampersand_or_super_class_or_properties_or_methods_or_end_0_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "not_ampersand_or_super_class_or_properties_or_methods_or_end_0_4")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = not_ampersand_or_super_class_or_properties_or_methods_or_end_0_4_0(b, l + 1);
    r = r && events_token(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // NEWLINE*
  private static boolean not_ampersand_or_super_class_or_properties_or_methods_or_end_0_4_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "not_ampersand_or_super_class_or_properties_or_methods_or_end_0_4_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, NEWLINE)) break;
      if (!empty_element_parsed_guard_(b, "not_ampersand_or_super_class_or_properties_or_methods_or_end_0_4_0", c)) break;
    }
    return true;
  }

  // NEWLINE* enumeration_token
  private static boolean not_ampersand_or_super_class_or_properties_or_methods_or_end_0_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "not_ampersand_or_super_class_or_properties_or_methods_or_end_0_5")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = not_ampersand_or_super_class_or_properties_or_methods_or_end_0_5_0(b, l + 1);
    r = r && enumeration_token(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // NEWLINE*
  private static boolean not_ampersand_or_super_class_or_properties_or_methods_or_end_0_5_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "not_ampersand_or_super_class_or_properties_or_methods_or_end_0_5_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, NEWLINE)) break;
      if (!empty_element_parsed_guard_(b, "not_ampersand_or_super_class_or_properties_or_methods_or_end_0_5_0", c)) break;
    }
    return true;
  }

  // NEWLINE* end
  private static boolean not_ampersand_or_super_class_or_properties_or_methods_or_end_0_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "not_ampersand_or_super_class_or_properties_or_methods_or_end_0_6")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = not_ampersand_or_super_class_or_properties_or_methods_or_end_0_6_0(b, l + 1);
    r = r && consumeToken(b, END);
    exit_section_(b, m, null, r);
    return r;
  }

  // NEWLINE*
  private static boolean not_ampersand_or_super_class_or_properties_or_methods_or_end_0_6_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "not_ampersand_or_super_class_or_properties_or_methods_or_end_0_6_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, NEWLINE)) break;
      if (!empty_element_parsed_guard_(b, "not_ampersand_or_super_class_or_properties_or_methods_or_end_0_6_0", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // !('}')
  static boolean not_brace(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "not_brace")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !consumeToken(b, RBRACE);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // !( NEWLINE* (end | elseif | else) )
  static boolean not_end_or_elseif_or_else(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "not_end_or_elseif_or_else")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !not_end_or_elseif_or_else_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // NEWLINE* (end | elseif | else)
  private static boolean not_end_or_elseif_or_else_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "not_end_or_elseif_or_else_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = not_end_or_elseif_or_else_0_0(b, l + 1);
    r = r && not_end_or_elseif_or_else_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // NEWLINE*
  private static boolean not_end_or_elseif_or_else_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "not_end_or_elseif_or_else_0_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, NEWLINE)) break;
      if (!empty_element_parsed_guard_(b, "not_end_or_elseif_or_else_0_0", c)) break;
    }
    return true;
  }

  // end | elseif | else
  private static boolean not_end_or_elseif_or_else_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "not_end_or_elseif_or_else_0_1")) return false;
    boolean r;
    r = consumeToken(b, END);
    if (!r) r = consumeToken(b, ELSEIF);
    if (!r) r = consumeToken(b, ELSE);
    return r;
  }

  /* ********************************************************** */
  // !( NEWLINE* ( end | <<eof>> ) )
  static boolean not_end_or_oef(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "not_end_or_oef")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !not_end_or_oef_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // NEWLINE* ( end | <<eof>> )
  private static boolean not_end_or_oef_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "not_end_or_oef_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = not_end_or_oef_0_0(b, l + 1);
    r = r && not_end_or_oef_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // NEWLINE*
  private static boolean not_end_or_oef_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "not_end_or_oef_0_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, NEWLINE)) break;
      if (!empty_element_parsed_guard_(b, "not_end_or_oef_0_0", c)) break;
    }
    return true;
  }

  // end | <<eof>>
  private static boolean not_end_or_oef_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "not_end_or_oef_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, END);
    if (!r) r = eof(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // !( ident | expr | '=' | NEWLINE )
  static boolean not_expr_or_equal(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "not_expr_or_equal")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !not_expr_or_equal_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ident | expr | '=' | NEWLINE
  private static boolean not_expr_or_equal_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "not_expr_or_equal_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = parseIdentifier(b, l + 1);
    if (!r) r = expr(b, l + 1, -1);
    if (!r) r = consumeToken(b, ASSIGN);
    if (!r) r = consumeToken(b, NEWLINE);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // one_line_recovery !( ']' )
  static boolean not_rbracket(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "not_rbracket")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = one_line_recovery(b, l + 1);
    r = r && not_rbracket_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // !( ']' )
  private static boolean not_rbracket_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "not_rbracket_1")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !consumeToken(b, RBRACKET);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // not_rbracket !( ';' )
  static boolean not_rbracket_or_semicolon(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "not_rbracket_or_semicolon")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = not_rbracket(b, l + 1);
    r = r && not_rbracket_or_semicolon_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // !( ';' )
  private static boolean not_rbracket_or_semicolon_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "not_rbracket_or_semicolon_1")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !consumeToken(b, SEMICOLON);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // !( ')' )
  static boolean not_rparenth(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "not_rparenth")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !consumeToken(b, RPARENTH);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // !( ')' | ',' | '...' | NEWLINE )
  static boolean not_rparenth_or_comma(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "not_rparenth_or_comma")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !not_rparenth_or_comma_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ')' | ',' | '...' | NEWLINE
  private static boolean not_rparenth_or_comma_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "not_rparenth_or_comma_0")) return false;
    boolean r;
    r = consumeToken(b, RPARENTH);
    if (!r) r = consumeToken(b, COMMA);
    if (!r) r = consumeToken(b, ELLIPSIS);
    if (!r) r = consumeToken(b, NEWLINE);
    return r;
  }

  /* ********************************************************** */
  // FLOAT | FLOAT_EXPONENTIAL | INTEGER
  static boolean number_literal(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "number_literal")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, null, "<literal>");
    r = consumeToken(b, FLOAT);
    if (!r) r = consumeToken(b, FLOAT_EXPONENTIAL);
    if (!r) r = consumeToken(b, INTEGER);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // !( end | NEWLINE | '...' )
  static boolean one_line_recovery(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "one_line_recovery")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !one_line_recovery_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // end | NEWLINE | '...'
  private static boolean one_line_recovery_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "one_line_recovery_0")) return false;
    boolean r;
    r = consumeToken(b, END);
    if (!r) r = consumeToken(b, NEWLINE);
    if (!r) r = consumeToken(b, ELLIPSIS);
    return r;
  }

  /* ********************************************************** */
  // otherwise NEWLINE* <<block element>>
  public static boolean otherwise_block(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "otherwise_block")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, OTHERWISE_BLOCK, "<otherwise block>");
    r = consumeToken(b, OTHERWISE);
    p = r; // pin = 1
    r = r && report_error_(b, otherwise_block_1(b, l + 1));
    r = p && block(b, l + 1, element_parser_) && r;
    exit_section_(b, l, m, r, p, case_block_recovery_parser_);
    return r || p;
  }

  // NEWLINE*
  private static boolean otherwise_block_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "otherwise_block_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, NEWLINE)) break;
      if (!empty_element_parsed_guard_(b, "otherwise_block_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // '(' [ !')' <<list <<item>>>> sep ] ')'
  static boolean p_opt_list(PsiBuilder b, int l, Parser _item) {
    if (!recursion_guard_(b, l, "p_opt_list")) return false;
    if (!nextTokenIs(b, LPARENTH)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeToken(b, LPARENTH);
    p = r; // pin = 1
    r = r && report_error_(b, p_opt_list_1(b, l + 1, _item));
    r = p && consumeToken(b, RPARENTH) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // [ !')' <<list <<item>>>> sep ]
  private static boolean p_opt_list_1(PsiBuilder b, int l, Parser _item) {
    if (!recursion_guard_(b, l, "p_opt_list_1")) return false;
    p_opt_list_1_0(b, l + 1, _item);
    return true;
  }

  // !')' <<list <<item>>>> sep
  private static boolean p_opt_list_1_0(PsiBuilder b, int l, Parser _item) {
    if (!recursion_guard_(b, l, "p_opt_list_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = p_opt_list_1_0_0(b, l + 1);
    r = r && list(b, l + 1, _item);
    r = r && sep(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // !')'
  private static boolean p_opt_list_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "p_opt_list_1_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !consumeToken(b, RPARENTH);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // ident | '~'
  public static boolean parameter(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parameter")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, PARAMETER, "<parameter>");
    r = parseIdentifier(b, l + 1);
    if (!r) r = consumeToken(b, TILDA);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // <<p_opt_list parameter>>
  public static boolean parameters(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parameters")) return false;
    if (!nextTokenIs(b, LPARENTH)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = p_opt_list(b, l + 1, parameter_parser_);
    exit_section_(b, m, PARAMETERS, r);
    return r;
  }

  /* ********************************************************** */
  // properties_token br* attributes? NEWLINE*
  //     properties_block_body NEWLINE*
  //     end
  public static boolean properties_block(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "properties_block")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, PROPERTIES_BLOCK, "<properties block>");
    r = properties_token(b, l + 1);
    p = r; // pin = 1
    r = r && report_error_(b, properties_block_1(b, l + 1));
    r = p && report_error_(b, properties_block_2(b, l + 1)) && r;
    r = p && report_error_(b, properties_block_3(b, l + 1)) && r;
    r = p && report_error_(b, properties_block_body(b, l + 1)) && r;
    r = p && report_error_(b, properties_block_5(b, l + 1)) && r;
    r = p && consumeToken(b, END) && r;
    exit_section_(b, l, m, r, p, block_inside_class_recovery_parser_);
    return r || p;
  }

  // br*
  private static boolean properties_block_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "properties_block_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!br(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "properties_block_1", c)) break;
    }
    return true;
  }

  // attributes?
  private static boolean properties_block_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "properties_block_2")) return false;
    attributes(b, l + 1);
    return true;
  }

  // NEWLINE*
  private static boolean properties_block_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "properties_block_3")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, NEWLINE)) break;
      if (!empty_element_parsed_guard_(b, "properties_block_3", c)) break;
    }
    return true;
  }

  // NEWLINE*
  private static boolean properties_block_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "properties_block_5")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, NEWLINE)) break;
      if (!empty_element_parsed_guard_(b, "properties_block_5", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // <<block element>>
  static boolean properties_block_body(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "properties_block_body")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_);
    r = block(b, l + 1, element_parser_);
    exit_section_(b, l, m, r, false, block_inside_class_recovery_parser_);
    return r;
  }

  /* ********************************************************** */
  // <<parseIdentifierAsKeyword 'PROPERTIES'>>
  static boolean properties_token(PsiBuilder b, int l) {
    return parseIdentifierAsKeyword(b, l + 1, PROPERTIES);
  }

  /* ********************************************************** */
  // ident
  public static boolean ret_value(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ret_value")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, RET_VALUE, "<ret value>");
    r = parseIdentifier(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // ret_value (br* ','? br* ret_value)*
  static boolean ret_values_list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ret_values_list")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_);
    r = ret_value(b, l + 1);
    r = r && ret_values_list_1(b, l + 1);
    exit_section_(b, l, m, r, false, ret_values_list_recovery_parser_);
    return r;
  }

  // (br* ','? br* ret_value)*
  private static boolean ret_values_list_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ret_values_list_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!ret_values_list_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "ret_values_list_1", c)) break;
    }
    return true;
  }

  // br* ','? br* ret_value
  private static boolean ret_values_list_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ret_values_list_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = ret_values_list_1_0_0(b, l + 1);
    r = r && ret_values_list_1_0_1(b, l + 1);
    r = r && ret_values_list_1_0_2(b, l + 1);
    r = r && ret_value(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // br*
  private static boolean ret_values_list_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ret_values_list_1_0_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!br(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "ret_values_list_1_0_0", c)) break;
    }
    return true;
  }

  // ','?
  private static boolean ret_values_list_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ret_values_list_1_0_1")) return false;
    consumeToken(b, COMMA);
    return true;
  }

  // br*
  private static boolean ret_values_list_1_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ret_values_list_1_0_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!br(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "ret_values_list_1_0_2", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // !( br* ']' | br* '=' | br* NEWLINE )
  static boolean ret_values_list_recovery(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ret_values_list_recovery")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !ret_values_list_recovery_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // br* ']' | br* '=' | br* NEWLINE
  private static boolean ret_values_list_recovery_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ret_values_list_recovery_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = ret_values_list_recovery_0_0(b, l + 1);
    if (!r) r = ret_values_list_recovery_0_1(b, l + 1);
    if (!r) r = ret_values_list_recovery_0_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // br* ']'
  private static boolean ret_values_list_recovery_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ret_values_list_recovery_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = ret_values_list_recovery_0_0_0(b, l + 1);
    r = r && consumeToken(b, RBRACKET);
    exit_section_(b, m, null, r);
    return r;
  }

  // br*
  private static boolean ret_values_list_recovery_0_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ret_values_list_recovery_0_0_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!br(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "ret_values_list_recovery_0_0_0", c)) break;
    }
    return true;
  }

  // br* '='
  private static boolean ret_values_list_recovery_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ret_values_list_recovery_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = ret_values_list_recovery_0_1_0(b, l + 1);
    r = r && consumeToken(b, ASSIGN);
    exit_section_(b, m, null, r);
    return r;
  }

  // br*
  private static boolean ret_values_list_recovery_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ret_values_list_recovery_0_1_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!br(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "ret_values_list_recovery_0_1_0", c)) break;
    }
    return true;
  }

  // br* NEWLINE
  private static boolean ret_values_list_recovery_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ret_values_list_recovery_0_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = ret_values_list_recovery_0_2_0(b, l + 1);
    r = r && consumeToken(b, NEWLINE);
    exit_section_(b, m, null, r);
    return r;
  }

  // br*
  private static boolean ret_values_list_recovery_0_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ret_values_list_recovery_0_2_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!br(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "ret_values_list_recovery_0_2_0", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // single_return_value_part | multiple_return_values_part
  static boolean return_value_part(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "return_value_part")) return false;
    boolean r;
    r = single_return_value_part(b, l + 1);
    if (!r) r = multiple_return_values_part(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // NEWLINE * (element NEWLINE *) * end?
  static boolean root(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "root")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = root_0(b, l + 1);
    r = r && root_1(b, l + 1);
    r = r && root_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // NEWLINE *
  private static boolean root_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "root_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, NEWLINE)) break;
      if (!empty_element_parsed_guard_(b, "root_0", c)) break;
    }
    return true;
  }

  // (element NEWLINE *) *
  private static boolean root_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "root_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!root_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "root_1", c)) break;
    }
    return true;
  }

  // element NEWLINE *
  private static boolean root_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "root_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = element(b, l + 1);
    r = r && root_1_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // NEWLINE *
  private static boolean root_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "root_1_0_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, NEWLINE)) break;
      if (!empty_element_parsed_guard_(b, "root_1_0_1", c)) break;
    }
    return true;
  }

  // end?
  private static boolean root_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "root_2")) return false;
    consumeToken(b, END);
    return true;
  }

  /* ********************************************************** */
  // (br | NEWLINE)*
  static boolean sep(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sep")) return false;
    while (true) {
      int c = current_position_(b);
      if (!sep_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "sep", c)) break;
    }
    return true;
  }

  // br | NEWLINE
  private static boolean sep_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sep_0")) return false;
    boolean r;
    r = br(b, l + 1);
    if (!r) r = consumeToken(b, NEWLINE);
    return r;
  }

  /* ********************************************************** */
  // ret_value
  public static boolean single_ret_value(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "single_ret_value")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, RETURN_VALUES, "<single ret value>");
    r = ret_value(b, l + 1);
    exit_section_(b, l, m, r, false, not_expr_or_equal_parser_);
    return r;
  }

  /* ********************************************************** */
  // single_ret_value br* '='
  static boolean single_return_value_part(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "single_return_value_part")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = single_ret_value(b, l + 1);
    r = r && single_return_value_part_1(b, l + 1);
    r = r && consumeToken(b, ASSIGN);
    exit_section_(b, m, null, r);
    return r;
  }

  // br*
  private static boolean single_return_value_part_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "single_return_value_part_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!br(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "single_return_value_part_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // DOUBLE_QUOTE_STRING | SINGLE_QUOTE_STRING
  static boolean string_literal(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "string_literal")) return false;
    if (!nextTokenIs(b, "<literal>", DOUBLE_QUOTE_STRING, SINGLE_QUOTE_STRING)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, null, "<literal>");
    r = consumeToken(b, DOUBLE_QUOTE_STRING);
    if (!r) r = consumeToken(b, SINGLE_QUOTE_STRING);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // '<' br* super_classes_list
  public static boolean super_classes(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "super_classes")) return false;
    if (!nextTokenIs(b, LESS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, SUPER_CLASSES, null);
    r = consumeToken(b, LESS);
    p = r; // pin = 1
    r = r && report_error_(b, super_classes_1(b, l + 1));
    r = p && super_classes_list(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // br*
  private static boolean super_classes_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "super_classes_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!br(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "super_classes_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // super_classes_list_item ('&' super_classes_list_item) *
  static boolean super_classes_list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "super_classes_list")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = super_classes_list_item(b, l + 1);
    r = r && super_classes_list_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ('&' super_classes_list_item) *
  private static boolean super_classes_list_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "super_classes_list_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!super_classes_list_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "super_classes_list_1", c)) break;
    }
    return true;
  }

  // '&' super_classes_list_item
  private static boolean super_classes_list_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "super_classes_list_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, MATRIX_AND);
    r = r && super_classes_list_item(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ref_expr
  static boolean super_classes_list_item(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "super_classes_list_item")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_);
    r = ref_expr(b, l + 1);
    exit_section_(b, l, m, r, false, not_ampersand_or_super_class_or_properties_or_methods_or_end_parser_);
    return r;
  }

  /* ********************************************************** */
  // switch br* switch_expression NEWLINE*
  //   case_block* NEWLINE*
  //   [otherwise_block NEWLINE*]
  //   end
  public static boolean switch_block(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "switch_block")) return false;
    if (!nextTokenIs(b, SWITCH)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, SWITCH_BLOCK, null);
    r = consumeToken(b, SWITCH);
    p = r; // pin = 1
    r = r && report_error_(b, switch_block_1(b, l + 1));
    r = p && report_error_(b, switch_expression(b, l + 1)) && r;
    r = p && report_error_(b, switch_block_3(b, l + 1)) && r;
    r = p && report_error_(b, switch_block_4(b, l + 1)) && r;
    r = p && report_error_(b, switch_block_5(b, l + 1)) && r;
    r = p && report_error_(b, switch_block_6(b, l + 1)) && r;
    r = p && consumeToken(b, END) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // br*
  private static boolean switch_block_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "switch_block_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!br(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "switch_block_1", c)) break;
    }
    return true;
  }

  // NEWLINE*
  private static boolean switch_block_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "switch_block_3")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, NEWLINE)) break;
      if (!empty_element_parsed_guard_(b, "switch_block_3", c)) break;
    }
    return true;
  }

  // case_block*
  private static boolean switch_block_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "switch_block_4")) return false;
    while (true) {
      int c = current_position_(b);
      if (!case_block(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "switch_block_4", c)) break;
    }
    return true;
  }

  // NEWLINE*
  private static boolean switch_block_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "switch_block_5")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, NEWLINE)) break;
      if (!empty_element_parsed_guard_(b, "switch_block_5", c)) break;
    }
    return true;
  }

  // [otherwise_block NEWLINE*]
  private static boolean switch_block_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "switch_block_6")) return false;
    switch_block_6_0(b, l + 1);
    return true;
  }

  // otherwise_block NEWLINE*
  private static boolean switch_block_6_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "switch_block_6_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = otherwise_block(b, l + 1);
    r = r && switch_block_6_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // NEWLINE*
  private static boolean switch_block_6_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "switch_block_6_0_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, NEWLINE)) break;
      if (!empty_element_parsed_guard_(b, "switch_block_6_0_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // expr
  public static boolean switch_expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "switch_expression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, SWITCH_EXPRESSION, "<switch expression>");
    r = expr(b, l + 1, -1);
    exit_section_(b, l, m, r, false, case_block_recovery_parser_);
    return r;
  }

  /* ********************************************************** */
  // try sep
  //   <<block element>>
  //   catch_block?
  //   end
  public static boolean try_block(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "try_block")) return false;
    if (!nextTokenIs(b, TRY)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, TRY_BLOCK, null);
    r = consumeToken(b, TRY);
    p = r; // pin = 1
    r = r && report_error_(b, sep(b, l + 1));
    r = p && report_error_(b, block(b, l + 1, element_parser_)) && r;
    r = p && report_error_(b, try_block_3(b, l + 1)) && r;
    r = p && consumeToken(b, END) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // catch_block?
  private static boolean try_block_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "try_block_3")) return false;
    catch_block(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // ident
  static boolean variable(PsiBuilder b, int l) {
    return parseIdentifier(b, l + 1);
  }

  /* ********************************************************** */
  // variable+
  public static boolean variables(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variables")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, VARIABLES, "<variables>");
    r = variable(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!variable(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "variables", c)) break;
    }
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // while br* while_loop_condition [ ';' | ',' ] NEWLINE*
  //     block_that_recovers_until_end
  //     end
  public static boolean while_loop(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "while_loop")) return false;
    if (!nextTokenIs(b, WHILE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, WHILE_LOOP, null);
    r = consumeToken(b, WHILE);
    p = r; // pin = 1
    r = r && report_error_(b, while_loop_1(b, l + 1));
    r = p && report_error_(b, while_loop_condition(b, l + 1)) && r;
    r = p && report_error_(b, while_loop_3(b, l + 1)) && r;
    r = p && report_error_(b, while_loop_4(b, l + 1)) && r;
    r = p && report_error_(b, block_that_recovers_until_end(b, l + 1)) && r;
    r = p && consumeToken(b, END) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // br*
  private static boolean while_loop_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "while_loop_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!br(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "while_loop_1", c)) break;
    }
    return true;
  }

  // [ ';' | ',' ]
  private static boolean while_loop_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "while_loop_3")) return false;
    while_loop_3_0(b, l + 1);
    return true;
  }

  // ';' | ','
  private static boolean while_loop_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "while_loop_3_0")) return false;
    boolean r;
    r = consumeToken(b, SEMICOLON);
    if (!r) r = consumeToken(b, COMMA);
    return r;
  }

  // NEWLINE*
  private static boolean while_loop_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "while_loop_4")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, NEWLINE)) break;
      if (!empty_element_parsed_guard_(b, "while_loop_4", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // expr
  public static boolean while_loop_condition(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "while_loop_condition")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, WHILE_LOOP_CONDITION, "<while loop condition>");
    r = expr(b, l + 1, -1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // Expression root: expr
  // Operator priority table:
  // 0: BINARY(assign_expr)
  // 1: BINARY(equal_expr) BINARY(not_equal_expr) BINARY(less_expr) BINARY(less_or_equal_expr)
  //    BINARY(more_expr) BINARY(more_or_equal_expr)
  // 2: BINARY(or_expr) BINARY(matrix_or_expr) BINARY(and_expr) BINARY(matrix_and_expr)
  // 3: BINARY(range_expr)
  // 4: BINARY(plus_expr) BINARY(minus_expr)
  // 5: BINARY(mul_expr) BINARY(element_wise_mul_expr) BINARY(rdiv_expr) BINARY(element_wise_rdiv_expr)
  //    BINARY(ldiv_expr) BINARY(element_wise_ldiv_expr) BINARY(pow_expr) BINARY(element_wise_pow_expr)
  // 6: POSTFIX(unary_inc_expr) POSTFIX(unary_dec_expr) PREFIX(unary_prefix_dec_expr) PREFIX(unary_prefix_inc_expr)
  //    PREFIX(unary_plus_expr) PREFIX(unary_min_expr) POSTFIX(ctranspose_expr) POSTFIX(transpose_expr)
  //    PREFIX(unary_negation_expr) PREFIX(meta_class_expr)
  // 7: ATOM(literal_expr) POSTFIX(qualified_with_keyword_expr) BINARY(qualified_expr) ATOM(direct_function_expr)
  //    POSTFIX(function_expr) POSTFIX(cell_array_access_expr) ATOM(ref_expr) ATOM(paren_expr)
  //    ATOM(lambda_expr) ATOM(function_ref_expr)
  public static boolean expr(PsiBuilder b, int l, int g) {
    if (!recursion_guard_(b, l, "expr")) return false;
    addVariant(b, "<expression>");
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, "<expression>");
    r = unary_prefix_dec_expr(b, l + 1);
    if (!r) r = unary_prefix_inc_expr(b, l + 1);
    if (!r) r = unary_plus_expr(b, l + 1);
    if (!r) r = unary_min_expr(b, l + 1);
    if (!r) r = unary_negation_expr(b, l + 1);
    if (!r) r = meta_class_expr(b, l + 1);
    if (!r) r = literal_expr(b, l + 1);
    if (!r) r = direct_function_expr(b, l + 1);
    if (!r) r = ref_expr(b, l + 1);
    if (!r) r = paren_expr(b, l + 1);
    if (!r) r = lambda_expr(b, l + 1);
    if (!r) r = function_ref_expr(b, l + 1);
    p = r;
    r = r && expr_0(b, l + 1, g);
    exit_section_(b, l, m, null, r, p, null);
    return r || p;
  }

  public static boolean expr_0(PsiBuilder b, int l, int g) {
    if (!recursion_guard_(b, l, "expr_0")) return false;
    boolean r = true;
    while (true) {
      Marker m = enter_section_(b, l, _LEFT_, null);
      if (g < 0 && assign_expr_0(b, l + 1)) {
        r = expr(b, l, -1);
        exit_section_(b, l, m, ASSIGN_EXPR, r, true, null);
      }
      else if (g < 1 && equal_expr_0(b, l + 1)) {
        r = expr(b, l, 1);
        exit_section_(b, l, m, EQUAL_EXPR, r, true, null);
      }
      else if (g < 1 && not_equal_expr_0(b, l + 1)) {
        r = expr(b, l, 1);
        exit_section_(b, l, m, NOT_EQUAL_EXPR, r, true, null);
      }
      else if (g < 1 && less_expr_0(b, l + 1)) {
        r = expr(b, l, 1);
        exit_section_(b, l, m, LESS_EXPR, r, true, null);
      }
      else if (g < 1 && less_or_equal_expr_0(b, l + 1)) {
        r = expr(b, l, 1);
        exit_section_(b, l, m, LESS_OR_EQUAL_EXPR, r, true, null);
      }
      else if (g < 1 && more_expr_0(b, l + 1)) {
        r = expr(b, l, 1);
        exit_section_(b, l, m, MORE_EXPR, r, true, null);
      }
      else if (g < 1 && more_or_equal_expr_0(b, l + 1)) {
        r = expr(b, l, 1);
        exit_section_(b, l, m, MORE_OR_EQUAL_EXPR, r, true, null);
      }
      else if (g < 2 && or_expr_0(b, l + 1)) {
        r = expr(b, l, 2);
        exit_section_(b, l, m, OR_EXPR, r, true, null);
      }
      else if (g < 2 && matrix_or_expr_0(b, l + 1)) {
        r = expr(b, l, 2);
        exit_section_(b, l, m, MATRIX_OR_EXPR, r, true, null);
      }
      else if (g < 2 && and_expr_0(b, l + 1)) {
        r = expr(b, l, 2);
        exit_section_(b, l, m, AND_EXPR, r, true, null);
      }
      else if (g < 2 && matrix_and_expr_0(b, l + 1)) {
        r = expr(b, l, 2);
        exit_section_(b, l, m, MATRIX_AND_EXPR, r, true, null);
      }
      else if (g < 3 && range_expr_0(b, l + 1)) {
        r = report_error_(b, expr(b, l, 3));
        r = range_expr_1(b, l + 1) && r;
        exit_section_(b, l, m, RANGE_EXPR, r, true, null);
      }
      else if (g < 4 && plus_expr_0(b, l + 1)) {
        r = expr(b, l, 4);
        exit_section_(b, l, m, PLUS_EXPR, r, true, null);
      }
      else if (g < 4 && minus_expr_0(b, l + 1)) {
        r = expr(b, l, 4);
        exit_section_(b, l, m, MINUS_EXPR, r, true, null);
      }
      else if (g < 5 && mul_expr_0(b, l + 1)) {
        r = expr(b, l, 5);
        exit_section_(b, l, m, MUL_EXPR, r, true, null);
      }
      else if (g < 5 && element_wise_mul_expr_0(b, l + 1)) {
        r = expr(b, l, 5);
        exit_section_(b, l, m, ELEMENT_WISE_MUL_EXPR, r, true, null);
      }
      else if (g < 5 && rdiv_expr_0(b, l + 1)) {
        r = expr(b, l, 5);
        exit_section_(b, l, m, RDIV_EXPR, r, true, null);
      }
      else if (g < 5 && element_wise_rdiv_expr_0(b, l + 1)) {
        r = expr(b, l, 5);
        exit_section_(b, l, m, ELEMENT_WISE_RDIV_EXPR, r, true, null);
      }
      else if (g < 5 && ldiv_expr_0(b, l + 1)) {
        r = expr(b, l, 5);
        exit_section_(b, l, m, LDIV_EXPR, r, true, null);
      }
      else if (g < 5 && element_wise_ldiv_expr_0(b, l + 1)) {
        r = expr(b, l, 5);
        exit_section_(b, l, m, ELEMENT_WISE_LDIV_EXPR, r, true, null);
      }
      else if (g < 5 && pow_expr_0(b, l + 1)) {
        r = expr(b, l, 5);
        exit_section_(b, l, m, POW_EXPR, r, true, null);
      }
      else if (g < 5 && element_wise_pow_expr_0(b, l + 1)) {
        r = expr(b, l, 5);
        exit_section_(b, l, m, ELEMENT_WISE_POW_EXPR, r, true, null);
      }
      else if (g < 6 && unary_inc_expr_0(b, l + 1)) {
        r = true;
        exit_section_(b, l, m, UNARY_INC_EXPR, r, true, null);
      }
      else if (g < 6 && unary_dec_expr_0(b, l + 1)) {
        r = true;
        exit_section_(b, l, m, UNARY_DEC_EXPR, r, true, null);
      }
      else if (g < 6 && ctranspose_expr_0(b, l + 1)) {
        r = true;
        exit_section_(b, l, m, CTRANSPOSE_EXPR, r, true, null);
      }
      else if (g < 6 && transpose_expr_0(b, l + 1)) {
        r = true;
        exit_section_(b, l, m, TRANSPOSE_EXPR, r, true, null);
      }
      else if (g < 7 && qualified_with_keyword_expr_0(b, l + 1)) {
        r = true;
        exit_section_(b, l, m, QUALIFIED_EXPR, r, true, null);
      }
      else if (g < 7 && consumeTokenSmart(b, DOT)) {
        r = expr(b, l, 7);
        exit_section_(b, l, m, QUALIFIED_EXPR, r, true, null);
      }
      else if (g < 7 && function_expr_0(b, l + 1)) {
        r = true;
        exit_section_(b, l, m, FUNCTION_EXPR, r, true, null);
      }
      else if (g < 7 && cell_array_access_expr_0(b, l + 1)) {
        r = true;
        exit_section_(b, l, m, CELL_ARRAY_ACCESS_EXPR, r, true, null);
      }
      else {
        exit_section_(b, l, m, null, false, false, null);
        break;
      }
    }
    return r;
  }

  // ws '=' ws
  private static boolean assign_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "assign_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = parseWhiteSpace(b, l + 1, br_parser_);
    r = r && consumeToken(b, ASSIGN);
    r = r && parseWhiteSpace(b, l + 1, br_parser_);
    exit_section_(b, m, null, r);
    return r;
  }

  // ws '==' ws
  private static boolean equal_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "equal_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = parseWhiteSpace(b, l + 1, br_parser_);
    r = r && consumeToken(b, EQUAL);
    r = r && parseWhiteSpace(b, l + 1, br_parser_);
    exit_section_(b, m, null, r);
    return r;
  }

  // ws '~=' ws
  private static boolean not_equal_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "not_equal_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = parseWhiteSpace(b, l + 1, br_parser_);
    r = r && consumeToken(b, NOT_EQUAL);
    r = r && parseWhiteSpace(b, l + 1, br_parser_);
    exit_section_(b, m, null, r);
    return r;
  }

  // ws '<' ws
  private static boolean less_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "less_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = parseWhiteSpace(b, l + 1, br_parser_);
    r = r && consumeToken(b, LESS);
    r = r && parseWhiteSpace(b, l + 1, br_parser_);
    exit_section_(b, m, null, r);
    return r;
  }

  // ws '<=' ws
  private static boolean less_or_equal_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "less_or_equal_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = parseWhiteSpace(b, l + 1, br_parser_);
    r = r && consumeToken(b, LESS_OR_EQUAL);
    r = r && parseWhiteSpace(b, l + 1, br_parser_);
    exit_section_(b, m, null, r);
    return r;
  }

  // ws '>' ws
  private static boolean more_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "more_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = parseWhiteSpace(b, l + 1, br_parser_);
    r = r && consumeToken(b, MORE);
    r = r && parseWhiteSpace(b, l + 1, br_parser_);
    exit_section_(b, m, null, r);
    return r;
  }

  // ws '>=' ws
  private static boolean more_or_equal_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "more_or_equal_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = parseWhiteSpace(b, l + 1, br_parser_);
    r = r && consumeToken(b, MORE_OR_EQUAL);
    r = r && parseWhiteSpace(b, l + 1, br_parser_);
    exit_section_(b, m, null, r);
    return r;
  }

  // ws '||' ws
  private static boolean or_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "or_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = parseWhiteSpace(b, l + 1, br_parser_);
    r = r && consumeToken(b, OR);
    r = r && parseWhiteSpace(b, l + 1, br_parser_);
    exit_section_(b, m, null, r);
    return r;
  }

  // ws '|' ws
  private static boolean matrix_or_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "matrix_or_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = parseWhiteSpace(b, l + 1, br_parser_);
    r = r && consumeToken(b, MATRIX_OR);
    r = r && parseWhiteSpace(b, l + 1, br_parser_);
    exit_section_(b, m, null, r);
    return r;
  }

  // ws '&&' ws
  private static boolean and_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "and_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = parseWhiteSpace(b, l + 1, br_parser_);
    r = r && consumeToken(b, AND);
    r = r && parseWhiteSpace(b, l + 1, br_parser_);
    exit_section_(b, m, null, r);
    return r;
  }

  // ws '&' ws
  private static boolean matrix_and_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "matrix_and_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = parseWhiteSpace(b, l + 1, br_parser_);
    r = r && consumeToken(b, MATRIX_AND);
    r = r && parseWhiteSpace(b, l + 1, br_parser_);
    exit_section_(b, m, null, r);
    return r;
  }

  // ws ':' ws
  private static boolean range_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "range_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = parseWhiteSpace(b, l + 1, br_parser_);
    r = r && consumeToken(b, COLON);
    r = r && parseWhiteSpace(b, l + 1, br_parser_);
    exit_section_(b, m, null, r);
    return r;
  }

  // [ ws ':' ws expr ]
  private static boolean range_expr_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "range_expr_1")) return false;
    range_expr_1_0(b, l + 1);
    return true;
  }

  // ws ':' ws expr
  private static boolean range_expr_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "range_expr_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = parseWhiteSpace(b, l + 1, br_parser_);
    r = r && consumeToken(b, COLON);
    r = r && parseWhiteSpace(b, l + 1, br_parser_);
    r = r && expr(b, l + 1, -1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ws '+' ws
  private static boolean plus_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "plus_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = parseWhiteSpace(b, l + 1, br_parser_);
    r = r && consumeToken(b, PLUS);
    r = r && parseWhiteSpace(b, l + 1, br_parser_);
    exit_section_(b, m, null, r);
    return r;
  }

  // ws '-' ws
  private static boolean minus_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "minus_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = parseWhiteSpace(b, l + 1, br_parser_);
    r = r && consumeToken(b, MINUS);
    r = r && parseWhiteSpace(b, l + 1, br_parser_);
    exit_section_(b, m, null, r);
    return r;
  }

  // ws '*' ws
  private static boolean mul_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "mul_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = parseWhiteSpace(b, l + 1, br_parser_);
    r = r && consumeToken(b, MUL);
    r = r && parseWhiteSpace(b, l + 1, br_parser_);
    exit_section_(b, m, null, r);
    return r;
  }

  // ws '.*' ws
  private static boolean element_wise_mul_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "element_wise_mul_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = parseWhiteSpace(b, l + 1, br_parser_);
    r = r && consumeToken(b, DOT_MUL);
    r = r && parseWhiteSpace(b, l + 1, br_parser_);
    exit_section_(b, m, null, r);
    return r;
  }

  // ws '/' ws
  private static boolean rdiv_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rdiv_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = parseWhiteSpace(b, l + 1, br_parser_);
    r = r && consumeToken(b, RDIV);
    r = r && parseWhiteSpace(b, l + 1, br_parser_);
    exit_section_(b, m, null, r);
    return r;
  }

  // ws './' ws
  private static boolean element_wise_rdiv_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "element_wise_rdiv_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = parseWhiteSpace(b, l + 1, br_parser_);
    r = r && consumeToken(b, DOT_RDIV);
    r = r && parseWhiteSpace(b, l + 1, br_parser_);
    exit_section_(b, m, null, r);
    return r;
  }

  // ws '\' ws
  private static boolean ldiv_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ldiv_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = parseWhiteSpace(b, l + 1, br_parser_);
    r = r && consumeToken(b, LDIV);
    r = r && parseWhiteSpace(b, l + 1, br_parser_);
    exit_section_(b, m, null, r);
    return r;
  }

  // ws '.\' ws
  private static boolean element_wise_ldiv_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "element_wise_ldiv_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = parseWhiteSpace(b, l + 1, br_parser_);
    r = r && consumeToken(b, DOT_LDIV);
    r = r && parseWhiteSpace(b, l + 1, br_parser_);
    exit_section_(b, m, null, r);
    return r;
  }

  // ws '^' ws
  private static boolean pow_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "pow_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = parseWhiteSpace(b, l + 1, br_parser_);
    r = r && consumeToken(b, POW);
    r = r && parseWhiteSpace(b, l + 1, br_parser_);
    exit_section_(b, m, null, r);
    return r;
  }

  // ws '.^' ws
  private static boolean element_wise_pow_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "element_wise_pow_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = parseWhiteSpace(b, l + 1, br_parser_);
    r = r && consumeToken(b, DOT_POW);
    r = r && parseWhiteSpace(b, l + 1, br_parser_);
    exit_section_(b, m, null, r);
    return r;
  }

  // ws '++'
  private static boolean unary_inc_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unary_inc_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = parseWhiteSpace(b, l + 1, br_parser_);
    r = r && consumeToken(b, PLUSPLUS);
    exit_section_(b, m, null, r);
    return r;
  }

  // ws '--'
  private static boolean unary_dec_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unary_dec_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = parseWhiteSpace(b, l + 1, br_parser_);
    r = r && consumeToken(b, MINUSMINUS);
    exit_section_(b, m, null, r);
    return r;
  }

  public static boolean unary_prefix_dec_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unary_prefix_dec_expr")) return false;
    if (!nextTokenIsSmart(b, MINUSMINUS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = unary_prefix_dec_expr_0(b, l + 1);
    p = r;
    r = p && expr(b, l, 6);
    exit_section_(b, l, m, UNARY_PREFIX_DEC_EXPR, r, p, null);
    return r || p;
  }

  // '--' ws
  private static boolean unary_prefix_dec_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unary_prefix_dec_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, MINUSMINUS);
    r = r && parseWhiteSpace(b, l + 1, br_parser_);
    exit_section_(b, m, null, r);
    return r;
  }

  public static boolean unary_prefix_inc_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unary_prefix_inc_expr")) return false;
    if (!nextTokenIsSmart(b, PLUSPLUS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = unary_prefix_inc_expr_0(b, l + 1);
    p = r;
    r = p && expr(b, l, 6);
    exit_section_(b, l, m, UNARY_PREFIX_INC_EXPR, r, p, null);
    return r || p;
  }

  // '++' ws
  private static boolean unary_prefix_inc_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unary_prefix_inc_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, PLUSPLUS);
    r = r && parseWhiteSpace(b, l + 1, br_parser_);
    exit_section_(b, m, null, r);
    return r;
  }

  public static boolean unary_plus_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unary_plus_expr")) return false;
    if (!nextTokenIsSmart(b, PLUS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = unary_plus_expr_0(b, l + 1);
    p = r;
    r = p && expr(b, l, 6);
    exit_section_(b, l, m, UNARY_PLUS_EXPR, r, p, null);
    return r || p;
  }

  // '+' ws
  private static boolean unary_plus_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unary_plus_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, PLUS);
    r = r && parseWhiteSpace(b, l + 1, br_parser_);
    exit_section_(b, m, null, r);
    return r;
  }

  public static boolean unary_min_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unary_min_expr")) return false;
    if (!nextTokenIsSmart(b, MINUS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = unary_min_expr_0(b, l + 1);
    p = r;
    r = p && expr(b, l, 6);
    exit_section_(b, l, m, UNARY_MIN_EXPR, r, p, null);
    return r || p;
  }

  // '-' ws
  private static boolean unary_min_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unary_min_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, MINUS);
    r = r && parseWhiteSpace(b, l + 1, br_parser_);
    exit_section_(b, m, null, r);
    return r;
  }

  // ws "'"
  private static boolean ctranspose_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ctranspose_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = parseWhiteSpace(b, l + 1, br_parser_);
    r = r && consumeToken(b, CTRANS);
    exit_section_(b, m, null, r);
    return r;
  }

  // ws ".'"
  private static boolean transpose_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "transpose_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = parseWhiteSpace(b, l + 1, br_parser_);
    r = r && consumeToken(b, TRANS);
    exit_section_(b, m, null, r);
    return r;
  }

  public static boolean unary_negation_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unary_negation_expr")) return false;
    if (!nextTokenIsSmart(b, TILDA)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = unary_negation_expr_0(b, l + 1);
    p = r;
    r = p && expr(b, l, 6);
    exit_section_(b, l, m, UNARY_NEGATION_EXPR, r, p, null);
    return r || p;
  }

  // '~' ws
  private static boolean unary_negation_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unary_negation_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, TILDA);
    r = r && parseWhiteSpace(b, l + 1, br_parser_);
    exit_section_(b, m, null, r);
    return r;
  }

  public static boolean meta_class_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "meta_class_expr")) return false;
    if (!nextTokenIsSmart(b, QUESTION_MARK)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = meta_class_expr_0(b, l + 1);
    p = r;
    r = p && expr(b, l, 6);
    exit_section_(b, l, m, META_CLASS_EXPR, r, p, null);
    return r || p;
  }

  // '?' ws
  private static boolean meta_class_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "meta_class_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, QUESTION_MARK);
    r = r && parseWhiteSpace(b, l + 1, br_parser_);
    exit_section_(b, m, null, r);
    return r;
  }

  // number_literal
  //   | string_literal
  //   | matrix_literal
  //   | cell_array_literal
  public static boolean literal_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "literal_expr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, LITERAL_EXPR, "<expression>");
    r = number_literal(b, l + 1);
    if (!r) r = string_literal(b, l + 1);
    if (!r) r = matrix_literal(b, l + 1);
    if (!r) r = cell_array_literal(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // '.' <<parseKeywordAsRefExpression>>
  private static boolean qualified_with_keyword_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "qualified_with_keyword_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, DOT);
    r = r && parseKeywordAsRefExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // function_call_or_matrix_element_access
  public static boolean direct_function_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "direct_function_expr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, FUNCTION_EXPR, "<expression>");
    r = function_call_or_matrix_element_access(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ws arguments
  private static boolean function_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = parseWhiteSpace(b, l + 1, br_parser_);
    r = r && arguments(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '{' sep cell_array_access_item_list sep '}'
  private static boolean cell_array_access_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cell_array_access_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, LBRACE);
    r = r && sep(b, l + 1);
    r = r && cell_array_access_item_list(b, l + 1);
    r = r && sep(b, l + 1);
    r = r && consumeToken(b, RBRACE);
    exit_section_(b, m, null, r);
    return r;
  }

  // ident
  public static boolean ref_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ref_expr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_, REF_EXPR, "<expression>");
    r = parseIdentifier(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // '(' ws expr? ws ')'
  public static boolean paren_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "paren_expr")) return false;
    if (!nextTokenIsSmart(b, LPARENTH)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, PAREN_EXPR, "<expression>");
    r = consumeTokenSmart(b, LPARENTH);
    p = r; // pin = 1
    r = r && report_error_(b, parseWhiteSpace(b, l + 1, br_parser_));
    r = p && report_error_(b, paren_expr_2(b, l + 1)) && r;
    r = p && report_error_(b, parseWhiteSpace(b, l + 1, br_parser_)) && r;
    r = p && consumeToken(b, RPARENTH) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // expr?
  private static boolean paren_expr_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "paren_expr_2")) return false;
    expr(b, l + 1, -1);
    return true;
  }

  // '@' br* parameters br* lambda_body
  public static boolean lambda_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "lambda_expr")) return false;
    if (!nextTokenIsSmart(b, AT)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, LAMBDA_EXPR, "<expression>");
    r = consumeTokenSmart(b, AT);
    r = r && lambda_expr_1(b, l + 1);
    r = r && parameters(b, l + 1);
    p = r; // pin = 3
    r = r && report_error_(b, lambda_expr_3(b, l + 1));
    r = p && lambda_body(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // br*
  private static boolean lambda_expr_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "lambda_expr_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!br(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "lambda_expr_1", c)) break;
    }
    return true;
  }

  // br*
  private static boolean lambda_expr_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "lambda_expr_3")) return false;
    while (true) {
      int c = current_position_(b);
      if (!br(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "lambda_expr_3", c)) break;
    }
    return true;
  }

  // '@' ref_expr
  public static boolean function_ref_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_ref_expr")) return false;
    if (!nextTokenIsSmart(b, AT)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, FUNCTION_REF_EXPR, "<expression>");
    r = consumeTokenSmart(b, AT);
    p = r; // pin = 1
    r = r && ref_expr(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  static final Parser argument_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return argument(b, l + 1);
    }
  };
  static final Parser arguments_0_1_0_1_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return arguments_0_1_0_1(b, l + 1);
    }
  };
  static final Parser arguments_0_1_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return arguments_0_1(b, l + 1);
    }
  };
  static final Parser attribute_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return attribute(b, l + 1);
    }
  };
  static final Parser block_inside_class_recovery_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return block_inside_class_recovery(b, l + 1);
    }
  };
  static final Parser br_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return br(b, l + 1);
    }
  };
  static final Parser case_block_recovery_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return case_block_recovery(b, l + 1);
    }
  };
  static final Parser cell_array_access_item_list_0_1_0_1_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return cell_array_access_item_list_0_1_0_1(b, l + 1);
    }
  };
  static final Parser cell_array_access_item_list_0_1_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return cell_array_access_item_list_0_1(b, l + 1);
    }
  };
  static final Parser cell_array_content_recovery_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return cell_array_content_recovery(b, l + 1);
    }
  };
  static final Parser cell_array_row_recovery_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return cell_array_row_recovery(b, l + 1);
    }
  };
  static final Parser element_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return element(b, l + 1);
    }
  };
  static final Parser enumeration_block_2_0_0_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return enum_item(b, l + 1);
    }
  };
  static final Parser enumeration_block_2_1_0_0_1_0_parser_ = enumeration_block_2_0_0_parser_;
  static final Parser events_block_body_recovery_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return events_block_body_recovery(b, l + 1);
    }
  };
  static final Parser ident_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return parseIdentifier(b, l + 1);
    }
  };
  static final Parser not_ampersand_or_super_class_or_properties_or_methods_or_end_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return not_ampersand_or_super_class_or_properties_or_methods_or_end(b, l + 1);
    }
  };
  static final Parser not_brace_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return not_brace(b, l + 1);
    }
  };
  static final Parser not_end_or_elseif_or_else_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return not_end_or_elseif_or_else(b, l + 1);
    }
  };
  static final Parser not_end_or_oef_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return not_end_or_oef(b, l + 1);
    }
  };
  static final Parser not_expr_or_equal_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return not_expr_or_equal(b, l + 1);
    }
  };
  static final Parser not_rbracket_or_semicolon_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return not_rbracket_or_semicolon(b, l + 1);
    }
  };
  static final Parser not_rbracket_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return not_rbracket(b, l + 1);
    }
  };
  static final Parser not_rparenth_or_comma_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return not_rparenth_or_comma(b, l + 1);
    }
  };
  static final Parser not_rparenth_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return not_rparenth(b, l + 1);
    }
  };
  static final Parser parameter_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return parameter(b, l + 1);
    }
  };
  static final Parser ref_expr_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return ref_expr(b, l + 1);
    }
  };
  static final Parser ret_values_list_recovery_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return ret_values_list_recovery(b, l + 1);
    }
  };
}
