package org.omg.uml;

public class Element {
    public String toString() {
        return this.getClass().getSimpleName();
    }
    
    public void print(String prefix) {
        System.out.println(prefix + this.toString());
    }
}
