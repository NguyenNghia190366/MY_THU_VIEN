///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package dao;
//
//import dto.Book;
//import dto.BookRequest;
//import dto.SystemConfig;
//import dto.User;
//import java.sql.Connection;
//import java.sql.Date;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.Statement;
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.time.ZoneId;
//import java.time.ZoneOffset;
//import java.util.ArrayList;
//import java.util.Calendar;
//import mylib.DBUtils;
//
///**
// *
// * @author Admin
// */
//public class BookRequestDAO {
//    public ArrayList<BookRequest> showRequest(){
//        ArrayList<BookRequest> list = new ArrayList<>();
//        Connection cn = null;
//        try {
//            cn = DBUtils.getConnection();
//            if(cn != null){
//                String sql="SELECT [id]\n" +
//"      ,[user_id]\n" +
//"      ,[book_id]\n" +
//"      ,[request_date]\n" +
//"      ,[status]\n" +
//"  FROM [library_system].[dbo].[book_requests]";
//                Statement st = cn.createStatement();
//                ResultSet table = st.executeQuery(sql);
//                if(table!=null){
//                    while(table.next()){
//                        int id = table.getInt("id");
//                        int user_id = table.getInt("user_id");
//                        int book_id = table.getInt("book_id");
//                        UserDAO ud = new UserDAO();
//                        BookDAO bd = new BookDAO();
//                        User user = ud.checkUserByID(user_id);
//                        Book book = bd.findBookById(book_id);
//                        Date request_date = table.getDate("request_date");
//                        String status = table.getString("status");
//                        BookRequest book_request = new BookRequest(id, user, book, request_date, status);
//                        list.add(book_request);
//                    }
//                }
//            }
//        } catch (Exception e) {
//        }
//        return list;
//    }
//    
//    public BookRequest findRequestByID(int id){
//         ArrayList<BookRequest> list = this.showRequest();
//         BookRequest request = null;
//        for (BookRequest bookrequest : list) {
//            if(bookrequest.getId()==id){
//                request = bookrequest;
//                break;
//            }
//        }
//         return request;
//    }
//    
//    public int changeStatus(String choice, int id){
//        int re = 0;
//        SystemConfigDAO scd = new SystemConfigDAO();
//        ArrayList<SystemConfig> sc = scd.getConfigList();
//        int days = Integer.parseInt(sc.get(1).getConfig_value());
//        
//        Connection cn = null;
//        BookRequest request = null;
//       
//        
//        
//        ArrayList<BookRequest> list = this.showRequest();
//        for(int i = 0;i<list.size();i++){
//            if(list.get(i).getId()==id){
//                request = list.get(i);
//                break;
//            }
//        }
//        try {
//            cn = DBUtils.getConnection();
//            if(cn != null && request!=null){
//                if(choice.equals("approve")){
//                    String sql = "UPDATE book_requests set status = 'approved' where id = ?;";
//                    PreparedStatement st = cn.prepareStatement(sql);
//                    st.setInt(1, id);
//                    re = st.executeUpdate();
//                } else if(choice.equals("reject")){
//                String sql = "UPDATE book_requests set status = 'rejected' where id = ?;";
//                    PreparedStatement st = cn.prepareStatement(sql);
//                    st.setInt(1, id);
//                    re = st.executeUpdate();
//            } else if(choice.equals("borrowed")){
//                Date date = null, dueDate = null;
//                String sqlDate = "  Select Convert(date, GETDATE()) AS currenttime;";
//                Statement stDate = cn.createStatement();
//                ResultSet tableDate = stDate.executeQuery(sqlDate);
//                if(tableDate!=null && tableDate.next()){
//                    date = (Date) tableDate.getDate("currenttime");
//                }
//                String sqlDueDate = "Select Convert(date, DATEADD(day,?,GETDATE())) AS notcurrenttime;";
//                PreparedStatement stDueDate = cn.prepareStatement(sqlDueDate);
//                stDueDate.setInt(1, days);
//                ResultSet tableDueDate = stDueDate.executeQuery();
//                if(tableDueDate!=null && tableDueDate.next()){
//                    dueDate = (Date) tableDueDate.getDate("notcurrenttime");
//                }
//                
//                
//                String sql = "INSERT INTO [borrow_records] (user_id, book_id, borrow_date, due_date, status) values (?,?,?,?,'borrowed');";
//                    PreparedStatement st = cn.prepareStatement(sql);
//                    st.setInt(1, request.getUser().getId());
//                    st.setInt(2, request.getBook().getId());
//                    st.setDate(3, date);
//                    st.setDate(4, dueDate);
//                   re = st.executeUpdate();
//                   
//                   String sqldelete = "DELETE FROM book_requests WHERE id=?;";
//                    PreparedStatement stdelete = cn.prepareStatement(sqldelete);
//                    stdelete.setInt(1, request.getId());
//                   re = stdelete.executeUpdate();
//                   
//                   
//            } }
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return re;
//    }
//}
