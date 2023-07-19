package Controller;

import java.math.BigDecimal;

import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import Model.ConexaoBanco;
import Model.Marca;
import Model.Remedio;

import java.util.List;

public class ControleRemedio {

	ConexaoBanco bd;

	public ControleRemedio() {
		try {
			bd = new ConexaoBanco();
		} catch (Exception e) {
			System.out.println("Erro de conexão com o banco de Dados!!");
		}
	}

	public void incluiRemedio(JTextField tfCodigoBarra, JTextField tfNome, Marca marca,
			JFormattedTextField tfDataProducao, JFormattedTextField tfDataValidade, JTextField tfValorCusto,
			JTextField tfValorVenda, JTextField tfQuantidade) {
		Remedio remedio = new Remedio();
		remedio.setCodigoBarra(tfCodigoBarra.getText());
		remedio.setNome(tfNome.getText());
		remedio.setMarca(marca);
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {
			remedio.setDataProducao(dateFormat.parse(tfDataProducao.getText()));
			remedio.setDataValidade(dateFormat.parse(tfDataValidade.getText()));
		} catch (ParseException e) {
			System.out.println("Erro ao converter data: " + e.getMessage());
		}
		remedio.setValorCusto(new BigDecimal(tfValorCusto.getText()));
		remedio.setValorVenda(new BigDecimal(tfValorVenda.getText()));
		remedio.setQuantidade(Integer.parseInt(tfQuantidade.getText()));
		bd.inserirRemedio(remedio);
	}

	public void editaRemedio(int id, JTextField tfCodigoBarra, JTextField tfNome, Marca marca,
			JFormattedTextField tfDataProducao, JFormattedTextField tfDataValidade, JTextField tfValorCusto,
			JTextField tfValorVenda, JTextField tfQuantidade) {
		Remedio remedio = new Remedio();
		remedio.setId(id);
		remedio.setCodigoBarra(tfCodigoBarra.getText());
		remedio.setNome(tfNome.getText());
		remedio.setMarca(marca);
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {
			remedio.setDataProducao(dateFormat.parse(tfDataProducao.getText()));
			remedio.setDataValidade(dateFormat.parse(tfDataValidade.getText()));
		} catch (ParseException e) {
			System.out.println("Erro ao converter data: " + e.getMessage());
		}
		remedio.setValorCusto(new BigDecimal(tfValorCusto.getText()));
		remedio.setValorVenda(new BigDecimal(tfValorVenda.getText()));
		remedio.setQuantidade(Integer.parseInt(tfQuantidade.getText()));
		bd.editarRemedio(remedio);
	}

	public Boolean verificaUsoDeRemedio(int id) {
		return bd.verificaUsoDeRemedio(id);
	}

	public void excluiRemedio(int id) {
		bd.deleteRemedios(id);
	}

	public List<Remedio> listaRemedios() {
		return bd.listarRemedios();
	}

	public Remedio pegaRemedioPorId(int id) {
		return bd.pegaRemedioPorId(id);
	}
}
