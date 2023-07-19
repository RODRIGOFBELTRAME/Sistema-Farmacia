package Controller;

import java.util.List;

import javax.swing.JTextField;

import Model.ConexaoBanco;
import Model.Marca;

public class ControleMarca {

	ConexaoBanco bd;

	public ControleMarca() {
		try {
			bd = new ConexaoBanco();
		} catch (Exception e) {
			System.out.println("Erro de conexão com o banco de Dados!!");
		}
	}

	public void incluiMarca(JTextField tfNome, JTextField tfCnpj, JTextField tfTelefone) {
		Marca marca = new Marca();
		marca.setNome(tfNome.getText());
		marca.setCnpj(tfCnpj.getText());
		marca.setTelefone(tfTelefone.getText());
		bd.inserirMarca(marca);
	}

	public void editaMarca(int id, JTextField tfNome, JTextField tfCnpj, JTextField tfTelefone) {
		Marca marca = new Marca();
		marca.setId(id);
		marca.setNome(tfNome.getText());
		marca.setCnpj(tfCnpj.getText());
		marca.setTelefone(tfTelefone.getText());
		bd.editarMarca(marca);
	}

	public Boolean verificaUsoDeMarca(int id) {
		return bd.verificaUsoDeMarca(id);
	}

	public void excluiMarca(int id) {
		bd.deleteMarcas(id);
	}

	public List<Marca> listaMarcas() {
		return bd.listarMarcas();
	}

	public Marca pegaMarcaPorId(int id) {
		return bd.pegaMarcaPorId(id);
	}
}
