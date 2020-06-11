package entity;

import util.tool;

import java.util.ArrayList;
import java.util.List;

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

    public ArrayList<TransactionInput> inputs = new ArrayList<>();
    public ArrayList<TransactionOutput> outputs = new ArrayList<>();

    private static int sequence = 0;

    public Transaction(String id, String sender, String recipient, int amount,ArrayList<TransactionInput> inputs) {
        super();
        this.id = id;
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
        this.inputs = inputs;
    }

    public String getId() {
        return id;
    }

    private String calculateHash(){
        sequence++;
        StringBuilder builder = new StringBuilder(sender);
        builder.append(recipient).append(amount).append(sequence);
        return tool.SHA256(builder.toString());
    }
}
