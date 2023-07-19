package Model;

public class Marca {
	private int id;
	private String nome;
	private String cnpj;
	private String telefone;
	
	public Marca() {
	}
	
	public Marca(int id, String nome, String cnpj, String telefone) {
        this.id = id;
        this.nome = nome;
        this.cnpj = cnpj;
        this.telefone = telefone;
    }
	
	public String toString() { //alterando método toString para a ComboBox aparecer o nome da marca
        return nome;
    }
	
	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
