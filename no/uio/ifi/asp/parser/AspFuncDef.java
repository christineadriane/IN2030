package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspFuncDef extends AspCompoundStmt{
    AspSuite as;
    ArrayList<AspName> name = new ArrayList<>();

    AspFuncDef(int n){
        super(n);
    }

    static AspFuncDef parse(Scanner s){
        enterParser("func def");

        AspFuncDef afd = new AspFuncDef(s.curLineNum());

        skip(s, defToken);
        afd.name.add(AspName.parse(s));
        skip(s, leftParToken);

        while(s.curToken().kind != rightParToken){
            afd.name.add(AspName.parse(s));
            if(s.curToken().kind != commaToken){
                break;
            }
            skip(s, commaToken);
        }
        skip(s, rightParToken);
        skip(s, colonToken);
        afd.as = AspSuite.parse(s);

        leaveParser("func def");
        return afd;
    }

    @Override
    public void prettyPrint() {
        int nPrinted = 0;
        prettyWrite("def");
        name.get(0).prettyPrint();
        prettyWrite("(");

        if(name.size()>1){
          for(int i = 1; i < name.size(); i++){
            if(nPrinted > 0){
                prettyWrite(", ");
            }
            name.get(i).prettyPrint();
            nPrinted++;
          }
        }
        prettyWrite(")");
        prettyWrite(":");
        as.prettyPrint();
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
	//-- Must be changed in part 3:
	return null;
    }

}
