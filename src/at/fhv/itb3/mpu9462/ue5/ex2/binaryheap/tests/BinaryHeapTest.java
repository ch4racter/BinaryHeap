package at.fhv.itb3.mpu9462.ue5.ex2.binaryheap.tests;

import at.fhv.itb3.mpu9462.ue5.ex2.binaryheap.BinaryHeap;
import org.junit.Before;
import org.junit.Test;


import java.util.Comparator;

import static org.junit.Assert.*;

public class BinaryHeapTest {

    private BinaryHeap<Integer> test;

    @Before
    public void setUp() throws Exception {
        test = new BinaryHeap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if(o1.equals(o2)){
                    return 0;
                }else if(o1 > o2){
                    return 1;
                }else{
                    return -1;
                }
            }
        });
    }

    @Test
    public void enqueue() throws Exception {
        test.enqueue(2);
        assertFalse(test.isEmpty());
        assertTrue(test.dequeue() == 2);
    }

    @Test
    public void dequeue() throws Exception {
        int[] values = {1,2,7,6,3,4,5,0};
        for (int value : values) {
            test.enqueue(value);
        }
        for(int i = values.length - 1; i >= 0; i--){
            assertTrue(test.dequeue() == i);
        }
    }

    @Test
    public void isEmpty() throws Exception {
        assertTrue(test.isEmpty());
        test.enqueue(2);
        assertFalse(test.isEmpty());
    }

}
