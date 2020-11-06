package no.uio.ifi.asp.scanner;

import java.io.*;
import java.util.*;

import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class Scanner {
    private LineNumberReader sourceFile = null;
    private String curFileName;
    private ArrayList<Token> curLineTokens = new ArrayList<>();

    private Stack<Integer> indents = new Stack<>();
    private final int TABDIST = 4;
    private Boolean jump = false;

    public Scanner(String fileName){
        curFileName = fileName;
        indents.push(0);

        try{
            sourceFile = new LineNumberReader(new InputStreamReader(new FileInputStream(fileName),"UTF-8"));
        }
        catch (IOException e){
            scannerError("Cannot read " + fileName + "!");
        }
    }

    private void scannerError(String message){
        String m = "Asp scanner error";
        if(curLineNum() > 0)
        m += " on line " + curLineNum();
        m += ": " + message;

        Main.error(m);
    }

    public Token curToken(){
        while (curLineTokens.isEmpty()){
            readNextLine();
        }
        return curLineTokens.get(0);
    }

    public void readNextToken(){
        if (! curLineTokens.isEmpty())
        curLineTokens.remove(0);
    }

    private void readNextLine(){
        curLineTokens.clear();

        // Read the next line:
        String line = null;

        try{
            line = sourceFile.readLine();
            if (line == null){
                sourceFile.close();
                sourceFile = null;
            }
            else{
                Main.log.noteSourceLine(curLineNum(), line);
            }
        }catch (IOException e){
            sourceFile = null;
            scannerError("Unspecified I/O error!");
        }


        //-- Must be changed in part 1:

        // Finner først ut om linjen = null
        // Hvis linjen != null fortsetter vi å sjekke karakterene på linjen

        if(line != null){

            // Indentering
            String indent = expandLeadingTabs(line);
            int space = 0;

            if(line.trim().length() == 0){
                jump = true;
            }

            try{
                while(line.charAt(space) == ' '){
                    space++;
                }
                if(line.charAt(space) == '#'){
                    assert true;
                }
                else{
                    int antall = findIndent(line);

                    if(antall > indents.peek()){
                        indents.push(antall);
                        curLineTokens.add(new Token(indentToken, curLineNum()));
                    }
                    else{
                        while(antall < indents.peek()){
                            indents.pop();
                            curLineTokens.add(new Token(dedentToken, curLineNum()));
                        }
                    }
                    if(antall != indents.peek()){
                        int lineNr = curLineNum();
                        System.out.println("Asp scanner error on line " + lineNr + ": Indentation error!");
                        System.exit(1);
                    }
                }
            }
            catch(Exception e){
            }

            int pos = 0;
            while (pos < line.length()){
                char c = line.charAt(pos++);
                String word = "";
                String str = "";
                String nr = "";

                String opr = "";

                // break og hopp til neste linje
                if(c == '#' && line.charAt(pos-1) != '\"'){
                    break;
                }

                // string-token.
                // Bruker overloaded version of indexOf og sjekker om linjen har avsluttende fnutter
                //https://stackoverflow.com/questions/19035893/finding-second-occurrence-of-a-substring-in-a-string-in-java
                if(c == '\"' || c == '\''){
                    int index = 0;
                    int index1 = 0;
                    try{
                        if(c == '\"'){
                            index1 = line.indexOf('\"', line.indexOf('\"') + 1);
                        }
                        else if(c == '\''){
                            index = line.indexOf('\'', line.indexOf('\'') + 1);
                        }

                        if(index == - 1 || index1 == -1){
                            System.out.println("Asp scanner error on line " + curLineNum() + ": String literal not terminated!");
                            System.exit(1);
                        }

                        boolean string = true;
                        while(string){

                            if (line.charAt(pos) == '\"' || line.charAt(pos) == '\''){
                                string = false;
                                pos+=1;
                                break;
                            }
                            str += line.charAt(pos++);
                        }

                        if(string == false) {
                            Token s = new Token(stringToken,(curLineNum()));
                            s.stringLit = str;
                            curLineTokens.add(s);
                            str="";
                        }
                    }catch (Exception e){
                    }
                }

                // keywords og nameTokens
                if(isLetterAZ(c)){
                    word += c;
                    try{
                        while(isLetterAZ(line.charAt(pos)) || isDigit(line.charAt(pos))){
                            word += line.charAt(pos++);
                        }
                    }
                    catch (Exception e){

                    }

                    // https://www.baeldung.com/java-enum-iteration
                    for(TokenKind t : TokenKind.values()){
                        if(t.toString().equals(word)){
                            curLineTokens.add(new Token(t, curLineNum()));
                            word="";
                        }
                    }
                    if (word != ""){
                        Token n = new Token(nameToken, (curLineNum()));
                        n.name = word;
                        curLineTokens.add(n);
                        word = "";
                    }
                }

                // int og float
                if(isDigit(c)){
                    nr += c;
                    try{
                        while(isDigit(line.charAt(pos)) || line.charAt(pos) == '.'){
                            nr += line.charAt(pos++);
                        }
                    }
                    catch (Exception e){

                    }

                    // Splitter opp og lagrer 0 som eget token
                    if(nr.indexOf('.') == -1){
                        if(nr.indexOf('0') == 0 && nr.length() > 1){
                            char[] num = nr.toCharArray();
                            Token d = new Token(integerToken, curLineNum());
                            d.integerLit = Character.getNumericValue(num[0]);
                            curLineTokens.add(d);
                        }
                        Token d = new Token(integerToken, curLineNum());
                        d.integerLit = Integer.parseInt(nr);
                        curLineTokens.add(d);
                    }
                    else{
                        double floaty = Double.parseDouble(nr);
                        if(floaty % 1 == 0){
                            int ln = curLineNum();
                            System.out.println("Asp scanner error on line " + ln + ": Illegal float literal: " + nr + "!");
                            System.exit(1);
                        }
                        else{
                            Token f = new Token(floatToken, curLineNum());
                            f.floatLit = Double.parseDouble(nr);
                            curLineTokens.add(f);
                        }
                    }
                }

                //Operators and delimiters
                if(c != ' ' && !isLetterAZ(c) && !isDigit(c)){
                    Boolean done = false;

                    if(c == '='){
                        try{
                            if(line.charAt(pos++) == '='){
                                curLineTokens.add(new Token(TokenKind.doubleEqualToken, curLineNum()));
                                done = true;
                            }
                            else{
                                pos--;
                            }
                        }catch(Exception e){
                        }
                    }

                    else if(c == '/'){
                        try{
                            if(line.charAt(pos++) == '/'){
                                curLineTokens.add(new Token(TokenKind.doubleSlashToken, curLineNum()));
                                done = true;
                            }
                            else{
                                pos--;
                            }
                        }catch(Exception e){
                        }
                    }

                    else if(c == '>'){
                        try{
                            if(line.charAt(pos++) == '='){
                                curLineTokens.add(new Token(TokenKind.greaterEqualToken, curLineNum()));
                                done = true;
                            }
                            else{
                                pos--;
                            }
                        }catch(Exception e){
                        }
                    }

                    else if(c == '<'){
                        try{
                            if(line.charAt(pos++) == '='){
                                curLineTokens.add(new Token(TokenKind.lessEqualToken, curLineNum()));
                                done = true;
                            }
                            else{
                                pos--;
                            }
                        }catch(Exception e){
                        }
                    }

                    else if(c == '!'){
                        try{
                            if(line.charAt(pos++) == '='){
                                curLineTokens.add(new Token(TokenKind.notEqualToken, curLineNum()));
                                done = true;
                            }
                            else{
                                pos--;
                            }
                        } catch(Exception e){
                        }
                    }

                    if(!done){
                        opr = String.valueOf(c);
                        for(TokenKind t : TokenKind.values()){

                            if(t.toString().equals(opr)){
                                curLineTokens.add(new Token(t, curLineNum()));
                                opr = "";
                                done = true;
                            }
                        }
                    }
                }
            }

            // Terminate line:
            if(!line.isEmpty() && !jump){
                if(line.charAt(0) != '#'){
                    curLineTokens.add(new Token(newLineToken,curLineNum()));
                }
            }
        }

        // Om linjen er null, legger vi inn E-o-F
        else{
            while(indents.size() > 1){
                indents.pop();
                curLineTokens.add(new Token(TokenKind.dedentToken));
            }
            curLineTokens.add(new Token(TokenKind.eofToken));
        }
        jump = false;
        for (Token t: curLineTokens)
        Main.log.noteToken(t);
    }

    public int curLineNum(){
        return sourceFile!=null ? sourceFile.getLineNumber():0;
    }

    private int findIndent(String s){
        int indent = 0;

        while (indent<s.length() && s.charAt(indent)==' ') indent++;
        return indent;
    }

    private String expandLeadingTabs(String s){
        String newS = "";
        for (int i = 0;  i < s.length();  i++){
            char c = s.charAt(i);

            if (c == '\t'){
                do{
                    newS += " ";
                }
                while (newS.length()%TABDIST > 0);
            }

            else if(c == ' '){
                newS += " ";
  	        }

            else{
                newS += s.substring(i);
                break;
  	        }
        }
        return newS;
    }

    private boolean isLetterAZ(char c){
        return ('A'<=c && c<='Z') || ('a'<=c && c<='z') || (c=='_');
    }

    private boolean isDigit(char c){
        return '0'<=c && c<='9';
    }

    public boolean isCompOpr(){
        TokenKind k = curToken().kind;

        if(k == lessToken || k == greaterToken || k == doubleEqualToken || k == greaterEqualToken || k == lessEqualToken || k == notEqualToken){
          return true;
        }
        return false;
    }

    public boolean isFactorPrefix(){
        TokenKind k = curToken().kind;

        if(k == plusToken || k == minusToken){
          return true;
        }
        return false;
    }

    public boolean isFactorOpr(){
        TokenKind k = curToken().kind;

        if(k == percentToken || k == slashToken || k == doubleSlashToken || k == astToken){
          return true;
        }
        return false;
    }

    public boolean isTermOpr(){
        TokenKind k = curToken().kind;

        if(k == plusToken || k == minusToken){
          return true;
        }
        return false;
    }

    public boolean anyEqualToken(){
        for (Token t: curLineTokens){
        if (t.kind == equalToken) return true;
        if (t.kind == semicolonToken) return false;
    }
    return false;
    }
}
