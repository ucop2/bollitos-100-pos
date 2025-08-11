package com.bollitos.dao;

import com.bollitos.modelo.Conexion;
import com.bollitos.modelo.Producto;
import java.sql.*;
import java.util.List;

public class VentaDAO {

    public int registrarVenta(double total) {
        String sql = "INSERT INTO ventas(total) VALUES(?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setDouble(1, total);
            int affected = ps.executeUpdate();
            if (affected == 0) return -1;
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return -1;
    }

    public boolean registrarDetalle(int idVenta, int idProducto, int cantidad, double subtotal) {
        String sql = "INSERT INTO detalle_ventas(id_venta, id_producto, cantidad, subtotal) VALUES(?,?,?,?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idVenta);
            ps.setInt(2, idProducto);
            ps.setInt(3, cantidad);
            ps.setDouble(4, subtotal);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean guardarVenta(List<Producto> productos, List<Integer> cantidades, List<Double> subtotales) {
        double total = subtotales.stream().mapToDouble(Double::doubleValue).sum();
        int idVenta = registrarVenta(total);
        if (idVenta < 0) return false;
        ProductoDAO pdao = new ProductoDAO();
        for (int i = 0; i < productos.size(); i++) {
            Producto p = productos.get(i);
            int cantidad = cantidades.get(i);
            double subtotal = subtotales.get(i);
            boolean ok1 = registrarDetalle(idVenta, p.getId(), cantidad, subtotal);
            boolean ok2 = pdao.reducirStock(p.getId(), cantidad);
            if (!ok1 || !ok2) {
                System.err.println("Error guardando detalle o reduciendo stock para producto: " + p.getNombre());
            }
        }
        return true;
    }
}
