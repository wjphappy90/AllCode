package cn.itcast.demo;

/*
Forѭ���ṹ��ʹ��
һ��ѭ���ṹ��4��Ҫ��
�� ��ʼ������
�� ѭ������  --->��boolean����
�� ѭ����
�� ��������

����forѭ���Ľṹ

for(��;��;��){
	��
}

ִ�й��̣��� - �� - �� - �� - �� - �� - �� - ... - ��

*/
class ForTest {
	public static void main(String[] args) {

		/*
		System.out.println("Hello World!");
		System.out.println("Hello World!");
		System.out.println("Hello World!");
		System.out.println("Hello World!");
		System.out.println("Hello World!");
		*/

		for(int i = 1;i <= 5;i++){//i:1,2,3,4,5
			System.out.println("Hello World!");
		}
		//i:��forѭ������Ч������forѭ����ʧЧ�ˡ�
		//System.out.println(i);
		
		//��ϰ��
		int num = 1;
		for(System.out.print('a');num <= 3;System.out.print('c'),num++){
			System.out.print('b');
		}
		//��������abcbcbc

		System.out.println();

		//���⣺����100���ڵ�ż��,�������ż���ĺ�,���ż���ĸ���
		int sum = 0;//��¼����ż���ĺ�
		int count = 0;//��¼ż���ĸ���
		for(int i = 1;i <= 100;i++){
			
			if(i % 2 == 0){
				System.out.println(i);
				sum += i;
				count++;
			}
			//System.out.println("�ܺ�Ϊ��" + sum);
		}

		System.out.println("�ܺ�Ϊ��" + sum);
		System.out.println("����Ϊ��" + count);

	}
}
