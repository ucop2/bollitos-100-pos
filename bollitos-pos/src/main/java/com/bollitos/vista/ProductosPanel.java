package com.bollitos.vista;

import com.bollitos.dao.ProductoDAO;
import com.bollitos.modelo.Producto;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProductosPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private ProductoDAO dao = new ProductoDAO();

    public ProductosPanel() {
        setLayout(new BorderLayout(5,5));
        model = new DefaultTableModel(new Object[]{"ID","Nombre","Precio","Stock"}, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel controls = new JPanel();
        JButton btnRefresh = new JButton("Actualizar");
        btnRefresh.addActionListener(e -> llenarTabla());
        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.addActionListener(e -> mostrarDialogoAgregar());
        JButton btnEditar = new JButton("Editar");
        btnEditar.addActionListener(e -> mostrarDialogoEditar());
        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(e -> eliminarSeleccion());
        controls.add(btnRefresh); controls.add(btnAgregar); controls.add(btnEditar); controls.add(btnEliminar);
        add(controls, BorderLayout.SOUTH);

        llenarTabla();
    }

    private void llenarTabla() {
        model.setRowCount(0);
        List<Producto> lista = dao.listar();
        for (Producto p : lista) {
            model.addRow(new Object[]{p.getId(), p.getNombre(), p.getPrecio(), p.getStock()});
        }
    }

    private void mostrarDialogoAgregar() {
        JTextField tfNombre = new JTextField();
        JTextField tfPrecio = new JTextField();
        JTextField tfStock = new JTextField();
        Object[] fields = {"Nombre:", tfNombre, "Precio:", tfPrecio, "Stock:", tfStock};
        int opc = JOptionPane.showConfirmDialog(this, fields, "Agregar producto", JOptionPane.OK_CANCEL_OPTION);
        if (opc == JOptionPane.OK_OPTION) {
            try {
                Producto p = new Producto();
                p.setNombre(tfNombre.getText().trim());
                p.setPrecio(Double.parseDouble(tfPrecio.getText().trim()));
                p.setStock(Integer.parseInt(tfStock.getText().trim()));
                if (dao.agregar(p)) {
                    llenarTabla();
                } else {
                    JOptionPane.showMessageDialog(this, "Error agregando producto");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Datos inválidos: " + ex.getMessage());
            }
        }
    }

    private void mostrarDialogoEditar() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Seleccione un producto"); return; }
        int id = (int) model.getValueAt(row, 0);
        Producto p = dao.buscarPorId(id);
        if (p == null) { JOptionPane.showMessageDialog(this, "Producto no encontrado"); return; }
        JTextField tfNombre = new JTextField(p.getNombre());
        JTextField tfPrecio = new JTextField(String.valueOf(p.getPrecio()));
        JTextField tfStock = new JTextField(String.valueOf(p.getStock()));
        Object[] fields = {"Nombre:", tfNombre, "Precio:", tfPrecio, "Stock:", tfStock};
        int opc = JOptionPane.showConfirmDialog(this, fields, "Editar producto", JOptionPane.OK_CANCEL_OPTION);
        if (opc == JOptionPane.OK_OPTION) {
            try {
                p.setNombre(tfNombre.getText().trim());
                p.setPrecio(Double.parseDouble(tfPrecio.getText().trim()));
                p.setStock(Integer.parseInt(tfStock.getText().trim()));
                if (dao.actualizar(p)) {
                    llenarTabla();
                } else {
                    JOptionPane.showMessageDialog(this, "Error actualizando producto");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Datos inválidos: " + ex.getMessage());
            }
        }
    }

    private void eliminarSeleccion() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Seleccione un producto"); return; }
        int id = (int) model.getValueAt(row, 0);
        int confirma = JOptionPane.showConfirmDialog(this, "¿Eliminar producto seleccionado?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            if (dao.eliminar(id)) llenarTabla();
            else JOptionPane.showMessageDialog(this, "Error eliminando producto");
        }
    }
}
