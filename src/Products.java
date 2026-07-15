import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;

public class Products extends JFrame {

    private JTextField txtProductId, txtProductName, txtPrice, txtStock, txtSearch;
    private JComboBox<String> cmbCategory;
    private JTable tableProducts;
    private DefaultTableModel tableModel;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear, btnAdminHome;
    private JLabel lblTitle;
    private int selectedRow = -1;

    public Products() {
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setTitle("Products");
        setLayout(new BorderLayout());

        // ─── TOP HEADER ───
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(136, 204, 204));
        headerPanel.setPreferredSize(new Dimension(1550, 85));
        lblTitle = new JLabel("Product Management");
        lblTitle.setFont(new Font("Arial Black", Font.BOLD, 30));
        headerPanel.add(lblTitle);
        add(headerPanel, BorderLayout.NORTH);

        // ─── MAIN CONTENT ───
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // ─── LEFT: INPUT FORM ───
        JPanel formPanel = new JPanel();
        formPanel.setBackground(Color.BLACK);
        formPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.WHITE, 2),
            "Add / Edit Product",
            0, 0,
            new Font("Segoe UI", Font.BOLD, 20),
            Color.WHITE
        ));
        formPanel.setPreferredSize(new Dimension(480, 700));
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        Font labelFont = new Font("Dialog", Font.BOLD, 18);
        Font fieldFont = new Font("Dialog", Font.PLAIN, 18);

        // Product ID
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel l1 = new JLabel("Product ID :"); l1.setFont(labelFont); l1.setForeground(Color.WHITE);
        formPanel.add(l1, gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        txtProductId = new JTextField(); txtProductId.setFont(fieldFont); txtProductId.setPreferredSize(new Dimension(350, 40));
        formPanel.add(txtProductId, gbc);

        // Product Name
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel l2 = new JLabel("Product Name :"); l2.setFont(labelFont); l2.setForeground(Color.WHITE);
        formPanel.add(l2, gbc);
        gbc.gridx = 0; gbc.gridy = 3;
        txtProductName = new JTextField(); txtProductName.setFont(fieldFont); txtProductName.setPreferredSize(new Dimension(350, 40));
        formPanel.add(txtProductName, gbc);

        // Category
        gbc.gridx = 0; gbc.gridy = 4;
        JLabel l3 = new JLabel("Category :"); l3.setFont(labelFont); l3.setForeground(Color.WHITE);
        formPanel.add(l3, gbc);
        gbc.gridx = 0; gbc.gridy = 5;
        cmbCategory = new JComboBox<>(new String[]{"Electronics", "Mobile", "Laptop", "Accessories", "Audio", "TV", "Camera", "Other"});
        cmbCategory.setFont(fieldFont); cmbCategory.setPreferredSize(new Dimension(350, 40));
        formPanel.add(cmbCategory, gbc);

        // Price
        gbc.gridx = 0; gbc.gridy = 6;
        JLabel l4 = new JLabel("Price (Rs.) :"); l4.setFont(labelFont); l4.setForeground(Color.WHITE);
        formPanel.add(l4, gbc);
        gbc.gridx = 0; gbc.gridy = 7;
        txtPrice = new JTextField(); txtPrice.setFont(fieldFont); txtPrice.setPreferredSize(new Dimension(350, 40));
        formPanel.add(txtPrice, gbc);

        // Stock
        gbc.gridx = 0; gbc.gridy = 8;
        JLabel l5 = new JLabel("Stock Qty :"); l5.setFont(labelFont); l5.setForeground(Color.WHITE);
        formPanel.add(l5, gbc);
        gbc.gridx = 0; gbc.gridy = 9;
        txtStock = new JTextField(); txtStock.setFont(fieldFont); txtStock.setPreferredSize(new Dimension(350, 40));
        formPanel.add(txtStock, gbc);

        // Buttons Row
        gbc.gridx = 0; gbc.gridy = 10;
        JPanel btnFormPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnFormPanel.setBackground(Color.BLACK);

        btnAdd = makeButton("Add", new Color(0, 153, 76), Color.WHITE);
        btnUpdate = makeButton("Update", new Color(0, 102, 204), Color.WHITE);
        btnClear = makeButton("Clear", new Color(100, 100, 100), Color.WHITE);

        btnFormPanel.add(btnAdd);
        btnFormPanel.add(btnUpdate);
        btnFormPanel.add(btnClear);
        formPanel.add(btnFormPanel, gbc);

        // ─── RIGHT: TABLE + SEARCH ───
        JPanel rightPanel = new JPanel(new BorderLayout(10, 10));
        rightPanel.setBackground(new Color(245, 245, 245));

        // Search bar
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(new Color(245, 245, 245));
        JLabel lblSearch = new JLabel("Search:"); lblSearch.setFont(new Font("Segoe UI", Font.BOLD, 18));
        txtSearch = new JTextField(25); txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        txtSearch.setPreferredSize(new Dimension(300, 38));
        searchPanel.add(lblSearch);
        searchPanel.add(txtSearch);

        JLabel lblList = new JLabel("  Product List");
        lblList.setFont(new Font("Segoe UI", Font.BOLD, 22));
        searchPanel.add(lblList);

        // Table
        tableModel = new DefaultTableModel(
            new String[]{"Product ID", "Product Name", "Category", "Price (Rs.)", "Stock"},
            0
        ) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        tableProducts = new JTable(tableModel);
        tableProducts.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        tableProducts.setRowHeight(30);
        tableProducts.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        tableProducts.getTableHeader().setBackground(new Color(136, 204, 204));
        tableProducts.setSelectionBackground(new Color(200, 230, 255));
        tableProducts.setGridColor(new Color(200, 200, 200));

        // Live search filter
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        tableProducts.setRowSorter(sorter);
        txtSearch.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                String text = txtSearch.getText().trim();
                sorter.setRowFilter(text.isEmpty() ? null : RowFilter.regexFilter("(?i)" + text));
            }
        });

        // Row click → fill form
        tableProducts.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = tableProducts.getSelectedRow();
                if (row >= 0) {
                    selectedRow = tableProducts.convertRowIndexToModel(row);
                    txtProductId.setText(tableModel.getValueAt(selectedRow, 0).toString());
                    txtProductName.setText(tableModel.getValueAt(selectedRow, 1).toString());
                    cmbCategory.setSelectedItem(tableModel.getValueAt(selectedRow, 2).toString());
                    txtPrice.setText(tableModel.getValueAt(selectedRow, 3).toString());
                    txtStock.setText(tableModel.getValueAt(selectedRow, 4).toString());
                    txtProductId.setEditable(false); // don't change ID during update
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(tableProducts);
        scrollPane.setPreferredSize(new Dimension(900, 520));

        // Delete + Admin Home buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        bottomPanel.setBackground(new Color(245, 245, 245));
        btnDelete = makeButton("Delete Selected", new Color(153, 0, 0), Color.WHITE);
        btnAdminHome = makeButton("Admin Home", new Color(136, 204, 204), new Color(51, 51, 51));
        bottomPanel.add(btnDelete);
        bottomPanel.add(btnAdminHome);

        rightPanel.add(searchPanel, BorderLayout.NORTH);
        rightPanel.add(scrollPane, BorderLayout.CENTER);
        rightPanel.add(bottomPanel, BorderLayout.SOUTH);

        mainPanel.add(formPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

        // ─── BUTTON ACTIONS ───
        btnAdd.addActionListener(e -> addProduct());
        btnUpdate.addActionListener(e -> updateProduct());
        btnDelete.addActionListener(e -> deleteProduct());
        btnClear.addActionListener(e -> clearForm());
        btnAdminHome.addActionListener(e -> goToAdmin());

        pack();
        setSize(1550, 860);
        setLocationRelativeTo(null);
    }

    private JButton makeButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(160, 42));
        return btn;
    }

    private void addProduct() {
        String id = txtProductId.getText().trim();
        String name = txtProductName.getText().trim();
        String cat = (String) cmbCategory.getSelectedItem();
        String price = txtPrice.getText().trim();
        String stock = txtStock.getText().trim();

        if (id.isEmpty() || name.isEmpty() || price.isEmpty() || stock.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Duplicate ID check
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 0).toString().equalsIgnoreCase(id)) {
                JOptionPane.showMessageDialog(this, "Product ID already exists!", "Duplicate", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        try {
            double p = Double.parseDouble(price);
            int s = Integer.parseInt(stock);
            if (p < 0 || s < 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Price and Stock must be valid positive numbers!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        tableModel.addRow(new Object[]{id, name, cat, price, stock});
        JOptionPane.showMessageDialog(this, "Product added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        clearForm();
    }

    private void updateProduct() {
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a product from the table to update.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String name = txtProductName.getText().trim();
        String cat = (String) cmbCategory.getSelectedItem();
        String price = txtPrice.getText().trim();
        String stock = txtStock.getText().trim();

        if (name.isEmpty() || price.isEmpty() || stock.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            double p = Double.parseDouble(price);
            int s = Integer.parseInt(stock);
            if (p < 0 || s < 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Price and Stock must be valid positive numbers!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        tableModel.setValueAt(name, selectedRow, 1);
        tableModel.setValueAt(cat, selectedRow, 2);
        tableModel.setValueAt(price, selectedRow, 3);
        tableModel.setValueAt(stock, selectedRow, 4);
        JOptionPane.showMessageDialog(this, "Product updated successfully!", "Updated", JOptionPane.INFORMATION_MESSAGE);
        clearForm();
    }

    private void deleteProduct() {
        int viewRow = tableProducts.getSelectedRow();
        if (viewRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a row to delete.", "No Selection", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete this product?", "Confirm Delete",
            JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            int modelRow = tableProducts.convertRowIndexToModel(viewRow);
            tableModel.removeRow(modelRow);
            clearForm();
        }
    }

    private void clearForm() {
        txtProductId.setText("");
        txtProductName.setText("");
        txtPrice.setText("");
        txtStock.setText("");
        cmbCategory.setSelectedIndex(0);
        txtProductId.setEditable(true);
        selectedRow = -1;
        tableProducts.clearSelection();
    }

    private void goToAdmin() {
        Admin adminFrame = new Admin();
        adminFrame.setVisible(true);
        adminFrame.pack();
        adminFrame.setLocationRelativeTo(null);
        this.dispose();
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new Products().setVisible(true));
    }
}