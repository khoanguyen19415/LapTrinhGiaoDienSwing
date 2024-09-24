/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BT02;

import java.awt.BorderLayout;
import java.awt.Button;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Administrator
 */
public class DemoJtable extends JFrame {

    private JTable tblTaiKhoan;
    private JLabel lbTen, lbTien;
    private JTextField txtTen, txtTien;
    private JButton btnThem, btnXoa;

    public DemoJtable() {
        super("Demo Jtable");
        createGUI();
        processEvent();
        setSize(650, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void createGUI() {
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        //tạo jtable
        String[] columnName = {"Tên tài khoản", "số tiền"};
        Object[][] data = new Object[][]{
            {"Nguyễn Cảnh Đăng Khoa", 1000000000},
            {"Đỗ Uyên", 300000000}
        };
        DefaultTableModel model = new DefaultTableModel(data, columnName);
        tblTaiKhoan = new JTable(model);

        JScrollPane srcollpaneTble = new JScrollPane(tblTaiKhoan);

        p.add(srcollpaneTble);

        //tạo jpanel chứa các điều khiển nhập liệu
        JPanel pNhapLieu = new JPanel();
        pNhapLieu.add(lbTen = new JLabel("Tên tài khoản"));
        pNhapLieu.add(txtTen = new JTextField(15));
        pNhapLieu.add(lbTien = new JLabel("Số tiền"));
        pNhapLieu.add(txtTien = new JTextField(10));
        pNhapLieu.add(btnThem = new JButton("Thêm"));
        pNhapLieu.add(btnXoa = new JButton("Xóa"));

        p.add(pNhapLieu, BorderLayout.NORTH);
        add(p);

        //thêm icon cho button
        btnThem.setIcon(new ImageIcon(this.getClass().getResource("/images/new.png")));
        btnXoa.setIcon(new ImageIcon(this.getClass().getResource("/images/Save.png")));

    }

    public static void main(String[] args) {
        DemoJtable frm = new DemoJtable();
        frm.setVisible(true);
    }

    private void processEvent() {
        //Thêm
        btnThem.addActionListener((e) -> {
            //get model của JTable         
            DefaultTableModel model = (DefaultTableModel) tblTaiKhoan.getModel();
            model.addRow(new Object[]{txtTen.getText(), txtTien.getText()});
            javax.swing.JOptionPane.showMessageDialog(this, "Thêm thành công", "thông báo", javax.swing.JOptionPane.INFORMATION_MESSAGE);
        });
        //Xóa
        btnXoa.addActionListener((e) -> {
            int selectedRow = tblTaiKhoan.getSelectedRow();

            // Kiểm tra xem có hàng nào được chọn không
            if (selectedRow == -1) {
                javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng chọn một hàng để xóa!", "Thông báo", javax.swing.JOptionPane.WARNING_MESSAGE);
            } else {
                // Hiển thị hộp thoại xác nhận
                int confirmation = javax.swing.JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa hàng này?", "Xác nhận xóa", javax.swing.JOptionPane.YES_NO_OPTION);

                if (confirmation == javax.swing.JOptionPane.YES_OPTION) {
                    DefaultTableModel model = (DefaultTableModel) tblTaiKhoan.getModel();
                    model.removeRow(selectedRow);
                    // Thông báo xóa thành công
                    javax.swing.JOptionPane.showMessageDialog(this, "Đã xóa hàng thành công!", "Thông báo", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }
}
