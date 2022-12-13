import controller.Controller;
import model.expressions.*;
import model.ProgramState;
import model.statements.*;
import model.types.BoolType;
import model.types.IntType;
import model.types.ReferenceType;
import model.types.StringType;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.StringValue;
import repository.Repository;
import repository.IRepository;
import view.ExitCommand;
import view.RunExample;
import view.TextMenu;

public class Interpreter {

    public static void main(String[] args){
        IStatement firstStatement = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v"))));

        ProgramState firstState = new ProgramState(firstStatement);
        IRepository firstRepository = new Repository(firstState, "log1.txt");
        Controller firstController = new Controller(firstRepository, true);



        IStatement secondStatement = new CompoundStatement(new VariableDeclarationStatement("a",new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("b",new IntType()),
                        new CompoundStatement(new AssignStatement("a", new ArithmeticExpression('+',new ValueExpression(new IntValue(2)),new
                                ArithmeticExpression('*', new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                                new CompoundStatement(new AssignStatement("b",new ArithmeticExpression('+',new VariableExpression("a"), new ValueExpression(new
                                        IntValue(1)))), new PrintStatement(new VariableExpression("b"))))));

        ProgramState secondState = new ProgramState(secondStatement);
        IRepository secondRepository = new Repository(secondState, "log2.txt");
        Controller secondController = new Controller(secondRepository, true);


        IStatement thirdStatement = new CompoundStatement(new VariableDeclarationStatement("a", new BoolType()),
                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                        new CompoundStatement(new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(new IfStatement(new VariableExpression("a"),
                                        new AssignStatement("v", new ValueExpression(new IntValue(2))),
                                        new AssignStatement("v", new ValueExpression(new IntValue(3)))),
                                        new PrintStatement(new VariableExpression("v"))))));


        ProgramState thirdState = new ProgramState(thirdStatement);
        IRepository thirdRepository = new Repository(thirdState, "log3.txt");
        Controller thirdController = new Controller(thirdRepository, true);

        IStatement fourthStatement = new CompoundStatement(new VariableDeclarationStatement("varf", new StringType()),
                new CompoundStatement(new AssignStatement("varf", new ValueExpression(new StringValue("src/test.in"))),
                        new CompoundStatement(new OpenReadFileStatement(new VariableExpression("varf")),
                                new CompoundStatement(new VariableDeclarationStatement("varc", new IntType()),
                                        new CompoundStatement(new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                        new CompoundStatement(new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")), new CloseReadFileStatement(new VariableExpression("varf"))))))))));

        ProgramState fourthState = new ProgramState(fourthStatement);
        IRepository fourthRepository = new Repository(fourthState, "test.out");
        Controller fourthController = new Controller(fourthRepository, true);

        IStatement fifthStatement = new CompoundStatement(new VariableDeclarationStatement("v",new ReferenceType(new IntType())),
                new CompoundStatement(new HeapAllocationStatement("v",new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new  IntType()))),
                                new CompoundStatement(new HeapAllocationStatement("a",new VariableExpression("v")),
                                        new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                                (new PrintStatement(new VariableExpression("a"))))))));

        ProgramState fifthState = new ProgramState(fifthStatement);
        IRepository fifthRepository = new Repository(fifthState, "log4.txt");
        Controller fifthController = new Controller(fifthRepository, true);


        IStatement sixthStatement = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType()))),
                                new CompoundStatement(new HeapAllocationStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(new PrintStatement(new HeapReadingExpression(new VariableExpression("v"))),
                                                (new PrintStatement(new ArithmeticExpression('+',
                                                        new HeapReadingExpression(new HeapReadingExpression( new VariableExpression("a"))),
                                                        new ValueExpression(new IntValue(5))))))))));

        ProgramState sixthState = new ProgramState(sixthStatement);
        IRepository sixthRepository = new Repository(sixthState, "log5.txt");
        Controller sixthController = new Controller(sixthRepository, true);


        IStatement seventhStatement = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))), new CompoundStatement(
                        new PrintStatement(new HeapReadingExpression(new VariableExpression("v"))), new CompoundStatement(
                        new HeapWritingStatement("v", new ValueExpression(new IntValue(30))),
                        new PrintStatement(new ArithmeticExpression('+',
                                new HeapReadingExpression(new VariableExpression("v")),
                                new ValueExpression(new IntValue(5))))))));

        ProgramState seventhState = new ProgramState(seventhStatement);
        IRepository seventhRepository = new Repository(seventhState, "log6.txt");
        Controller seventhController = new Controller(seventhRepository, true);


        IStatement eighthStatement = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType()))),
                                new CompoundStatement(new HeapAllocationStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(30))),
                                                (new PrintStatement(new HeapReadingExpression(new HeapReadingExpression(new VariableExpression("a"))))))))));

        ProgramState eighthState = new ProgramState(eighthStatement);
        IRepository eighthRepository = new Repository(eighthState, "log7.txt");
        Controller eighthController = new Controller(eighthRepository, true);


        IStatement ninthStatement = new CompoundStatement(new VariableDeclarationStatement("v",new IntType()),
                new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(4))),
                        new WhileStatement(new RelationalExpression(">", new VariableExpression("v"),
                                new ValueExpression(new IntValue(0))), new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                new AssignStatement( "v", new ArithmeticExpression('-', new VariableExpression("v"),new ValueExpression(new IntValue(1))))))));

        ProgramState ninthState = new ProgramState(ninthStatement);
        IRepository ninthRepository = new Repository(ninthState, "log8.txt");
        Controller ninthController = new Controller(ninthRepository, true);


        TextMenu menu = new TextMenu();


        menu.addCommand(new ExitCommand("0", "Exit."));
        menu.addCommand(new RunExample("1", firstStatement.toString(), firstController));
        menu.addCommand(new RunExample("2", secondStatement.toString(), secondController));
        menu.addCommand(new RunExample("3", thirdStatement.toString(), thirdController));
        menu.addCommand(new RunExample("4", fourthStatement.toString(), fourthController));
        menu.addCommand(new RunExample("5", fifthStatement.toString(), fifthController));
        menu.addCommand(new RunExample("6", sixthStatement.toString(), sixthController));
        menu.addCommand(new RunExample("7", seventhStatement.toString(), seventhController));
        menu.addCommand(new RunExample("8", eighthStatement.toString(), eighthController));
        menu.addCommand(new RunExample("9", ninthStatement.toString(), ninthController));

        menu.show();


    }

}
