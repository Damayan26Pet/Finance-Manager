public class Transaction {
    private double amount;
    private String category;
    private String date;

    public Transaction(double amount, String category, String date) {
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    public double getAmount() { return amount; }
    public String getCategory() { return category; }
    public String getDate() { return date; }

    public void setAmount(double amount) { this.amount = amount; }
    public void setCategory(String category) { this.category = category; }
    public void setDate(String date) { this.date = date; }

    public static Transaction fromString(String line) {
        String[] parts = line.split(",");
        double amount = Double.parseDouble(parts[0]);
        String category = parts[1];
        String date = parts[2];
        return new Transaction(amount, category, date);
    }
    @Override
    public String toString() {
        return amount + "," + category + "," + date;
    }
}

