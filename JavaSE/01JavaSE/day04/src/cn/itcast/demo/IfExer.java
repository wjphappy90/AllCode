package cn.itcast.demo;

class IfExer {
	public static void main(String[] args) {
		int x = 4;
		int y = 1;
		if (x > 2) 
			if (y > 2) 
                System.out.println(x + y);
				//System.out.println("atguigu");
			else //�ͽ�ԭ��
				System.out.println("x is " + x);
		

		//�κ���ϰ3�����㹷������
		int dogAge = 6;
		if(dogAge >= 0 && dogAge <= 2){
			System.out.println("�൱���˵����䣺" + dogAge * 10.5);
		}else if( dogAge > 2){
			System.out.println("�൱���˵����䣺" + (2 * 10.5 + (dogAge - 2) * 4));
		}else{
			System.out.println("������û�����أ�");
		}

		//�κ���ϰ4����λ�ȡһ���������10 - 99
		int value = (int)(Math.random() * 90 + 10);// [0.0,1.0) --> [0.0,90.0) --->[10.0, 100.0) -->[10,99]
		System.out.println(value);
		//��ʽ��[a,b]  :  (int)(Math.random() * (b - a + 1) )+ a
	}
}
