 package org.example.jucdemo2.jol;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;

import static java.lang.System.out;


 public class JolDemo19Test {

     public static void main(String[] args) throws Exception {
         ArrayList<Integer> al = new ArrayList<>();
         LinkedList<Integer> ll = new LinkedList<>();

         for (int i = 0; i < 1000; i++) {
             Integer io = i; // box once
             al.add(io);
             ll.add(io);
         }

         al.trimToSize();

         PrintWriter pw = new PrintWriter(out);
         pw.println(GraphLayout.parseInstance(al).toFootprint());
         pw.println(GraphLayout.parseInstance(ll).toFootprint());
         pw.println(GraphLayout.parseInstance(al, ll).toFootprint());
         pw.close();
     }
}
