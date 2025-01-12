package bassi.itau_unibanco.exerc5_itau_unibanco.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

/**
 * Classe base para exceções personalizadas no microsserviço de Produto.
 *
 * Segue o princípio OCP do SOLID, pois está aberta para extensão, permitindo
 * a criação de novas exceções específicas, mas fechada para modificações.
 * Também segue o LSP, pois qualquer exceção derivada pode ser usada
 * onde a classe ProdutoException é esperada, garantindo substituição consistente.
 */
@NoArgsConstructor
public class ProdutoException extends RuntimeException {

    public ProdutoException(String message) {
        super(message);
    }

    /**
     * Converte a exceção para um formato padronizado de Problem Detail.
     * Segue o contrato comum para todas as exceções derivadas.
     *
     * @return um objeto ProblemDetail representando o erro
     */
    public ProblemDetail toProblemDetail() {
        var problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setTitle("Microsserviço Produto internal server error.");
        return problemDetail;
    }
}
