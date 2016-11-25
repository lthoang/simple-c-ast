package com.trhoanglee.ast;

import java.io.PrintWriter;

abstract class AST {
    protected static final String DELIMITER = "\t";

    /**
     * 
     * @param p
     * @param delimiters
     */
    public void printTree(PrintWriter p, String delimiters) {
    }
}

// program -> declList
class Program extends AST {
    private DeclList declList;

    public Program(DeclList declList) {
        this.declList = declList;
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        p.println(delimiters + "Program");
        p.println(delimiters + DELIMITER + "DeclList");
        this.declList.printTree(p, delimiters + DELIMITER);
    }

}

// declList -> declList decl | NULL
abstract class DeclList extends AST {

}

class EmptyDeclList extends DeclList {
    public EmptyDeclList() {
        //
    }
}

class NonemptyDeclList extends DeclList {
    private DeclList declList;
    private Decl decl;

    public NonemptyDeclList(DeclList declList, Decl decl) {
        this.declList = declList;
        this.decl = decl;
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        this.declList.printTree(p, delimiters);
        this.decl.printTree(p, delimiters + DELIMITER);
    }
}

abstract class Decl extends AST {

}

abstract class VarDecl extends Decl {

}

class NormalVarDecl extends VarDecl {
    private Type type;
    private ID id;

    public NormalVarDecl(Type type, ID id) {
        this.type = type;
        this.id = id;
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        p.println(delimiters + "VarDecl");
        this.type.printTree(p, delimiters + DELIMITER);
        this.id.printTree(p, delimiters + DELIMITER);
        p.println(delimiters + DELIMITER + "SEMICOLON");
    }
}

class ArrayVarDecl extends VarDecl {
    private Type type;
    private ID id;
    private Integer intLiteral;

    public ArrayVarDecl(Type type, ID id, Integer intLiteral) {
        this.type = type;
        this.id = id;
        this.intLiteral = intLiteral;
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        p.println(delimiters + "VarDecl");
        this.type.printTree(p, delimiters + DELIMITER);
        this.id.printTree(p, delimiters + DELIMITER);
        p.println(delimiters + DELIMITER + "LSQBRACKET");
        p.println(delimiters + DELIMITER + "INTLITERAL (" + this.intLiteral + ")");
        p.println(delimiters + DELIMITER + "RSQBRACKET");
        p.println(delimiters + DELIMITER + "SEMICOLON");
    }
}

class FuncDef extends Decl {
    private Type type;
    private ID id;
    private Formals formals;
    private FuncBody body;

    public FuncDef(Type type, ID id, Formals formals, FuncBody body) {
        this.type = type;
        this.id = id;
        this.formals = formals;
        this.body = body;
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        p.println(delimiters + "FuncDef");
        this.type.printTree(p, delimiters + DELIMITER);
        this.id.printTree(p, delimiters + DELIMITER);
        this.formals.printTree(p, delimiters + DELIMITER);
        this.body.printTree(p, delimiters + DELIMITER);
    }
}

class FuncDecl extends Decl {
    private Type type;
    private ID id;
    private Formals formals;

    public FuncDecl(Type type, ID id, Formals formals) {
        this.type = type;
        this.id = id;
        this.formals = formals;
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        p.println(delimiters + "FuncDecl");
        this.type.printTree(p, delimiters + DELIMITER);
        this.id.printTree(p, delimiters + DELIMITER);
        this.formals.printTree(p, delimiters + DELIMITER);
        p.println(delimiters + DELIMITER + "SEMICOLON");
    }
}

abstract class Formals extends AST {

}

class EmptyFormals extends Formals {
    public EmptyFormals() {
        //
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        p.println(delimiters + "LPAREN");
        p.println(delimiters + "RPAREN");
    }
}

class NonemptyFormals extends Formals {
    private FormalsList formalsList;

