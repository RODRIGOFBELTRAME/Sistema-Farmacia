package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import Model.MetodoPagamento;
import Model.Remedio;

import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import Controller.ControleRemedio;
import Controller.ControleVenda;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import javax.swing.JFormattedTextField;

public class CadastraVenda extends JFrame {

	private JPanel contentPane;
	private JTable tbRemedios;
	private JFormattedTextField tfDataVenda;
	private JComboBox cbMetodoPagamento;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadastraVenda frame = new CadastraVenda();
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
	public CadastraVenda() {
		Map<Integer, Integer> rowId_remedioId = new HashMap<>();
		ControleVenda controle_venda = new ControleVenda();
		
		setTitle("Cadastro de venda");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 636, 481);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controle_venda.incluiVenda(cbMetodoPagamento, tfDataVenda, tbRemedios, rowId_remedioId);
				ListaVenda lista_venda = new ListaVenda();
				lista_venda.setVisible(true);
				setVisible(false);
			}
		});
		btnCadastrar.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnCadastrar.setBounds(207, 392, 196, 42);
		contentPane.add(btnCadastrar);
		
		JLabel lblDataDeVenda = new JLabel("Data de venda:");
		lblDataDeVenda.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDataDeVenda.setBounds(63, 78, 122, 28);
		contentPane.add(lblDataDeVenda);
		
		JLabel lblNome_1 = new JLabel("Método de pagamento:");
		lblNome_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNome_1.setBounds(63, 40, 153, 28);
		contentPane.add(lblNome_1);
		
		JLabel lblRemdios = new JLabel("Remédios");
		lblRemdios.setHorizontalAlignment(SwingConstants.CENTER);
		lblRemdios.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRemdios.setBounds(10, 127, 602, 28);
		contentPane.add(lblRemdios);
		
		cbMetodoPagamento = new JComboBox();
		cbMetodoPagamento.setModel(new DefaultComboBoxModel(MetodoPagamento.values()));
		cbMetodoPagamento.setBounds(217, 46, 299, 21);
		contentPane.add(cbMetodoPagamento);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 165, 602, 217);
		contentPane.add(scrollPane);
		
		tbRemedios = new JTable();
		tbRemedios.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Quantidade", "Nome", "Marca", "C\u00F3digo de barras", "Valor de venda", "Estoque"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class, String.class, String.class, Double.class, Integer.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		scrollPane.setViewportView(tbRemedios);
		atualizaLista(tbRemedios, rowId_remedioId);
		
		try {
			MaskFormatter formatter = new MaskFormatter("##/##/####");
			tfDataVenda = new JFormattedTextField(formatter);
			tfDataVenda.setBounds(217, 85, 299, 19);
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String formattedDate = dateFormat.format(new Date());
			tfDataVenda.setText(formattedDate);
			contentPane.add(tfDataVenda);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
	}
	
	private void atualizaLista(JTable tbRemedios, Map<Integer, Integer> rowId_remedioId) {
		ControleRemedio controle_remedio = new ControleRemedio();
		List<Remedio> remedios = controle_remedio.listaRemedios();
		
		int i = -1; //contador do ID linha
		for (Remedio remedio : remedios) {
			i++;
			DefaultTableModel model = (DefaultTableModel) tbRemedios.getModel();
			Object[] newRow = {0, remedio.getNome(), remedio.getMarca().getNome(), remedio.getCodigoBarra(), remedio.getValorVenda(), remedio.getQuantidade()};
			model.addRow(newRow);
			rowId_remedioId.put(i, remedio.getId());
		}
	}
}
