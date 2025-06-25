/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.SystemConfig;
import dto.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import mylib.DBUtils;

/**
 *
 * @author Admin
 */
public class SystemConfigDAO {
    public ArrayList<SystemConfig> getConfigList(){
        ArrayList<SystemConfig> newRule = new ArrayList<>();;
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if(cn != null){
                //bc 2: viet query va execute query
                String sql = "select id,config_key,config_value,description\n"
                        + "from dbo.system_config";
                Statement st=cn.createStatement();
                ResultSet table=st.executeQuery(sql);
                if(table!=null){
                    while(table.next()){
                         int id=table.getInt("id");
                         String key=table.getString("config_key");
                         String value=table.getString("config_value");
                         String description=table.getString("description");
                         SystemConfig c=new SystemConfig(id, key, value, description);
                         newRule.add(c);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("ERRORRR");
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return newRule;
    }
    public void updateConfig(String a, String b, String c){
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if(cn != null){
                String[] setkey = {a, b, c};
                for(int i = 0; i<setkey.length;i++){
                String sql = "UPDATE system_config SET config_value=? where id = ?;";
                PreparedStatement st = cn.prepareStatement(sql);
                st.setString(1, setkey[i]);
                st.setInt(2, i+1);
                st.executeUpdate();
                }

                }
        } catch (Exception e) {
        }
    }
}
