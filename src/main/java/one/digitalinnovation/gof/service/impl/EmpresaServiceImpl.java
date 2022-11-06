package one.digitalinnovation.gof.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import one.digitalinnovation.gof.model.Empresa;
import one.digitalinnovation.gof.model.EmpresaRepository;
import one.digitalinnovation.gof.model.Endereco;
import one.digitalinnovation.gof.model.EnderecoRepository;
import one.digitalinnovation.gof.service.EmpresaService;
import one.digitalinnovation.gof.service.ViaCepService;

/**
 * Implementação da <b>Strategy</b> {@link EmpresaService}, a qual pode ser
 * injetada pelo Spring (via {@link Autowired}). Com isso, como essa classe é um
 * {@link Service}, ela será tratada como um <b>Singleton</b>.
 * 
 * @author rodrigoapolo
 */
@Service
public class EmpresaServiceImpl implements EmpresaService {

	// Singleton: Injetar os componentes do Spring com @Autowired.
	@Autowired
	private EmpresaRepository empresaRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private ViaCepService viaCepService;
	
	// Strategy: Implementar os métodos definidos na interface.
	// Facade: Abstrair integrações com subsistemas, provendo uma interface simples.

	@Override
	public Iterable<Empresa> buscarTodos() {
		// Buscar todos os Empresas.
		return empresaRepository.findAll();
	}

	@Override
	public Empresa buscarPorId(Long id) {
		// Buscar Empresa por ID.
		Optional<Empresa> empresa = empresaRepository.findById(id);
		return empresa.get();
	}

	@Override
	public void inserir(Empresa empresa) {
		salvarEmpresaComCep(empresa);
	}

	@Override
	public void atualizar(Long id, Empresa empresa) {
		// Buscar Empresa por ID, caso exista:
		Optional<Empresa> empresaBd = empresaRepository.findById(id);
		if (empresaBd.isPresent()) {
			salvarEmpresaComCep(empresa);
		}
	}

	@Override
	public void deletar(Long id) {
		// Deletar Empresa por ID.
		empresaRepository.deleteById(id);
	}

	private void salvarEmpresaComCep(Empresa empresa) {
		// Verificar se o Endereco do Empresa já existe (pelo CEP).
		String cep = empresa.getEndereco().getCep();
		Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
			// Caso não exista, integrar com o ViaCEP e persistir o retorno.
			Endereco novoEndereco = viaCepService.consultarCep(cep);
			enderecoRepository.save(novoEndereco);
			return novoEndereco;
		});
		empresa.setEndereco(endereco);
		// Inserir Empresa, vinculando o Endereco (novo ou existente).
		empresaRepository.save(empresa);
	}

}
