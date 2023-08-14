package com.zishi.pattern.behavioral.observer;

public interface GreenTeaBitchSubject {
    void add(TianDogObserver observer);

    void remove(TianDogObserver observer);

    void notifyState(String state);
}