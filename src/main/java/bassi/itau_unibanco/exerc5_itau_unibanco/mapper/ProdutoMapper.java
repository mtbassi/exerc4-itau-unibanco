package bassi.itau_unibanco.exerc5_itau_unibanco.mapper;

import bassi.itau_unibanco.exerc5_itau_unibanco.dto.ProdutoRequest;
import bassi.itau_unibanco.exerc5_itau_unibanco.dto.ProdutoResponse;
import bassi.itau_unibanco.exerc5_itau_unibanco.entity.ProdutoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Interface responsável por mapear objetos entre ProdutoRequest, ProdutoEntity e ProdutoResponse.
 *
 * Segue o princípio SRP ao centralizar a lógica de conversão, promovendo organização
 * e separação de responsabilidades.
 *
 * Usa o MapStruct para geração automática de código eficiente em tempo de compilação.
 */
@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    @Mapping(target = "id", ignore = true)
    ProdutoEntity mapToProdutoEntity(ProdutoRequest data);

    ProdutoResponse mapToProdutoResponse(ProdutoEntity data);

    @Mapping(target = "id", ignore = true)
    void mapToProdutoEntity(ProdutoRequest produtoRequest, @MappingTarget ProdutoEntity produtoAtual);
}
