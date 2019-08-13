package io.github.iamazy.elasticsearch.dsl.sql.parser.query.join;

import io.github.iamazy.elasticsearch.dsl.antlr4.ElasticsearchParser;
import io.github.iamazy.elasticsearch.dsl.sql.model.AtomicQuery;
import io.github.iamazy.elasticsearch.dsl.sql.parser.BoolExpressionParser;
import io.github.iamazy.elasticsearch.dsl.sql.parser.ExpressionQueryParser;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.join.query.JoinQueryBuilders;

/**
 * @author iamazy
 * @date 2019/8/2
 * @descrition
 **/
public class HasChildQueryParser implements ExpressionQueryParser<ElasticsearchParser.HasChildClauseContext> {

    private static BoolExpressionParser boolExpressionParser;

    @Override
    public AtomicQuery parse(ElasticsearchParser.HasChildClauseContext expression) {
        String type = expression.type.getText();
        if(boolExpressionParser==null){
            boolExpressionParser=new BoolExpressionParser();
        }
        QueryBuilder queryBuilder = boolExpressionParser.parseBoolQueryExpr(expression.query);
        QueryBuilder hasChildQueryBuilder=JoinQueryBuilders.hasChildQuery(type,queryBuilder, ScoreMode.Avg);
        return new AtomicQuery(hasChildQueryBuilder);
    }

    @Override
    public boolean isMatchExpressionInvocation(Class clazz) {
        return ElasticsearchParser.HasChildClauseContext.class==clazz;
    }
}
