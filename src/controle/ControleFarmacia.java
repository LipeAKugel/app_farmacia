package controle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import modelo.*;

/**
 * A classe ControleFarmacia é responsável por gerenciar as operações da farmácia,
 * como acesso aos produtos e filiais, busca, adição, remoção e edição de produtos e filiais.
 * @author Felipe Amorim
 * @since 2023
 * @version 1.0
 */
public class ControleFarmacia {
	/**
	 * Construtor da classe ControleFarmacia.
	 * Inicializa a instância da farmácia e preenche com alguns dados.
	 */
	private Farmacia farmacia = new Farmacia("Gammapharma",
											 "04.974.867/0001-07");
	
	// Construtor.
	public ControleFarmacia() {
		farmacia.fillWithSomeData();
	}
	
	// Gets e sets.
	public Farmacia getFarmacia() {
		return farmacia;
	}
	
	public void setFarmacia(Farmacia farmacia) {
		this.farmacia = farmacia;
	}
	
	// Métodos.
	/**
	 * Obtém um array de todos os produtos cadastrados na farmácia.
	 *
	 * @return Um array de produtos cadastrados na farmácia.
	 */
	public Produto[] getProdutos() {
		// Retorna um array de produtos.
		ArrayList<Produto> lista = new ArrayList<Produto>(); 
		lista = farmacia.produtosCadastrados();
		
		Produto[] array = lista.toArray(new Produto[0]);
		
		return array;
	}
	/**
	 * Busca produtos na farmácia filtrados pelo nome.
	 *
	 * @param nome O nome do produto a ser buscado.
	 * @return Um array de produtos filtrados pelo nome.
	 */
	public Produto[] buscaProdutos(String nome) {
		// Retorna uma lista de produtos filtrados pelo nome.
		ArrayList<Produto> lista = new ArrayList<Produto>();
		lista = farmacia.buscarProdutos(nome);
		
		Produto[] array = lista.toArray(new Produto[0]);
		return array;
	}
	/**
	 * Obtém um array de todas as filiais da farmácia.
	 *
	 * @return Um array de filiais da farmácia.
	 */
	public Filial[] getFiliais() {
		// Retorna um array de filiais.
		ArrayList<Filial> lista = new ArrayList<Filial>();
		lista = farmacia.getlistaFiliais();
		
		Filial[] array = lista.toArray(new Filial[0]);
		
		return array;
	}
	/**
	 * Busca filiais da farmácia filtradas pela cidade.
	 *
	 * @param cidade A cidade das filiais a serem buscadas.
	 * @return Um array de filiais filtradas pela cidade.
	 */
	public Filial[] buscaFilial(String cidade) {
		// Retorna uma lista de filiais filtradas pela cidade.
		ArrayList<Filial> lista = new ArrayList<Filial>();
		lista = farmacia.buscarFiliais(cidade);
		
		Filial[] array = lista.toArray(new Filial[0]);
		return array;
	}
	/**
	 * Salva um produto na farmácia com base nos dados fornecidos.
	 *
	 * @param dados Os dados do produto a serem salvos.
	 * @param pos A posição do produto na lista de produtos.
	 * @param op A operação a ser realizada (1 - Cadastro de produto novo, 3 - Salvar produto existente).
	 * @return true se o produto foi salvo com sucesso, caso contrário, false.
	 */
	public boolean salvarProduto(String[] dados, int pos, int op) {
		// Salva um produto na farmácia a partir dos dados recebidos.
		Produto prodNovo;
		Filial filial = getFiliais()[Integer.parseInt(dados[1])];
		boolean salvo;
		
		Date validade = new Date();
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		try {
			validade = formato.parse(dados[7]);
		} catch (ParseException e) {
			return false;
		}
		
		// Salve o produto de acordo com a categoria.
		switch (Integer.parseInt(dados[0])) {
		
			case 0: // Medicamento.
				prodNovo = new Medicamento(dados[2], dados[6], dados[3], validade,
										   Double.parseDouble(dados[4]), Double.parseDouble(dados[5]),
										   dados[8], dados[9], dados[9], dados[10]);
				
			break;
			
			case 1: // Suplemento.
				prodNovo = new Suplemento(dados[2], dados[6], dados[3], validade,
							   			  Double.parseDouble(dados[4]), Double.parseDouble(dados[5]),
							   			  dados[8], dados[9], dados[10], dados[11], dados[12]);
				
			break;
			
			case 2: // Cosmético.
				prodNovo = new Cosmetico(dados[2], dados[6], dados[3], validade,
							   			 Double.parseDouble(dados[4]), Double.parseDouble(dados[5]),
							   			 dados[8], dados[9], dados[10]);
				
			break;
			
			default:
				// Caso não seja nenhuma categoria, não salve.
				return false;
		}
		
		// Salvar no sistema.
		if (op == 1) { // Cadastro de produto novo.
			salvo = filial.addProduto(prodNovo);
		} else if (op == 3) { // Salvar produto existente.
			// Achar a posição do produto na sua filial.
			int posNaFilial = filial.getlistaProdutos().indexOf(getProdutos()[pos]);
			filial.getlistaProdutos().set(posNaFilial, prodNovo);
			salvo = true;
		} else {
			salvo = false;
		}
		
		return salvo;
	}
	/**
	 * Salva uma filial na farmácia com base nos dados fornecidos.
	 *
	 * @param dados Os dados da filial a serem salvos.
	 * @param pos A posição da filial na lista de filiais.
	 * @param op A operação a ser realizada (2 - Cadastro de filial nova, 4 - Edição de filial já existente).
	 * @return true se a filial foi salva com sucesso, caso contrário, false.
	 */
	public boolean salvarFilial(String[] dados, int pos, int op) {
		// Salva uma filial na farmácia a partir dos dados recebidos.
		Endereco end = new Endereco(dados[1], dados[6], dados[2], dados[5], dados[4]);
		Filial filialNova = new Filial(dados[0], dados[3], end);
		boolean salvo;
		
		// Salvar no sistema.
		if (op == 2) { // Cadastro de filial nova.
			salvo = farmacia.addFilial(filialNova);
		} else if (op == 4) { // Edição de filial já existente.
			// Guarde os produtos na filial nova.
			filialNova.setlistaProdutos(getFiliais()[pos].getlistaProdutos());
			farmacia.getlistaFiliais().set(pos, filialNova);
			salvo = true;
		} else {
			salvo = false;
		}
		
		return salvo;
	}
	/**
	 * Remove um produto específico da farmácia.
	 *
	 * @param pos A posição do produto na lista de produtos.
	 * @return true se o produto foi removido com sucesso, caso contrário, false.
	 */
	public boolean removerProduto(int pos) {
		// Remove um produto específico da farmácia.
		boolean removido;
		Produto prod = getProdutos()[pos];
		
		// Ache a filial que esse produto está.
		int pos_filial = new ControleFilial(this).acharFilial(prod);
		
		// Delete o produto.
		removido = farmacia.getlistaFiliais().get(pos_filial).deletarProduto(prod);
		
		return removido;
		
	}
	/**
	 * Remove uma filial específica da farmácia.
	 *
	 * @param pos A posição da filial na lista de filiais.
	 * @return true se a filial foi removida com sucesso, caso contrário, false.
	 */
	public boolean removerFilial(int pos) {
		// Remove uma filial específica da farmácia.
		boolean removido;
		Filial filial = getFiliais()[pos];
		
		// Remova a filial da farmácia.
		removido = farmacia.deletarFilial(filial);
		
		return removido;
	}
	/**
	 * Verifica se os dados fornecidos são válidos.
	 *
	 * @param dados Os dados a serem verificados.
	 * @param op A operação a ser realizada (1 - Checar os dados de um produto, 2 - Checar os dados de uma filial).
	 * @return true se os dados são válidos, caso contrário, false.
	 */
	public boolean checarDados(String[] dados, int op) {
		// Checa os dados de um produto.
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		
		if (op == 1) { // Checar os dados de um produto.
			
			if (dados[2].isEmpty()) return false;
			if (dados[3].isEmpty()) return false;
			if (dados[6].isEmpty()) return false;
			
			try {
				Double.parseDouble(dados[4]); // Peso
				Double.parseDouble(dados[5]); // Preço
			} catch (NumberFormatException e) {
				return false;
			}
			
			try {
				formato.parse(dados[7]);
			} catch (ParseException e) {
				return false;
			}
			
		}
		
		if (op == 2) { // Checar os dados de uma filial.
			
			if (dados[0].isEmpty()) return false;
			if (dados[1].isEmpty()) return false;
			if (dados[2].isEmpty()) return false;
			if (dados[3].isEmpty()) return false;
			if (dados[4].isEmpty()) return false;
			if (dados[5].isEmpty()) return false;
			
		}
		
		return true;
	}
	
}
