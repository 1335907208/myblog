package org.springblade.test;

import java.util.*;

public class draw {
	public static void main(String[] args) {
//		for(int i = 0; i < 10; i++){
//			int random = new Random().nextInt(1000) + 1;     // 生成一个随机整数，范围从 1 到 10
//			System.out.println(random);
//		}
//		Queue<String> queueDraw = new LinkedList<String>();
//		queueDraw.offer("a");
//		queueDraw.offer("b");
//		queueDraw.offer("c");
//		queueDraw.offer("d");
//		queueDraw.offer("e");
//		System.out.println("=======:"+queueDraw.poll());
//		System.out.println("=======:"+queueDraw.poll());
//		for(String q:queueDraw){
//			System.out.println(q);
//		}

		List<String> list = new ArrayList<>();
		list.add("a");
		list.add("b");
		list.add("c");
		list.add("d");
		list.add("e");
		System.out.println("=======:"+list.remove(0));
		for(String q:list){
			System.out.println(q);
		}

	}
}
