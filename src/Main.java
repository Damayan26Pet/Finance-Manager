import java.awt.*;
import java.io.IOException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Main {
    private static LinkedList<Transaction> transactionList = new LinkedList<>();
    private static JTable table;
    private static DefaultTableModel tableModel;

    public static void main(String[] args) {
        // GUI 
        JFrame frame = new JFrame("Finance Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        String[] columns = {"Amount", "Category", "Date"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);

        JButton addBtn = new JButton("Add Transaction");
        addBtn.addActionListener(e -> addTransaction());
        JButton saveBtn = new JButton("Save to File");
        saveBtn.addActionListener(e -> {
            try {
                FileManager.saveToFile(transactionList, "transactions.txt");
                JOptionPane.showMessageDialog(null, "Transactions saved successfully.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error saving file: " + ex.getMessage());
            }
        });

        JButton loadBtn = new JButton("Load from File");
        loadBtn.addActionListener(e -> {
            try {
                transactionList = FileManager.loadFromFile("transactions.txt");
                refreshTable();
                JOptionPane.showMessageDialog(null, "Transactions loaded successfully.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error loading file: " + ex.getMessage());
            }
        });


        JButton sortBtn = new JButton("Sort by Date");
        sortBtn.addActionListener(e -> {
            transactionList.bubbleSort();
            refreshTable();
        });

        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu("Actions");
        menuBar.add(menu);

        JMenuItem addItem = new JMenuItem("Add Transaction");
        addItem.addActionListener(e -> addTransaction());
        menu.add(addItem);

        JMenuItem sortItem = new JMenuItem("Sort by Date");
        sortItem.addActionListener(e -> {
            transactionList.bubbleSort();
            refreshTable();
        });
        menu.add(sortItem);

        JMenuItem saveItem = new JMenuItem("Save to File");
        saveItem.addActionListener(e -> {
            try {
                FileManager.saveToFile(transactionList, "transactions.txt");
                JOptionPane.showMessageDialog(null, "Transactions saved successfully.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error saving file: " + ex.getMessage());
            }
        });
        menu.add(saveItem);

        JMenuItem loadItem = new JMenuItem("Load from File");
        loadItem.addActionListener(e -> {
            try {
                transactionList = FileManager.loadFromFile("transactions.txt");
                refreshTable();
                JOptionPane.showMessageDialog(null, "Transactions loaded successfully.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error loading file: " + ex.getMessage());
            }
        });
        menu.add(loadItem);

        JMenuItem editItem = new JMenuItem("Edit Transaction");
        editItem.addActionListener(e -> editTransaction());
        menu.add(editItem);

        JMenuItem deleteItem = new JMenuItem("Delete Transaction");
        deleteItem.addActionListener(e -> deleteTransaction());
        menu.add(deleteItem);

        JMenuItem searchItem = new JMenuItem("Search");
        searchItem.addActionListener(e -> searchTransaction());
        menu.add(searchItem);

        frame.add(new JScrollPane(table), BorderLayout.CENTER);
        frame.setVisible(true);
        frame.setJMenuBar(menuBar);
    }

    // Methods for transactions in GUI
    private static void addTransaction() {
        try {
            String amountStr = JOptionPane.showInputDialog("Enter amount:");
            double amount = Double.parseDouble(amountStr);
            if (amount < 0) throw new IllegalArgumentException("Amount cannot be negative.");

            String category = JOptionPane.showInputDialog("Enter category:");
            String date = JOptionPane.showInputDialog("Enter date (YYYY-MM-DD):");
            if (date == null || date.isEmpty()) throw new IllegalArgumentException("Date cannot be empty.");
            if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) throw new IllegalArgumentException("Date must be in YYYY-MM-DD format.");

            Transaction t = new Transaction(amount, category, date);
            transactionList.add(t);
            tableModel.addRow(new Object[]{amount, category, date});
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
    }

    private static void refreshTable() {
        tableModel.setRowCount(0);
        for (int i = 0; i < transactionList.size(); i++) {
            Transaction t = transactionList.get(i);
            tableModel.addRow(new Object[]{t.getAmount(), t.getCategory(), t.getDate()});
        }
    }

    private static void editTransaction() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0 && selectedRow < transactionList.size()) {
            try {
                Transaction t = transactionList.get(selectedRow);

                String newAmountStr = JOptionPane.showInputDialog("Edit amount:", t.getAmount());
                double newAmount = Double.parseDouble(newAmountStr);

                String newCategory = JOptionPane.showInputDialog("Edit category:", t.getCategory());
                String newDate = JOptionPane.showInputDialog("Edit date (YYYY-MM-DD):", t.getDate());

                t.setAmount(newAmount);
                t.setCategory(newCategory);
                t.setDate(newDate);

                refreshTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Select a transaction to edit.");
        }
    }

    private static void deleteTransaction() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0 && selectedRow < transactionList.size()) {
            transactionList.remove(selectedRow);
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(null, "Select a transaction to delete.");
        }
    }

    private static void searchTransaction() {
        String keyword = JOptionPane.showInputDialog("Enter category or amount to search:");
        if (keyword == null || keyword.isEmpty()) return;

        tableModel.setRowCount(0);

        for (int i = 0; i < transactionList.size(); i++) {
            Transaction t = transactionList.get(i);
            if (t.getCategory().equalsIgnoreCase(keyword) || String.valueOf(t.getAmount()).equals(keyword)) {
                tableModel.addRow(new Object[]{t.getAmount(), t.getCategory(), t.getDate()});
            }
        }
    }
}
