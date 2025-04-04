grammar AnnoyLang;

// parser rules
program returns [Program ast]
        locals [ArrayList<Exp> list]
        @init { $list = new ArrayList<Exp>(); }: 

        (e=exp {$list.add($e.ast);})* { $ast = new Program($list); }
        ;

exp returns [Exp ast]
    locals [ArrayList<Exp> list]
    @init { $list = new ArrayList<Exp>(); }: 
    '(' e=clause {$list.add($e.ast);} ';' ')'* 
    e=clause {$list.add($e.ast);} '.' {$ast = new Exp($list);}
    ;

clause : definition
         | call
         ;

definition : idexp paramlist? '<-' exp
               | idexp paramlist? '<=' exp  
               ;

arglist : (clause,* clause);

paramlist: (id,* id);

call : idexp arglist?
       | atom
       ;

idexp : $*id;

atom : number
       | string
       ;

// lexer rules
Id : [^0-9(),<-=.;$"][^(),<-=.;$"]*;

Number : [0-9]+
           | [0-9]+ '.' [0-9]+
           ;

String : '"'[^"]*'"';

// skip rules
WS  :  [ \t\r\n\u000C]+ -> skip;
 Line_Comment :   '//' ~[\r\n]* -> skip;
