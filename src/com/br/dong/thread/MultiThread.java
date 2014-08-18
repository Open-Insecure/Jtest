package com.br.dong.thread;

public class MultiThread {
	public static void main(String[] args) {
		int i = 5;
		for (int j = 0; j < i; j++) {
			new Thread(new RunabledImp()).start();
		}
		System.out.println("**********************");
		for (int j = 0; j < i; j++) {
			new RunabledImp().run();
		}
		new NewThread().start();
	}

	static class NewThread extends Thread {
		@Override
		public void run() {
			setName("new thread");
			System.out.println("New thread");
		}
	}

	static class RunabledImp implements Runnable {
		private int count = 10;
		private static int countDown = 0;
		private final int threadCount = countDown++;

		public RunabledImp(int index) {
			// threadCount=index;
		}

		public RunabledImp() {
		}

		private String status() {
			return "#" + threadCount + " " + count + "thread";
		}

		@Override
		public void run() {
			while (count-- > 0) {
				System.out.println(status());
			}
			System.out.println("count:" + count);
		}
	}
}