    public NonemptyFormals(FormalsList formalsList) {
        this.formalsList = formalsList;
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        p.println(delimiters + "LPAREN");
        p.println(delimiters + "FormalsList");
        this.formalsList.printTree(p, delimiters);
        p.println(delimiters + "RPAREN");
    }
}

abstract class FormalsList extends Formals {

}

class SingleFormalsList extends FormalsList {
    private FormalDecl formalDecl;

    public SingleFormalsList(FormalDecl formalDecl) {
        this.formalDecl = formalDecl;
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        this.formalDecl.printTree(p, delimiters + DELIMITER);
    }
}

class MultipleFormalsList extends FormalsList {
    private FormalDecl formalDecl;
    private FormalsList formalList;

    public MultipleFormalsList(FormalDecl formalDecl, FormalsList formalsList) {
        this.formalDecl = formalDecl;
        this.formalList = formalsList;
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        this.formalDecl.printTree(p, delimiters + DELIMITER);
        p.println(delimiters + DELIMITER + "COMMA");
        this.formalList.printTree(p, delimiters);
    }
}

class FormalDecl extends Decl {
    private Type type;
    private ID id;

    public FormalDecl(Type type, ID id) {
        this.type = type;
        this.id = id;
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        p.println(delimiters + "FormalDecl");
        this.type.printTree(p, delimiters + DELIMITER);
        this.id.printTree(p, delimiters + DELIMITER);
    }
}

class StructDecl extends Decl {
    private VarDeclList varDeclList;
    private StmtList stmtList;

    public StructDecl(VarDeclList dl, StmtList sl) {
        this.varDeclList = dl;
        this.stmtList = sl;
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        p.println(delimiters + "StructDecl");
        p.println(delimiters + DELIMITER + "LCURLY");
        this.varDeclList.printTree(p, delimiters + DELIMITER);
        p.println(delimiters + DELIMITER + "StmtList");
        this.stmtList.printTree(p, delimiters + DELIMITER);
        p.println(delimiters + DELIMITER + "RCURLY");
    }
}

class FuncBody extends AST {
    private VarDeclList varDeclList;
    private StmtList stmtList;

    public FuncBody(VarDeclList varDeclList, StmtList stmtList) {
        this.varDeclList = varDeclList;
        this.stmtList = stmtList;
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        p.println(delimiters + "FuncBody");
        p.println(delimiters + DELIMITER + "LCURLY");
        this.varDeclList.printTree(p, delimiters + DELIMITER);
        p.println(delimiters + DELIMITER + "StmtList");
        this.stmtList.printTree(p, delimiters + DELIMITER);
        p.println(delimiters + DELIMITER + "RCURLY");
    }
}

// **********************************************************************
// Stmt
// **********************************************************************

abstract class StmtList extends AST {

}

class NonemptyStmtList extends StmtList {
    private StmtList stmtList;
    private Stmt stmt;

    public NonemptyStmtList(StmtList stmtList, Stmt stmt) {
        this.stmtList = stmtList;
        this.stmt = stmt;
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        this.stmtList.printTree(p, delimiters);
        this.stmt.printTree(p, delimiters + DELIMITER);
    }
}

class EmptyStmtList extends StmtList {
    public EmptyStmtList() {
        //
    }
}

abstract class Stmt extends AST {

}

class IfStmt extends Stmt {
    private VarDeclList varDeclList;
    private StmtList stmtList;
    private Expr expr;

    public IfStmt(Expr expr, VarDeclList varDeclList, StmtList stmtList) {
        this.expr = expr;
        this.varDeclList = varDeclList;
        this.stmtList = stmtList;
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        p.println(delimiters + "IfStmt");
        p.println(delimiters + DELIMITER + "IF");
        p.println(delimiters + DELIMITER + "LPAREN");
        this.expr.printTree(p, delimiters + DELIMITER);
        p.println(delimiters + DELIMITER + "RPAREN");
        p.println(delimiters + DELIMITER + "LCURLY");
        this.varDeclList.printTree(p, delimiters + DELIMITER);
        this.stmtList.printTree(p, delimiters + DELIMITER);
        p.println(delimiters + DELIMITER + "RCURLY");
    }
}

