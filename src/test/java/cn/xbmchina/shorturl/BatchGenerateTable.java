package cn.xbmchina.shorturl;

import java.sql.*;

public class BatchGenerateTable {
    private static final char[] toBase62 = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    public static void main(String[] args) {
        conn();
    }

    /**
     * Statement 和 PreparedStatement之间的关系和区别.
     * 关系：PreparedStatement继承自Statement,都是接口
     * 区别：PreparedStatement可以使用占位符，是预编译的，批处理比Statement效率高
     */
    public static void conn() {
        String URL = "jdbc:mysql://115.159.2.165:3306/short_link_01?characterEncoding=utf-8";
        String USER = "root";
        String PASSWORD = "xxxxpwd";
        // 1.加载驱动程序
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 2.获得数据库链接
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            // 3.通过数据库的连接操作数据库，实现增删改查（使用Statement类）
            String name = "short_url_";
            conn.setAutoCommit(false);
            Statement statement = conn.createStatement();
            for (int i = 0; i < toBase62.length; i++) {
                char c = toBase62[i];
                //预编译
                String sql = "create table " + name + c + " like short_url_ ";
                statement.addBatch(sql);
            }

            statement.executeBatch();
            conn.commit();//2,进行手动提交（commit）
            System.out.println("提交成功!");
            conn.setAutoCommit(true);//3,提交完成后回复现场将Auto commit,还原为true,
            // 关闭资源
            conn.close();
            statement.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
