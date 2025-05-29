import java.io.*;
public class FileManager {
    public static void saveToFile(LinkedList<Transaction> list, String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        for (int i = 0; i < list.size(); i++) {
            Transaction t = list.get(i);
            writer.write(t.getAmount() + "," + t.getCategory() + "," + t.getDate());
            writer.newLine();
        }
        writer.close();
    }

    public static LinkedList<Transaction> loadFromFile(String filename) throws IOException {
        LinkedList<Transaction> list = new LinkedList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            double amount = Double.parseDouble(parts[0]);
            String category = parts[1];
            String date = parts[2];
            list.add(new Transaction(amount, category, date));
        }
        reader.close();
        return list;
    }
}

