package com.workwise.workwisebackend.support.utils;

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

    public static final String FIND_APPLICATIONS_FOR_COMPANY = "SELECT a.* FROM applications a JOIN job_offers jo ON a.job_offer_id = jo.id WHERE jo.company_id = :companyId";

}