class IfElseStmt extends Stmt {
    private Expr expr;
    private VarDeclList varDeclList1;
    private StmtList stmtList1;
    private VarDeclList varDeclList2;
    private StmtList stmtList2;

    public IfElseStmt(Expr expr, VarDeclList varDeclList1, StmtList stmtList1, VarDeclList varDeclList2,
            StmtList stmtList2) {
        this.expr = expr;
        this.varDeclList1 = varDeclList1;
        this.stmtList1 = stmtList1;
        this.varDeclList2 = varDeclList2;
        this.stmtList2 = stmtList2;
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        p.println(delimiters + "IfElseStmt");
        p.println(delimiters + DELIMITER + "IF");
        p.println(delimiters + DELIMITER + "LPAREN");
        this.expr.printTree(p, delimiters + DELIMITER);
        p.println(delimiters + DELIMITER + "RPAREN");
        p.println(delimiters + DELIMITER + "LCURLY");
        this.varDeclList1.printTree(p, delimiters + DELIMITER);
        this.stmtList1.printTree(p, delimiters + DELIMITER);
        p.println(delimiters + DELIMITER + "RCURLY");
        p.println(delimiters + DELIMITER + "ELSE");
        p.println(delimiters + DELIMITER + "LCURLY");
        this.varDeclList2.printTree(p, delimiters + DELIMITER);
        this.stmtList2.printTree(p, delimiters + DELIMITER);
        p.println(delimiters + DELIMITER + "RCURLY");
    }
}

class ForStmt extends Stmt {
    private ForInitStmt forInitStmt1;
    private Expr expr;
    private ForInitStmt forInitStmt2;
    private VarDeclList varDeclList;
    private StmtList stmtList;

    public ForStmt(ForInitStmt forInitStmt1, Expr exp, ForInitStmt forInitStmt2, VarDeclList varDeclList,
            StmtList stmtList) {
        this.forInitStmt1 = forInitStmt1;
        this.expr = exp;
        this.forInitStmt2 = forInitStmt2;
        this.varDeclList = varDeclList;
        this.stmtList = stmtList;
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        p.println(delimiters + "ForStmt");
        p.println(delimiters + DELIMITER + "FOR");
        p.println(delimiters + DELIMITER + "LPAREN");
        this.forInitStmt1.printTree(p, delimiters + DELIMITER);
        p.println(delimiters + DELIMITER + "SEMICOLON");
        this.expr.printTree(p, delimiters + DELIMITER);
        p.println(delimiters + DELIMITER + "SEMICOLON");
        this.forInitStmt2.printTree(p, delimiters + DELIMITER);
        p.println(delimiters + DELIMITER + "RPARN");
        p.println(delimiters + DELIMITER + "LCURLY");
        this.varDeclList.printTree(p, delimiters + DELIMITER);
        this.stmtList.printTree(p, delimiters + DELIMITER);
    }
}

class WhileStmt extends Stmt {
    private Expr expr;
    private VarDeclList varDeclList;
    private StmtList stmtList;

    public WhileStmt(Expr expr, VarDeclList varDeclList, StmtList stmtList) {
        this.expr = expr;
        this.varDeclList = varDeclList;
        this.stmtList = stmtList;
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        p.println(delimiters + "WhileStmt");
        p.println(delimiters + DELIMITER + "WHILE");
        p.println(delimiters + DELIMITER + "LPAREN");
        this.expr.printTree(p, delimiters + DELIMITER);
        p.println(delimiters + DELIMITER + "RPAREN");
        p.println(delimiters + DELIMITER + "LCURLY");
        this.varDeclList.printTree(p, delimiters + DELIMITER);
        this.stmtList.printTree(p, delimiters + DELIMITER);
        p.println(delimiters + DELIMITER + "RCURLY");
    }
}

