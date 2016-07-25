package ec.patrimonio.web.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;


@Aspect
@Component
public class NullMonitor {

    private static final Logger log = LoggerFactory.getLogger(NullMonitor.class);
    private static class Arg {

        private final int index;
        private final List<Annotation> annotations;
        private final Object value;

        private Arg(int index,List<Annotation> annotations, Object value) {
            this.index = index;
            this.annotations = Collections.unmodifiableList(annotations);
            this.value = value;
        }

        public int getIndex() { return index; }

        public List<Annotation> getAnnotations() { return annotations; }

        public boolean hasAnnotation(Class<? extends Annotation> type) {
            for (Annotation annotation : annotations)
                if (annotation.annotationType().equals(type))
                    return true;
            return false;
        }

        public Object getValue() { return value; }

        public static List<Arg> of(JoinPoint joinPoint) {
            List<Arg> arguments = new ArrayList<>();
            MethodSignature methodSignature = (MethodSignature) joinPoint.getStaticPart().getSignature();
            Method method = methodSignature.getMethod();
            Annotation[][] annotations = method.getParameterAnnotations();
            Object[] values = joinPoint.getArgs();
            for (int i = 0; i < values.length; i++)
                arguments.add(new Arg(i, Arrays.asList(annotations[i]), values[i]));
            return Collections.unmodifiableList(arguments);
        }

    }

    /**
     * Decorador que permite que no se ejecute un metodo de tipo void con parametros
     * anotados con {@link javax.annotation.Nonnull @Nonnull}.
     * @param joinPoint
     * @throws Throwable
     */
    @Around("execution(* *(.., @javax.annotation.Nonnull (*), ..))")
    public Object salvaNullsVoid(ProceedingJoinPoint joinPoint) throws Throwable {
        log.debug(String.format("Protegiendo el metodo  %s", joinPoint.toShortString()));
        for (Arg argument : Arg.of(joinPoint)){
            if (argument.hasAnnotation(Nonnull.class) && argument.getValue() == null) {
                log.debug(String.format("Saliendo por null de %s", joinPoint.toShortString()));
                return null;
            }
        }
        Object[]values = (new Function<List<Arg>, Object[]>(){
            @Override
            public Object[] apply(List<Arg> args) {
                ArrayList<Object> list = new ArrayList<Object>();
                for (Arg arg : args) {
                    list.add(arg.getValue());
                }
                return list.toArray();
            }
        }).apply(Arg.of(joinPoint));
        log.debug(String.format("Procediendo con %s", joinPoint.toShortString()));
        return joinPoint.proceed(values);
    }

}
