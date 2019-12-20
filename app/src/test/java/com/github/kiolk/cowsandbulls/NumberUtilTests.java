package com.github.kiolk.cowsandbulls;

import com.github.kiolk.cowsandbulls.utils.NumberUtil;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NumberUtilTests {

    @Test
    public void checkRandomGeneration(){
        String random = NumberUtil.generateRandom(4);
        assertEquals(random.indexOf('0'), random.lastIndexOf('0'));
        assertEquals(random.indexOf('1'), random.lastIndexOf('1'));
        assertEquals(random.indexOf('2'), random.lastIndexOf('2'));
        assertEquals(random.indexOf('3'), random.lastIndexOf('3'));
        assertEquals(random.indexOf('4'), random.lastIndexOf('4'));
        assertEquals(random.indexOf('5'), random.lastIndexOf('5'));
        assertEquals(random.indexOf('6'), random.lastIndexOf('6'));
        assertEquals(random.indexOf('7'), random.lastIndexOf('7'));
        assertEquals(random.indexOf('8'), random.lastIndexOf('8'));
    }
}
