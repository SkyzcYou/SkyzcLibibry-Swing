package com.edu.admin;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static com.edu.DBHandle.*;
import static com.edu.Information.show_arraylist;
import static com.edu.Redirect.toBookShow;

public class BookShowManagement extends JFrame implements ActionListener {

    private JButton btn_showDelete;
    private JPanel jp;
    private JScrollPane jsp_table;
    private JTable table;
    private ArrayList<String> selected_info = new ArrayList<>();

    public BookShowManagement() {
        jp = (JPanel) getContentPane();
        setTitle("借阅信息管理");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(400, 100, 550, 420);
        setResizable(false);
        jp.setBackground(Color.lightGray);
        jp.setBorder(new EmptyBorder(5, 5, 5, 5));
        jp.setLayout(null);

        btn_showDelete = new JButton("删除借阅信息");
        btn_showDelete.setFont(new Font("宋体", Font.BOLD, 20));
        btn_showDelete.setBounds(170, 350, 200, 25);
        jp.add(btn_showDelete);
        btn_showDelete.addActionListener(this);

        class MyTableModel extends AbstractTableModel {

            String[] column_name = {"姓名", "书号", "借阅的书本", "借阅时间", "选择"};
            ;
            Object[][] obj;

            public MyTableModel() {
                showAll();

                obj = new Object[show_arraylist.size()][column_name.length];

                for (int i = 0; i < show_arraylist.size(); i++) {
                    obj[i][0] = show_arraylist.get(i).user_name;
                    //obj[i][1] = show_arraylist.get(i).user_name;
                    obj[i][1] = show_arraylist.get(i).book_no;
                    obj[i][2] = show_arraylist.get(i).book_name;
                    obj[i][3] = show_arraylist.get(i).BorrowDate;
                    obj[i][4] = new Boolean(false);
                }
            }

            //定义每列的数据类型
            Class[] typeArray = {Object.class, Object.class, Object.class, Object.class, Boolean.class};

            /**
             * 获得表格的列名称
             */
            @Override

            public String getColumnName(int column) {

                return column_name[column];

            }

            /**
             * 重写方法，得到表格列数
             */
            @Override
            public int getColumnCount() {
                return column_name.length;
            }

            public int getRowCount() {
                return obj.length;
            }

            /**
             * 得到数据所对应对象
             */
            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                return obj[rowIndex][columnIndex];
            }


            /**
             * 使表格具有可编辑性
             */

            @Override

            public boolean isCellEditable(int rowIndex, int columnIndex) {

                if (columnIndex == 4)
                    return true;
                else
                    return false;

            }


            /**
             * 替换单元格的值
             */
            @Override

            public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

                obj[rowIndex][columnIndex] = aValue;

                fireTableCellUpdated(rowIndex, columnIndex);

                selected_info.clear();
                for (int i = 0; i < getRowCount(); i++) {
                    String book_no = (String) getValueAt(i, 1);



                    if ((Boolean) getValueAt(i, 4)) {
                        selected_info.add(book_no);
                    } else {
                        for (int j = 0; j < selected_info.size(); j++) {
                            if (selected_info.get(j) == book_no) {
                                selected_info.remove(i);
                            }
                        }
                    }
                }
                System.out.println(selected_info);

            }

            /**
             *返回列的数据类型
             */
            @Override
            public Class getColumnClass(int columnIndex) {
                return typeArray[columnIndex];// 返回每一列的数据类型
            }

        }


        table = new JTable(new MyTableModel());
        jsp_table = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jsp_table.setBounds(10, 20, 520, 300);
        jp.add(jsp_table);


    }


    public void actionPerformed(ActionEvent event) {
        if (selected_info.size() == 0) {
            JOptionPane.showMessageDialog(this, "请选择至少一条信息！");
            return;
        }

        for (int i = 0; i < selected_info.size(); i++) {
            if (deleteShow(selected_info.get(i))) {
                JOptionPane.showMessageDialog(this, "借阅信息" + selected_info.get(i) + "删除成功");

            } else {
                JOptionPane.showMessageDialog(this, "借阅信息" + selected_info.get(i) + "删除失败");
            }
        }
        this.dispose();
        toBookShow();
    }
}
