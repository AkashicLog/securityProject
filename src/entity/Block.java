package entity;

import util.tool;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Block implements Serializable {
    private BigInteger index;//索引值
    private String hash; //hash标识
    private long nonce;   //工作量证明，即计算正确的hash值次数
    private List<Transaction> transactions; //交易信息

    //blockheader
    private String hashPreBlock;  //前一个区块的hash值
    private String hashMerkleRoot; //Merkle树的hash值
    private long timestamp; //时间戳

    private MerkleTree merkleRoot;//Merkle树


    public Block(BigInteger index , String hashPreBlock, List<Transaction> transactions, Long nonce,Long timestamp){
        this.index = index;
        this.hashPreBlock = hashPreBlock;
        this.transactions = transactions;
        this.nonce =nonce;
        this.timestamp = timestamp;
    }

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
    private String calculateHash(BigInteger index,String hashPreBlock,long timestamp,String data,long nonce){
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

    public void setMerKleRoot(String merKleRoot) {
        hashTransactions();
    }

    public long getNonce() {
        return nonce;
    }

    public void setHashPreBlock(String hashPreBlock) {
        this.hashPreBlock = hashPreBlock;
    }

    public String getHashPreBlock() {
        return hashPreBlock;
    }

    public boolean isgenesisBlock(){
        //如果是创世区块 那么 只有一笔输入交易 和输出交易  并且输入交易的前一笔交易为null
        return this.hashPreBlock.startsWith("767be8afab82c0");
    }

    public static Block newGenesisBlock(){
        // to 锁定脚本 memo 解锁脚本
        Transaction myfirst = Transaction.createCoinBase("haha".getBytes(),"Last time");
        //将第一笔交易装入 list
        List<Transaction> firsttrans = new ArrayList<Transaction>();
        firsttrans.add(myfirst);
        //传入block
        Block firstBlock = new Block(new BigInteger("0") ,"767be8afab82c0c91c1941effc71f809de1acfded90d7219f7f762909fbe40c9",firsttrans,new Long(0),new Long("1574383821000"));
        firstBlock.hashTransactions();
        return firstBlock;
    }
    public boolean isBlockValid(List<Block> block) throws Exception {
        if(this.isgenesisBlock())
            return true;
        //验证一些列参数是否为空
        if(!ObjectUtils.notEmpty(this.previousHash) || !ObjectUtils.notEmpty(this.MerKleRoot) || !ObjectUtils.notEmpty(this.nBits )
                || !ObjectUtils.notEmpty(this.data)  || !ObjectUtils.notEmpty(this.index) || !ObjectUtils.notEmpty(this.nonce)
                || !ObjectUtils.notEmpty(this.timestamp))
            return false;
        for(int i = 0;i< data.size();i++){
            //验证交易失败则认为这个block是无效的
            if(!this.getData().get(i).isTransValid())
                return false;
            //验证交易签名是否正确
            if(!Transaction.VerifyTransaction(this.data.get(i),block))
                return false;
        }
        return true;
    }


    public static boolean isProofValid(Block block){
        if(block.isgenesisBlock())
            return true;
        //todo
        return ProofOfWork.isHashValid(block.gethash(),block.getnBits());
    }

}
