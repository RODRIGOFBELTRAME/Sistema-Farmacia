package Controller;

import java.text.SimpleDateFormat;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Model.ConexaoBanco;
import Model.MetodoPagamento;
import Model.Remedio;
import Model.Venda;

public class ControleVenda {

	ConexaoBanco bd;

	public ControleVenda() {
		try {
			bd = new ConexaoBanco();
		} catch (Exception e) {
			System.out.println("Erro de conexão com o banco de Dados!!");
		}
	}

	public void incluiVenda(JComboBox cbMetodoPagamento, JTextField tfDataVenda, JTable tbRemedios,
			Map<Integer, Integer> rowId_remedioId) {
		Venda venda = new Venda();
		venda.setMetodoPagamento(MetodoPagamento.class.cast(cbMetodoPagamento.getSelectedItem()));
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {
			venda.setDataVenda(dateFormat.parse(tfDataVenda.getText()));
		} catch (ParseException e) {
			System.out.println("Erro ao converter data: " + e.getMessage());
		}
		DefaultTableModel model = (DefaultTableModel) tbRemedios.getModel();

		int rowCount = model.getRowCount();

		Remedio[] remedios_vendidos = new Remedio[rowCount];

		for (int row = 0; row < rowCount; row++) {
			Object quantidadeObj = model.getValueAt(row, 0); // Coluna quantidade
			Object valorVendaObj = model.getValueAt(row, 4); // Coluna Valor de Venda
			int quantidade = Integer.parseInt(quantidadeObj.toString());
			BigDecimal valorVenda = new BigDecimal(valorVendaObj.toString());
			if (quantidade <= 0) {
				continue;
			}
			;
			Remedio remedio = new Remedio();
			remedio.setId(rowId_remedioId.get(row));
			remedio.setQuantidade(quantidade);
			remedio.setValorVenda(valorVenda);
			remedios_vendidos[row] = remedio;
		}
		venda.setRemedios(remedios_vendidos);
		bd.inserirVenda(venda);
	}

	public void deletaVenda(int id) {
		bd.deleteVendaRemedios(id);
	}

	public List<Venda> listaVendas() {
		return bd.listarVendas();
	}
}
