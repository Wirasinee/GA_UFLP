/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pGa;

import java.util.Random;

/**
 *
 * @author Wirasinee
 */
public class ActionGA {
    
    
    public static int[] combine(int[] a, int[] b, int lk) {
        int[] result = new int[a.length];
        System.arraycopy(a, 0, result, 0, lk);
        System.arraycopy(b, lk, result, lk, b.length - lk);
        return result;
    }

    public int randomZeroOrOne() {
        double r = Math.random();
        if (r > 0.5) {
            return 1;
        } else {
            return 0;
        }
    }

    public int RandomTo(int a, int b) {
        Random random = new Random();
        return random.nextInt(b) + a;
    }
    
}
