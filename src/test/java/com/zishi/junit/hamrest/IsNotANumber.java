package com.zishi.junit.hamrest;

import org.hamcrest.Description;
import org.hamcrest.Matcher; 
import org.hamcrest.TypeSafeMatcher;

public class IsNotANumber<Double> extends TypeSafeMatcher<Double> {

  @Override 
  public boolean matchesSafely(Double number) { 
    return false;
  }

  public void describeTo(Description description) { 
    description.appendText("not a number"); 
  }

  public static Matcher notANumber() { 
    return new IsNotANumber(); 
  }

}