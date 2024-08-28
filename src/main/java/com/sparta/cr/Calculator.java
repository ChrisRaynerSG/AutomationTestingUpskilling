package com.sparta.cr;

import java.util.ArrayList;

public class Calculator {

    private Integer num1;
    private Integer num2;
    private ArrayList<Integer> numbers;

    public void setNum1(int num1) {
        this.num1 = num1;
    }

    public void setNum2(int num2) {
        this.num2 = num2;
    }
    public void setNumbers(ArrayList<Integer> numbers) {
        this.numbers = numbers;
    }

    public ArrayList<Integer> getNumbers() {
        return numbers;
    }

    public Integer add(){
        return num1 + num2;
    }

    public Integer subtract(){
        return num1 - num2;
    }
    public Integer multiply(){
        return num1 * num2;
    }
    public Integer divide(){
        if(num2 == 0){
            throw new ArithmeticException("/ by zero");
        }
        else {
            return num1 / num2;
        }
    }
    public Integer sumEvens(){
        int sum = 0;
        for(Integer num : numbers){
            if(num % 2 == 0){
                sum += num;
            }
        }
        return sum;
    }
}
