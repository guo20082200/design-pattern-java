package com.zishi.qq.mvcdemo.resovler;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ArgumentResolverTest {


        /**
         * ArgumentResolver 里面的万能工厂函数
         * from 函数，里面是个function，可以写自己定义的任何逻辑
         */
        @Test
        void test05() {
            Map<Class<?>, Object> map = Map.of(String.class, "haha", Integer.class, 34);
            ArgumentResolver resolver = ArgumentResolver.from(map::get);
            System.out.println(resolver.resolve(String.class)); // haha
        }



        /**
         * 多个 resolver 组合
         *
         */
        @Test
        void test04() {
            ArgumentResolver resolver = ArgumentResolver.ofSupplied(UUID.class, UUID::randomUUID);
            ArgumentResolver resolver1 = resolver.andSupplied(Integer.class, () -> 0);
            System.out.println(resolver1.resolve(Integer.class)); // 0
        }


        /**
         * 给某个类型绑定了一个 “动态生成的值”
         *
         * 作用：
         * 1.绑定计算逻辑，而不是绑定了 “固定值”
         * 2. 值是动态的，懒加载的
         * 3. 这种效果用Map或者if/else 很难做到
         */
        @Test
        void test03() {
            //String uuid = UUID.randomUUID().toString();
            ArgumentResolver resolver = ArgumentResolver.ofSupplied(UUID.class, UUID::randomUUID);
            System.out.println(resolver.resolve(UUID.class)); // 每次都不一样
            System.out.println(resolver.resolve(UUID.class));

        }



        /**
         * 不需要map，不需要if， 不需要switch
         */
        @Test
        void test02() {
            ArgumentResolver resolver = ArgumentResolver.none()
                    .and(Integer.class, 34)
                    .and(String.class, "abc");
            System.out.println(resolver.resolve(Integer.class)); // 34
            System.out.println(resolver.resolve(String.class)); // abc

        }

        /**
         * 基本使用
         */
        @Test
        void test01() {
            ArgumentResolver resolver = ArgumentResolver.of(String.class, "hello");

            System.out.println(resolver.resolve(String.class)); //hello


            ArgumentResolver andResolver = resolver.and(Integer.class, 34);
            System.out.println(resolver.resolve(Integer.class)); // null
            System.out.println(andResolver.resolve(Integer.class)); // 34
            System.out.println(andResolver.resolve(String.class)); // hello

        }


}