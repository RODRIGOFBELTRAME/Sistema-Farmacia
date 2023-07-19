package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConexaoBanco {
	private Connection conexao;

	public ConexaoBanco() throws Exception {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			this.conexao = DriverManager.getConnection("jdbc:mysql://localhost/farmacia", "root", "");
		} catch (Exception e) {
			throw new Exception("Ocorreu um erro na conexão");
		}
	}

	public void fecharConexao() {
		try {
			this.conexao.close();
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro no encerramento da conexão");
		}
	}

	public void liberar(PreparedStatement ps) {
		try {
			if (ps != null) {
				ps.close();
			}
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro na liberação do cursor");
		}
	}

	public void inserirRemedio(Remedio remedio) {
		PreparedStatement psInsert = null;
		try {
			psInsert = conexao.prepareStatement("INSERT INTO remedios "
					+ "(codigo_barras, nome, id_marca, data_producao, data_validade, valor_custo, valor_venda, quantidade) "
					+ "VALUES (?,?,?,?,?,?,?,?) ");
			psInsert.setString(1, remedio.getCodigoBarra());
			psInsert.setString(2, remedio.getNome());
			psInsert.setInt(3, remedio.getMarca().getId());
			psInsert.setDate(4, new java.sql.Date(remedio.getDataProducao().getTime()));
			psInsert.setDate(5, new java.sql.Date(remedio.getDataValidade().getTime()));
			psInsert.setBigDecimal(6, remedio.getValorCusto());
			psInsert.setBigDecimal(7, remedio.getValorVenda());
			psInsert.setInt(8, remedio.getQuantidade());
			psInsert.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro na inserção do remédio: " + e.getMessage());
		} finally {
			this.liberar(psInsert);
		}
	}

	public void editarRemedio(Remedio remedio) {
		PreparedStatement psInsert = null;
		try {
			psInsert = conexao.prepareStatement("UPDATE remedios "
					+ "SET codigo_barras = ?, nome = ?, id_marca = ?, data_producao= ?, data_validade = ?, valor_custo = ?, valor_venda = ?, quantidade = ? "
					+ "WHERE id = ? ");
			psInsert.setString(1, remedio.getCodigoBarra());
			psInsert.setString(2, remedio.getNome());
			psInsert.setInt(3, remedio.getMarca().getId());
			psInsert.setDate(4, new java.sql.Date(remedio.getDataProducao().getTime()));
			psInsert.setDate(5, new java.sql.Date(remedio.getDataValidade().getTime()));
			psInsert.setBigDecimal(6, remedio.getValorCusto());
			psInsert.setBigDecimal(7, remedio.getValorVenda());
			psInsert.setInt(8, remedio.getQuantidade());
			psInsert.setInt(9, remedio.getId());
			psInsert.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro na edição do remédio: " + e.getMessage());
		} finally {
			this.liberar(psInsert);
		}
	}

	public List<Remedio> listarRemedios() {
		List<Remedio> remedios = new ArrayList<Remedio>();
		PreparedStatement psSelect = null;
		try {
			psSelect = conexao.prepareStatement("SELECT * "
					+ "FROM remedios r "
					+ "LEFT JOIN marcas m "
					+ "ON r.id_marca=m.id "
					+ "Order by r.id");
			ResultSet rs = psSelect.executeQuery();
			while (rs.next()) {
				remedios.add(new Remedio(rs.getInt("r.id"),
						rs.getString("codigo_barras"),
						rs.getString("r.nome"),
						new Marca(rs.getInt("id_marca"), rs.getString("m.nome"), rs.getString("cnpj"),
								rs.getString("telefone")),
						rs.getDate("data_producao"),
						rs.getDate("data_validade"),
						rs.getBigDecimal("valor_custo"),
						rs.getBigDecimal("valor_venda"),
						rs.getInt("quantidade")));
			}
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro na listagem dos remédios: " + e.getMessage());
		} finally {
			this.liberar(psSelect);
		}
		return remedios;
	}

	public Remedio pegaRemedioPorId(int id) {
		Remedio remedio = new Remedio();
		remedio.setId(id);
		PreparedStatement psSelect = null;
		try {
			psSelect = conexao.prepareStatement("SELECT * "
					+ "FROM remedios r "
					+ "LEFT JOIN marcas m "
					+ "ON r.id_marca=m.id "
					+ "WHERE r.id = ? ");
			psSelect.setInt(1, remedio.getId());
			ResultSet rs = psSelect.executeQuery();
			while (rs.next()) {
				remedio.setNome(rs.getString("r.nome"));
				remedio.setCodigoBarra(rs.getString("codigo_barras"));
				remedio.setMarca(new Marca(rs.getInt("id_marca"), rs.getString("m.nome"), rs.getString("cnpj"),
						rs.getString("telefone")));
				remedio.setDataProducao(rs.getDate("data_producao"));
				remedio.setDataValidade(rs.getDate("data_validade"));
				remedio.setValorCusto(rs.getBigDecimal("valor_custo"));
				remedio.setValorVenda(rs.getBigDecimal("valor_venda"));
				remedio.setQuantidade(rs.getInt("quantidade"));
			}
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro para pegar o remédio por ID: " + e.getMessage());
		} finally {
			this.liberar(psSelect);
		}
		return remedio;
	}

	public void deleteRemedios(int id) {
		PreparedStatement psInsert = null;
		try {
			psInsert = conexao.prepareStatement("DELETE FROM remedios "
					+ "WHERE id = ? ");
			psInsert.setInt(1, id);
			psInsert.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro em deletar remédios de uma venda: " + e.getMessage());
		} finally {
			this.liberar(psInsert);
		}
	}

	public Boolean verificaUsoDeRemedio(int id) {
		PreparedStatement psSelect = null;
		try {
			psSelect = conexao.prepareStatement("SELECT * "
					+ "FROM venda_remedios "
					+ "WHERE id_remedio = ? ");
			psSelect.setInt(1, id);
			ResultSet rs = psSelect.executeQuery();
			while (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro para verificar a existência do remédio: " + e.getMessage());
		} finally {
			this.liberar(psSelect);
		}
		return false;
	}

	public void inserirMarca(Marca marca) {
		PreparedStatement psInsert = null;
		try {
			psInsert = conexao.prepareStatement("INSERT INTO marcas "
					+ "(nome, cnpj, telefone) "
					+ "VALUES (?,?,?) ");
			psInsert.setString(1, marca.getNome());
			psInsert.setString(2, marca.getCnpj());
			psInsert.setString(3, marca.getTelefone());
			psInsert.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro na inserção da marca: " + e.getMessage());
		} finally {
			this.liberar(psInsert);
		}
	}

	public void editarMarca(Marca marca) {
		PreparedStatement psInsert = null;
		try {
			psInsert = conexao.prepareStatement("UPDATE marcas "
					+ "SET nome = ?, cnpj = ?, telefone = ? "
					+ "WHERE id = ? ");
			psInsert.setString(1, marca.getNome());
			psInsert.setString(2, marca.getCnpj());
			psInsert.setString(3, marca.getTelefone());
			psInsert.setInt(4, marca.getId());
			psInsert.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro na edição da marca: " + e.getMessage());
		} finally {
			this.liberar(psInsert);
		}
	}

	public void deleteMarcas(int id) {
		PreparedStatement psInsert = null;
		try {
			psInsert = conexao.prepareStatement("DELETE FROM marcas "
					+ "WHERE id = ? ");
			psInsert.setInt(1, id);
			psInsert.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro em deletar marcas de uma venda: " + e.getMessage());
		} finally {
			this.liberar(psInsert);
		}
	}

	public List<Marca> listarMarcas() {
		List<Marca> marcas = new ArrayList<Marca>();
		PreparedStatement psSelect = null;
		try {
			psSelect = conexao.prepareStatement("SELECT * "
					+ "FROM marcas "
					+ "Order by id");
			ResultSet rs = psSelect.executeQuery();
			while (rs.next()) {
				marcas.add(new Marca(rs.getInt("id"),
						rs.getString("nome"),
						rs.getString("cnpj"),
						rs.getString("telefone")));
			}
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro na listagem das marcas: " + e.getMessage());
		} finally {
			this.liberar(psSelect);
		}
		return marcas;
	}

	public Marca pegaMarcaPorId(int id) {
		Marca marca = new Marca();
		marca.setId(id);
		PreparedStatement psSelect = null;
		try {
			psSelect = conexao.prepareStatement("SELECT * "
					+ "FROM marcas "
					+ "WHERE id = ? ");
			psSelect.setInt(1, marca.getId());
			ResultSet rs = psSelect.executeQuery();
			while (rs.next()) {
				marca.setNome(rs.getString("nome"));
				marca.setCnpj(rs.getString("cnpj"));
				marca.setTelefone(rs.getString("telefone"));
			}
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro para pegar a marca por ID: " + e.getMessage());
		} finally {
			this.liberar(psSelect);
		}
		return marca;
	}

	public Boolean verificaUsoDeMarca(int id) {
		PreparedStatement psSelect = null;
		try {
			psSelect = conexao.prepareStatement("SELECT * "
					+ "FROM remedios "
					+ "WHERE id_marca = ? ");
			psSelect.setInt(1, id);
			ResultSet rs = psSelect.executeQuery();
			while (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro para verificar a existência da marca: " + e.getMessage());
		} finally {
			this.liberar(psSelect);
		}
		return false;
	}

	public void inserirVenda(Venda venda) {
		PreparedStatement psInsert = null;
		try {
			psInsert = conexao.prepareStatement("INSERT INTO vendas "
					+ "(id_metodo_pagamento, data_venda, valor_total) "
					+ "VALUES (?,?,?) ", Statement.RETURN_GENERATED_KEYS);
			psInsert.setInt(1, venda.getMetodoPagamento().getId());
			psInsert.setDate(2, new java.sql.Date(venda.getDataVenda().getTime()));
			psInsert.setBigDecimal(3, venda.getValorTotal());
			psInsert.executeUpdate();
			ResultSet rs = psInsert.getGeneratedKeys();
			int generatedId = -1;
			if (rs.next()) {
				generatedId = rs.getInt(1);
			}
			if (generatedId > -1) {
				for (Remedio remedio : venda.getRemedios()) {
					if (remedio == null) {
						continue;
					}
					;
					psInsert = null;
					psInsert = conexao.prepareStatement("INSERT INTO venda_remedios "
							+ "(id_venda, id_remedio, valor_venda, quantidade) "
							+ "VALUES (?,?,?,?) ");
					psInsert.setInt(1, generatedId);
					psInsert.setInt(2, remedio.getId());
					psInsert.setBigDecimal(3, remedio.getValorVenda()); // valor unitario do remedio
					psInsert.setInt(4, remedio.getQuantidade());
					psInsert.executeUpdate();
					psInsert = null;
					psInsert = conexao.prepareStatement("UPDATE remedios "
							+ "SET quantidade = quantidade - ? "
							+ "WHERE id = ? ");

					psInsert.setInt(1, remedio.getQuantidade());
					psInsert.setInt(2, remedio.getId());
					psInsert.executeUpdate();
				}
			}

		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro na inserção da venda: " + e.getMessage());
		} finally {
			this.liberar(psInsert);
		}
	}

	public void editarVenda(Venda venda) {
		PreparedStatement psInsert = null;
		try {
			psInsert = conexao.prepareStatement("UPDATE vendas "
					+ "SET id_metodo_pagamento = ?, data_venda = ?, valor_total = ? "
					+ "WHERE id = ? ");
			psInsert.setInt(1, venda.getMetodoPagamento().getId());
			psInsert.setDate(2, new java.sql.Date(venda.getDataVenda().getTime()));
			psInsert.setBigDecimal(3, venda.getValorTotal());
			psInsert.setInt(4, venda.getId());
			psInsert.executeUpdate();
			deleteVendaRemedios(venda.getId());
			for (Remedio remedio : venda.getRemedios()) {
				if (remedio == null) {
					continue;
				}
				;
				psInsert = null;
				psInsert = conexao.prepareStatement("INSERT INTO venda_remedios "
						+ "(id_venda, id_remedio, valor_venda, quantidade) "
						+ "VALUES (?,?,?,?) ");
				psInsert.setInt(1, venda.getId());
				psInsert.setInt(2, remedio.getId());
				psInsert.setBigDecimal(3, remedio.getValorVenda()); // valor unitario do remedio
				psInsert.setInt(4, remedio.getQuantidade());
				psInsert.executeUpdate();
			}
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro na inserção da venda: " + e.getMessage());
		} finally {
			this.liberar(psInsert);
		}
	}

	public void deleteVendaRemedios(int id_venda) {
		PreparedStatement psInsert = null;
		try {
			psInsert = conexao.prepareStatement("DELETE FROM venda_remedios "
					+ "WHERE id_venda = ? ");
			psInsert.setInt(1, id_venda);
			psInsert.executeUpdate();
			psInsert = null;
			psInsert = conexao.prepareStatement("DELETE FROM vendas "
					+ "WHERE id = ? ");
			psInsert.setInt(1, id_venda);
			psInsert.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro em deletar remédios de uma venda: " + e.getMessage());
		} finally {
			this.liberar(psInsert);
		}
	}

	public List<Venda> listarVendas() {
		List<Venda> vendas = new ArrayList<Venda>();
		PreparedStatement psSelect = null;
		try {
			psSelect = conexao.prepareStatement("SELECT * "
					+ "FROM vendas "
					+ "Order by id");
			ResultSet rs = psSelect.executeQuery();
			while (rs.next()) {
				Venda venda = new Venda();
				venda.setId(rs.getInt("id"));
				venda.setMetodoPagamento(MetodoPagamento.getMetodoPagamentoById(rs.getInt("id_metodo_pagamento")));
				venda.setDataVenda(rs.getDate("data_venda"));
				vendas.add(venda);
			}
			for (Venda venda : vendas) {
				psSelect = null;
				rs = null;
				psSelect = conexao.prepareStatement("SELECT * "
						+ "FROM venda_remedios "
						+ "WHERE id_venda = ?");
				psSelect.setInt(1, venda.getId());
				rs = psSelect.executeQuery();
				List<Remedio> remedios = new ArrayList<>();
				while (rs.next()) {
					Remedio remedio = new Remedio();
					remedio.setId(rs.getInt("id_remedio"));
					remedio.setValorVenda(rs.getBigDecimal("valor_venda"));
					remedio.setQuantidade(rs.getInt("quantidade"));
					remedios.add(remedio);
				}

				venda.setRemedios(remedios.toArray(new Remedio[0]));
			}
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro na listagem das vendas: " + e.getMessage());
		} finally {
			this.liberar(psSelect);
		}
		return vendas;
	}
}
