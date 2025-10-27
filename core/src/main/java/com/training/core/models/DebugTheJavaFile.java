package com.training.core.models;

public class DebugTheJavaFile {
    public static void main(String[] args) {
        int a=5;
        int b=10;
        int sum=add(a,b);
        System.out.println("Sum = "+sum);
    }

    static int add(int x, int y){
        int result=x+y;
        return  result;
    }
}
