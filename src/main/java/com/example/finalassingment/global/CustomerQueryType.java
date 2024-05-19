package com.example.finalassingment.global;

public class CustomerQueryType {
    public QueryType queryType;
    public enum QueryType {
        GET_ALL,
        GET_ALL_DEPENDANT,
        GET_ALL_DEPENDANT_OF_POLICY_HOLDER,
        GET_ALL_DEPENDANT_OF_POLICY_OWNER,
        GET_ALL_POLICY_HOLDER,
        GET_ALL_POLICY_HOLDER_OF_POLICY_OWNER,
        GET_ALL_DEPENDANT_AND_POLICY_HOLDER,
        GET_ALL_BENEFICIARY_OF_POLICY_OWNER,
        GET_ALL_POLICY_OWNER
    }
}
