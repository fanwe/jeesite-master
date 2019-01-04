package com.thinkgem.jeesite.modules.company.utiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompanyData implements Runnable{

	protected static Logger logger = LoggerFactory.getLogger(ParsePDFFileUtile.class);
	public static void main(String[] args) {
		CompanyData c= new CompanyData();
		
		c.run();
	}
	@Override
	public void run() {
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					for (int i = 0; i < 10; i++) {
						if(i==2){
							Thread.sleep(50);
						}else {
							System.out.println("========="+i);
							Thread.sleep(5000);
						}
					}
				}
				catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		t.start();
	}

}
