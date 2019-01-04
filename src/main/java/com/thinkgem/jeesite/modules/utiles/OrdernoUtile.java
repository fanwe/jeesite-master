package com.thinkgem.jeesite.modules.utiles;

import java.util.UUID;

public class OrdernoUtile {

	//1.用UUID生成十六位数唯一订单号

	public static String getOrderIdByUUId() {
		int machineId = 1;//最大支持1-9个集群机器部署
		int hashCodeV = UUID.randomUUID().toString().hashCode();
		if(hashCodeV < 0) {//有可能是负数
			hashCodeV = - hashCodeV;
			
		}
//      0 代表前面补充0     
//      4 代表长度为4     
//      d 代表参数为正数型
		return  machineId+ String.format("%015d", hashCodeV);

	}
	public static void main(String[] args) {
		String orderIdByUUId = getOrderIdByUUId();
		System.out.println(orderIdByUUId);
	}
}