abstract class ReturnStmt extends Stmt {
}

class EmptyReturnStmt extends ReturnStmt {
    public EmptyReturnStmt() {
        //
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        p.println(delimiters + "ReturnStmt");
        p.println(delimiters + DELIMITER + "RETURN");
        p.println(delimiters + DELIMITER + "SEMICOLON");
    }
}

class NonemptyReturnStmt extends ReturnStmt {
    private Expr expr;

    public NonemptyReturnStmt(Expr exp) {
        this.expr = exp;
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        p.println(delimiters + "ReturnStmt");
        p.println(delimiters + DELIMITER + "RETURN");
        this.expr.printTree(p, delimiters + DELIMITER);
        p.println(delimiters + DELIMITER + "SEMICOLON");
    }
}

class CallStmt extends Stmt {
    private CallExpr callExpr;

    public CallStmt(CallExpr callExpr) {
        this.callExpr = callExpr;
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        p.println(delimiters + "CallStmt");
        this.callExpr.printTree(p, delimiters + DELIMITER);
        p.println(delimiters + DELIMITER + "SEMICOLON");
    }
}

class AssignStmtSemicolon extends Stmt {
    private AssignStmt assignStmt;

    public AssignStmtSemicolon(AssignStmt assignStmt) {
        this.assignStmt = assignStmt;
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        p.println(delimiters + "AssignStmt");
        this.assignStmt.printTree(p, delimiters);
        p.println(delimiters + DELIMITER + "SEMICOLON");
    }
}

abstract class ForInitStmt extends Stmt {

}

class NonemptyForInitStmt extends ForInitStmt {
    private AssignStmt assignStmt;

    public NonemptyForInitStmt(AssignStmt assignStmt) {
        this.assignStmt = assignStmt;
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        p.println(delimiters + "AssignStmt");
        this.assignStmt.printTree(p, delimiters + DELIMITER);
    }
}

class EmptyForInitStmt extends ForInitStmt {
    public EmptyForInitStmt() {
        //
    }
}

class AssignStmt extends Stmt {
    private Expr lhs;
    private Expr expr;

    public AssignStmt(Expr lhs, Expr expr) {

        this.lhs = lhs;
        this.expr = expr;
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        this.lhs.printTree(p, delimiters + DELIMITER);
        p.println(delimiters + DELIMITER + "ASSIGN");
        this.expr.printTree(p, delimiters + DELIMITER);
    }

}

// **********************************************************************
// Expr
// **********************************************************************

abstract class Expr extends AST {

}

/*
 * Unary Express
 */

abstract class UnaryExpr extends Expr {
    protected Expr expr;

    public UnaryExpr(Expr expr) {
        this.expr = expr;
    }
}

class AddrOfUnaryExpr extends UnaryExpr {
    public AddrOfUnaryExpr(Expr exp) {
        super(exp);
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        p.println(delimiters + DELIMITER + "ADDROF");
        this.expr.printTree(p, delimiters + DELIMITER);
    }
}

class NotUnaryExpr extends UnaryExpr {
    public NotUnaryExpr(Expr expr) {
        super(expr);
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        p.println(delimiters + DELIMITER + "NOT");
        this.expr.printTree(p, delimiters + DELIMITER);
    }
}

class MinusUnaryExpr extends UnaryExpr {
    public MinusUnaryExpr(Expr expr) {
        super(expr);
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        p.println(delimiters + DELIMITER + "MINUS");
        this.expr.printTree(p, delimiters + DELIMITER);
    }
}

/*
 * Binary Express
 */
abstract class BinaryExpr extends Expr {
    protected Expr expr1;
    protected Expr expr2;

    public BinaryExpr(Expr expr1, Expr expr2) {
        this.expr1 = expr1;
        this.expr2 = expr2;
    }
}

