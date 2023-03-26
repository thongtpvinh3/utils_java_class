package annotation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ExampleServiceProxy implements InvocationHandler {
    private final ExampleService target;

    public ExampleServiceProxy(ExampleService target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.isAnnotationPresent(LogExecutionTime.class)) {
            return LoggingAspect.logExecutionTime(target, method, args);
        } else {
            return method.invoke(target, args);
        }
    }

    public static ExampleService createProxy(ExampleService target) {
        return (ExampleService) Proxy.newProxyInstance(
                ExampleService.class.getClassLoader(),
                new Class<?>[]{ExampleService.class},
                new ExampleServiceProxy(target));
    }
}
