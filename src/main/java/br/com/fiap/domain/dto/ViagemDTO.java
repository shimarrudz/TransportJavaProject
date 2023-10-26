package br.com.fiap.domain.dto;

import br.com.fiap.Main;
import br.com.fiap.domain.entity.Endereco;
import br.com.fiap.domain.entity.Transportavel;
import br.com.fiap.domain.entity.Viagem;
import br.com.fiap.domain.service.EnderecoService;
import br.com.fiap.domain.service.PacoteService;
import br.com.fiap.domain.service.PassageiroService;
import br.com.fiap.domain.service.ViagemService;
import br.com.fiap.infra.security.dto.PessoaDTO;
import br.com.fiap.infra.security.service.PessoaFisicaService;
import br.com.fiap.infra.security.service.PessoaJuridicaService;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

public record ViagemDTO(
        Long id,
        PessoaDTO cliente,
        LocalDateTime saida,
        LocalDateTime chegada,
        Endereco origem,
        Endereco destino,
        Set<? extends Transportavel> transportaveis
) {

    private static ViagemService service = ViagemService.build(Main.PERSISTENCE_UNIT);
    private static PessoaFisicaService pfService = PessoaFisicaService.of(Main.PERSISTENCE_UNIT);
    private static PessoaJuridicaService pjService = PessoaJuridicaService.of(Main.PERSISTENCE_UNIT);
    private static PassageiroService passageiroService = PassageiroService.build(Main.PERSISTENCE_UNIT);
    private static PacoteService pacoteService = PacoteService.build(Main.PERSISTENCE_UNIT);

    private static EnderecoService enderecoService = EnderecoService.build(Main.PERSISTENCE_UNIT);


    public static Viagem of(ViagemDTO dto) {

        Viagem p = new Viagem();

        if (Objects.isNull(dto)) return null;

        if (Objects.isNull(dto.cliente.id())) return null;

        if (Objects.nonNull(dto.id)) return service.findById(dto.id);

        if (Objects.isNull(dto.origem.getCep())) return null;

        if (Objects.isNull(dto.destino.getCep())) return null;

        if (dto.cliente.tipo().equalsIgnoreCase("PF")) {
            var pessoa = pfService.findById(dto.cliente.id());
            if (Objects.isNull(pessoa)) return null;
            p.setCliente(pessoa);
        } else {
            var pessoa = pjService.findById(dto.cliente.id());
            if (Objects.isNull(pessoa)) return null;
            p.setCliente(pessoa);
        }

        // var origem = enderecoService.findByCEP(dto.origem.getCep());
        p.setOrigem(dto.origem.getCep());

        // var destino = enderecoService.findByCEP(dto.origem.getCep());
        p.setDestino(dto.destino.getCep());


        if (Objects.nonNull(dto.transportaveis) && dto.transportaveis().size() > 0) {
            dto.transportaveis.forEach(t -> {
                if (t.getTipo().equalsIgnoreCase("PASSAGEIRO")) {
                    p.addTransportavel(passageiroService.findById(t.getId()));
                } else {
                    p.addTransportavel(pacoteService.findById(t.getId()));
                }
            });
        }

        return p;
    }

    public static ViagemDTO of(Viagem v) {
        return new ViagemDTO(v.getId(), PessoaDTO.of(v.getCliente()), v.getSaida(), v.getChegada(), v.getOrigem(), v.getDestino(), v.getTransportaveis());
    }

}
