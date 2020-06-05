package entity;

import java.util.List;

public class Block {
    private int index;//索引值
    private String hash; //hash标识
    private int nonce;   //工作量证明，即计算正确的hash值次数
    private long timestamp; //时间戳
    private String previousHash;  //前一个区块的hash值
    private List<Transaction> transactions; //交易集合

    public Block() {
        super();
    }

    public Block(int index, long timestamp, List<Transaction> transactions, int nonce, String previousHash, String hash) {
        super();
        this.index = index;
        this.timestamp = timestamp;
        this.transactions = transactions;
        this.nonce = nonce;
        this.previousHash = previousHash;
        this.hash = hash;
    }
}
