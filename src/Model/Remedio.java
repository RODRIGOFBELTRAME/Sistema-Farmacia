package Model;

import java.math.BigDecimal;
import java.util.Date;

public class Remedio {
    private int id;
    private String codigo_barras;
    private String nome;
    private Marca marca;
    private Date data_producao;
    private Date data_validade;
    private BigDecimal valor_custo;
    private BigDecimal valor_venda;
    private int quantidade;

    public Remedio() {
    }

    public Remedio(int id, String codigo_barras, String nome, Marca marca, Date data_producao, Date data_validade,
            BigDecimal valor_custo, BigDecimal valor_venda, int quantidade) {
        this.id = id;
        this.codigo_barras = codigo_barras;
        this.nome = nome;
        this.marca = marca;
        this.data_producao = data_producao;
        this.data_validade = data_validade;
        this.valor_custo = valor_custo;
        this.valor_venda = valor_venda;
        this.quantidade = quantidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigoBarra() {
        return codigo_barras;
    }

    public void setCodigoBarra(String codigo_barras) {
        this.codigo_barras = codigo_barras;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(int id, String nome, String cnpj, String telefone) {
        Marca marca = new Marca(id, nome, cnpj, telefone);
        this.marca = marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public Date getDataProducao() {
        return data_producao;
    }

    public void setDataProducao(Date data_producao) {
        this.data_producao = data_producao;
    }

    public Date getDataValidade() {
        return data_validade;
    }

    public void setDataValidade(Date data_validade) {
        this.data_validade = data_validade;
    }

    public BigDecimal getValorCusto() {
        return valor_custo;
    }

    public void setValorCusto(BigDecimal valor_custo) {
        this.valor_custo = valor_custo;
    }

    public BigDecimal getValorVenda() {
        return valor_venda;
    }

    public void setValorVenda(BigDecimal valor_venda) {
        this.valor_venda = valor_venda;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
