package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspIfStmt extends AspCompoundStmt{
    ArrayList<AspExpr> expr = new ArrayList<>();
    ArrayList<AspSuite> suite = new ArrayList<>();

    AspIfStmt(int n){
        super(n);
    }

    static AspIfStmt parse(Scanner s){
        enterParser("if stmt");

        AspIfStmt ais = new AspIfStmt(s.curLineNum());

        skip(s, ifToken);
        ais.expr.add(AspExpr.parse(s));
        skip(s, colonToken);
        ais.suite.add(AspSuite.parse(s));

        while(s.curToken().kind == elifToken){
            skip(s, elifToken);
            ais.expr.add(AspExpr.parse(s));
            skip(s, colonToken);
            ais.suite.add(AspSuite.parse(s));
        }

        if(s.curToken().kind == elseToken){
            skip(s, elseToken);
            skip(s, colonToken);
            ais.suite.add(AspSuite.parse(s));
        }

        leaveParser("if stmt");
        return ais;
    }

    @Override
    public void prettyPrint() {
        prettyWrite("if");
        expr.get(0).prettyPrint();
        prettyWrite(":");
        suite.get(0).prettyPrint();

        int i = 1;
        while(expr.size()>i){
            prettyWrite("elif ");
            expr.get(i).prettyPrint();
            prettyWrite(":");
            suite.get(i).prettyPrint();
            i++;
        }

        if(expr.size() < suite.size()){
            prettyWrite("else");
            prettyWrite(":");
            suite.get(i).prettyPrint();
        }
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
	//-- Must be changed in part 3:
	return null;
    }

}
