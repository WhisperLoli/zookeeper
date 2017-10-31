package com.java.zookeeper.fuzaijunheng;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

import com.java.zookeeper.config.Properties;
/**
 * 用zookeeper实现负载均衡
 * @author HNJ
 *
 */
public class Consumer {
	private List<String> serverList=new ArrayList<String>();
	private String serviceName="service-A";
	public void init(){
		String path="/configcenter/"+serviceName;
		String zkServiceList=Properties.IP_PORT;
		ZkClient zkClient=new ZkClient(zkServiceList);
		if(zkClient.exists(path)){
			serverList=zkClient.getChildren(path);
		}
		
		zkClient.subscribeChildChanges(path, new IZkChildListener() {
			
			@Override
			public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
				System.out.println(parentPath+"变化了");
				serverList=currentChilds;
			}
		});
	}
	
	public void consume(){
		if(serverList.size()>0){
			int index=new Random().nextInt(serverList.size());
			System.out.println("调用"+serverList.get(index)+"提供服务"+serviceName);
		}else {
			System.out.println("没有可用服务");
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		Consumer consumer=new Consumer();
		consumer.init();
		while(true){
			consumer.consume();
			Thread.sleep(1000);
		}
	}
}
