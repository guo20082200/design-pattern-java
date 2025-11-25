package com.zishi.qq.mvcdemo;

import com.zishi.qq.mvcdemo.resovler.ArgumentResolver;
import com.zishi.qq.mvcdemo.resovler.SpringArgumentResolver;
import com.zishi.qq.mvcdemo.service.ServiceA;
import com.zishi.qq.mvcdemo.service.impl.ServiceAImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Constructor;
import java.util.Arrays;

@SpringBootApplication
public class MvcDemoApplication implements CommandLineRunner {

    private final ApplicationContext applicationContext;

    public MvcDemoApplication(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public static void main(String[] args) {
        SpringApplication.run(MvcDemoApplication.class, args);
    }



    @Override
    public void run(String... args) throws Exception {
        ArgumentResolver resolver = SpringArgumentResolver.from(applicationContext);

        ServiceA serviceA = resolver.resolve(ServiceA.class);
        //ServiceA serviceA = createInstance(ServiceAImpl.class, resolver);
        serviceA.run();
    }

    /**
     *  自动构造器注入方法, 但是该方法只适合实现类。不支持接口。
     * @param type
     * @param resolver
     * @return
     * @param <T>
     */
    static <T> T createInstance(Class<T> type, ArgumentResolver resolver) {
        try {
            Constructor<?> constructor = Arrays.stream(type.getConstructors())
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No public constructor found"));

            Object[] args = Arrays.stream(constructor.getParameterTypes())
                    .map(resolver::resolve)
                    .toArray();

            return type.cast(constructor.newInstance(args));
        } catch (Exception e) {
            throw new RuntimeException("Failed to create instance of " + type, e);
        }
    }
}
