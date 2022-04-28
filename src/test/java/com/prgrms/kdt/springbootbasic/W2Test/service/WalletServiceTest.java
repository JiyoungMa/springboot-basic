package com.prgrms.kdt.springbootbasic.W2Test.service;

import com.prgrms.kdt.springbootbasic.entity.Customer;
import com.prgrms.kdt.springbootbasic.entity.Wallet;
import com.prgrms.kdt.springbootbasic.entity.voucher.FixedAmountVoucher;
import com.prgrms.kdt.springbootbasic.entity.voucher.Voucher;
import com.prgrms.kdt.springbootbasic.repository.WalletRepository;
import com.prgrms.kdt.springbootbasic.service.WalletService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WalletServiceTest {

    WalletRepository walletRepository = mock(WalletRepository.class);

    WalletService walletService = new WalletService(walletRepository);

    Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
    Customer customer = new Customer(UUID.randomUUID(),"tester", "tester@gmail.com");
    Wallet wallet = new Wallet(UUID.randomUUID(),customer.getCustomerId(),voucher.getVoucherId());

    @Test
    void createWallet() {
        var createdWallet = walletService.createWallet(customer,voucher);

        assertThat(createdWallet).as("Wallet").isEqualToIgnoringGivenFields(wallet,"walletId");

    }

    @Test
    void checkWalletDuplicationExist(){
        //Given
        when(walletRepository.getWalletWithCustomerAndVoucher(wallet.getCustomerId(),wallet.getVoucherId())).thenReturn(Optional.of(wallet));

        //When
        var duplicationResult = walletService.checkWalletDuplication(wallet);

        //Then
        assertThat(duplicationResult).isTrue();
    }

    @Test
    void checkWalletDuplicationNotExist(){
        //Given
        var newWallet = new Wallet(UUID.randomUUID(),UUID.randomUUID(),UUID.randomUUID());
        when(walletRepository.getWalletWithCustomerAndVoucher(newWallet.getCustomerId(),newWallet.getVoucherId())).thenReturn(Optional.empty());

        //When
        var duplicationResult = walletService.checkWalletDuplication(newWallet);

        //Then
        assertThat(duplicationResult).isFalse();
    }

    @Test
    void saveWalletDuplicated() {
        //Given
        when(walletRepository.saveWallet(wallet)).thenReturn(Optional.empty());

        //When
        var savedWallet = walletService.saveWallet(wallet);

        //Then
        assertThat(savedWallet.isEmpty()).isTrue();
    }

    @Test
    void saveWalletNotDuplicated() {
        //Given
        var newWallet = new Wallet(UUID.randomUUID(),UUID.randomUUID(),UUID.randomUUID());
        when(walletRepository.getWalletWithCustomerAndVoucher(newWallet.getCustomerId(),newWallet.getVoucherId())).thenReturn(Optional.empty());
        when(walletRepository.saveWallet(newWallet)).thenReturn(Optional.of(newWallet));

        //When
        var savedWallet = walletService.saveWallet(newWallet);

        //Then
        assertThat(savedWallet.get()).as("Wallet").isEqualToComparingFieldByField(newWallet);
    }

    @Test
    void findWalletByVoucherId() {
        //Given
        List<Wallet> walletList = new ArrayList<>();
        walletList.add(wallet);
        when(walletRepository.findWalletByVoucherId(voucher.getVoucherId())).thenReturn(walletList);

        //When
        var foundWalletList = walletService.findWalletByVoucherId(voucher.getVoucherId());

        //Then
        assertThat(foundWalletList)
                .usingRecursiveFieldByFieldElementComparator()
                .hasSameElementsAs(walletList);
    }

    @Test
    void findWalletByCustomerId() {
        //Given
        List<Wallet> walletList = new ArrayList<>();
        walletList.add(wallet);
        when(walletRepository.findWalletByCustomerId(customer.getCustomerId())).thenReturn(walletList);

        //When
        var foundWalletList = walletService.findWalletByCustomerId(customer.getCustomerId());

        //Then
        assertThat(foundWalletList)
                .usingRecursiveFieldByFieldElementComparator()
                .hasSameElementsAs(walletList);
    }

    @Test
    void findVoucherByCustomerId() {
        //Given
        List<Voucher> voucherList = new ArrayList<>();
        voucherList.add(voucher);
        when(walletRepository.findVoucherByCustomerId(customer.getCustomerId())).thenReturn(voucherList);

        //When
        var foundVoucherList = walletService.findVoucherByCustomerId(customer.getCustomerId());

        //Then
        assertThat(foundVoucherList)
                .usingRecursiveFieldByFieldElementComparator()
                .hasSameElementsAs(voucherList);
    }

    @Test
    void findCustomerByVoucherId() {
        //Given
        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer);
        when(walletRepository.findCustomerByVoucherId(voucher.getVoucherId())).thenReturn(customerList);

        //When
        var foundCustomerList = walletService.findCustomerByVoucherId(voucher.getVoucherId());

        //Then
        assertThat(foundCustomerList)
                .usingRecursiveFieldByFieldElementComparator()
                .hasSameElementsAs(customerList);
    }

    @Test
    void getAllWallets() {
        //Given
        List<Wallet> walletList = new ArrayList<>();
        walletList.add(wallet);
        when(walletRepository.getAllWallets()).thenReturn(walletList);

        //When
        var returnWalletList = walletService.getAllWallets();

        //Then
        assertThat(returnWalletList)
                .usingRecursiveFieldByFieldElementComparator()
                .hasSameElementsAs(walletList);
    }
}