package cn.itcast.demo;

/*
100000���ڵ����������������ʵ�ַ�ʽ��
������������ֻ�ܱ�1����������������Ȼ����-->��2��ʼ���������-1����Ϊֹ�������ܱ����������������

��PrimeNumberTest.java�ļ����������������Ż�
*/
class PrimeNumberTest2 {
	public static void main(String[] args) {
		
		
		int count = 0;//��¼�����ĸ���

		//��ȡ��ǰʱ�����1970-01-01 00:00:00 �ĺ�����
		long start = System.currentTimeMillis();

		label:for(int i = 2;i <= 100000;i++){//����100000���ڵ���Ȼ��
			
			for(int j = 2;j <= Math.sqrt(i);j++){//j:��iȥ��
				
				if(i % j == 0){ //i��j����
					continue label;
				}
				
			}
			//��ִ�е��˲���ģ���������
			count++;
		
		}

		//��ȡ��ǰʱ�����1970-01-01 00:00:00 �ĺ�����
		long end = System.currentTimeMillis();
		System.out.println("�����ĸ���Ϊ��" + count);
		System.out.println("�����ѵ�ʱ��Ϊ��" + (end - start));//17110 - �Ż�һ��break:1546 - �Ż�����13

	}
}