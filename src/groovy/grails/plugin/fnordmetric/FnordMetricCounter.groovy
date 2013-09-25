package grails.plugin.fnordmetric

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target
import java.lang.annotation.ElementType
import org.codehaus.groovy.transform.GroovyASTTransformationClass

@Retention(RetentionPolicy.SOURCE)
@Target([ElementType.METHOD])
@GroovyASTTransformationClass(['grails.plugin.fnordmetric.ast.CounterASTTransformation'])
public @interface FnordMetricCounter {
    String key() default '';
    int magnitude() default 1;
}