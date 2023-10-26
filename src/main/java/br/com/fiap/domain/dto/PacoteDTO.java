package br.com.fiap.domain.dto;

import br.com.fiap.Main;
import br.com.fiap.domain.entity.Pacote;
import br.com.fiap.domain.entity.service.PacoteService;

import java.util.Objects;

public record PacoteDTO(
        Long id,
        String tipo,
        String nome,
        String etiqueta,
        Float altura,
        Float largura,
        Float profundidade,
        Float peso) {

    private static PacoteService service = PacoteService.build(Main.PERSISTENCE_UNIT);

    public static Pacote of(PacoteDTO dto) {
        if (Objects.isNull(dto)) return null;
        if (Objects.nonNull(dto.id)) return service.findById(dto.id);
        Pacote p = new Pacote();
        p.setNome(dto.nome);
        p.setAltura(dto.altura);
        p.setLargura(dto.largura);
        p.setProfundidade(dto.profundidade);
        p.setPeso(dto.peso);
        p.setEtiqueta(dto.etiqueta);
        return p;
    }

    public static PacoteDTO of(Pacote p) {
        if (Objects.isNull(p)) return null;
        return new PacoteDTO(p.getId(), p.getTipo(), p.getNome(), p.getEtiqueta(), p.getAltura(), p.getLargura(), p.getProfundidade(), p.getPeso());
    }

}
