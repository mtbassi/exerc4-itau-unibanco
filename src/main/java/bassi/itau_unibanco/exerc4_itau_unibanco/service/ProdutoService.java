package bassi.itau_unibanco.exerc4_itau_unibanco.service;

import bassi.itau_unibanco.exerc4_itau_unibanco.dto.ProdutoRequest;
import bassi.itau_unibanco.exerc4_itau_unibanco.dto.ProdutoResponse;
import bassi.itau_unibanco.exerc4_itau_unibanco.entity.ProdutoEntity;
import bassi.itau_unibanco.exerc4_itau_unibanco.exception.ProdutoNaoEncontradoException;
import bassi.itau_unibanco.exerc4_itau_unibanco.mapper.ProdutoMapper;
import bassi.itau_unibanco.exerc4_itau_unibanco.producer.CadastroProdutoProducer;
import bassi.itau_unibanco.exerc4_itau_unibanco.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Classe de serviço responsável pelas operações de negócio relacionadas a produtos.
 * <p>
 * Segue o princípio SRP (Single Responsibility Principle) ao manter uma única responsabilidade:
 * gerenciar a lógica de negócios do domínio de produtos.
 * <p>
 * As dependências injetadas respeitam o DIP (Dependency Inversion Principle), permitindo
 * desacoplamento e facilidade para testes unitários ou substituições de implementação.
 */
@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository repository;

    private final ProdutoMapper mapper;

    private final CadastroProdutoProducer producer;

    @Transactional(readOnly = true)
    public List<ProdutoResponse> listar() {
        return this.repository.findAll()
                .stream()
                .map(this.mapper::mapToProdutoResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProdutoResponse listarPeloId(UUID id) {
        return this.repository.findById(id)
                .map(this.mapper::mapToProdutoResponse)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(id));
    }

    @Transactional(readOnly = true)
    public List<ProdutoResponse> listagemPersonalizada(String nome, BigDecimal preco, String categoria) {
        return this.repository.listagemPersonalizada(nome, preco, categoria)
                .stream()
                .map(this.mapper::mapToProdutoResponse)
                .toList();
    }

    @Transactional
    public ProdutoResponse cadastrar(ProdutoRequest produtoRequest) {
        var entity = this.repository.save(this.mapper.mapToProdutoEntity(produtoRequest));
        this.producer.sendMessage(entity);
        return this.mapper.mapToProdutoResponse(entity);
    }

    @Transactional
    public ProdutoResponse atualizar(UUID id, ProdutoRequest produtoRequest) {
        var entity = this.repository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(id));
        this.atualizar(produtoRequest, entity);
        this.repository.save(entity);
        return this.mapper.mapToProdutoResponse(entity);
    }

    private void atualizar(ProdutoRequest produtoRequest, ProdutoEntity produtoAtual) {
        this.mapper.mapToProdutoEntity(produtoRequest, produtoAtual);
    }

    @Transactional
    public void deletar(UUID id) {
        this.repository.findById(id).ifPresentOrElse(
                this.repository::delete,
                () -> {
                    throw new ProdutoNaoEncontradoException(id);
                }
        );
    }
}
