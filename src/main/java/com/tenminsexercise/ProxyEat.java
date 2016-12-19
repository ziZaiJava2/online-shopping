package com.tenminsexercise;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyEat implements InvocationHandler{

	private Object object;
	
	public ProxyEat(Object obj){
		this.object = obj;
	}
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("œ¥ ÷");
		Object o =method.invoke(object, args);
		return o;
	}



	
}
