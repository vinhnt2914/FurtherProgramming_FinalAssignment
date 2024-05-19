package com.example.finalassingment.repository;

import com.example.finalassingment.model.items.Proposal;

import java.util.List;

public interface IProposalRepository {
    void add(Proposal proposal);
    void add(Proposal... proposals);
    Proposal findByID(int id);
    List<Proposal> getAll();
    void update(Proposal proposal);
    Proposal removeByID(int id);
}
