package com.tenminsexercise;

import java.lang.reflect.Proxy;

public class Main {

	public static void main(String[] args) {

		RealEat realEat = new RealEat();
		ProxyEat proxyEat = new ProxyEat(realEat);
		Eat eat;
		eat = (Eat) Proxy.newProxyInstance(realEat.getClass().getClassLoader()
											, realEat.getClass().getInterfaces()
											, proxyEat);
		eat.eat("11");
	}

}
