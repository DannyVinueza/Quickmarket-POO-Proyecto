import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Usuarios extends Administrador {
    private JPanel contentPane;
    private JComboBox comboBox1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTable table1;

    public Usuarios() {
        super(2);
        Image img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("Imagenes/LOGO.png"));
        setIconImage(img);
        setTitle("Usuarios");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(contentPane);
        pack();
        setVisible(true);

        /*regresarL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Administrador login = new Administrador(2);
            }
        });*/
        }
}
