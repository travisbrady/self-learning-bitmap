package com.github.travisbrady.cardinality;

import java.io.BufferedInputStream;
import java.util.Scanner;

public class SBitmapStdin {

    public static void main(String args[]) {
        Scanner stdin = new Scanner(new BufferedInputStream(System.in));
        SBitmap sb = new SBitmap(1000000, 0.03);
        int ctr = 0;
        while (stdin.hasNext()) {
            sb.offer(stdin.next());
            ctr++;
        }
        System.out.println(String.format("Lines: %d", ctr));
        System.out.println(String.format("Card Est: %f", sb.estimate()));
    }
}
