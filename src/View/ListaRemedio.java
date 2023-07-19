package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Controller.ControleRemedio;
import Model.Remedio;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionEvent;

public class ListaRemedio extends JFrame {

	private JPanel contentPane;
	private JTable tbRemedios;
	private JButton btnEditaRemedio;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ListaRemedio frame = new ListaRemedio();
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
	public ListaRemedio() {

		Map<Integer, Integer> rowId_remedioId = new HashMap<>();

		setTitle("Remédios");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setBounds(100, 100, 803, 508);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setResizable(false);
		contentPane.setLayout(null);

		Menu menu = new Menu();
		menu.setVisible(false);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				menu.setVisible(true);
			}
		});

		JButton btnCadastraRemedio = new JButton("Cadastrar remédio");
		btnCadastraRemedio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CadastraRemedio cadastra_remedio = new CadastraRemedio();
				cadastra_remedio.setVisible(true);
				setVisible(false);
			}
		});
		btnCadastraRemedio.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnCadastraRemedio.setBounds(10, 419, 196, 42);
		contentPane.add(btnCadastraRemedio);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 772, 400);
		contentPane.add(scrollPane);

		JButton btnExcluiRemedio = new JButton("Excluir Remédio");
		btnExcluiRemedio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ControleRemedio controle_remedio = new ControleRemedio();

				int id = retornaIdRemedioSelecionado(tbRemedios, rowId_remedioId);
				if (!controle_remedio.verificaUsoDeRemedio(id)) {
					controle_remedio.excluiRemedio(id);
					DefaultTableModel model = (DefaultTableModel) tbRemedios.getModel();
					model.setRowCount(0);
					atualizaLista(tbRemedios, rowId_remedioId);
				} else {
					JOptionPane.showMessageDialog(null, "Não é possível excluir um remédio que está atrelado a uma venda!", "Alerta", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnExcluiRemedio.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnExcluiRemedio.setBounds(616, 419, 166, 42);
		contentPane.add(btnExcluiRemedio);

		tbRemedios = new JTable();
		tbRemedios.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"Nome", "Marca", "C\u00F3digo de barras", "Data de produ\u00E7\u00E3o", "Validade",
						"Valor de custo", "Valor de venda", "Quantidade"
				}) {
			Class[] columnTypes = new Class[] {
					String.class, String.class, String.class, String.class, String.class, Double.class, Double.class,
					Integer.class
			};

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] {
					false, false, false, false, false, false, false, false
			};

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane.setViewportView(tbRemedios);
		atualizaLista(tbRemedios, rowId_remedioId);

		btnEditaRemedio = new JButton("Editar Remédios");
		btnEditaRemedio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CadastraRemedio cadastra_remedio = new CadastraRemedio(
						retornaIdRemedioSelecionado(tbRemedios, rowId_remedioId));
				cadastra_remedio.setVisible(true);
				setVisible(false);
			}
		});
		btnEditaRemedio.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnEditaRemedio.setBounds(325, 419, 187, 42);
		contentPane.add(btnEditaRemedio);
	}

	private void atualizaLista(JTable tbRemedios, Map<Integer, Integer> rowId_remedioId) {
		ControleRemedio controle_remedio = new ControleRemedio();
		List<Remedio> remedios = controle_remedio.listaRemedios();

		int i = -1; // contador do ID linha
		for (Remedio remedio : remedios) {
			i++;
			DefaultTableModel model = (DefaultTableModel) tbRemedios.getModel();
			Object[] newRow = { remedio.getNome(), remedio.getMarca().getNome(), remedio.getCodigoBarra(),
					remedio.getDataProducao().toString(), remedio.getDataValidade().toString(),
					remedio.getValorCusto(), remedio.getValorVenda(), remedio.getQuantidade() };
			model.addRow(newRow);
			rowId_remedioId.put(i, remedio.getId());
		}
	}

	private int retornaIdRemedioSelecionado(JTable tbRemedios, Map<Integer, Integer> rowId_remedioId) {
		int rowIndex = tbRemedios.getSelectedRow();
		return rowId_remedioId.get(rowIndex);
	}
}
