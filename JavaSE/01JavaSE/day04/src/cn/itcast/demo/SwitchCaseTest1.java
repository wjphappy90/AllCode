package cn.itcast.demo;

/*
���⣺��ѧ���ɼ�����60�ֵģ�������ϸ񡱡�����60�ֵģ���������ϸ񡱡�

˵�������switch-case�ṹ�еĶ��case��ִ�������ͬ������Կ��ǽ��кϲ���
*/
class SwitchCaseTest1 {
	public static void main(String[] args) {
		
		/*
		int score = 78;
		switch(score){
		case 0:

		case 1:

		case 2:

			...
		case 100:
		
		}
		*/

		/*
		int score = 78;
		if(score >= 60){
		
		}else{
		
		}
		*/
		
		int score = 78;
		switch(score / 10){
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
			System.out.println("������");
			break;
		case 6:
		case 7:
		case 8:
		case 9:
		case 10:
			System.out.println("����");
			break;

		}
		
		//���ŵĽ��������
		switch(score / 60){
		case 0:
			System.out.println("������");
			break;
		case 1:
			System.out.println("����");
			break;
		}
	
	}
}
