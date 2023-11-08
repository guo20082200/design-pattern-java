package com.zishi.jvm.jol.ab;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

public class JOLSample_03_Packing {
    public JOLSample_03_Packing() {
    }

    public static void main(String[] args) {
        System.out.println(ClassLayout.parseClass(D.class).toPrintable());
    }


}