package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Controller.ControleMarca;
import Model.Marca;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Optional;
import java.awt.event.ActionEvent;

public class CadastraMarca extends JFrame {

	private JPanel contentPane;
	private JTextField tfNome;
	private JTextField tfCnpj;
	private JTextField tfTelefone;
	private JButton btnCadastrar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadastraMarca frame = new CadastraMarca();
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
	public CadastraMarca() {
		carregaTela("Cadastro de marca", "Cadastrar");
		
		//Personalizando o botão de cadastro
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ControleMarca controle_marca = new ControleMarca();	
				controle_marca.incluiMarca(tfNome, tfCnpj, tfTelefone);
				ListaMarca lista_marca = new ListaMarca();
				lista_marca.setVisible(true);
				setVisible(false);
			}
		});
		contentPane.add(btnCadastrar);
	}
	
	public CadastraMarca(int id) {
		carregaTela("Edição de marca", "Editar");
		
		ControleMarca controle_marca = new ControleMarca();	
		
		//Personalizando o botão de edição
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controle_marca.editaMarca(id, tfNome, tfCnpj, tfTelefone);
				ListaMarca lista_marca = new ListaMarca();
				lista_marca.setVisible(true);
				setVisible(false);
			}
		});
		contentPane.add(btnCadastrar);
		
		//Escrevendo valores antigos da marca para edição
		Marca marca = controle_marca.pegaMarcaPorId(id);
		tfNome.setText(marca.getNome());
		tfCnpj.setText(marca.getCnpj());
		tfTelefone.setText(marca.getTelefone());
	}
	
	private void carregaTela(String titulo, String tituloBotao) {		
		setTitle(titulo);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 635, 499);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNome_1 = new JLabel("Nome:");
		lblNome_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNome_1.setBounds(83, 90, 122, 28);
		contentPane.add(lblNome_1);
		
		tfNome = new JTextField();
		tfNome.setColumns(10);
		tfNome.setBounds(218, 95, 299, 23);
		contentPane.add(tfNome);
		
		tfCnpj = new JTextField();
		tfCnpj.setColumns(10);
		tfCnpj.setBounds(218, 156, 299, 23);
		contentPane.add(tfCnpj);
		
		JLabel lblCnpj = new JLabel("CNPJ:");
		lblCnpj.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCnpj.setBounds(83, 154, 122, 28);
		contentPane.add(lblCnpj);
		
		JLabel lblTelefone = new JLabel("Telefone:");
		lblTelefone.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTelefone.setBounds(83, 214, 122, 28);
		contentPane.add(lblTelefone);
		
		tfTelefone = new JTextField();
		tfTelefone.setColumns(10);
		tfTelefone.setBounds(218, 219, 299, 23);
		contentPane.add(tfTelefone);
		
		btnCadastrar = new JButton(tituloBotao);
		btnCadastrar.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnCadastrar.setBounds(211, 354, 196, 42);
	}
}
