package com.zishi.assist;

import javassist.*;

/**
 * An observer of Loader.
 * The added event listener is notified when the class loader loads a class
 */
public class MyTranslator implements Translator {
    public void start(ClassPool pool)
            throws NotFoundException, CannotCompileException {
    }

    public void onLoad(ClassPool pool, String classname)
            throws NotFoundException, CannotCompileException {
        CtClass cc = pool.get(classname);
        cc.setModifiers(Modifier.PUBLIC);
    }
}