package annoylang;

import java.util.ArrayList;
import java.util.List;

// Based on MIT Licensed code from Hridesh Rajan's Definelang

/**
 * This class hierarchy represents expressions in the abstract syntax tree
 * manipulated by this interpreter.
 * 
 * @author hridesh
 * 
 */
public interface AST {
	public static abstract class ASTNode implements AST {
		public abstract <T> T accept(Visitor<T> visitor, Env env);
	}
	public static class Program extends ASTNode {
		List<Exp> _exps;

		public Program(List<Exp>exps) {
			_exps = exps;
		}

		public List<Exp> exps() {
			return _exps;
		}
		
		public <T> T accept(Visitor<T> visitor, Env env) {
			return visitor.visit(this, env);
		}
	}
	public static abstract class Exp extends ASTNode {

	}

	public static class IdExp extends Exp {
		String _name;
		int _level;

		public IdExp(String name, int level) {
			_name = name;
			_level = level;
		}

		public String name() {
			return _name;
		}

		public int level() {
			return _level;
		}
		
		public <T> T accept(Visitor<T> visitor, Env env) {
			return visitor.visit(this, env);
		}
	}

	public static class NumExp extends Exp {
		double _val;

		public NumExp(double v) {
			_val = v;
		}

		public double v() {
			return _val;
		}
		
		public <T> T accept(Visitor<T> visitor, Env env) {
			return visitor.visit(this, env);
		}
	}

	public static class StringExp extends Exp {
		String _string;

		public StringExp(String s) {
			_string = s;
		}

		public String string() {
			return _string;
		}

		public <T> T accept(Visitor<T> visitor, Env env) {
			return visitor.visit(this, env);
		}
	}

	public static class NullExp extends Exp {
		public NullExp() {
		}

		public <T> T accept(Visitor<T> visitor, Env env) {
			return visitor.visit(this, env);
		}
	}


	public static class FunctionDefExp extends Exp {
		IdExp _id;
		List<IdExp> _params;
		Exp _body;

		public FunctionDefExp(IdExp id, List<IdExp> params, Exp body) {
			_id = id;
			_params = params;
			_body = body;
		}

		public IdExp id() {
			return _id;
		}

		public List<IdExp> params() {
			return _params;
		}

		public Exp body() {
			return _body;
		}

		public <T> T accept(Visitor<T> visitor, Env env) {
			return visitor.visit(this, env);
		}

	}

	public static class CallExp extends Exp {
		public IdExp _id;
		public List<Exp> _args;

		public CallExp(IdExp id, List<Exp> args) {
			_id = id;
			_args = args;
		}

		public IdExp id() {
			return _id;
		}

		public List<Exp> args() {
			return _args;
		}

		public <T> T accept(Visitor<T> visitor, Env env) {
			return visitor.visit(this, env);
		}
	}

	public class AddExp extends Exp {
		Exp left, right;

		public AddExp(Exp left, Exp right) {
			this.left = left;
			this.right = right;
		}

		public Exp left() {
			return left;
		}

		public Exp right() {
			return right;
		}

		public <T> T accept(Visitor<T> visitor, Env env) {
			return visitor.visit(this, env);
		}
	}

	public class SubExp extends Exp {
		Exp left, right;

		public SubExp(Exp left, Exp right) {
			this.left = left;
			this.right = right;
		}

		public Exp left() {
			return left;
		}

		public Exp right() {
			return right;
		}

		public <T> T accept(Visitor<T> visitor, Env env) {
			return visitor.visit(this, env);
		}
	}

	public class MulExp extends Exp {
		Exp left, right;

		public MulExp(Exp left, Exp right) {
			this.left = left;
			this.right = right;
		}

		public Exp left() {
			return left;
		}

		public Exp right() {
			return right;
		}

		public <T> T accept(Visitor<T> visitor, Env env) {
			return visitor.visit(this, env);
		}
	}

	public class DivExp extends Exp {
		Exp left, right;

		public DivExp(Exp left, Exp right) {
			this.left = left;
			this.right = right;
		}

		public Exp left() {
			return left;
		}

		public Exp right() {
			return right;
		}

		public <T> T accept(Visitor<T> visitor, Env env) {
			return visitor.visit(this, env);
		}
	}

	public class PowExp extends Exp {
		Exp left, right;

		public PowExp(Exp left, Exp right) {
			this.left = left;
			this.right = right;
		}

		public Exp left() {
			return left;
		}

		public Exp right() {
			return right;
		}

		public <T> T accept(Visitor<T> visitor, Env env) {
			return visitor.visit(this, env);
		}
	}

	public class EqExp extends Exp {
		Exp left, right;

		public EqExp(Exp left, Exp right) {
			this.left = left;
			this.right = right;
		}

		public Exp left() {
			return left;
		}

		public Exp right() {
			return right;
		}

		public <T> T accept(Visitor<T> visitor, Env env) {
			return visitor.visit(this, env);
		}
	}

	public class NeqExp extends Exp {
		Exp left, right;

		public NeqExp(Exp left, Exp right) {
			this.left = left;
			this.right = right;
		}

		public Exp left() {
			return left;
		}

		public Exp right() {
			return right;
		}

		public <T> T accept(Visitor<T> visitor, Env env) {
			return visitor.visit(this, env);
		}
	}

	public class LtExp extends Exp {
		Exp left, right;

		public LtExp(Exp left, Exp right) {
			this.left = left;
			this.right = right;
		}

		public Exp left() {
			return left;
		}

		public Exp right() {
			return right;
		}

		public <T> T accept(Visitor<T> visitor, Env env) {
			return visitor.visit(this, env);
		}
	}

	public class LteExp extends Exp {
		Exp left, right;

		public LteExp(Exp left, Exp right) {
			this.left = left;
			this.right = right;
		}

		public Exp left() {
			return left;
		}

		public Exp right() {
			return right;
		}

		public <T> T accept(Visitor<T> visitor, Env env) {
			return visitor.visit(this, env);
		}
	}

	public class GtExp extends Exp {
		Exp left, right;

		public GtExp(Exp left, Exp right) {
			this.left = left;
			this.right = right;
		}

		public Exp left() {
			return left;
		}

		public Exp right() {
			return right;
		}

		public <T> T accept(Visitor<T> visitor, Env env) {
			return visitor.visit(this, env);
		}
	}

	public class GteExp extends Exp {
		Exp left, right;

		public GteExp(Exp left, Exp right) {
			this.left = left;
			this.right = right;
		}

		public Exp left() {
			return left;
		}

		public Exp right() {
			return right;
		}

		public <T> T accept(Visitor<T> visitor, Env env) {
			return visitor.visit(this, env);
		}
	}


	public interface Visitor <T> {
		public T visit(Program p, Env env);
		public T visit(IdExp e, Env env);
		public T visit(NumExp e, Env env);
		public T visit(StringExp e, Env env);
		public T visit(NullExp e, Env env);
		public T visit(FunctionDefExp e, Env env);
		public T visit(CallExp e, Env env);
		public T visit(AddExp e, Env env);
		public T visit(SubExp e, Env env);
		public T visit(MulExp e, Env env);
		public T visit(DivExp e, Env env);
		public T visit(PowExp e, Env env);
		public T visit(EqExp e, Env env);
		public T visit(NeqExp e, Env env);
		public T visit(LtExp e, Env env);
		public T visit(LteExp e, Env env);
		public T visit(GtExp e, Env env);
		public T visit(GteExp e, Env env);
		
	}	
}