class PlusExpr extends BinaryExpr {
    public PlusExpr(Expr expr1, Expr expr2) {
        super(expr1, expr2);
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        p.println(delimiters + "PlusExpr");
        this.expr1.printTree(p, delimiters + DELIMITER);
        p.println(delimiters + DELIMITER + "PLUS");
        this.expr2.printTree(p, delimiters + DELIMITER);
    }
}

class MinusExpr extends BinaryExpr {
    public MinusExpr(Expr expr1, Expr expr2) {
        super(expr1, expr2);
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        p.println(delimiters + "MinusExpr");
        this.expr1.printTree(p, delimiters + DELIMITER);
        p.println(delimiters + DELIMITER + "MINUS");
        this.expr2.printTree(p, delimiters + DELIMITER);
    }
}

class TimesExpr extends BinaryExpr {
    public TimesExpr(Expr expr1, Expr expr2) {
        super(expr1, expr2);
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        p.println(delimiters + "TimesExpr");
        this.expr1.printTree(p, delimiters + DELIMITER);
        p.println(delimiters + DELIMITER + "TIMES");
        this.expr2.printTree(p, delimiters + DELIMITER);
    }
}

class DivExpr extends BinaryExpr {
    public DivExpr(Expr expr1, Expr expr2) {
        super(expr1, expr2);
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        p.println(delimiters + "DivideExpr");
        this.expr1.printTree(p, delimiters + DELIMITER);
        p.println(delimiters + DELIMITER + "DIVIDE");
        this.expr2.printTree(p, delimiters + DELIMITER);
    }
}

class ModuloExpr extends BinaryExpr {
    public ModuloExpr(Expr expr1, Expr expr2) {
        super(expr1, expr2);
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        p.println(delimiters + "ModuloExpr");
        this.expr1.printTree(p, delimiters + DELIMITER);
        p.println(delimiters + DELIMITER + "MODULO");
        this.expr2.printTree(p, delimiters + DELIMITER);
    }
}

class AndExpr extends BinaryExpr {
    public AndExpr(Expr expr1, Expr expr2) {
        super(expr1, expr2);
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        p.println(delimiters + "AndExpr");
        this.expr1.printTree(p, delimiters + DELIMITER);
        p.println(delimiters + DELIMITER + "AND");
        this.expr2.printTree(p, delimiters + DELIMITER);
    }
}

class OrExpr extends BinaryExpr {
    public OrExpr(Expr expr1, Expr expr2) {
        super(expr1, expr2);
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        p.println(delimiters + "OrExpr");
        this.expr1.printTree(p, delimiters + DELIMITER);
        p.println(delimiters + DELIMITER + "OR");
        this.expr2.printTree(p, delimiters + DELIMITER);
    }
}

class EqualsExpr extends BinaryExpr {
    public EqualsExpr(Expr expr1, Expr expr2) {
        super(expr1, expr2);
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        p.println(delimiters + "EqualsExpr");
        this.expr1.printTree(p, delimiters + DELIMITER);
        p.println(delimiters + DELIMITER + "EQUALS");
        this.expr2.printTree(p, delimiters + DELIMITER);
    }
}

class NotEqualsExpr extends BinaryExpr {
    public NotEqualsExpr(Expr expr1, Expr expr2) {
        super(expr1, expr2);
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        p.println(delimiters + "NotEqualsExpr");
        this.expr1.printTree(p, delimiters + DELIMITER);
        p.println(delimiters + DELIMITER + "NOTEQUALS");
        this.expr2.printTree(p, delimiters + DELIMITER);
    }
}

class GreaterExpr extends BinaryExpr {
    public GreaterExpr(Expr expr1, Expr expr2) {
        super(expr1, expr2);
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        p.println(delimiters + "GreaterExpr");
        this.expr1.printTree(p, delimiters + DELIMITER);
        p.println(delimiters + DELIMITER + "GREATER");
        this.expr2.printTree(p, delimiters + DELIMITER);
    }
}

