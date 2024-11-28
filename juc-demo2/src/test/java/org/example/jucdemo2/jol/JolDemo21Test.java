 package org.example.jucdemo2.jol;

import org.openjdk.jol.info.GraphLayout;
import org.openjdk.jol.samples.JOLSample_21_Layouts;
import org.openjdk.jol.vm.VM;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import static java.lang.System.out;


 public class JolDemo21Test {

     public static void main(String[] args) {

         PrintWriter pw = new PrintWriter(System.out, true);

         Map<Dummy, Void> map = new HashMap<>();

         map.put(new Dummy(1), null);
         map.put(new Dummy(2), null);

         System.gc();
         pw.println(GraphLayout.parseInstance(map).toPrintable());

         map.put(new Dummy(2), null);
         map.put(new Dummy(2), null);
         map.put(new Dummy(2), null);
         map.put(new Dummy(2), null);

         System.gc();
         pw.println(GraphLayout.parseInstance(map).toPrintable());

         for (int c = 0; c < 12; c++) {
             map.put(new Dummy(2), null);
         }

         System.gc();
         pw.println(GraphLayout.parseInstance(map).toPrintable());

         pw.close();
     }

     /**
      * Dummy class which controls the hashcode and is decently Comparable.
      */
     public static class Dummy implements Comparable<Dummy> {
         static int ID;
         final int id = ID++;
         final int hc;

         public Dummy(int hc) {
             this.hc = hc;
         }

         @Override
         public boolean equals(Object o) {
             return (this == o);
         }

         @Override
         public int hashCode() {
             return hc;
         }

         @Override
         public int compareTo(Dummy o) {
             return Integer.compare(id, o.id);
         }
     }
}
