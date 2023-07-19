package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Menu extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu frame = new Menu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Menu() {
		setTitle("Sistema de farmácia");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 522);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnRemedio = new JButton("Remédios");
		btnRemedio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListaRemedio lista_remedio = new ListaRemedio();
				lista_remedio.setVisible(true);
				setVisible(false);
			}
		});
		btnRemedio.setFont(new Font("Tahoma", Font.PLAIN, 23));
		btnRemedio.setBounds(211, 216, 193, 50);
		contentPane.add(btnRemedio);

		JButton btnVenda = new JButton("Vendas");
		btnVenda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListaVenda lista_venda = new ListaVenda();
				lista_venda.setVisible(true);
				setVisible(false);
			}
		});
		btnVenda.setFont(new Font("Tahoma", Font.PLAIN, 23));
		btnVenda.setBounds(211, 309, 193, 50);
		contentPane.add(btnVenda);

		JButton btnMarca = new JButton("Marcas");
		btnMarca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListaMarca lista_marca = new ListaMarca();
				lista_marca.setVisible(true);
				setVisible(false);
			}
		});
		btnMarca.setFont(new Font("Tahoma", Font.PLAIN, 23));
		btnMarca.setBounds(211, 123, 193, 50);
		contentPane.add(btnMarca);
	}

}
