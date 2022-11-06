package one.digitalinnovation.gof.service;

import one.digitalinnovation.gof.model.Empresa;

/**
 * Interface que define o padrão <b>Strategy</b> no domínio de empresa. Com
 * isso, se necessário, podemos ter multiplas implementações dessa mesma
 * interface.
 * 
 * @author rodrigoapolo
 */
public interface EmpresaService {

	Iterable<Empresa> buscarTodos();

	Empresa buscarPorId(Long id);

	void inserir(Empresa empresa);

	void atualizar(Long id, Empresa empresa);

	void deletar(Long id);

}
