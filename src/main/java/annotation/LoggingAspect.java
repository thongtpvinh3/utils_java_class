package annotation;

import java.lang.reflect.Method;

public class LoggingAspect {

    public static Object logExecutionTime(Object target, Method method, Object[] args) throws Throwable {
        long start = System.currentTimeMillis();

        Object result = method.invoke(target, args);

        long executionTime = System.currentTimeMillis() - start;

        LogExecutionTime annotation = method.getAnnotation(LogExecutionTime.class);
        if (annotation != null) {
            String message = annotation.value();
            System.out.println(method.getName() + " executed in " + executionTime + "ms" + (message.isEmpty() ? "" : ": " + message));
        } else {
            System.out.println(method.getName() + " executed in " + executionTime + "ms");
        }

        return result;
    }
}
