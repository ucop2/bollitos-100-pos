package com.bollitos.vista;

import com.bollitos.dao.ProductoDAO;
import com.bollitos.dao.VentaDAO;
import com.bollitos.modelo.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VentasPanel extends JPanel {
    private JComboBox<Producto> cbProductos;
    private JTextField tfCantidad;
    private DefaultTableModel model;
    private List<Producto> productosEnVenta = new ArrayList<>();
    private List<Integer> cantidades = new ArrayList<>();
    private List<Double> subtotales = new ArrayList<>();
    private ProductoDAO pdao = new ProductoDAO();
    private VentaDAO vdao = new VentaDAO();

    public VentasPanel() {
        setLayout(new BorderLayout(5,5));
        JPanel top = new JPanel();
        cbProductos = new JComboBox<>();
        cargarProductos();
        tfCantidad = new JTextField(4);
        JButton btnAgregar = new JButton("Agregar al carrito");
        btnAgregar.addActionListener(e -> agregarCarrito());
        top.add(new JLabel("Producto:")); top.add(cbProductos);
        top.add(new JLabel("Cantidad:")); top.add(tfCantidad); top.add(btnAgregar);
        add(top, BorderLayout.NORTH);

        model = new DefaultTableModel(new Object[]{"Producto","Cantidad","Subtotal"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        JButton btnFinalizar = new JButton("Finalizar venta");
        btnFinalizar.addActionListener(e -> finalizarVenta());
        JButton btnLimpiar = new JButton("Limpiar");
        btnLimpiar.addActionListener(e -> limpiarCarrito());
        bottom.add(btnFinalizar); bottom.add(btnLimpiar);
        add(bottom, BorderLayout.SOUTH);
    }

    private void cargarProductos() {
        cbProductos.removeAllItems();
        for (Producto p : pdao.listar()) cbProductos.addItem(p);
    }

    private void agregarCarrito() {
        Producto p = (Producto) cbProductos.getSelectedItem();
        if (p == null) return;
        try {
            int cant = Integer.parseInt(tfCantidad.getText().trim());
            if (cant <= 0) { JOptionPane.showMessageDialog(this, "Cantidad inválida"); return; }
            if (cant > p.getStock()) { JOptionPane.showMessageDialog(this, "Stock insuficiente"); return; }
            productosEnVenta.add(p);
            cantidades.add(cant);
            double sub = cant * p.getPrecio();
            subtotales.add(sub);
            model.addRow(new Object[]{p.getNombre(), cant, sub});
            tfCantidad.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Cantidad inválida");
        }
    }

    private void finalizarVenta() {
        if (productosEnVenta.isEmpty()) { JOptionPane.showMessageDialog(this, "Carrito vacío"); return; }
        boolean ok = vdao.guardarVenta(productosEnVenta, cantidades, subtotales);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Venta registrada correctamente");
            limpiarCarrito();
            cargarProductos();
        } else {
            JOptionPane.showMessageDialog(this, "Error registrando la venta");
        }
    }

    private void limpiarCarrito() {
        productosEnVenta.clear(); cantidades.clear(); subtotales.clear();
        model.setRowCount(0);
    }
}
