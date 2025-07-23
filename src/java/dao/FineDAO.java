/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.Book;
import dto.BorrowRecord;
import dto.Fine;
import dto.SystemConfig;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import mylib.DBUtils;

/**
 *
 * @author Admin
 */
public class FineDAO {

    public ArrayList<Fine> showAllFine() {
        ArrayList<Fine> list = new ArrayList<>();
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT [id]\n"
                        + "      ,[borrow_id]\n"
                        + "      ,[fine_amount]\n"
                        + "      ,[paid_status]\n"
                        + "  FROM [library_system].[dbo].[fines]\n";
                Statement st = cn.createStatement();
                ResultSet table = st.executeQuery(sql);
                if (table != null) {
                    while (table.next()) {
                        int id = table.getInt("id");
                        int borrow_id = table.getInt("borrow_id");
                        BorrowRecordDAO brd = new BorrowRecordDAO();
                        BorrowRecord record = brd.findBorrowRecordByID(borrow_id);
                        BigDecimal fine_amount = table.getBigDecimal("fine_amount");
                        String paid_status = table.getString("paid_status");
                        Fine fine = new Fine(id, record, fine_amount, paid_status);
                        list.add(fine);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public int insertFine(int borrow_id) {
        int re = 0;
        Connection cn = null;
        try {
            BorrowRecordDAO d = new BorrowRecordDAO();
            BorrowRecord record = d.findBorrowRecordByID(borrow_id);
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "INSERT INTO [dbo].[fines]\n" +
"           ([borrow_id]\n" +
"           ,[fine_amount]\n" +
"           ,[paid_status]) VALUES (?,0.0,'unpaid')";
                PreparedStatement st = cn.prepareStatement(sql);
                st.setInt(1, borrow_id);
                re = st.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("error at FineDAO" + e.toString());
        }
        return re;
    }

    public int updateFine(int borrow_id) {
        int result = 0;
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                ArrayList<Fine> listfine = this.showAllFine();
                for (int i = 0; i < listfine.size(); i++) {
                    if (listfine.get(i).getPaid_status().equals("unpaid")
                            && listfine.get(i).getBorrowRecord().getId()==borrow_id) {
                        String sql = "UPDATE [Fines]\n"
                                + "SET [fine_amount] = ?\n"
                                + "WHERE [id] = ?";
                        PreparedStatement st = cn.prepareStatement(sql);
                        Date duedate = listfine.get(i).getBorrowRecord().getDue_date();
                        Date today = null;
                        String sqlDate = "  SELECT DATEDIFF(DAY, [due_date], GETDATE()) as number_of_days FROM "
                                + "[library_system].[dbo].[borrow_records] "
                                + "WHERE id=?;";
                        PreparedStatement stDate = cn.prepareStatement(sqlDate);
                        stDate.setInt(1, borrow_id);
                        ResultSet tableDate = stDate.executeQuery();
                        int number_of_fine_days = 0;
                        if (tableDate != null && tableDate.next()) {
                            number_of_fine_days = tableDate.getInt("number_of_days");
                            if(number_of_fine_days <= 0){return 0;}
                        }
                        String number_of_fine_days_string = ""+number_of_fine_days;
                        SystemConfigDAO d = new SystemConfigDAO();
                        ArrayList<SystemConfig> configlist = d.getConfigList();
                        String fee_string = configlist.get(0).getConfig_value();
                       BigDecimal number_of_fine_days_decimal = new BigDecimal(number_of_fine_days_string);                     
                        BigDecimal fee = BigDecimal.valueOf(Double.parseDouble(fee_string));
                       fee = fee.multiply(number_of_fine_days_decimal);
                        st.setBigDecimal(1, fee);                       
                        st.setInt(2, listfine.get(i).getId());
                       result = st.executeUpdate();
                       return 1;

                    }
                }
            }
        } catch (Exception e) {
            System.out.println("error at FineDAO" + e.toString());
        }
        return result;
    }

    public Fine showAFineByID(int borrow_id) {
        Fine fine = null;
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT [id]\n"
                        + "      ,[borrow_id]\n"
                        + "      ,[fine_amount]\n"
                        + "      ,[paid_status]\n"
                        + "  FROM [library_system].[dbo].[fines]\n"
                        + "  WHERE [borrow_id] = ?";
                PreparedStatement st = cn.prepareStatement(sql);
                st.setInt(1, borrow_id);
                ResultSet table = st.executeQuery();
                System.out.println("tui o day ne");
                System.out.println(borrow_id);
                if (table.next()) {
                    BorrowRecordDAO brd = new BorrowRecordDAO();
                    BorrowRecord record = brd.findBorrowRecordByID(borrow_id);
                    int id = table.getInt("id");
                    BigDecimal amount = table.getBigDecimal("fine_amount");
                    String status = table.getString("paid_status");
                    fine = new Fine(id, record, amount, status);
                }
            }
        } catch (Exception e) {
            System.out.println("error at FineDAO" + e.toString());
        }
        return fine;
    }
}
