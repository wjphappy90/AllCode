package cn.itcast.demo;/*
��Ŀ��
�Ӽ��̶��������ȷ�������������ж϶���������͸����ĸ���������Ϊ0ʱ��������

˵����
1. ����ѭ�������������ƴ����Ľṹ��for(;;) �� while(true)
2. ����ѭ���м��ַ�ʽ��
     ��ʽһ��ѭ���������ַ���false
	 ��ʽ������ѭ�����У�ִ��break
*/

import java.util.Scanner;

class ForWhileTest {
	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		
		int positiveNumber = 0;//��¼�����ĸ���
		int negativeNumber = 0;//��¼�����ĸ���

		for(;;){//while(true){
			
			int number = scan.nextInt();

			//�ж�number���������
			if(number > 0){
				positiveNumber++;
			}else if(number < 0){
				negativeNumber++;
			}else{
				//һ��ִ��break������ѭ��
				break;
			}

		}

		System.out.println("�������������Ϊ��" + positiveNumber);
		System.out.println("����ĸ�������Ϊ��" + negativeNumber);
		

	}
}
