package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import controle.ControleFarmacia;
/**
 * Classe responsável por exibir o menu inicial da aplicação.
 * @author Felipe Amorim e João Vitor
 * @since 2023
 * @version 1.0
 */
public class TelaMenu implements ActionListener {
	
	// Crie o container JFrame.
	TelaBusca telaBusca;
	private static JFrame janela = new JFrame("Menu Inicial");
	private static JLabel titulo = new JLabel("Menu Inicial");
	private static JButton buscaProd = new JButton("Buscar Produto");
	private static JButton buscaFilial = new JButton("Buscar Filial");
	private static JButton editarProduto = new JButton("Editar Produto");
	private static JButton editarFilial = new JButton("Editar Filial");
	private static ControleFarmacia dados = new ControleFarmacia();
	
	/**
     * Construtor da classe TelaMenu.
     * Cria e exibe a janela do menu inicial.
     */
	public TelaMenu() {
		
		// Set as bounds.
		int j_comp = 500;
		int j_larg = 500;
		int b_comp = 200;
		int b_larg = 40;
		int sep = 60;
		
		int x_inicial = 150;
		int y_inicial = 180;
		
		titulo.setBounds(135, 50, 300, 100);
		titulo.setFont(new Font("Arial", Font.BOLD, 40));
		
		buscaProd.setBounds(x_inicial,y_inicial,b_comp,b_larg);
		buscaFilial.setBounds(x_inicial,y_inicial+sep,b_comp,b_larg);
		editarProduto.setBounds(x_inicial,y_inicial+sep*2,b_comp,b_larg);
		editarFilial.setBounds(x_inicial,y_inicial+sep*3,b_comp,b_larg);
		
		buscaProd.addActionListener(this);
		buscaFilial.addActionListener(this);
		editarFilial.addActionListener(this);
		editarProduto.addActionListener(this);
		
		janela.setSize(j_comp, j_larg);
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Adicione e mostre a janela.
		janela.add(titulo);
		janela.add(buscaProd);
		janela.add(buscaFilial);
		janela.add(editarFilial);
		janela.add(editarProduto);
		
		janela.setLayout(null);
		janela.setVisible(true);
		
	}
	/**
     * Método chamado quando um botão é clicado.
     * 
     * @param e O evento de ação.
     */
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		
		if (src == buscaProd) {
			new TelaBusca(1, dados);
		}
		if (src == buscaFilial) {
			new TelaBusca(2, dados);
		}
		if (src == editarProduto) {
			new TelaEditar().mostrarDados(1,dados);
		}
		if (src == editarFilial) {
			new TelaEditar().mostrarDados(2,dados);
		}
	}
	/**
     * Método principal para iniciar o programa.
     * 
     * @param args Argumentos de linha de comando.
     */
	public static void main(String args[]) {
		TelaMenu menu = new TelaMenu();
	}
}
