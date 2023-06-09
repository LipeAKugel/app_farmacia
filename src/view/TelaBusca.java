package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import controle.ControleFarmacia;
import controle.ControleFilial;
import controle.ControleProduto;
/**
 * A classe TelaBusca é responsável por exibir uma janela de busca na interface gráfica
 * da farmácia. Ela permite pesquisar produtos por nome, pesquisar filiais por cidade
 * e listar todos os produtos ou filiais.
 * @author Felipe Amorim e João Vitor
 * @since 2023
 * @version 1.0
 */
public class TelaBusca implements ActionListener {
	private static JFrame janela;
	private static ControleFarmacia dados;
	private JLabel titulo;
	private JLabel labelBusca;
	private JTextField busca;
	private JButton pesquisarProduto;
	private JButton pesquisarFilial;
	private JButton listarTodos;
	private JList<String> listaProdutos;
	private JList<String> listaFiliais;
	private String[] nomes_p = new String[50];
	private String[] nomes_f = new String[50];
	
	/**
	 * Construtor da classe TelaBusca. Cria uma nova instância da janela de busca.
	 *
	 * @param op    A opção de busca (1 para busca de produtos, 2 para busca de filiais).
	 * @param dados Uma instância de ControleFarmacia contendo os dados da farmácia.
	 */
	public TelaBusca(int op, ControleFarmacia dados) {
		// Resete a janela.
		if (janela != null) {
			janela.dispose();
		}
		this.dados = dados;
		
		// Dimensões da janela.
		int j_comp = 500;
		int j_larg = 500;
		int l_comp = 420;
		int l_larg = 290;
		
		// Criar os elementos da tela.
		janela = new JFrame("Pesquisar");
		busca = new JTextField(100);
		
		// Sete as dimensões.
		janela.setSize(j_comp, j_larg);
		busca.setBounds(140,70,200,20);
		
		// Adicione os elementos e mostre a janela.
		janela.add(busca);
		
		janela.setLayout(null);
		janela.setVisible(true);
		janela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		// Crie os elementos dependendo da config de tela.
		switch (op) {
			case 1: // Busca de produtos.
				
				// Crie os elementos da tela.
				labelBusca = new JLabel("Busca por nome:");
				pesquisarProduto = new JButton("Pesquisar");
				listaProdutos = new JList<String>(nomes_p);
				listarTodos = new JButton("Listar Todos");
				
				// Label.
				titulo = new JLabel("Pesquisar produtos");
				titulo.setFont(new Font("Arial", Font.BOLD, 30));
				titulo.setBounds(100, 20, 400, 40);
				janela.add(titulo);
				
				// Sete as dimensões.
				labelBusca.setBounds(30,69,120,20);
				pesquisarProduto.setBounds(340, 70, 110, 20);
				listaProdutos.setBounds(30, 110, l_comp, l_larg);
				listarTodos.setBounds(195, 410, 110, 30);
				
				// Adicione os ActionListener.
				pesquisarProduto.addActionListener(this);
				listarTodos.addActionListener(this);
				
				// Adicione os elementos para a janela.
				janela.add(labelBusca);
				janela.add(pesquisarProduto);
				janela.add(listaProdutos);
				janela.add(listarTodos);
				
			break;
			
			case 2: // Busca de filiais.
				
				// Crie os elementos da tela.
				labelBusca = new JLabel("Busca por cidade:");
				pesquisarFilial = new JButton("Pesquisar");
				listaFiliais = new JList<String>(nomes_f);
				
				// Label.
				titulo = new JLabel("Pesquisar filiais");
				titulo.setFont(new Font("Arial", Font.BOLD, 30));
				titulo.setBounds(120, 20, 400, 40);
				janela.add(titulo);
				
				// Adicione os ActionListener.
				pesquisarFilial.addActionListener(this);
				
				// Sete as dimensões.
				labelBusca.setBounds(30,69,120,20);
				pesquisarFilial.setBounds(340, 70, 110, 20);
				listaFiliais.setBounds(30, 110, l_comp, l_larg);
				
				// Adicione os elementos para a janela.
				janela.add(labelBusca);
				janela.add(pesquisarFilial);
				janela.add(listaFiliais);
				
			break;
			
		}
	}

	/**
	 * Método que é acionado quando ocorre uma ação nos botões da janela.
	 * É responsável por executar as ações correspondentes às ações dos botões.
	 *
	 * @param e O evento de ação que ocorreu.
	 */
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		
		if (src == listarTodos) {
			nomes_p = new ControleProduto(dados).getNomeProdutos();
			listaProdutos.setListData(nomes_p);
		}
			
		if (src == pesquisarProduto) {
			String nome = busca.getText();
			nomes_p = new ControleProduto(dados).getNomeFiltrado(nome);
			listaProdutos.setListData(nomes_p);
		}
			
		if (src == pesquisarFilial) {
			String cidade = busca.getText();
			nomes_f = new ControleFilial(dados).getNomeFiltrado(cidade);
			listaFiliais.setListData(nomes_f);
		}
	}

}
