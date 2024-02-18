package com.zishi.classloader;

import jdk.internal.loader.URLClassPath;
import jdk.internal.misc.VM;


public class CLTest {

    public static void main(String[] args) {

        ClassLoader classLoader01 = String.class.getClassLoader();
        System.out.println(classLoader01); // null,

        ClassLoader classLoader02 = CLTest.class.getClassLoader();
        System.out.println(classLoader02); // jdk.internal.loader.ClassLoaders$AppClassLoader@2437c6dc


        System.out.println(classLoader02.getParent()); //jdk.internal.loader.ClassLoaders$PlatformClassLoader@3e6fa38a
        System.out.println(classLoader02.getParent().getParent()); // null
        String cp = System.getProperty("java.class.path");
        System.out.println(cp);
        /**
         * D:\my-learn\design-pattern-java\learn-java\target\classes;
         * D:\soft\maven\repo\org\apache\zookeeper\zookeeper\3.8.3\zookeeper-3.8.3.jar;
         * D:\soft\maven\repo\org\apache\zookeeper\zookeeper-jute\3.8.3\zookeeper-jute-3.8.3.jar;
         * D:\soft\maven\repo\org\apache\yetus\audience-annotations\0.12.0\audience-annotations-0.12.0.jar;
         * D:\soft\maven\repo\io\netty\netty-handler\4.1.94.Final\netty-handler-4.1.94.Final.jar;
         * D:\soft\maven\repo\io\netty\netty-transport-native-epoll\4.1.94.Final\netty-transport-native-epoll-4.1.94.Final.jar;
         * D:\soft\maven\repo\org\slf4j\slf4j-api\1.7.36\slf4j-api-1.7.36.jar;
         * D:\soft\maven\repo\ch\qos\logback\logback-core\1.2.10\logback-core-1.2.10.jar;
         * D:\soft\maven\repo\ch\qos\logback\logback-classic\1.2.12\logback-classic-1.2.12.jar;
         * D:\soft\maven\repo\commons-io\commons-io\2.11.0\commons-io-2.11.0.jar;
         * D:\soft\maven\repo\io\netty\netty-all\4.1.104.Final\netty-all-4.1.104.Final.jar;
         * D:\soft\maven\repo\io\netty\netty-buffer\4.1.104.Final\netty-buffer-4.1.104.Final.jar;
         * D:\soft\maven\repo\io\netty\netty-codec\4.1.104.Final\netty-codec-4.1.104.Final.jar;
         * D:\soft\maven\repo\io\netty\netty-codec-dns\4.1.104.Final\netty-codec-dns-4.1.104.Final.jar;
         * D:\soft\maven\repo\io\netty\netty-codec-haproxy\4.1.104.Final\netty-codec-haproxy-4.1.104.Final.jar;
         * D:\soft\maven\repo\io\netty\netty-codec-http\4.1.104.Final\netty-codec-http-4.1.104.Final.jar;
         * D:\soft\maven\repo\io\netty\netty-codec-http2\4.1.104.Final\netty-codec-http2-4.1.104.Final.jar;
         * D:\soft\maven\repo\io\netty\netty-codec-memcache\4.1.104.Final\netty-codec-memcache-4.1.104.Final.jar;
         * D:\soft\maven\repo\io\netty\netty-codec-mqtt\4.1.104.Final\netty-codec-mqtt-4.1.104.Final.jar;
         * D:\soft\maven\repo\io\netty\netty-codec-redis\4.1.104.Final\netty-codec-redis-4.1.104.Final.jar;
         * D:\soft\maven\repo\io\netty\netty-codec-smtp\4.1.104.Final\netty-codec-smtp-4.1.104.Final.jar;
         * D:\soft\maven\repo\io\netty\netty-codec-socks\4.1.104.Final\netty-codec-socks-4.1.104.Final.jar;
         * D:\soft\maven\repo\io\netty\netty-codec-stomp\4.1.104.Final\netty-codec-stomp-4.1.104.Final.jar;
         * D:\soft\maven\repo\io\netty\netty-codec-xml\4.1.104.Final\netty-codec-xml-4.1.104.Final.jar;
         * D:\soft\maven\repo\io\netty\netty-common\4.1.104.Final\netty-common-4.1.104.Final.jar;
         * D:\soft\maven\repo\io\netty\netty-transport-native-unix-common\4.1.104.Final\netty-transport-native-unix-common-4.1.104.Final.jar;
         * D:\soft\maven\repo\io\netty\netty-handler-proxy\4.1.104.Final\netty-handler-proxy-4.1.104.Final.jar;
         * D:\soft\maven\repo\io\netty\netty-handler-ssl-ocsp\4.1.104.Final\netty-handler-ssl-ocsp-4.1.104.Final.jar;
         * D:\soft\maven\repo\io\netty\netty-resolver\4.1.104.Final\netty-resolver-4.1.104.Final.jar;
         * D:\soft\maven\repo\io\netty\netty-resolver-dns\4.1.104.Final\netty-resolver-dns-4.1.104.Final.jar;
         * D:\soft\maven\repo\io\netty\netty-transport\4.1.104.Final\netty-transport-4.1.104.Final.jar;
         * D:\soft\maven\repo\io\netty\netty-transport-rxtx\4.1.104.Final\netty-transport-rxtx-4.1.104.Final.jar;
         * D:\soft\maven\repo\io\netty\netty-transport-sctp\4.1.104.Final\netty-transport-sctp-4.1.104.Final.jar;
         * D:\soft\maven\repo\io\netty\netty-transport-udt\4.1.104.Final\netty-transport-udt-4.1.104.Final.jar;
         * D:\soft\maven\repo\io\netty\netty-transport-classes-epoll\4.1.104.Final\netty-transport-classes-epoll-4.1.104.Final.jar;
         * D:\soft\maven\repo\io\netty\netty-transport-classes-kqueue\4.1.104.Final\netty-transport-classes-kqueue-4.1.104.Final.jar;
         * D:\soft\maven\repo\io\netty\netty-resolver-dns-classes-macos\4.1.104.Final\netty-resolver-dns-classes-macos-4.1.104.Final.jar;
         * D:\soft\maven\repo\io\netty\netty-transport-native-epoll\4.1.104.Final\netty-transport-native-epoll-4.1.104.Final-linux-x86_64.jar;
         * D:\soft\maven\repo\io\netty\netty-transport-native-epoll\4.1.104.Final\netty-transport-native-epoll-4.1.104.Final-linux-aarch_64.jar;
         * D:\soft\maven\repo\io\netty\netty-transport-native-epoll\4.1.104.Final\netty-transport-native-epoll-4.1.104.Final-linux-riscv64.jar;
         * D:\soft\maven\repo\io\netty\netty-transport-native-kqueue\4.1.104.Final\netty-transport-native-kqueue-4.1.104.Final-osx-x86_64.jar;
         * D:\soft\maven\repo\io\netty\netty-transport-native-kqueue\4.1.104.Final\netty-transport-native-kqueue-4.1.104.Final-osx-aarch_64.jar;
         * D:\soft\maven\repo\io\netty\netty-resolver-dns-native-macos\4.1.104.Final\netty-resolver-dns-native-macos-4.1.104.Final-osx-x86_64.jar;
         * D:\soft\maven\repo\io\netty\netty-resolver-dns-native-macos\4.1.104.Final\netty-resolver-dns-native-macos-4.1.104.Final-osx-aarch_64.jar;
         * D:\soft\maven\repo\org\jboss\weld\weld-core\2.4.8.Final\weld-core-2.4.8.Final.jar;
         * D:\soft\maven\repo\javax\enterprise\cdi-api\1.2\cdi-api-1.2.jar;
         * D:\soft\maven\repo\javax\inject\javax.inject\1\javax.inject-1.jar;
         * D:\soft\maven\repo\org\jboss\weld\weld-api\2.4.SP2\weld-api-2.4.SP2.jar;
         * D:\soft\maven\repo\org\jboss\weld\weld-spi\2.4.SP2\weld-spi-2.4.SP2.jar;
         * D:\soft\maven\repo\org\jboss\classfilewriter\jboss-classfilewriter\1.1.2.Final\jboss-classfilewriter-1.1.2.Final.jar;
         * D:\soft\maven\repo\org\jboss\spec\javax\annotation\jboss-annotations-api_1.2_spec\1.0.0.Final\jboss-annotations-api_1.2_spec-1.0.0.Final.jar;
         * D:\soft\maven\repo\org\jboss\spec\javax\el\jboss-el-api_3.0_spec\1.0.0.Alpha1\jboss-el-api_3.0_spec-1.0.0.Alpha1.jar;
         * D:\soft\maven\repo\org\jboss\spec\javax\interceptor\jboss-interceptors-api_1.2_spec\1.0.0.Final\jboss-interceptors-api_1.2_spec-1.0.0.Final.jar;
         * D:\soft\maven\repo\org\jboss\logging\jboss-logging\3.2.1.Final\jboss-logging-3.2.1.Final.jar;
         * D:\soft\maven\repo\com\101tec\zkclient\0.11\zkclient-0.11.jar;D:\soft\maven\repo\org\jbehave\jbehave-core\5.2.0\jbehave-core-5.2.0.jar;
         * D:\soft\maven\repo\org\junit\platform\junit-platform-launcher\1.10.0\junit-platform-launcher-1.10.0.jar;
         * D:\soft\maven\repo\org\junit\vintage\junit-vintage-engine\5.10.0\junit-vintage-engine-5.10.0.jar;
         * D:\soft\maven\repo\org\apache\commons\commons-lang3\3.12.0\commons-lang3-3.12.0.jar;
         * D:\soft\maven\repo\org\apache\commons\commons-text\1.10.0\commons-text-1.10.0.jar;
         * D:\soft\maven\repo\org\codehaus\plexus\plexus-utils\3.5.1\plexus-utils-3.5.1.jar;
         * D:\soft\maven\repo\org\freemarker\freemarker\2.3.32\freemarker-2.3.32.jar;
         * D:\soft\maven\repo\com\google\code\gson\gson\2.10.1\gson-2.10.1.jar;
         * D:\soft\maven\repo\com\thoughtworks\paranamer\paranamer\2.8\paranamer-2.8.jar;
         * D:\soft\maven\repo\org\javassist\javassist\3.29.2-GA\javassist-3.29.2-GA.jar;
         * D:\soft\maven\repo\org\opentest4j\opentest4j\1.3.0\opentest4j-1.3.0.jar;
         * D:\soft\maven\repo\org\junit\platform\junit-platform-commons\1.10.1\junit-platform-commons-1.10.1.jar;
         * D:\soft\maven\repo\org\apiguardian\apiguardian-api\1.1.2\apiguardian-api-1.1.2.jar;
         * D:\soft\maven\repo\org\junit\platform\junit-platform-engine\1.10.1\junit-platform-engine-1.10.1.jar;
         * D:\soft\maven\repo\org\openjdk\jol\jol-core\0.17\jol-core-0.17.jar;
         * D:\soft\maven\repo\org\openjdk\jol\jol-cli\0.17\jol-cli-0.17.jar;
         * D:\soft\maven\repo\net\sf\jopt-simple\jopt-simple\4.6\jopt-simple-4.6.jar;
         * D:\soft\maven\repo\org\openjdk\jol\jol-samples\0.17\jol-samples-0.17.jar;
         * D:\soft\maven\repo\org\apache\curator\curator-recipes\5.2.0\curator-recipes-5.2.0.jar;
         * D:\soft\maven\repo\org\apache\curator\curator-framework\5.2.0\curator-framework-5.2.0.jar;
         * D:\soft\maven\repo\org\apache\curator\curator-client\5.2.0\curator-client-5.2.0.jar;
         * D:\soft\maven\repo\org\apache\curator\curator-x-discovery\5.2.0\curator-x-discovery-5.2.0.jar;
         * D:\soft\maven\repo\com\fasterxml\jackson\core\jackson-databind\2.10.0\jackson-databind-2.10.0.jar;
         * D:\soft\maven\repo\com\fasterxml\jackson\core\jackson-annotations\2.10.0\jackson-annotations-2.10.0.jar;
         * D:\soft\maven\repo\com\fasterxml\jackson\core\jackson-core\2.10.0\jackson-core-2.10.0.jar;
         * D:\soft\maven\repo\com\google\guava\guava\32.1.2-jre\guava-32.1.2-jre.jar;
         * D:\soft\maven\repo\com\google\guava\failureaccess\1.0.1\failureaccess-1.0.1.jar;
         * D:\soft\maven\repo\com\google\guava\listenablefuture\9999.0-empty-to-avoid-conflict-with-guava\listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar;
         * D:\soft\maven\repo\com\google\code\findbugs\jsr305\3.0.2\jsr305-3.0.2.jar;
         * D:\soft\maven\repo\org\checkerframework\checker-qual\3.33.0\checker-qual-3.33.0.jar;
         * D:\soft\maven\repo\com\google\errorprone\error_prone_annotations\2.18.0\error_prone_annotations-2.18.0.jar;
         * D:\soft\maven\repo\com\google\j2objc\j2objc-annotations\2.8\j2objc-annotations-2.8.jar;
         * D:\soft\maven\repo\org\projectlombok\lombok\1.18.26\lombok-1.18.26.jar
         */

        //FileSystem fileSystem = DefaultFileSystem.getFileSystem();

        //URLClassPath ucp = new URLClassPath(cp, false);


        String initialModuleName = System.getProperty("jdk.module.main"); // null
        System.out.println(initialModuleName);



        String append = VM.getSavedProperty("jdk.boot.class.path.append");

        System.out.println(append);

    }
}
