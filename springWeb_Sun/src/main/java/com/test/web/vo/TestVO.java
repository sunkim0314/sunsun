package com.test.web.vo;

public class TestVO {
	private String a;
	private int b;
	
	public TestVO(){}//�⺻������
	//����� ���� �����ڸ� ����Ϸ��� �־���Ѵ�. -> �ִ� ���� ���� �Ϲ��� ��
	public TestVO(String a, int b) {
		super();
		this.a = a;
		this.b = b;
	}
	//getter & setter �ݵ�� �����ؾ� �Ѵ�.
	public String getA() {
		return a;
	}
	public void setA(String a) {
		this.a = a;
	}
	public int getB() {
		return b;
	}
	public void setB(int b) {
		this.b = b;
	}
	//toString�� Option
	@Override
	public String toString() {
		return "TestVO [a=" + a + ", b=" + b + "]";
	}
	
}
