package com.williamblondel.infinitrade.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

public interface CustomRepresentationModelAssembler<T, D extends EntityModel<T>> extends RepresentationModelAssembler<T, D> {

    D toModel(T entity, Boolean includeAll);
}