class LessExpr extends BinaryExpr {
    public LessExpr(Expr expr1, Expr expr2) {
        super(expr1, expr2);
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        p.println(delimiters + "LessExpr");
        this.expr1.printTree(p, delimiters + DELIMITER);
        p.println(delimiters + DELIMITER + "LESS");
        this.expr2.printTree(p, delimiters + DELIMITER);
    }
}

class GreaterEqExpr extends BinaryExpr {
    public GreaterEqExpr(Expr expr1, Expr expr2) {
        super(expr1, expr2);
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        p.println(delimiters + "GreaterEqExpr");
        this.expr1.printTree(p, delimiters + DELIMITER);
        p.println(delimiters + DELIMITER + "GREATEREQ");
        this.expr2.printTree(p, delimiters + DELIMITER);
    }
}

class LessEqExpr extends BinaryExpr {
    public LessEqExpr(Expr expr1, Expr expr2) {
        super(expr1, expr2);
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        p.println(delimiters + "LessEqExpr");
        this.expr1.printTree(p, delimiters + DELIMITER);
        p.println(delimiters + DELIMITER + "LESSEQ");
        this.expr2.printTree(p, delimiters + DELIMITER);
    }
}

/*
 * Term
 */
abstract class Term extends Expr {

}

class IntLiteral extends Term {
    private Integer intVal;

    public IntLiteral(Integer intVal) {
        this.intVal = intVal;
    }

    public Integer getVal() {
        return this.intVal;
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        p.println(delimiters + "INTLITERAL (" + intVal + ")");
    }
}

class DoubleLiteral extends Term {
    private double val;

    public DoubleLiteral(double val) {
        this.val = val;
    }

    public double getVal() {
        return this.val;
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        p.println(delimiters + "DOUBLELITERAL (" + this.val + ")");
    }
}

class StringLiteral extends Term {
    private String val;

    public StringLiteral(String val) {
        this.val = val;
    }

    public String getVal() {
        return this.val;
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        p.println(delimiters + "STRINGLITERAL (" + this.val + ")");
    }
}

class TrueTerm extends Term {
    public TrueTerm() {
        //
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        p.println(delimiters + DELIMITER + "TRUE");
    }
}

class FalseTerm extends Term {
    public FalseTerm() {
        //
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        p.println(delimiters + DELIMITER + "FASLE");
    }
}

class ExpTerm extends Term {
    private Expr expr;

    public ExpTerm(Expr expr) {
        this.expr = expr;
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        p.println(delimiters + DELIMITER + "LPAREN");
        this.expr.printTree(p, delimiters + DELIMITER);
        p.println(delimiters + DELIMITER + "RPAREN");
    }
}

class CallExprTerm extends Term {
    private CallExpr callExpr;

    public CallExprTerm(CallExpr callExpr) {
        this.callExpr = callExpr;
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        this.callExpr.printTree(p, delimiters + DELIMITER);
    }
}

class NullTerm extends Term {
    public NullTerm() {
        //
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        p.println(delimiters + DELIMITER + "NULL");
    }
}

class SizeOfTerm extends Term {
    private ID id;

    public SizeOfTerm(ID id) {
        this.id = id;
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        p.println(delimiters + DELIMITER + "SIZEOF");
        p.println(delimiters + DELIMITER + "LPAREN");
        this.id.printTree(p, delimiters + DELIMITER);
        p.println(delimiters + DELIMITER + "RPAREN");
    }
}

abstract class CallExpr extends Term {

}

class EmptyCallExpr extends CallExpr {
    private ID id;

    public EmptyCallExpr(ID id) {
        this.id = id;
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        p.println(delimiters + "CallExpr");
        this.id.printTree(p, delimiters + DELIMITER);
        p.println(delimiters + DELIMITER + "LPAREN");
        p.println(delimiters + DELIMITER + "RPAREN");
    }
}

class NonemptyCallExpr extends CallExpr {
    private ID id;
    private ActualList actualList;

