package test;

import org.junit.Test;

public class ImageTest {

    /**
     * 快排
     */
    @Test
    public void aaa(){
        int[] arr = {1,0,17,8,98,7,6,5,4,0};
        int left = 0;
        int right = arr.length-1;
        sort(arr,left,right);
        for (int i : arr) {
            System.out.print(i+" ");
        }
    }

    public void sort(int[] arr, int left, int right){
        if(left > right) {
            return;
        }
        int base = arr[left];
        int i = left,j = right;
        while (i!=j){
            while (arr[j]>=base && i<j){
                j--;
            }
            while(arr[i]<=base && i<j){
                i++;
            }
            if(i<j){
                int t = arr[i];
                arr[i] = arr[j];
                arr[j] = t;
            }
        }

        arr[left] = arr[i];
        arr[i] = base;

        // 递归，继续向基准的左右两边执行和上面同样的操作
        // i的索引处为上面已确定好的基准值的位置，无需再处理
        for (int x : arr) {
            System.out.print(x+" ");
        }
        System.out.println();
        sort(arr, left, i - 1);
        sort(arr, i + 1, right);
    }

    @Test
    public void test() throws Exception {
        long start = System.currentTimeMillis() * 1000;   //获取开始时间
        int a = 5;
        a = a * 2 - 1;
        int[][] ring = new int[a][a];
        for (int i = a - 1; i >= 0; i--) {
            for (int j = 0; j <= a - 1; j++) {
                if (j <= i) {
                    ring[i][j] = j + 1;
                    ring[j][i] = j + 1;
                }
            }
        }
        int y = 0;
        int t = 0;
        for (int i = a - 1; i >= (a + 1) / 2; i--) {
            y++;
            for (int j = a - 1; j >= t; j--) {
                if (j <= i) {
                    ring[i][j] = y;
                    ring[j][i] = y;
                }
            }
            t++;
        }
        //输出环
        for (int i = 0; i <= a - 1; i++) {
            for (int j = 0; j <= a - 1; j++) {
                System.out.print(ring[i][j] + " ");
            }
            System.out.println();

        }
        long end = System.currentTimeMillis() * 1000; //获取结束时间
        System.out.println("程序运行时间： " + (end - start) + "ms");
    }

    @Test
    public void test2() {
        long start = System.currentTimeMillis() * 1000;   //获取开始时间
        for (int x = 0; x < 10000; x++) {
            int number = 5;
            int m, n;
            int a = number;
            for (m = 1; m <= 2 * number - 1; m++) {
                for (n = 1; n <= 2 * number - 1; n++) {
                    if (n < number - a + 1) {
                        System.out.print(number - n + 1);
                    } else {
                        if (n > number + a - 1) {
                            System.out.print(n + 1 - number);
                        } else {
                            System.out.print(a);
                        }
                    }
                }
                System.out.println("");
                if (m < number) {
                    a--;
                } else {
                    a++;
                }
            }
        }
        long end = System.currentTimeMillis() * 1000; //获取结束时间
        System.out.println("程序运行时间： " + (end - start) + "ms");
    }
}
