package Main;

/* Created by AMXPC on 2017/1/5. */

import DBConnect.DBConnector;
import DataAccess.DataAccessor;
import Expriments.ExprimentExecutor;

public class Main {
    public static void main(String[] args) {
        DBConnector.init();
        DataAccessor.init();
        ExprimentExecutor.init();

        //TODO:Expriments here
        //ExprimentExecutor.getExprimentExecutor().doSerialSimpleExp(1000);
        //ExprimentExecutor.getExprimentExecutor().doSerialDepositExp(1000);
        //ExprimentExecutor.getExprimentExecutor().doSerialComplexExp(1000);
        //ExprimentExecutor.getExprimentExecutor().doParallelSimpleExp(1000);
        //ExprimentExecutor.getExprimentExecutor().doParallelDepositExp(1000);
        //ExprimentExecutor.getExprimentExecutor().doParallelComplexExp(1000);

        DBConnector.getDbConnector().close();
    }
}
