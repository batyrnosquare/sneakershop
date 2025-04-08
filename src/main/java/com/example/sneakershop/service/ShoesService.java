package com.example.sneakershop.service;

import com.example.sneakershop.model.FilterShoes;
import com.example.sneakershop.model.Shoes;
import com.example.sneakershop.model.Sizes;
import com.example.sneakershop.repository.ShoesRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ShoesService {

    private final ShoesRepository shoesRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public ShoesService(ShoesRepository shoesRepository) {
        this.shoesRepository = shoesRepository;
    }

    public Shoes save(Shoes shoes) {
        shoesRepository.save(shoes);
        return shoes;
    }

    public Shoes findById(Long id) {
        return shoesRepository.findById(id).orElse(null);
    }

    public List<Shoes> findAll(FilterShoes filterBy, String sortBy) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Shoes> cq = cb.createQuery(Shoes.class);
        Root<Shoes> root = cq.from(Shoes.class);
        List<Predicate> predicates = new ArrayList<>();
        log.info(predicates.toString());
        Join<Shoes, Sizes> sizesJoin =  root.join("sizes", JoinType.INNER);
        if (filterBy.getBrand() != null && !filterBy.getBrand().isEmpty()) {
            predicates.add(root.get("brand").in(filterBy.getBrand()));
        }
        log.info("Filter by brand: {}", filterBy.getBrand());
        log.info(predicates.toString());

        if (filterBy.getColor() != null && !filterBy.getColor().isEmpty()) {
            predicates.add(root.get("color").in(filterBy.getColor()));
        }
        if (filterBy.getMaterial() != null && !filterBy.getMaterial().isEmpty()) {
            predicates.add(root.get("material").in(filterBy.getMaterial()));
        }
        if (filterBy.getPriceFrom() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("price"), filterBy.getPriceFrom()));
        }
        if (filterBy.getPriceTo() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("price"), filterBy.getPriceTo()));
        }
        if (filterBy.getSize() != null && !filterBy.getSize().isEmpty()) {
            predicates.add(sizesJoin.get("size").in(filterBy.getSize()));
        }
        cq.where(predicates.toArray(new Predicate[0]));

        if (sortBy != null) {
            switch (sortBy) {
                case "price_asc" -> cq.orderBy(cb.asc(root.get("price")));
                case "price_desc" -> cq.orderBy(cb.desc(root.get("price")));
                case "name_asc" -> cq.orderBy(cb.asc(root.get("name")));
                case "name_desc" -> cq.orderBy(cb.desc(root.get("name")));
            }
        }

        TypedQuery<Shoes> query = entityManager.createQuery(cq);
        return query.getResultList();
    }

    public Shoes update(Shoes shoes) {
        return shoesRepository.save(shoes);
    }

    public void delete(Long id) {
        shoesRepository.deleteById(id);
    }
}
