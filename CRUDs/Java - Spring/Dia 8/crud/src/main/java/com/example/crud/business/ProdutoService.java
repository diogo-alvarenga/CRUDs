package com.example.crud.business;

import org.springframework.stereotype.Service;

import com.example.crud.structure.entity.Produto;
import com.example.crud.structure.repository.ProdutoRepository;

@Service
public class ProdutoService {

	private final ProdutoRepository repository;
	
	public ProdutoService(ProdutoRepository repository) {
		this.repository = repository;
	}
	
	public Produto adicionarProduto(Produto produto) {
		if(produto.getQuantidade()>0) {
			produto.setDisponivel(true);
		}else {
			produto.setDisponivel(false);
		}
		return repository.saveAndFlush(produto);
	}
	
	public Produto buscarProduto(Integer id) {
		return repository.findById(id).orElseThrow(
				() -> new RuntimeException("Id não encontrado"));
	}
	
	public void deletarProduto(Integer id) {
		repository.deleteById(id);
	}
	
	public Produto atualizarProduto(Produto produto, Integer id) {
		Produto produtoEntity = buscarProduto(id);
		
		Produto produtoAtualizado = Produto.builder()
				.nome(produto.getNome()!= null? produto.getNome(): produtoEntity.getNome())
				.quantidade(produto.getQuantidade()!= null? produto.getQuantidade(): produtoEntity.getQuantidade())
				.disponivel(produto.isDisponivel())
				.id(produtoEntity.getId())
				.build();
		
		return repository.saveAndFlush(produtoAtualizado);
	}
}
