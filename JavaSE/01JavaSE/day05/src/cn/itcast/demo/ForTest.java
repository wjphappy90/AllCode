package cn.itcast.demo;/*
��Ŀ����������������m��n���������Լ������С��������
���磺12��20�����Լ����4����С��������60��

˵����break�ؼ��ֵ�ʹ�ã�һ����ѭ����ִ�е�break��������ѭ��

*/

import java.util.Scanner;
class ForTest{

	public static void main(String[] args){
	
		Scanner scan = new Scanner(System.in);

		System.out.println("�������һ����������");
		int m = scan.nextInt();
		
		System.out.println("������ڶ�����������");
		int n = scan.nextInt();
		
		//��ȡ���Լ��
		//1.��ȡ�������еĽ�Сֵ
		int min = (m <= n)? m : n;
		//2.����
		for(int i = min;i >= 1 ;i--){
			if(m % i == 0 && n % i == 0){
				System.out.println("���Լ��Ϊ��" + i);
				break;//һ����ѭ����ִ�е�break��������ѭ��
			}
		}
		
		//��ȡ��С������
		//1.��ȡ�������еĽϴ�ֵ
		int max = (m >= n)? m : n;
		//2.����
		for(int i = max;i <= m * n;i++){
			if(i % m == 0 && i % n == 0){
				
				System.out.println("��С��������" + i);
				break;
			
			}
		}
		
	}

}
