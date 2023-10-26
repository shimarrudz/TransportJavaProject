package br.com.fiap.domain.dto;

import br.com.fiap.Main;
import br.com.fiap.domain.entity.Endereco;
import br.com.fiap.domain.service.EnderecoService;
import br.com.fiap.infra.security.dto.PessoaDTO;

public record EnderecoDTO(PessoaDTO pessoa, String cep, String numero, String complemento) {

    static EnderecoService service = EnderecoService.build(Main.PERSISTENCE_UNIT);

    public static Endereco of(EnderecoDTO e) {
        Endereco endereco = service.findByCEP(e.cep);
        endereco.setComplemento(e.complemento).setNumero(e.numero);
        var p = PessoaDTO.of(e.pessoa);
        endereco.setPessoa(p);
        return endereco;
    }

    public static EnderecoDTO of(Endereco e) {
        var pessoa = PessoaDTO.of(e.getPessoa());
        return new EnderecoDTO(pessoa, e.getCep(), e.getNumero(), e.getComplemento());
    }

}
