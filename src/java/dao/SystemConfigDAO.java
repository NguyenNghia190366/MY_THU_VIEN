/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.SystemConfig;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import mylib.DBUtils;

/**
 *
 * @author DELL
 */
public class SystemConfigDAO {

    public ArrayList<SystemConfig> getConfigList() {
        ArrayList<SystemConfig> list = new ArrayList<>();
        Connection cn = null;  // connect database and netbeans 
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "select [id], [config_key], [config_value], [description]\n"
                        + "from [dbo].[system_config]";
                Statement st = cn.createStatement();
                ResultSet table = st.executeQuery(sql);

                if (table != null) {
                    while (table.next()) {
                        int id = table.getInt("id");
                        String key = table.getString("config_key");
                        double value = table.getDouble("config_value");
                        String description = table.getString("description");
                        SystemConfig c = new SystemConfig(id, key, value, description); // tao mot cai config moi
                        list.add(c);
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return list;

    }

    public void updateConfigValue(String key, double value) {
        try ( Connection cn = DBUtils.getConnection()) {
            String sql = "UPDATE system_config SET config_value = ? WHERE config_key = ?";
            PreparedStatement st = cn.prepareStatement(sql);
            st.setDouble(1, value);
            st.setString(2, key);
            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getNewBookYears() {
        int years = 0;
        Connection cn = null;  // connect database and netbeans 
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "select [config_value] from [dbo].[system_config]\n"
                        + "where [config_key] = 'new_book_year_range'";
                PreparedStatement pst = cn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    years = Integer.parseInt(rs.getString("config_value"));
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return years;
    }

}
