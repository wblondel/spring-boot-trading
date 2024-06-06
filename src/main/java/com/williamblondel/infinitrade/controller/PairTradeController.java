package com.williamblondel.infinitrade.controller;

import com.williamblondel.infinitrade.assembler.TradeModelAssembler;
import com.williamblondel.infinitrade.exception.PairNotFoundException;
import com.williamblondel.infinitrade.exception.UserNotFoundException;
import com.williamblondel.infinitrade.model.Pair;
import com.williamblondel.infinitrade.model.Trade;
import com.williamblondel.infinitrade.model.User;
import com.williamblondel.infinitrade.repository.PairRepository;
import com.williamblondel.infinitrade.repository.UserRepository;
import com.williamblondel.infinitrade.requests.CreatePairTradeRequest;
import com.williamblondel.infinitrade.service.TradeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class PairTradeController {
    private final PairRepository pairRepository;
    private final TradeModelAssembler tradeModelAssembler;
    private final TradeService tradeService;
    private final UserRepository userRepository;

    public PairTradeController(PairRepository pairRepository, TradeModelAssembler tradeModelAssembler,
                               TradeService tradeService, UserRepository userRepository) {
        this.pairRepository = pairRepository;
        this.tradeModelAssembler = tradeModelAssembler;
        this.tradeService = tradeService;
        this.userRepository = userRepository;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping({"/pairs/{pairCode}/trades", "/pairs/{pairCode}/trades/"})
    public CollectionModel<EntityModel<Trade>> all(@PathVariable String pairCode) {
        Pair pair = pairRepository.findByPairCode(pairCode)
                .orElseThrow(() -> new PairNotFoundException(pairCode));

        List<EntityModel<Trade>> trades = pair.getTrades().stream()
                .map(tradeModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(
                trades,
                linkTo(methodOn(PairTradeController.class)
                        .all(pairCode)
                ).withSelfRel()
        );
    }
    // end::get-aggregate-root[]

    @PostMapping({"/pairs/{pairCode}/trades", "/pairs/{pairCode}/trades/"})
    ResponseEntity<?> create(@Valid @RequestBody CreatePairTradeRequest createPairTradeRequest, @PathVariable String pairCode) {
        Pair pair = pairRepository.findByPairCode(pairCode)
                .orElseThrow(EntityNotFoundException::new);

        // Transform request to Trade
        Trade tradeToProcess = createPairTradeRequest.toTrade();

        // Set the user. We assume the authenticated user is user 1.
        // Normally, the user comes from the session. In this case, we fetch it from the database.
        User user = userRepository.findById(1L)
                .orElseThrow(() -> new UserNotFoundException(1L));
        tradeToProcess.setUser(user);

        // Set the pair
        tradeToProcess.setPair(pair);

        // Process the trade, get the saved Trade in return
        Trade savedTrade = tradeService.create(tradeToProcess);

        // Return with the saved trade
        EntityModel<Trade> entityModel = tradeModelAssembler.toModel(savedTrade);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }
}
