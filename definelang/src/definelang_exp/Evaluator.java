package definelang_exp;

import java.util.List;
import java.util.ArrayList;

import definelang_exp.AST.*;
import definelang_exp.Env.*;
import definelang_exp.Value.*;

public class Evaluator implements Visitor<Value> {
	
	final GlobalEnv global_env = new GlobalEnv(); //New for definelang
	
	Value valueOf(Program p) {
		// Value of a program in this language is the value of the expression
		// in the global environment.
		return (Value) p.accept(this, global_env);
	}
	
	@Override
	public Value visit(AddExp e, Env env) {
		List<Exp> operands = e.all();
		int result = 0;
		for(Exp exp: operands) {
			Int intermediate = (Int) exp.accept(this, env); // Dynamic type-checking
			result += intermediate.v(); //Semantics of AddExp in terms of the target language.
		}
		return new Int(result);
	}

	@Override
	public Value visit(Const e, Env env) {
		return new Int(e.v());
	}

	@Override
	public Value visit(DivExp e, Env env) {
		List<Exp> operands = e.all();
		Int lVal = (Int) operands.get(0).accept(this, env);
		int result = lVal.v(); 
		for(int i=1; i<operands.size(); i++) {
			Int rVal = (Int) operands.get(i).accept(this, env);
			result = result / rVal.v();
		}
		return new Int(result);
	}

	@Override
	public Value visit(ErrorExp e, Env env) {
		return new Value.DynamicError("Encountered an error expression");
	}

	@Override
	public Value visit(MultExp e, Env env) {
		List<Exp> operands = e.all();
		int result = 1;
		for(Exp exp: operands) {
			Int intermediate = (Int) exp.accept(this, env); // Dynamic type-checking
			result *= intermediate.v(); //Semantics of MultExp.
		}
		return new Int(result);
	}

	@Override
	public Value visit(Program p, Env env) {
		return (Value) p.e().accept(this, env);
	}

	@Override
	public Value visit(SubExp e, Env env) {
		List<Exp> operands = e.all();
		Int lVal = (Int) operands.get(0).accept(this, env);
		int result = lVal.v();
		for(int i=1; i<operands.size(); i++) {
			Int rVal = (Int) operands.get(i).accept(this, env);
			result = result - rVal.v();
		}
		return new Int(result);
	}

	@Override
	public Value visit(VarExp e, Env env) {
		// Previously, all variables had value 42. New semantics.
		return env.get(e.name());
	}	

	@Override
	public Value visit(LetExp e, Env env) { // New for varlang.
		List<String> names = e.names();
		List<Exp> value_exps = e.value_exps();
		List<Value> values = new ArrayList<Value>(value_exps.size());
		
		for(Exp exp : value_exps) 
			values.add((Value)exp.accept(this, env));
		
		Env new_env = env;
		for (int index = 0; index < names.size(); index++)
			new_env = new ExtendEnv(new_env, names.get(index), values.get(index));

		return (Value) e.body().accept(this, new_env);		
	}	
	
	@Override
	public Value visit(DefineExp e, Env env) { // New for definelang.
		String name = e.name();
		Exp value_exp = e.value_exp();
		Value value = (Value) value_exp.accept(this, env);
		env.define(name,value);
		return new Value.Unit();		
	}	

}
