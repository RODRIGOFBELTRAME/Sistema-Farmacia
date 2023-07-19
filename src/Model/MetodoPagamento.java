package Model;

public enum MetodoPagamento {
	BOLETO(1),
	CARTAO_DEBITO(2),
	CARTAO_CREDITO(3),
	DINHEIRO(4);
	
	private final int id;
	
	MetodoPagamento(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

	public static MetodoPagamento getMetodoPagamentoById(int id) {
		for (MetodoPagamento metodoPagamento : MetodoPagamento.values()) {
			if (metodoPagamento.getId() == id) {
				return metodoPagamento;
			}
		}
		throw new IllegalArgumentException("Invalid MetodoPagamento ID: " + id);
	}
}
