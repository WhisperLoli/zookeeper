package com.java.zookeeper.testwatcher;

import java.io.IOException;
import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import com.java.zookeeper.config.Properties;

public class TestWatcher {
	public static void main(String[] args) throws IOException {
		ZkClient client=new ZkClient(Properties.IP_PORT);
		//client.createPersistent("/parent","试试呗!");
		client.subscribeDataChanges("/parent", new IZkDataListener() {
			
			@Override
			public void handleDataDeleted(String dataPath) throws Exception {
				
			}
			
			@Override
			public void handleDataChange(String dataPath, Object data) throws Exception {
				System.out.println("/parent数据改变了");
			}
		});
		
		client.subscribeChildChanges("/parent", new IZkChildListener() {
			
			@Override
			public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
				System.out.println(parentPath+"孩子变化了！"+currentChilds);
				
			}
		});
		System.in.read();
		client.close();
		
	}
}
