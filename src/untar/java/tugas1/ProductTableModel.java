package untar.java.tugas1;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ProductTableModel extends AbstractTableModel {
    private final String[] columns = {"Kode","Nama","Qty","Harga"};
    ArrayList<ProductModel> products;

    public ProductTableModel(ArrayList<ProductModel> products) {
        this.products = products;
    }

    @Override public int getRowCount() { return products.size(); }
    @Override public int getColumnCount() { return columns.length; }
    @Override public String getColumnName(int column) { return columns[column]; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ProductModel p = products.get(rowIndex);
        switch (columnIndex) {
            case 0: return p.getCode();
            case 1: return p.getName();
            case 2: return p.getQty();
            case 3: return p.getPrice();
            default: return null;
        }
    }

    public void addProduct(ProductModel p) {
        products.add(p);
        int r = products.size()-1;
        fireTableRowsInserted(r, r);
    }

    public void removeProduct(int index) {
        if (index >= 0 && index < products.size()) {
            products.remove(index);
            fireTableRowsDeleted(index, index);
        }
    }
}
