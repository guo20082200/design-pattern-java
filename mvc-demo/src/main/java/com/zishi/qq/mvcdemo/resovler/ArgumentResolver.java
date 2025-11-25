package com.zishi.qq.mvcdemo.resovler;

import jakarta.annotation.Nullable;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 是的，这个类的设计很“牛逼”，因为它：
 *
 * 简洁优雅（函数式接口 + 工厂方法）
 *
 * 灵活可组合（责任链模式）
 *
 * 支持懒加载和类型安全
 *
 * 可作为轻量级依赖注入工具或参数解析器
 *
 * 它的精妙之处在于 用最少的代码实现了高度的可扩展性。
 */
@FunctionalInterface
public interface ArgumentResolver {

    /**
     * Resolve the given argument if possible.
     *
     * @param <T>  the argument type
     * @param type the argument type
     * @return the resolved argument value or {@code null}
     */
    <T> @Nullable T resolve(Class<T> type);

    /**
     * Create a new composed {@link ArgumentResolver} by combining this resolver
     * with the given type and value.
     *
     * @param <T>   the argument type
     * @param type  the argument type
     * @param value the argument value
     * @return a new composite {@link ArgumentResolver} instance
     */
    default <T> ArgumentResolver and(Class<T> type, T value) {
        return and(ArgumentResolver.of(type, value));
    }

    /**
     * Create a new composed {@link ArgumentResolver} by combining this resolver
     * with the given type and value.
     *
     * @param <T>           the argument type
     * @param type          the argument type
     * @param valueSupplier the argument value supplier
     * @return a new composite {@link ArgumentResolver} instance
     */
    default <T> ArgumentResolver andSupplied(Class<T> type, Supplier<T> valueSupplier) {
        return and(ArgumentResolver.ofSupplied(type, valueSupplier));
    }

    /**
     * Create a new composed {@link ArgumentResolver} by combining this resolver
     * with the given resolver.
     *
     * @param argumentResolver the argument resolver to add
     * @return a new composite {@link ArgumentResolver} instance
     */
    default ArgumentResolver and(ArgumentResolver argumentResolver) {
        return from(type -> {
            Object resolved = resolve(type);
            return (resolved != null ? resolved : argumentResolver.resolve(type));
        });
    }

    /**
     * Factory method that returns an {@link ArgumentResolver} that always
     * returns {@code null}.
     *
     * @return a new {@link ArgumentResolver} instance
     */
    static ArgumentResolver none() {
        return from(type -> null);
    }

    /**
     * Factory method that can be used to create an {@link ArgumentResolver}
     * that resolves only the given type.
     *
     * @param <T>   the argument type
     * @param type  the argument type
     * @param value the argument value
     * @return a new {@link ArgumentResolver} instance
     */
    static <T> ArgumentResolver of(Class<T> type, T value) {
        return ofSupplied(type, () -> value);
    }

    /**
     * Factory method that can be used to create an {@link ArgumentResolver}
     * that resolves only the given type.
     *
     * @param <T>           the argument type
     * @param type          the argument type
     * @param valueSupplier the argument value supplier
     * @return a new {@link ArgumentResolver} instance
     */
    static <T> ArgumentResolver ofSupplied(Class<T> type, Supplier<T> valueSupplier) {
        return from(candidateType -> (candidateType.equals(type) ? valueSupplier.get() : null));
    }

    /**
     * Factory method that creates a new {@link ArgumentResolver} from a
     * lambda friendly function. The given function is provided with the
     * argument type and must provide an instance of that type or {@code null}.
     *
     * @param function the resolver function
     * @return a new {@link ArgumentResolver} instance backed by the function
     */
    static ArgumentResolver from(Function<Class<?>, Object> function) {
        return new ArgumentResolver() {
            @SuppressWarnings("unchecked")
            @Override
            public <T> @Nullable T resolve(Class<T> type) {
                return (T) function.apply(type);
            }
        };
    }
}