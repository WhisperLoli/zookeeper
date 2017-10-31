package com.java.zookeeper.fuzaijunheng;

import java.io.IOException;

import org.I0Itec.zkclient.ZkClient;

import com.java.zookeeper.config.Properties;

public class ServerAProvider {
	private String serviceName="service-A";
	public void init(){
		String path="/configcenter";
		ZkClient zkClient=new ZkClient(Properties.IP_PORT);
		if(!zkClient.exists(path)){
			zkClient.createPersistent(path);
		}
		
		boolean flag=zkClient.exists(path+"/"+serviceName);
		if(!flag){
			zkClient.createPersistent(path+"/"+serviceName);
		}
		
		//注册当前服务
		String ip="192.168.174.1:20880";
		if(!zkClient.exists(path+"/"+serviceName+"/"+ip)){
			//创建临时节点以便故障转移
			zkClient.createEphemeral(path+"/"+serviceName+"/"+ip);
		}
		System.out.println("提供的服务器名称为"+path+"/"+serviceName+"/"+ip);
	}
	
	public static void main(String[] args) throws IOException {
		ServerAProvider serverAProvider=new ServerAProvider();
		serverAProvider.init();
		System.in.read();
	}
	
	
}
