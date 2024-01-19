package main;


import blacklist.BlacklistService;
import blacklist.BlacklistServiceImpl;
import domaine.QueryFactory;
import domaine.QueryFactoryImpl;
import server.ProxyServer;

public class Main {

	public static void main(String[] args) {
		QueryFactory queryFactory = new QueryFactoryImpl();
		BlacklistService blacklistService = new BlacklistServiceImpl();
		ProxyServer proxyServer = new ProxyServer(queryFactory, blacklistService);
		proxyServer.startServer();
	}

}