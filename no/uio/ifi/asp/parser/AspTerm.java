package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspTerm extends AspSyntax{
    ArrayList<AspFactor> factor = new ArrayList<>();
    ArrayList<AspTermOpr> termOpr = new ArrayList<>();

    AspTerm(int n){
        super(n);
    }

    static AspTerm parse(Scanner s){
        enterParser("term");

        AspTerm at = new AspTerm(s.curLineNum());

        while(true){
            at.factor.add(AspFactor.parse(s));
            if(s.isTermOpr()){
                at.termOpr.add(AspTermOpr.parse(s));
            }
            else{
                break;
            }
        }

        leaveParser("term");
        return at;
    }

    @Override
    public void prettyPrint() {
        int nPrinted = 0;

        for (AspFactor af : factor){
            int i = 0;
            if (nPrinted > 0){
                termOpr.get(i).prettyPrint();
                i++;
            }
            af.prettyPrint();
            nPrinted++;
        }
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue v = factor.get(0).eval(curScope);
        for(int i = 1; i < factor.size(); i++){
            TokenKind k = termOpr.get(i-1).kind;
            switch(k){
            case minusToken:
                v = v.evalSubtract(factor.get(i).eval(curScope), this); break;
            case plusToken:
                v = v.evalAdd(factor.get(i).eval(curScope), this); break;
            default:
                Main.panic("Illegal term operator: " + k + "!");
            }
        }
        return v;
    }
}
