package org.example.securitydemo.util;

public class SnowflakeIdParser {
    private static final long START_TIMESTAMP = 1735689600000L; // 起始时间戳
    //private static final long START_TIMESTAMP = 1745897445328; // 起始时间戳

    private static final long SEQUENCE_BITS = 12;
    private static final long MACHINE_BITS = 5;
    private static final long DATACENTER_BITS = 5;
    private static final long TIMESTAMP_SHIFT = SEQUENCE_BITS + MACHINE_BITS + DATACENTER_BITS;
    private static final long DATACENTER_SHIFT = SEQUENCE_BITS + MACHINE_BITS;
    private static final long MACHINE_SHIFT = SEQUENCE_BITS;

    public static void parseId(long id) {
        // 提取时间戳部分
        long timestamp = (id >> TIMESTAMP_SHIFT) + START_TIMESTAMP;

        // 提取数据中心ID部分
        long datacenterId = (id >> DATACENTER_SHIFT) & ~(-1L << DATACENTER_BITS);

        // 提取机器ID部分
        long machineId = (id >> MACHINE_SHIFT) & ~(-1L << MACHINE_BITS);

        // 提取序列号部分
        long sequence = id & ~(-1L << SEQUENCE_BITS);

        System.out.println("时间戳: " + timestamp);
        System.out.println("数据中心ID: " + datacenterId);
        System.out.println("机器ID: " + machineId);
        System.out.println("序列号: " + sequence);
    }

    public static void main(String[] args) {
        long id = 42815206379884548L; // 示例ID
        parseId(id);

        // 1745891642864

    }
}
