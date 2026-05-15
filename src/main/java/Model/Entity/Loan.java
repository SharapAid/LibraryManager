package Model.Entity;

public class Loan {
    private int bookId;
    private int index;
    private int clientId;
    private String dateIssued;

    public Loan(){}

    public Loan(int bookId, int clientId, String dateIssued, int index){
        this.bookId = bookId;
        this.clientId = clientId;
        this.dateIssued = dateIssued;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public int getBookId() {
        return bookId;
    }

    public int getClientId() {
        return clientId;
    }

    public String getDateIssued() {
        return dateIssued;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public void setDateIssued(String dateIssued) {
        this.dateIssued = dateIssued;
    }
}
