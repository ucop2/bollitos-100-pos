package com.bollitos.vista;

import com.bollitos.modelo.Conexion;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginFrame extends JFrame {
    private JTextField tfUser;
    private JPasswordField pfPass;
    public LoginFrame() {
        setTitle("Bollitos POS - Login");
        setSize(350,180);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        init();
    }
    private void init() {
        JPanel p = new JPanel(new GridLayout(3,2,5,5));
        p.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        p.add(new JLabel("Usuario:"));
        tfUser = new JTextField();
        p.add(tfUser);
        p.add(new JLabel("Contraseña:"));
        pfPass = new JPasswordField();
        p.add(pfPass);
        JButton btnLogin = new JButton("Ingresar");
        btnLogin.addActionListener(e -> login());
        p.add(btnLogin);
        JButton btnExit = new JButton("Salir");
        btnExit.addActionListener(e -> System.exit(0));
        p.add(btnExit);
        add(p);
    }
    private void login() {
        String user = tfUser.getText().trim();
        String pass = new String(pfPass.getPassword());
        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese usuario y contraseña");
            return;
        }
        // Consulta simple a tabla usuarios
        String sql = "SELECT usuario, contrasena FROM usuarios WHERE usuario = ? LIMIT 1";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, user);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String dbPass = rs.getString("contrasena");
                    if (pass.equals(dbPass)) {
                        // login correcto
                        SwingUtilities.invokeLater(() -> {
                            new MainFrame().setVisible(true);
                        });
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Credenciales incorrectas");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Usuario no encontrado");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error de conexión: " + ex.getMessage());
        }
    }
}
