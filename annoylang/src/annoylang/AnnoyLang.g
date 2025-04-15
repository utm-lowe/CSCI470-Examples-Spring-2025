grammar AnnoyLang;

program returns [Exp ast]:
    'add' {$ast = new AddExp(null, null);}
    ;

/*

// parser rules
program returns [Program ast]
        locals [ArrayList<Exp> list]
        @init { $list = new ArrayList<Exp>(); }: 

        (e=exp {$list.add($e.ast);})* { $ast = new Program($list); }
        ;

exp returns [Exp ast]
    locals [ArrayList<Exp> list]
    @init { $list = new ArrayList<Exp>(); }: 
    ( e=clause {$list.add($e.ast);} ';' )* 
    e=clause {$list.add($e.ast);} '.' {$ast = new Exp($list);}
    ;

clause returns [Exp ast]: 
         | d=definition { $ast = $d.ast; }
         | a=add { $ast = $a.ast; }
         | s=sub { $ast = $s.ast; }
         | m=mul { $ast = $m.ast; }
         | d=div { $ast = $m.ast; }
         | p=pow { $ast = $p.ast; }
         | e=eq { $ast = $e.ast; }
         | neq=neq { $ast = $neq.ast; }
         | lt=lt { $ast = $lt.ast; }
         | gt=gt { $ast = $gt.ast; }
         | lte=lte { $ast = $lte.ast; }
         | gte=gte { $ast = $gte.ast; }
         | and=and { $ast = $and.ast; }
         | or=or { $ast = $or.ast; }
         | not=not { $ast = $not.ast; }
         | while=while { $ast = $while.ast; }
         | if=if { $ast = $if.ast; }
         | rand=rand { $ast = $rand.ast; }
         | print=print { $ast = $print.ast; }
         | input=input { $ast = $input.ast; }
         | null=null { $ast = $null.ast; }
         | cat=cat { $ast = $cat.ast; }
         | slength=slength { $ast = $slength.ast; }
         | smid=smid { $ast = $smid.ast; }
         | string=string { $ast = $string.ast; }
         | call=call { $ast = $call.ast; }
         ;

definition returns [Exp ast] 
           locals [ArrayList<Exp> list, Exp idExp]
           @init { $list = new ArrayList<String>(); }: 
               id=idexp {$idExp=$id.ast} 
                 p=paramlist? {$list=$p.list} 
                 '<-' e=exp { $ast = new FunctionDefExp($idExp, $list, $e.ast); }
        
               | id=idexp '<=' exp  
               ;

arglist : (clause,* clause);

paramlist: '(' (Id',')* Id')';

call : idexp arglist?
       | atom
       ;

idexp : $*id;

atom : number
       | string
       ;

*/

// lexer rules
Id : [^0-9(),<-=.;$"][^(),<-=.;$"]*;

Number : [0-9]+
           | [0-9]+ '.' [0-9]+
           ;

String : '"'[^"]*'"';

// skip rules
WS  :  [ \t\r\n\u000C]+ -> skip;
 Line_Comment :   '//' ~[\r\n]* -> skip;
