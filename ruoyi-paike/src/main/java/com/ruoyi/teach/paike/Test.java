package com.ruoyi.teach.paike;

public class Test {

    public static void main(String[] args) {
        Integer num = (127 << 7) + 7, count = 0;
        System.out.println(Integer.toBinaryString(num));
        while (num > 0) {
            if ((num & 1) == 1) {
                count++;
            }
            num = num >> 1;
        }
        System.out.println(count);
    }
}
