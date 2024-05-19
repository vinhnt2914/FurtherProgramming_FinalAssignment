package com.example.finalassingment.repository;

import com.example.finalassingment.model.items.Proposal;
import com.example.finalassingment.model.provider.InsuranceManager;

import java.util.List;

public interface IProposalRepository {
    void add(Proposal proposal);
    void add(Proposal... proposals);
    Proposal findByID(int id);
    List<Proposal> getAll();
    List<Proposal> getAllToManager(InsuranceManager manager);
    void update(Proposal proposal);
    Proposal removeByID(int id);
}
