//package com.fc.nit.wallet.service;
//
//import java.math.BigInteger;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.web3j.protocol.core.DefaultBlockParameter;
//
//import com.fc.nit.wallet.util.EthConnectPool;
//
//import rx.Subscription;
//
///**
// * filter相关
// * 监听区块、交易
// * 所有监听都在Web3jRx中
// */
//@Service
//public class Filter {
//
////	public  void main(String[] args) {
////		/**
////		 * 新区块监听
////		 */
////		newBlockFilter(web3j);
////		/**
////		 * 新交易监听
////		 */
////		newTransactionFilter(web3j);
////		/**
////		 * 遍历旧区块、交易
////		 */
////		replayFilter(web3j);
////		/**
////		 * 从某一区块开始直到最新区块、交易
////		 */
////		catchUpFilter(web3j);
////
////		/**
////		 * 取消监听
////		 */
////		//subscription.unsubscribe();
////	}
//	
//	@Autowired
//	EthConnectPool ethConnectPool;
//	
//
//	private  void newBlockFilter() {
//		Subscription subscription = ethConnectPool.getWeb3j().
//				blockObservable(false).
//				subscribe(block -> {
//					System.out.println("new block come in");
//					System.out.println("block number" + block.getBlock().getNumber());
//				});
//	}
//
//	private  void newTransactionFilter() {
//		Subscription subscription = ethConnectPool.getWeb3j().
//				transactionObservable().
//				subscribe(transaction -> {
//					System.out.println("transaction come in");
//					System.out.println("transaction txHash " + transaction.getHash());
//				});
//	}
//
//	private  void replayFilter() {
//		BigInteger startBlock = BigInteger.valueOf(5786314);
//		BigInteger endBlock = BigInteger.valueOf(5786314);
//		/**
//		 * 遍历旧区块
//		 */
//		Subscription subscription = ethConnectPool.getWeb3j().
//				replayBlocksObservable(
//						DefaultBlockParameter.valueOf(startBlock),
//						DefaultBlockParameter.valueOf(endBlock),
//						false).
//				subscribe(ethBlock -> {
//					System.out.println("replay block");
//					System.out.println(ethBlock.getBlock().getNumber());
//				});
//
//		/**
//		 * 遍历旧交易
//		 */
//		Subscription subscription1 = ethConnectPool.getWeb3j().
//				replayTransactionsObservable(
//						DefaultBlockParameter.valueOf(startBlock),
//						DefaultBlockParameter.valueOf(endBlock)).
//				subscribe(transaction -> {
//					System.out.println("replay transaction");
//					System.out.println("txHash " + transaction.getHash());
//				});
//	}
//
//	private  void catchUpFilter() {
//		BigInteger startBlock = BigInteger.valueOf(5786314);
//
//		/**
//		 * 遍历旧区块，监听新区块
//		 */
//		Subscription subscription = ethConnectPool.getWeb3j().catchUpToLatestAndSubscribeToNewBlocksObservable(
//				DefaultBlockParameter.valueOf(startBlock), false)
//				.subscribe(block -> {
//					System.out.println("block");
//					System.out.println(block.getBlock().getNumber());
//				});
//
//		/**
//		 * 遍历旧交易，监听新交易
//		 */
//		Subscription subscription2 = ethConnectPool.getWeb3j().catchUpToLatestAndSubscribeToNewTransactionsObservable(
//				DefaultBlockParameter.valueOf(startBlock))
//				.subscribe(tx -> {
//					System.out.println("transaction");
//					System.out.println(tx.getHash());
//				});
//	}
//}
