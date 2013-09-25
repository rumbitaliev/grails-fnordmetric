package grails.plugin.fnordmetric

import java.lang.annotation.Retention
import java.lang.annotation.Target
import org.codehaus.groovy.transform.GroovyASTTransformationClass
import java.lang.annotation.ElementType
import java.lang.annotation.RetentionPolicy

@Retention(RetentionPolicy.SOURCE)
@Target([ElementType.METHOD])
@GroovyASTTransformationClass(['grails.plugin.fnordmetric.ast.TimerASTTransformation'])
public @interface FnordMetricTimer {
    String key() default '';
}
