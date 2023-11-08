package com.zishi.jvm.jol.ab;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import org.openjdk.jol.info.GraphLayout;
import org.openjdk.jol.vm.VM;

public class JOLSample_21_Layouts {
    public JOLSample_21_Layouts() {
    }

    public static void main(String[] args) {
        System.out.println(VM.current().details());
        PrintWriter pw = new PrintWriter(System.out, true);
        Map<Dummy, Void> map = new HashMap();
        map.put(new Dummy(1), null);
        map.put(new Dummy(2), null);
        System.gc();
        pw.println(GraphLayout.parseInstance(new Object[]{map}).toPrintable());
        map.put(new Dummy(2), null);
        map.put(new Dummy(2), null);
        map.put(new Dummy(2), null);
        map.put(new Dummy(2), null);
        System.gc();
        pw.println(GraphLayout.parseInstance(new Object[]{map}).toPrintable());

        for(int c = 0; c < 12; ++c) {
            map.put(new Dummy(2), null);
        }

        System.gc();
        pw.println(GraphLayout.parseInstance(new Object[]{map}).toPrintable());
        pw.close();
    }

    public static class Dummy implements Comparable<Dummy> {
        static int ID;
        final int id;
        final int hc;

        public Dummy(int hc) {
            this.id = ID++;
            this.hc = hc;
        }

        public boolean equals(Object o) {
            return this == o;
        }

        public int hashCode() {
            return this.hc;
        }

        public int compareTo(Dummy o) {
            return Integer.compare(this.id, o.id);
        }
    }
}
