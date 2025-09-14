package untar.java.tugas1;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class InventoryApp extends JFrame {
    private final ArrayList<ProductModel> products = new ArrayList<>();
    private final ProductTableModel tableModel = new ProductTableModel(products);

    private final JTextField tfKode  = new JTextField();
    private final JTextField tfNama  = new JTextField();
    private final JTextField tfQty   = new JTextField();
    private final JTextField tfHarga = new JTextField();

    private final JTable table = new JTable(tableModel);

    public InventoryApp() {
        super("Inventory App (Memory Only)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 520);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(12,12));
        root.setBorder(new EmptyBorder(12,12,12,12));
        setContentPane(root);

        // ---------- Form ----------
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6,6,6,6);
        g.fill = GridBagConstraints.HORIZONTAL;

        int r = 0;
        g.gridx=0; g.gridy=r; g.weightx=0; form.add(new JLabel("Kode:"), g);
        g.gridx=1; g.weightx=1; form.add(tfKode, g);

        r++; g.gridx=0; g.gridy=r; g.weightx=0; form.add(new JLabel("Nama:"), g);
        g.gridx=1; g.weightx=1; form.add(tfNama, g);

        r++; g.gridx=0; g.gridy=r; g.weightx=0; form.add(new JLabel("Qty:"), g);
        g.gridx=1; g.weightx=1; form.add(tfQty, g);

        r++; g.gridx=0; g.gridy=r; g.weightx=0; form.add(new JLabel("Harga:"), g);
        g.gridx=1; g.weightx=1; form.add(tfHarga, g);

        JButton btnTambah = new JButton("Tambah");
        r++; g.gridx=1; g.gridy=r; g.weightx=1;
        form.add(btnTambah, g);

        root.add(form, BorderLayout.NORTH);

        // ---------- Table ----------
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoCreateRowSorter(true);
        table.setRowHeight(22);
        root.add(new JScrollPane(table), BorderLayout.CENTER);

        // ---------- Bottom ----------
        JButton btnHapus = new JButton("Hapus Produk Terpilih");
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(btnHapus, BorderLayout.CENTER);
        root.add(bottom, BorderLayout.SOUTH);

        // Actions
        btnTambah.addActionListener(e -> onTambah());
        btnHapus.addActionListener(e -> onHapus());
        tfHarga.addActionListener(e -> onTambah());
    }

    private void onTambah() {
        String kode = tfKode.getText().trim();
        String nama = tfNama.getText().trim();
        String qtyStr = tfQty.getText().trim();
        String hargaStr = tfHarga.getText().trim();

        if (kode.isEmpty() || nama.isEmpty() || qtyStr.isEmpty() || hargaStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field wajib diisi.",
                    "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int qty;
        double harga;
        try {
            qty = Integer.parseInt(qtyStr.replace(",", ""));
            harga = Double.parseDouble(hargaStr.replace(",", ""));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Qty/Harga harus angka.",
                    "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (qty <= 0) {
            JOptionPane.showMessageDialog(this, "Qty harus > 0.",
                    "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        tableModel.addProduct(new ProductModel(kode, nama, qty, harga));
        clearForm();
        tfKode.requestFocusInWindow();
    }

    private void onHapus() {
        int viewRow = table.getSelectedRow();
        if (viewRow < 0) {
            JOptionPane.showMessageDialog(this, "Pilih baris yang akan dihapus.",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int modelRow = table.convertRowIndexToModel(viewRow);
        int ok = JOptionPane.showConfirmDialog(this,
                "Hapus record terpilih?", "Konfirmasi",
                JOptionPane.YES_NO_OPTION);
        if (ok == JOptionPane.YES_OPTION) tableModel.removeProduct(modelRow);
    }

    private void clearForm() {
        tfKode.setText(""); tfNama.setText(""); tfQty.setText(""); tfHarga.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InventoryApp().setVisible(true));
    }
}
