package cj.dataframe.expression.named;

import java.util.concurrent.atomic.AtomicLong;

public record ExpressionId(long id){

    private static AtomicLong exprId = new AtomicLong();

    public static ExpressionId newExpressionId(){
        return new ExpressionId(exprId.incrementAndGet());
    }
}
