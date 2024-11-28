package org.example.jucdemo2.jol;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

import static java.lang.System.out;


public class JolDemo09Test {
    /*
     * This is an example of special annotations that can affect the field layout.
     *
     * In order to dodge false sharing, users can put the @Contended annotation
     * on the selected fields/classes. The conservative effect of this annotation
     * is laying out the fields at sparse offsets, effectively providing the
     * artificial padding.
     *
     * This example requires at least JDK 8 (for sun.misc.Contended), or JDK 9
     * (for jdk.internal.vm.annotation.Contended). Any JDK also requires
     * -XX:-RestrictContended to access @Contended from unprivileged code.
     */

    public static void main(String[] args) {
        out.println(VM.current().details());
    }


}
