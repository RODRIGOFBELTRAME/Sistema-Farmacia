package Model;

import java.math.BigDecimal;
import java.util.Date;

public class Venda {
	private int id;
	private Remedio[] remedios;
	private MetodoPagamento metodo_pagamento;
	private Date data_venda;
	//private BigDecimal valor_total;
	
	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Remedio[] getRemedios() {
        return remedios;
    }

    public void setRemedios(Remedio[] remedios) {
        this.remedios = remedios;
    }

    public MetodoPagamento getMetodoPagamento() {
        return metodo_pagamento;
    }

    public void setMetodoPagamento(MetodoPagamento metodo_pagamento) {
        this.metodo_pagamento = metodo_pagamento;
    }

    public Date getDataVenda() {
        return data_venda;
    }

    public void setDataVenda(Date data_venda) {
        this.data_venda = data_venda;
    }

    public BigDecimal getValorTotal() {
    	BigDecimal valor_total = BigDecimal.ZERO;
    	for (Remedio remedio : this.remedios) {
    		if (remedio == null) {continue;}; //ignorando objetos nulos
    		BigDecimal valor_total_remedio = remedio.getValorVenda().multiply(BigDecimal.valueOf(remedio.getQuantidade()));
    		valor_total = valor_total.add(valor_total_remedio);
    	}
        return valor_total;
    }

    //public void setValorTotal(BigDecimal valor_total) {
    //    this.valor_total = valor_total;
    //}
}
