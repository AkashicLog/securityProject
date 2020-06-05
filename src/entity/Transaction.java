package entity;

public class Transaction {
    //前次交易ID
    private String prevId;
    //交易ID
    private String id;
    //交易发送方钱包地
    private String sender;
    //交易接收方钱包地址
    private String recipient;
    // 交易金额
    private int amount;
    //发送方钱包公钥
    private String publicKey;
    //交易接收方的钱包公钥hash值
    private String publicHashKey;


    public Transaction() {
        super();
    }

    public Transaction(String id, String sender, String recipient, int amount) {
        super();
        this.id = id;
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
    }
}
