package cn.itcast.demo;

/*
break��continue�ؼ��ֵ�ʹ��
				ʹ�÷�Χ			ѭ����ʹ�õ�����(��ͬ��)		��ͬ��
break:			switch-case			
				ѭ���ṹ��			������ǰѭ��					�ؼ��ֺ��治������ִ�����	

continue:		ѭ���ṹ��			��������ѭ��					�ؼ��ֺ��治������ִ�����



*/
class BreakContinueTest {
	public static void main(String[] args) {

		for(int i = 1;i <= 10;i++){
		
			if(i % 4 == 0){
				break;//123
				//continue;//123567910
				//System.out.println("��������Ȱ�ҪԼ�ң�����");
			}
			System.out.print(i);
		}

		System.out.println("\n");
		//******************************
		
		label:for(int i = 1;i <= 4;i++){
		
			for(int j = 1;j <= 10;j++){
				
				if(j % 4 == 0){
					//break;//Ĭ�����������˹ؼ��������һ��ѭ����
					//continue;

					//break label;//����ָ����ʶ��һ��ѭ���ṹ
					continue label;//����ָ����ʶ��һ��ѭ���ṹ����ѭ��
				}
				
				System.out.print(j);
			}
			
			System.out.println();
		}
	}
}
