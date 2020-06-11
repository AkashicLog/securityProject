package entity;

import util.tool;

import java.util.ArrayList;
import java.util.List;

public class Block {
    private int index;//索引值
    private String hash; //hash标识
    private int nonce;   //工作量证明，即计算正确的hash值次数
    private List<Transaction> transactions; //交易信息

    //blockheader
    private String hashPreBlock;  //前一个区块的hash值
    private String hashMerkleRoot; //Merkle树的hash值
    private long timestamp; //时间戳

    private MerkleTree merkleRoot;//Merkle树

    //设置交易数据
    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
        hashTransactions();
    }

    //获取交易数据
    public List<Transaction> getTransactions() {
        return transactions;
    }

    private String getHash(){
        return calculateHash(index,hashPreBlock,timestamp,merkleRoot.getRoot().getHash(),nonce);
    }

    //计算hash
    private String calculateHash(int index,String hashPreBlock,long timestamp,String data,long nonce){
        StringBuilder builder = new StringBuilder(String.valueOf(index));
        builder.append(hashPreBlock).append(timestamp).append(data).append(nonce);
        hash = tool.SHA256(tool.SHA256(builder.toString()));
        return hash;
    }

    public String getMerKleRoot() {
        return merkleRoot.getRoot().getHash();
    }

    public void hashTransactions(){
        List<String> buff = new ArrayList<String>();
        //初始化block 时 trans为空 此时不要计算
        if(transactions.get(0) ==null ){
            return;
        }
        for(int i =0;i<transactions.size();i++){
            //将 所有交易数据的id(hash256) 进行拼接
            buff.add(new String(transactions.get(i).getId()));
        }
        //拼接后做一次hash256
        merkleRoot = new MerkleTree(buff);
    }
}
