package com.example.finalassingment.global;
/**
 * @author Group 11
 */
public class ClaimQueryType {
    public QueryType queryType;
    public enum QueryType {
        GET_ALL,
        GET_ALL_OF_DEPENDANT,
        GET_ALL_OF_POLICY_HOLDER,
        GET_ALL_NEW,
        GET_ALL_PROCESSING,
        GET_ALL_DONE,
        GET_ALL_OF_POLICYHOLDER_AND_THEIR_DEPENDANTS,
    }
}
