package cn.itcast.demo;/*
��д�����ɼ����������������ֱ�������num1��num2��num3��
�����ǽ�������(ʹ�� if-else if-else),���Ҵ�С���������

˵����
1. if-else�ṹ�ǿ����໥Ƕ�׵ġ�
2. ���if-else�ṹ�е�ִ�����ֻ��һ��ʱ����Ӧ��һ��{}����ʡ�Եġ����ǣ���������ʡ�ԡ�
*/
import java.util.Scanner;
class IfTest2 {
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);

		System.out.println("�������һ��������");
		int num1 = scanner.nextInt();
		System.out.println("������ڶ���������");
		int num2 = scanner.nextInt();
		System.out.println("�����������������");
		int num3 = scanner.nextInt();

		if(num1 >= num2){
			if(num3 >= num1)
				System.out.println(num2 + "," + num1 + "," + num3);
			else if(num3 <= num2)
				System.out.println(num3 + "," + num2 + "," + num1);
			else
				System.out.println(num2 + "," + num3 + "," + num1);
			
		
		}else{
			if(num3 >= num2)
				System.out.println(num1 + "," + num2 + "," + num3);
			else if(num3 <= num1)
				System.out.println(num3 + "," + num1 + "," + num2);
			else
				System.out.println(num1 + "," + num3 + "," + num2);
			
		}

	}
}
