package com.hilfritz.mvp.api.psi;


import com.hilfritz.mvp.api.psi.pojo.PsiPojo;

import retrofit2.http.GET;
import rx.Observable;


/**
 * Created by Hilfritz P. Camallere on 21/4/2018.
 */

public interface PsiRestInterface {
    @GET(PsiRestApi.PSI_URL)
    Observable<PsiPojo> getAllPsi();
}
