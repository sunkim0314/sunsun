package com.test.web.vo;

public class TestVO {
	private String a;
	private int b;
	
	public TestVO(){}//기본생성자
	//사용자 정의 생성자를 사용하려면 있어야한다. -> 있는 것이 좋다 암묵적 룰
	public TestVO(String a, int b) {
		super();
		this.a = a;
		this.b = b;
	}
	//getter & setter 반드시 존재해야 한다.
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
	//toString은 Option
	@Override
	public String toString() {
		return "TestVO [a=" + a + ", b=" + b + "]";
	}
	
}
