package com.bollitos.vista;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Bollitos POS - Principal");
        setSize(800,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        init();
    }
    private void init() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Productos", new ProductosPanel());
        tabs.addTab("Ventas", new VentasPanel());
        add(tabs, BorderLayout.CENTER);
    }
}
