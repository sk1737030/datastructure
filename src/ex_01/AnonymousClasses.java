package ex_01;

import java.util.ArrayList;
import java.util.List;

/* 
 * interface�� method�� �̿��Ͽ��� implements.
 * �޼ҵ� ������ �͸�Ŭ���� ��������
 * ���������ÿ� ���ο� Ŭ������ �����Ѵ� �ѹ��� �����ϴ°Ծƴ϶�
 * 	Hellowworld frenchGreeting = new Hellowworld() { function }
 * 
 * @author sk173
 * 
 */
public class AnonymousClasses {
 
	interface Hellowworld {
		public void greet();
		public void greetSomeone(String zsomeone);
	}

	public void sayhellow() {
		class EnglishGreeting implements Hellowworld {
			String name = "world";

			@Override
			public void greet() {
				// TODO Auto-generated method stub
				greetSomeone("wrold");
			}

			@Override
			public void greetSomeone(String someone) {
				// TODO Auto-generated method stub
				name = someone;
				System.out.println("hellow" + someone);
			}
		}

		Hellowworld englishGreeting = new EnglishGreeting();

		Hellowworld frenchGreeting = new Hellowworld() {
			String name = "tout le monde";

			public void greet() {
				greetSomeone("tout le monde");
			}

			public void greetSomeone(String someone) {
				name = someone;
				System.out.println("Salut " + name);
			}
		};

		Hellowworld spanishGreeting = new Hellowworld() {
			String name = "mundo";

			public void greet() {
				greetSomeone("mundo");
			}

			public void greetSomeone(String someone) {
				name = someone;
				System.out.println("Hola, " + name);
			}
		};

		englishGreeting.greet();
		frenchGreeting.greetSomeone("Fred");
		spanishGreeting.greet();

	}

	public static void main(String[] args) {
		AnonymousClasses anonymousClasses = new AnonymousClasses();
		anonymousClasses.sayhellow();

	}

}
