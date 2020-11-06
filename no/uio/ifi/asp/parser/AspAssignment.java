package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspAssignment extends AspSmallStmt{
    AspName an;
    AspExpr ae;

    ArrayList<AspSubscription> sub = new ArrayList<>();

    AspAssignment(int n){
        super(n);
    }

    static AspAssignment parse(Scanner s){
        enterParser("assignment");

        AspAssignment aa = new AspAssignment(s.curLineNum());
        aa.an = AspName.parse(s);
        while(s.curToken().kind != equalToken){
            aa.sub.add(AspSubscription.parse(s));
        }
        skip(s, equalToken);
        aa.ae = AspExpr.parse(s);

        leaveParser("assignement");
        return aa;
    }

    @Override
    public void prettyPrint() {
        an.prettyPrint();
        for(AspSubscription as : sub){
            as.prettyPrint();
        }
        prettyWrite(" = ");
        ae.prettyPrint();
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue v = an.eval(curScope);
        RuntimeValue rv = sub.get(0).eval(curScope);

        for (int i = 1; i < sub.size(); i++) {
            v = sub.get(i).eval(curScope);
        }
        return v;
    }
}
