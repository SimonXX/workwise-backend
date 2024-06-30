package com.workwise.workwisebackend.support;

public final class Consts {


    public static final String FIND_AVAILABLE_JOB_OFFERS_QUERY = "SELECT jo.* FROM job_offers jo " +
            "WHERE NOT EXISTS (" +
            "   SELECT 1 FROM applications a " +
            "   WHERE jo.id = a.job_offer_id AND a.user_id = :userId" +
            ")";

    public static final String QUERY_FOR_PAGING = "SELECT COUNT(*) FROM job_offers jo " +
            "WHERE NOT EXISTS (" +
            "   SELECT 1 FROM applications a " +
            "   WHERE jo.id = a.job_offer_id AND a.user_id = :userId" +
            ")";
}
