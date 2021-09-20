package org.programmers.applicationcontext.execute;

import org.programmers.applicationcontext.OutPutView;
import org.programmers.applicationcontext.voucher.Voucher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.BufferedReader;
import java.util.List;

public class ListExecute implements Execute {

    @Override
    public boolean execute(List<Voucher> voucherList,
                           AnnotationConfigApplicationContext commandLineContext,
                           BufferedReader br, OutPutView outPutView) {
        outPutView.showAllVouchers(voucherList);
        return true;
    }
}