package com.prgrms.kdt.springbootbasic.repository;

import com.prgrms.kdt.springbootbasic.entity.voucher.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Profile("local")
@Repository
public class MemoryVoucherRepository implements VoucherRepository{
    private Map<UUID, Voucher> voucherStorage = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        if (voucherStorage.containsKey(voucherId)) {
            return Optional.of(voucherStorage.get(voucherId));
        }else{
            return Optional.empty();
        }
    }

    @Override
    public Optional<Voucher> findByTypeAndAmount(String voucherType, long amount) {
        return Optional.ofNullable(voucherStorage.values().stream()
                .filter(voucher -> voucher.getVoucherType().equals(voucherType) && voucher.getDiscountAmount() == amount)
                .collect(Collectors.toList()).get(0));
    }

    @Override
    public Optional<Voucher> saveVoucher(Voucher voucher){
        voucherStorage.put(voucher.getVoucherId(), voucher);
        return Optional.of(voucher);
    }

    @Override
    public List<Voucher> getAllVouchers(){
        return voucherStorage.values().stream().collect(Collectors.toList());
    }

    @Override
    public Optional<Voucher> updateVoucherAmount(Voucher voucher) {
        var foundVoucher = findById(voucher.getVoucherId());
        if (foundVoucher.isEmpty())
            return Optional.empty();

        foundVoucher.get().setAmount(voucher.getDiscountAmount());
        return Optional.of(foundVoucher.get());
    }

    @Override
    public boolean deleteVoucher(Voucher voucher) {
        var foundVoucher = findById(voucher.getVoucherId());
        if (foundVoucher.isEmpty())
            return false;

        voucherStorage.remove(foundVoucher.get().getVoucherId());
        return true;
    }
}
