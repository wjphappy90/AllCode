package cn.itcast.demo;

/*
100000���ڵ����������������ʵ�ַ�ʽһ
������������ֻ�ܱ�1����������������Ȼ����-->��2��ʼ���������-1����Ϊֹ�������ܱ����������������

��PrimeNumberTest.java�ļ����������������Ż�
*/
class PrimeNumberTest1 {
	public static void main(String[] args) {
		
		boolean isFlag = true;//��ʶi�Ƿ�j������һ���������޸���ֵ
		int count = 0;//��¼�����ĸ���

		//��ȡ��ǰʱ�����1970-01-01 00:00:00 �ĺ�����
		long start = System.currentTimeMillis();

		for(int i = 2;i <= 100000;i++){//����100000���ڵ���Ȼ��
			
			//�Ż������Ա�������������Ȼ������Ч�ġ�
			//for(int j = 2;j < i;j++){
			for(int j = 2;j <= Math.sqrt(i);j++){//j:��iȥ��
				
				if(i % j == 0){ //i��j����
					isFlag = false;
					break;//�Ż�һ��ֻ�Ա������������Ȼ������Ч�ġ�
				}
				
			}
			//
			if(isFlag == true){
				//System.out.println(i);
				count++;
			}
			//����isFlag
			isFlag = true;
		
		}

		//��ȡ��ǰʱ�����1970-01-01 00:00:00 �ĺ�����
		long end = System.currentTimeMillis();
		System.out.println("�����ĸ���Ϊ��" + count);
		System.out.println("�����ѵ�ʱ��Ϊ��" + (end - start));//17110 - �Ż�һ��break:1546 - �Ż�����13

	}
}