    public NonemptyCallExpr(ID id, ActualList actualList) {
        this.id = id;
        this.actualList = actualList;
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        p.println(delimiters + "CallExpr");
        this.id.printTree(p, delimiters + DELIMITER);
        p.println(delimiters + DELIMITER + "LPAREN");
        this.actualList.printTree(p, delimiters + DELIMITER);
        p.println(delimiters + DELIMITER + "RPAREN");
    }
}

abstract class VarDeclList extends AST {

}

class NonemptyVarDeclList extends VarDeclList {
    private VarDeclList varDeclList;
    private VarDecl varDecl;

    public NonemptyVarDeclList(VarDeclList varDeclList, VarDecl varDecl) {
        this.varDeclList = varDeclList;
        this.varDecl = varDecl;
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        this.varDeclList.printTree(p, delimiters);
        this.varDecl.printTree(p, delimiters + DELIMITER);
    }
}

class EmptyVarDeclList extends VarDeclList {
    public EmptyVarDeclList() {
        //
    }
}

abstract class ActualList extends AST {

}

class SingleActualList extends ActualList {
    private Expr expr;

    public SingleActualList(Expr expr) {
        this.expr = expr;
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        this.expr.printTree(p, delimiters + DELIMITER);
    }
}

class MultipleActualList extends ActualList {
    private ActualList actualList;
    private Expr expr;

    public MultipleActualList(ActualList actualList, Expr expr) {
        this.actualList = actualList;
        this.expr = expr;
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        p.println(delimiters + "ActualList");
        this.actualList.printTree(p, delimiters);
        p.println(delimiters + DELIMITER + "COMMA");
        this.expr.printTree(p, delimiters + DELIMITER);
    }
}

// **********************************************************************
// Type
// **********************************************************************
abstract class Type extends AST {
    /**
     * 
     * @return
     */
    public abstract String name();
}

class IntType extends Type {
    public IntType() {
        //
    }

    @Override
    public String name() {
        return "INT";
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        p.println(delimiters + "Type (INT)");
    }
}

class BoolType extends Type {
    public BoolType() {
        //
    }

    @Override
    public String name() {
        return "BOOL";
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        p.println(delimiters + "Type (BOOL)");
    }
}

class VoidType extends Type {
    public VoidType() {
        //
    }

    @Override
    public String name() {
        return "VOID";
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        p.println(delimiters + "type (VOID)");
    }
}

class StructType extends Type {
    private ID id;

    public StructType(ID id) {
        this.id = id;
    }

    @Override
    public String name() {
        return "STRUCT";
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        p.println(delimiters + "Type (STRUCT)");
        this.id.printTree(p, delimiters + DELIMITER);
    }

}

/*
 * Loc
 */
abstract class Loc extends Term {

}

class ID extends Loc {
    private String strVal;

    public ID(String strVal) {
        this.strVal = strVal;
    }

    public String getName() {
        return this.strVal;
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        p.println(delimiters + "ID (" + this.strVal + ")");
    }
}

class ArrayExpr extends Loc {
    private Loc loc;
    private Expr expr;

    public ArrayExpr(Loc loc, Expr expr) {
        this.loc = loc;
        this.expr = expr;
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        p.println(delimiters + "ArrayExpr");
        this.loc.printTree(p, delimiters + DELIMITER);
        p.println(delimiters + DELIMITER + "LSQBRACKET");
        this.expr.printTree(p, delimiters + DELIMITER);
        p.println(delimiters + DELIMITER + "RSQBRACKET");
    }
}

class PeriodLoc extends Loc {
    private Loc loc;
    private ID id;

    public PeriodLoc(Loc loc, ID id) {
        this.loc = loc;
        this.id = id;
    }

    @Override
    public void printTree(PrintWriter p, String delimiters) {
        this.loc.printTree(p, delimiters + DELIMITER);
        p.println(delimiters + DELIMITER + "PERIOD");
        this.id.printTree(p, delimiters + DELIMITER);
    }

}