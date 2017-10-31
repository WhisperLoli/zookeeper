package com.java.zookeeper.configtogether;

import java.util.UUID;

import org.I0Itec.zkclient.ZkClient;

import com.java.zookeeper.config.Properties;

/**
 * 利用zookeeper实现协同配置
 * @author HNJ
 *
 */
public class TestConfigServer {
	public static void main(String[] args) {
		TestConfigServer tConfigServer=new TestConfigServer();
		tConfigServer.initialize();
		System.out.println("服务启动");
	}
	
	ZkClient zkClient=new ZkClient(Properties.IP_PORT, Properties.CONNECTION_TIMEOUT);
	public void initialize(){
		if(!zkClient.exists("/root1")){
			zkClient.createEphemeral("/root1", new Long(System.currentTimeMillis()));
		}
		new Thread(new RootNodeChangeThread()).start();
	}
	
	private class RootNodeChangeThread implements Runnable{

		@Override
		public void run() {
			while(true){
				try{
					Thread.sleep(5000);
				}catch(Exception e){
					e.printStackTrace();
				}
				String uuidStr="配置"+UUID.randomUUID().toString();
				System.out.println("配置文件发生变化了"+uuidStr);
				zkClient.writeData("/root1", uuidStr);
			}
			
		}
		
	}
}


