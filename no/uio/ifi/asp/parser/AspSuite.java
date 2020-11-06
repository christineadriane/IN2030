package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspSuite extends AspSyntax{
    ArrayList<AspStmt> stmt = new ArrayList<>();
    AspSmallStmtList assl;

    AspSuite(int n){
        super(n);
    }

    static AspSuite parse(Scanner s){
        enterParser("suite");
        AspSuite as = new AspSuite(s.curLineNum());

        if(s.curToken().kind == newLineToken){
            skip(s, newLineToken);
            skip(s, indentToken);
            while(s.curToken().kind != dedentToken){
                as.stmt.add(AspStmt.parse(s));
            }
            skip(s, dedentToken);
        }
        else{
          as.assl = AspSmallStmtList.parse(s);
        }

        leaveParser("suite");
        return as;
    }

    @Override
    public void prettyPrint(){
        if(assl == null){
            prettyWriteLn();
            prettyIndent();
            for(AspStmt s : stmt){
                s.prettyPrint();
            }
            prettyDedent();
        }
        else{
            assl.prettyPrint();
        }
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
	//-- Must be changed in part 3:
	return null;
    }

}
