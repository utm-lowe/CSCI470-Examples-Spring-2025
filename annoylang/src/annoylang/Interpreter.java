package annoylang;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Interpreter {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java annoylang.Interpreter <source-file>");
            System.exit(1);
        }

        String sourceFile = args[0];
        String sourceCode = "";

        try {
            sourceCode = new String(Files.readAllBytes(Paths.get(sourceFile)));
        } catch (IOException e) {
            System.err.println("Error reading file: " + sourceFile);
            e.printStackTrace();
            System.exit(1);
        }

        // Assuming ANTLR generated parser and lexer classes are named AnnoyLangParser and AnnoyLangLexer
        AnnoyLangLexer lexer = new AnnoyLangLexer(CharStreams.fromString(sourceCode));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        AnnoyLangParser parser = new AnnoyLangParser(tokens);

        // Assuming the entry point of the parser is a method named 'program'
        AST.Program program = parser.program().ast;

        // Create an evaluator and evaluate the program AST
        Env globalEnv = new Env.RefEnv();
        Evaluator evaluator = new Evaluator();
        Value result = evaluator.visit(program, globalEnv);

        System.out.println("Program result: " + result);
    }
}