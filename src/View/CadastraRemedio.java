package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import Controller.ControleMarca;
import Controller.ControleRemedio;
import Model.Marca;
import Model.Remedio;

import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JFormattedTextField;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.List;
import java.awt.event.ActionEvent;

public class CadastraRemedio extends JFrame {

	private JPanel contentPane;
	private JTextField tfQuantidade;
	private JTextField tfValorVenda;
	private JTextField tfValorCusto;
	private JTextField tfNome;
	private JTextField tfCodigoBarras;
	private JComboBox cbMarca;
	private JFormattedTextField tfDataVencimento;
	private JFormattedTextField tfDataProducao;
	private JButton btnCadastrar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadastraRemedio frame = new CadastraRemedio();
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
	public CadastraRemedio() {
		carregaTela("Cadastro de remédios", "Cadastrar");

		ControleRemedio controle_remedio = new ControleRemedio();

		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controle_remedio.incluiRemedio(tfCodigoBarras, tfNome, Marca.class.cast(cbMarca.getSelectedItem()),
						tfDataProducao, tfDataVencimento, tfValorCusto, tfValorVenda, tfQuantidade);
				ListaRemedio lista_remedio = new ListaRemedio();
				lista_remedio.setVisible(true);
				setVisible(false);
			}
		});
		contentPane.add(btnCadastrar);
	}

	public CadastraRemedio(int id) {
		carregaTela("Edição de remédios", "Editar");

		ControleRemedio controle_remedio = new ControleRemedio();
		ControleMarca controle_marca = new ControleMarca();

		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controle_remedio.editaRemedio(id, tfCodigoBarras, tfNome, Marca.class.cast(cbMarca.getSelectedItem()),
						tfDataProducao, tfDataVencimento, tfValorCusto, tfValorVenda, tfQuantidade);
				ListaRemedio lista_remedio = new ListaRemedio();
				lista_remedio.setVisible(true);
				setVisible(false);
			}
		});
		contentPane.add(btnCadastrar);

		// Escrevendo valores antigos da marca para edição
		Remedio remedio = controle_remedio.pegaRemedioPorId(id);
		tfCodigoBarras.setText(remedio.getCodigoBarra());
		tfNome.setText(remedio.getNome());
		List<Marca> marcasArray = controle_marca.listaMarcas();
		for (int i = 0; i < marcasArray.size(); i++) {
			if (marcasArray.get(i).getId() == remedio.getMarca().getId()) {
				cbMarca.setSelectedIndex(i);
			}
		}
		// formatando data
		tfDataProducao.setText(formataData(remedio.getDataProducao().toString()));
		tfDataVencimento.setText(formataData(remedio.getDataValidade().toString()));

		tfValorCusto.setText(remedio.getValorCusto().toString());
		tfValorVenda.setText(remedio.getValorVenda().toString());
		tfQuantidade.setText(Integer.toString(remedio.getQuantidade()));
	}

	private void carregaTela(String titulo, String tituloBotao) {
		ControleMarca controle_marca = new ControleMarca();

		setTitle(titulo);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 655, 521);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnCadastrar = new JButton(tituloBotao);
		btnCadastrar.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnCadastrar.setBounds(230, 390, 196, 42);

		tfQuantidade = new JTextField();
		tfQuantidade.setColumns(10);
		tfQuantidade.setBounds(243, 315, 299, 23);
		contentPane.add(tfQuantidade);

		JLabel lblNewLabel_4_2_1 = new JLabel("Quantidade:");
		lblNewLabel_4_2_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_4_2_1.setBounds(93, 308, 130, 28);
		contentPane.add(lblNewLabel_4_2_1);

		tfValorVenda = new JTextField();
		tfValorVenda.setColumns(10);
		tfValorVenda.setBounds(243, 275, 299, 23);
		contentPane.add(tfValorVenda);

		JLabel lblNewLabel_4_2 = new JLabel("Valor de venda:");
		lblNewLabel_4_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_4_2.setBounds(93, 270, 130, 28);
		contentPane.add(lblNewLabel_4_2);

		tfValorCusto = new JTextField();
		tfValorCusto.setColumns(10);
		tfValorCusto.setBounds(243, 237, 299, 23);
		contentPane.add(tfValorCusto);

		JLabel lblNewLabel_4_1 = new JLabel("Valor de custo:");
		lblNewLabel_4_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_4_1.setBounds(93, 232, 130, 28);
		contentPane.add(lblNewLabel_4_1);

		try {
			MaskFormatter formatter = new MaskFormatter("##/##/####");
			tfDataVencimento = new JFormattedTextField(formatter);
			tfDataVencimento.setBounds(243, 199, 299, 23);
			contentPane.add(tfDataVencimento);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		JLabel lblNewLabel_4 = new JLabel("Data de vencimento:");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_4.setBounds(93, 194, 130, 28);
		contentPane.add(lblNewLabel_4);

		try {
			MaskFormatter formatter = new MaskFormatter("##/##/####");
			tfDataProducao = new JFormattedTextField(formatter);
			tfDataProducao.setBounds(243, 161, 299, 23);
			contentPane.add(tfDataProducao);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		JLabel lblDataDeProduo = new JLabel("Data de produção:");
		lblDataDeProduo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDataDeProduo.setBounds(93, 156, 122, 28);
		contentPane.add(lblDataDeProduo);

		// JComboBox cbMarca = new JComboBox();
		List<Marca> marcasArray = controle_marca.listaMarcas();
		Marca[] marcas = marcasArray.toArray(new Marca[0]);
		cbMarca = new JComboBox(marcas);
		cbMarca.setBounds(243, 121, 299, 27);
		contentPane.add(cbMarca);

		JLabel lblMarca = new JLabel("Marca:");
		lblMarca.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblMarca.setBounds(93, 118, 122, 28);
		contentPane.add(lblMarca);

		tfNome = new JTextField();
		tfNome.setColumns(10);
		tfNome.setBounds(243, 82, 299, 23);
		contentPane.add(tfNome);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNome.setBounds(93, 80, 122, 28);
		contentPane.add(lblNome);

		tfCodigoBarras = new JTextField();
		tfCodigoBarras.setColumns(10);
		tfCodigoBarras.setBounds(243, 42, 299, 23);
		tfCodigoBarras.setText(Integer.toString((int) (Math.random() * 1000000000)));
		contentPane.add(tfCodigoBarras);

		JLabel lblNewLabel = new JLabel("Código de barras:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(93, 42, 122, 28);
		contentPane.add(lblNewLabel);
	}

	private String formataData(String data) {
		String[] dataSplit = data.split("-");
		return dataSplit[2] + "/" + dataSplit[1] + "/" + dataSplit[0];
	}
}
