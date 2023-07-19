package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Controller.ControleMarca;
import Model.Marca;

public class ListaMarca extends JFrame {

	private JPanel contentPane;
	private JTable tbMarcas;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ListaMarca frame = new ListaMarca();
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
	public ListaMarca() {
		Map<Integer, Integer> rowId_marcaId = new HashMap<>();

		setTitle("Marcas");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setBounds(100, 100, 632, 522);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setResizable(false);
		contentPane.setLayout(null);

		// Create an instance of the Menu, but keep it invisible for now
		Menu menu = new Menu();
		menu.setVisible(false);

		// Add a window listener to the ListaRemedio window
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				// When the ListaRemedio window is closed, make the Menu window visible
				menu.setVisible(true);
			}
		});

		JButton btnCadastraMarca = new JButton("Cadastrar marca");
		btnCadastraMarca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CadastraMarca cadastra_marca = new CadastraMarca();
				cadastra_marca.setVisible(true);
				setVisible(false);
			}
		});
		btnCadastraMarca.setBounds(10, 425, 187, 52);
		btnCadastraMarca.setFont(new Font("Tahoma", Font.PLAIN, 18));
		contentPane.add(btnCadastraMarca);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 600, 405);
		contentPane.add(scrollPane);

		JButton btnExcluiMarca = new JButton("Excluir marca");
		btnExcluiMarca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ControleMarca controle_marca = new ControleMarca();
				int id = retornaIdMarcaSelecionada(tbMarcas, rowId_marcaId);
				if (!controle_marca.verificaUsoDeMarca(id)) {
					controle_marca.excluiMarca(id);
					DefaultTableModel model = (DefaultTableModel) tbMarcas.getModel();
					model.setRowCount(0);
					atualizaLista(tbMarcas, rowId_marcaId);
				} else {
					JOptionPane.showMessageDialog(null, "Não é possível excluir uma marca que está sendo usada por um remédio!", "Alerta", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		btnExcluiMarca.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnExcluiMarca.setBounds(443, 425, 167, 52);
		contentPane.add(btnExcluiMarca);

		tbMarcas = new JTable();
		scrollPane.setViewportView(tbMarcas);
		tbMarcas.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"Nome", "CNPJ", "Telefone"
				}) {
			Class[] columnTypes = new Class[] {
					String.class, String.class, String.class
			};
			boolean[] columnEditables = new boolean[] {
					false, false, false
			};

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		tbMarcas.getColumnModel().getColumn(0).setPreferredWidth(100);
		tbMarcas.getColumnModel().getColumn(1).setPreferredWidth(100);
		tbMarcas.getColumnModel().getColumn(2).setPreferredWidth(100);
		atualizaLista(tbMarcas, rowId_marcaId);

		JButton btnEditaMarca = new JButton("Editar marca");
		btnEditaMarca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CadastraMarca cadastra_marca = new CadastraMarca(retornaIdMarcaSelecionada(tbMarcas, rowId_marcaId));
				cadastra_marca.setVisible(true);
				setVisible(false);
			}
		});
		btnEditaMarca.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnEditaMarca.setBounds(226, 425, 187, 52);
		contentPane.add(btnEditaMarca);
	}

	private void atualizaLista(JTable tbMarcas, Map<Integer, Integer> rowId_marcaId) {
		ControleMarca controle_marca = new ControleMarca();
		List<Marca> marcas = controle_marca.listaMarcas();

		int i = -1; // contador do ID linha
		for (Marca marca : marcas) {
			i++;
			DefaultTableModel model = (DefaultTableModel) tbMarcas.getModel();
			Object[] newRow = { marca.getNome(), marca.getCnpj(), marca.getTelefone(), marca.getId() };
			model.addRow(newRow);
			rowId_marcaId.put(i, marca.getId());
		}
	}

	private int retornaIdMarcaSelecionada(JTable tbMarcas, Map<Integer, Integer> rowId_marcaId) {
		int rowIndex = tbMarcas.getSelectedRow();
		return rowId_marcaId.get(rowIndex);
	}
}
