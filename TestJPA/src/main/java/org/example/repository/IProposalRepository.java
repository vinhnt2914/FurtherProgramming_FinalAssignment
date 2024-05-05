package org.example.repository;

import org.example.model.items.Proposal;
import org.example.model.provider.Provider;

import java.util.List;

public interface IProposalRepository {
    void add(Proposal proposal);
    void add(Proposal... proposals);
    Proposal findByID(int id);
    List<Proposal> getAll();
    Proposal removeByID(int id);
}
