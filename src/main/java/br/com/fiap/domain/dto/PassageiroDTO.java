package br.com.fiap.domain.dto;

import br.com.fiap.Main;
import br.com.fiap.domain.entity.Passageiro;
import br.com.fiap.domain.entity.transportavel.Passageiro;
import br.com.fiap.domain.service.PassageiroService;
import br.com.fiap.infra.security.dto.PessoaFisicaDTO;
import br.com.fiap.infra.security.entity.PessoaFisica;
import br.com.fiap.infra.security.service.PessoaFisicaService;

import java.util.Objects;

public record PassageiroDTO(Long id, String tipo, PessoaFisicaDTO pessoa) {

    private static PassageiroService service = PassageiroService.build(Main.PERSISTENCE_UNIT);

    private static PessoaFisicaService pfService = PessoaFisicaService.of(Main.PERSISTENCE_UNIT);

    public static Passageiro of(PassageiroDTO dto) {
        Passageiro p = new Passageiro();

        p.setTipo(dto.tipo);

        if (Objects.isNull(dto)) return null;

        if (Objects.nonNull(dto.id)) {
            p = service.findById(dto.id);
        }

        if (Objects.nonNull(dto.pessoa.id())) {
            PessoaFisica pf = PessoaFisicaDTO.of(dto.pessoa);
            if (Objects.isNull(pf)) return null;
            p.setPessoa(pf);
            return p;
        }



        return p;
    }

    public static PassageiroDTO of(Passageiro p) {
        return new PassageiroDTO(p.getId(), p.getTipo(), PessoaFisicaDTO.of(p.getPessoa()));
    }


}
