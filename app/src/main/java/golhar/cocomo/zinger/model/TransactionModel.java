package golhar.cocomo.zinger.model;


import android.os.Parcel;
import android.os.Parcelable;

public class TransactionModel implements Parcelable {
    private String transactionId;
    private String bankTransactionId;
    private String currency;
    private String responseCode;
    private String responseMessage;
    private String gatewayName;
    private String bankName;
    private String paymentMode;
    private String checksumHash;

    public TransactionModel() {
    }

    protected TransactionModel(Parcel in) {
        transactionId = in.readString();
        bankTransactionId = in.readString();
        currency = in.readString();
        responseCode = in.readString();
        responseMessage = in.readString();
        gatewayName = in.readString();
        bankName = in.readString();
        paymentMode = in.readString();
        checksumHash = in.readString();
    }

    public static final Creator<TransactionModel> CREATOR = new Creator<TransactionModel>() {
        @Override
        public TransactionModel createFromParcel(Parcel in) {
            return new TransactionModel(in);
        }

        @Override
        public TransactionModel[] newArray(int size) {
            return new TransactionModel[size];
        }
    };

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getBankTransactionId() {
        return bankTransactionId;
    }

    public void setBankTransactionId(String bankTransactionId) {
        this.bankTransactionId = bankTransactionId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getGatewayName() {
        return gatewayName;
    }

    public void setGatewayName(String gatewayName) {
        this.gatewayName = gatewayName;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getChecksumHash() {
        return checksumHash;
    }

    public void setChecksumHash(String checksumHash) {
        this.checksumHash = checksumHash;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @Override
    public String toString() {
        return "TransactionModel{" +
                "transactionId='" + transactionId + '\'' +
                ", bankTransactionId='" + bankTransactionId + '\'' +
                ", currency='" + currency + '\'' +
                ", responseCode='" + responseCode + '\'' +
                ", responseMessage='" + responseMessage + '\'' +
                ", gatewayName='" + gatewayName + '\'' +
                ", bankName='" + bankName + '\'' +
                ", paymentMode='" + paymentMode + '\'' +
                ", checksumHash='" + checksumHash + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(transactionId);
        dest.writeString(bankTransactionId);
        dest.writeString(currency);
        dest.writeString(responseCode);
        dest.writeString(responseMessage);
        dest.writeString(gatewayName);
        dest.writeString(bankName);
        dest.writeString(paymentMode);
        dest.writeString(checksumHash);
    }
}
