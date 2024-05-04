package org.example.repository.impl;

import jakarta.persistence.TypedQuery;
import org.example.model.items.InsuranceCard;
import org.example.repository.EntityRepository;
import org.example.repository.IInsuranceCardRepository;

import java.util.List;

public class InsuranceCardRepository extends EntityRepository implements IInsuranceCardRepository {

    @Override
    public void add(InsuranceCard insuranceCard) {
        em.getTransaction().begin();
        em.persist(insuranceCard);
        em.getTransaction().commit();
    }

    @Override
    public void add(InsuranceCard... insuranceCards) {
        em.getTransaction().begin();
        for (InsuranceCard c : insuranceCards) {
            em.persist(c);
        }
        em.getTransaction().commit();
    }

    @Override
    public InsuranceCard findByID(String id) {
        return em.find(InsuranceCard.class, id);
    }

    @Override
    public List<InsuranceCard> getAll() {
        TypedQuery<InsuranceCard> query = em.createQuery("from InsuranceCard ", InsuranceCard.class);
        return query.getResultList();
    }

    @Override
    public void update(InsuranceCard insuranceCard) {
        InsuranceCard insuranceCardToUpdate = findByID(insuranceCard.getCardNumber());

        em.getTransaction().begin();

        insuranceCardToUpdate.setCardNumber(insuranceCard.getCardNumber());
        insuranceCardToUpdate.setCardHolder(insuranceCard.getCardHolder());
        insuranceCardToUpdate.setPolicyOwner(insuranceCard.getPolicyOwner());
        insuranceCardToUpdate.setExpireDate(insuranceCard.getExpireDate());

        em.getTransaction().commit();
    }

    @Override
    public InsuranceCard removeByID(String id) {
        em.getTransaction().begin();

        InsuranceCard insuranceCardToRemove = em.find(InsuranceCard.class, id);
        em.remove(insuranceCardToRemove);

        em.getTransaction().commit();
        return insuranceCardToRemove;
    }

    @Override
    public void close() {
        em.close();
    }


}
