package com.java.zookeeper.configtogether;

import java.io.IOException;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import com.java.zookeeper.config.Properties;

public class ServerOne {
	public static void main(String[] args) throws IOException {
		ServerOne serverOne=new ServerOne();
		serverOne.initialize();
		System.in.read();
	}
	
	public void initialize(){
		final ZkClient zkClient=new ZkClient(Properties.IP_PORT, Properties.CONNECTION_TIMEOUT);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				zkClient.subscribeDataChanges("/root1", new IZkDataListener() {
					
					@Override
					public void handleDataDeleted(String dataPath) throws Exception {
						System.out.println("配置被删除了"+dataPath);
					}
					
					@Override
					public void handleDataChange(String dataPath, Object data) throws Exception {
						System.out.println("配置文件被修改了:"+dataPath+"修改后的数据是:"+data);
					}
				});
				
			}
		}).start();
	}
}
