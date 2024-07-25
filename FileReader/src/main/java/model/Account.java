package model;

/**
 * @author Mandana Soleimani Nia
 *this is a class for account objects
 */
public class Account {
    private int recordNumber;
    private String accountNumber;
    private String accountType;
    private int accountCustomerId;
    private String accountLimit;
    private String accountOpenDate;
    private String accountBalance;
    private String msg;


    public Account(int recordNumber, String accountNumber, String accountType, int accountCustomerId, String accountLimit
            , String accountOpenDate, String accountBalance) {
        this.recordNumber = recordNumber;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.accountCustomerId = accountCustomerId;
        this.accountLimit = accountLimit;
        this.accountOpenDate = accountOpenDate;
        this.accountBalance = accountBalance;
    }

    public Account() {
    }

    @Override
    public String toString() {
        return "Account{" +
                "recordNumber=" + recordNumber +
                ", accountNumber='" + accountNumber + '\'' +
                ", accountType='" + accountType + '\'' +
                ", accountCustomerId=" + accountCustomerId +
                ", accountLimit='" + accountLimit + '\'' +
                ", accountOpenDate='" + accountOpenDate + '\'' +
                ", accountBalance='" + accountBalance + '\'' +
                '}';
    }

    public int getRecordNumber() {
        return recordNumber;
    }

    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public int getAccountCustomerId() {
        return accountCustomerId;
    }

    public void setAccountCustomerId(int accountCustomerId) {
        this.accountCustomerId = accountCustomerId;
    }

    public String getAccountLimit() {
        return accountLimit;
    }

    public void setAccountLimit(String accountLimit) {
        this.accountLimit = accountLimit;
    }

    public String getAccountOpenDate() {
        return accountOpenDate;
    }

    public void setAccountOpenDate(String accountOpenDate) {
        this.accountOpenDate = accountOpenDate;
    }

    public String getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(String accountBalance) {
        this.accountBalance = accountBalance;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
